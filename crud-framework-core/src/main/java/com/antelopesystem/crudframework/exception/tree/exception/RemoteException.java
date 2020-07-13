package com.antelopesystem.crudframework.exception.tree.exception;

public class RemoteException extends ServerException {

	private String originalException;

	public RemoteException() {
		super();
	}

	public RemoteException(ServerException e) {
		super();
		originalException = e.getClass().getSimpleName();
		withDisplayMessage("Remote Exception [ " + originalException + " ] was invoked with message [ " + e.getDisplayMessage() + " ]");
	}

	public String getOriginalException() {
		return originalException;
	}
}
