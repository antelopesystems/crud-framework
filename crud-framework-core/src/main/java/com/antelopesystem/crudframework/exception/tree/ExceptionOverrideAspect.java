package com.antelopesystem.crudframework.exception.tree;

import com.antelopesystem.crudframework.exception.tree.core.ExceptionOverride;
import com.antelopesystem.crudframework.exception.tree.core.LogLevel;
import com.antelopesystem.crudframework.exception.tree.exception.RemoteException;
import com.antelopesystem.crudframework.exception.tree.exception.ServerException;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Aspect
public class ExceptionOverrideAspect {

	@Pointcut("@within(exceptionOverride)")
	public void typeLevel(ExceptionOverride exceptionOverride) {
	}

	@Pointcut("@annotation(exceptionOverride)")
	public void methodLevel(ExceptionOverride exceptionOverride) {
	}

	@Around("methodLevel(exceptionOverride) || typeLevel(exceptionOverride)")
	public Object invokeExceptionTreeAwareMethod(ProceedingJoinPoint pjp, ExceptionOverride exceptionOverride) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		if(method.getAnnotation(ExceptionOverride.class) != null) {
			exceptionOverride = method.getAnnotation(ExceptionOverride.class);
		}
		Logger log;
		boolean logStackTrace = exceptionOverride.logLevel() == LogLevel.Error || exceptionOverride.logStackTrace();

		if(exceptionOverride.loggerOverride().equals("")) {
			log = LoggerFactory.getLogger(method.getDeclaringClass());
		} else {
			log = LoggerFactory.getLogger(exceptionOverride.loggerOverride());
		}

		Object[] args = pjp.getArgs();

		try {
			return pjp.proceed(args);
		} catch(ServerException e) {
			if(e.getClass() != exceptionOverride.value()) {
				ServerException wrapper;
				if(exceptionOverride.value() == RemoteException.class) {
					wrapper = new RemoteException(e);
				} else {
					wrapper = exceptionOverride.value().newInstance();
				}

				wrapper
						.withDisplayMessage(e.getDisplayMessage())
						.withErrorCode(exceptionOverride.errorCode())
						.withLogLevel(e.getLogLevel())
						.logStackTrace(logStackTrace)
						.withNestedException(e);
				e = wrapper;
				e.setStackTrace(new StackTraceElement[]{
						Thread.currentThread().getStackTrace()[1]
				});
			}

			String argumentString = buildArgs(exceptionOverride.logArguments(), args);
			fillMetadata(e, argumentString, method.getName(), method.getDeclaringClass().getSimpleName());
			e.log(log);

			if(exceptionOverride.rethrow()) {
				throw e;
			}
		} catch(Exception e) {
			ServerException exception = ServerException.of(exceptionOverride.value());
			exception.setStackTrace(e.getStackTrace());
			String argumentString = buildArgs(exceptionOverride.logArguments(), args);
			fillMetadata(exception, argumentString, method.getName(), method.getDeclaringClass().getSimpleName());
			exception
					.withErrorCode(exceptionOverride.errorCode())
					.withNestedException(e)
					.logArgs(true);
			e.setStackTrace(new StackTraceElement[]{
					Thread.currentThread().getStackTrace()[1]
			});

			if(e instanceof NullPointerException) {
				exception
						.withLogLevel(LogLevel.Error)
						.logStackTrace(true)
						.withDisplayMessage("NullPointerException");
			} else {
				exception
						.withLogLevel(exceptionOverride.logLevel())
						.logStackTrace(logStackTrace)
						.withDisplayMessage(e.getMessage());
			}

			exception.log(log);

			if(exceptionOverride.rethrow()) {
				throw exception;
			}
		}

		return null;
	}

	private String buildArgs(boolean logArgs, Object[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		if(logArgs) {
			try {
				if(args == null || args.length == 0) {
					stringBuilder.append("<no args>");
				} else {
					for(Object arg : args) {
						ReflectionToStringBuilder builder = new ReflectionToStringBuilder(arg, new MultilineRecursiveToStringStyle());
						builder.setExcludeNullValues(true);
						stringBuilder.append(builder.toString());
					}

				}
			} catch(Exception ex) {
				stringBuilder.append("<failed args serialization: " + ex.getMessage() + ">");
			}
		} else {
			stringBuilder.append("<args omitted>");
		}
		return stringBuilder.toString();
	}

	private void fillMetadata(ServerException exception, String args, String method, String type) {
		try {
			setPrivateField(exception, "args", args);
			setPrivateField(exception, "method", method);
			setPrivateField(exception, "type", type);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void setPrivateField(ServerException exception, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
		Field field = ServerException.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(exception, fieldValue);
	}

}
