package com.antelopesystem.crudframework.web.controller;

import com.antelopesystem.crudframework.ro.PagingDTO;
import com.antelopesystem.crudframework.web.ro.ResultRO;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

	private Logger log = LoggerFactory.getLogger(getClass());

	private Map<Class, ErrorHandler> errorHandlers = new HashMap<>();

	private static final Map<Class, ErrorHandler> globalErrorHandlers = new HashMap<>();

	protected ResultRO wrapVoidResult(VoidResultRunnable runnable) {
		return wrapResult(() -> {
			runnable.run();
			return null;
		});
	}

	protected ResultRO wrapResult(ResultRunnable runnable) {
		ResultRO resultRO = new ResultRO();
		try {
			Object data = runnable.run();
			if(data instanceof ResultRO) {
				return (ResultRO) data;
			}
			if(data instanceof PagingDTO) {
				resultRO.setPaging(((PagingDTO) data).getPagingRO());
				resultRO.setResult(((PagingDTO) data).getData());
			} else {
				resultRO.setResult(data);
			}
		} catch(Exception e) {
			resultRO.setError(e.getMessage());
			resultRO.setSuccess(false);
			ErrorHandler errorHandler = getErrorHandlerForClass(e.getClass());
			if(errorHandler != null) {
				return errorHandler.run(resultRO, e);
			}
			log.error(e.getMessage(), e);
		}

		return resultRO;
	}

	protected final <T> void addErrorHandler(Class<T> clazz, ErrorHandler<T> errorHandler) {
		errorHandlers.put(clazz, errorHandler);
	}

	protected interface ResultRunnable {

		Object run();
	}

	protected interface VoidResultRunnable {

		void run();
	}

	public interface ErrorHandler<T> {

		ResultRO run(@NotNull ResultRO resultRO, @NotNull T originalException);
	}

	private ErrorHandler getErrorHandlerForClass(Class<?> clazz) {
		ErrorHandler errorHandler = errorHandlers.getOrDefault(
				clazz,
				globalErrorHandlers.getOrDefault(clazz, null)
		);
		return errorHandler;
	}

	public static <T> void addGlobalErrorHandler(Class<T> clazz, ErrorHandler<T> errorHandler) {
		globalErrorHandlers.put(clazz, errorHandler);
	}

}