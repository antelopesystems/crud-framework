package com.antelopesystem.crudframework.exception.tree.exception;

import com.antelopesystem.crudframework.exception.dto.ErrorField;
import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.exception.tree.core.LogLevel;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class ServerException extends RuntimeException {

	private List<String> codePrefixes = new ArrayList<>();

	private LogLevel logLevel = LogLevel.None;

	private ErrorCode errorCode = ErrorCode.GeneralError;

	private String displayMessage = "";

	private boolean logStackTrace = false;

	private boolean logArgs = false;

	private transient List<Logger> loggedOn = new ArrayList<>();

	private transient Throwable nestedException = null;

	// Modified via Reflection

	private String args = "";

	private String method = "";

	private String type = "";

	private List<ErrorField> errorFields = null;

	public ServerException() {
	}

	public ServerException withLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
		return this;
	}

	public ServerException withErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public ServerException withDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
		return this;
	}

	public ServerException logStackTrace(boolean logStackTrace) {
		this.logStackTrace = logStackTrace;
		return this;
	}

	public ServerException logArgs(boolean logArgs) {
		this.logArgs = logArgs;
		return this;
	}

	public ServerException withArgs() {
		this.logArgs = true;
		return this;
	}

	public ServerException omitStackTrace() {
		this.logStackTrace = false;
		return this;
	}

	public ServerException withStackTrace() {
		this.logStackTrace = true;
		return this;
	}

	public ServerException withNestedException(Throwable nestedException) {
		this.nestedException = nestedException;
		initCause(nestedException);
		return this;
	}

	public ServerException withErrorFields(List<ErrorField> errorFields) {
		this.errorFields = errorFields;
		return this;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public ServerException logInfo() {
		this.logLevel = LogLevel.Info;
		return this;
	}

	public ServerException logError() {
		this.logLevel = LogLevel.Error;
		return this;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getArgs() {
		return args;
	}

	public boolean isLogArgs() {
		return logArgs;
	}

	public boolean isLogStackTrace() {
		return logStackTrace;
	}

	public Throwable getNestedException() {
		return nestedException;
	}

	public List<ErrorField> getErrorFields() {
		return errorFields;
	}

	public final void log(Logger log) {
		if(loggedOn.contains(log)) {
			return;
		}

		switch(logLevel) {
			case Debug:
				log.debug(buildLogMessage(this, null, true));
				break;
			case Info:
				log.info(buildLogMessage(this, null, true));
				break;
			case Error:
				log.error(buildLogMessage(this, null, true));
				break;
			case Console:
				System.out.println(buildLogMessage(this, null, true));
				break;
			case None:
			default:
				// Return to avoid appending the logger
				return;

		}

		loggedOn.add(log);
	}

	protected String getPrefix() {
		return this.getClass().getSimpleName();
	}

	protected final void appendCodePrefix(int codePrefix) {
		codePrefixes.add(codePrefix + "");
	}

	public static <T extends ServerException> T of(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch(Exception e) {
			throw new RuntimeException("ServerException of type [ " + clazz.getSimpleName() + " ] doesn't have a public default constructor!");
		}
	}

	private String buildLogMessage(Throwable e, StringBuilder messageBuilder, boolean isRoot) {
		if(messageBuilder == null) {
			messageBuilder = new StringBuilder();
		}

		if(!isRoot) {
			messageBuilder.append("\nCaused By: ");
		}

		if(isLogStackTrace()) {
			messageBuilder.append(ExceptionUtils.getStackTrace(e));
		} else {
			messageBuilder.append(e.getMessage());
		}

		if(e instanceof ServerException) {
			ServerException serverException = (ServerException) e;
			if(serverException.getNestedException() != null) {
				buildLogMessage(serverException.getNestedException(), messageBuilder, false);
			}
		}

		return messageBuilder.toString();
	}

	@Override
	public String getMessage() {
		return getDisplayMessage();
	}

	public String getFullMessage() {
		List<String> tree = new ArrayList<>();
		tree.add(getClass().getSimpleName());

		Throwable nested = nestedException;
		while(nested != null) {
			Class clazz = nested.getClass();
			tree.add(clazz.getSimpleName());
			if(nested instanceof ServerException) {
				nested = ((ServerException) nested).getNestedException();
			} else {
				nested = null;
			}
		}

		return "Exception (" + getFullErrorCode() + ") with message: [ " + displayMessage + " ]\n" +
				"Error Code: [ " + errorCode.name() + " ]\n" +
				"Error Tree: [ " + String.join("->", tree) + " ]\n" +
				((errorFields != null && !errorFields.isEmpty()) ? "Field Validation\n" + errorFields : "") +
				"Call Signature:\n" +
				(!type.isEmpty() ? type : "") +
				(!type.isEmpty() && !method.isEmpty() ? "." : "") +
				(!method.isEmpty() ? method : "") +
				"(" +
				(!args.isEmpty() ? args : "") +
				")";
	}

	public String getFullErrorCode() {
		return this.getClass().getSimpleName() + "_" + buildShortCode();
	}

	private String buildShortCode() {
		return codePrefixes.isEmpty() ? errorCode.getCode() + "" : String.join(".", codePrefixes) + "." + errorCode;
	}

}
