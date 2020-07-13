package com.antelopesystem.crudframework.exception.tree.core;

public enum ErrorCode {

	/**
	 * 1001-1999 General/Unknown
	 * 2001-2999 CRUD
	 * 3001-3999 Security
	 * 4001-4999 Business Logic
	 * 5001-5999 Remoting Exception
	 * 6001-6999 Third Party API
	 * 7001-7999 Affiliates API
	 */


	// 11001-11999 General/Unknown

	GeneralError(1001),
	FieldTypeMismatch(1002),
	RequirementNotFulfilled(1003),
	NullArgument(1004),

	// 12001-12999 CRUD

	NotFound(2001),
	OperationNotSupported(2002),
	FillError(2003),
	ROGenerationError(2004),
	UpdateError(2005),
	CreateError(2006),
	ShowError(2007),
	DeleteError(2008),
	ValidationError(2009)

	// 13001-13999 Security


	// 14001-14999 Business Logic


	// 15001-15999 Remoting Exception

	// 16001-16999 Third Party API

	// 17001-17999 Affiliates API

	;

	private int code;

	ErrorCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
