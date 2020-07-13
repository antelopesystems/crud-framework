package com.antelopesystem.crudframework.web.ro;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.antelopesystem.crudframework.exception.dto.ErrorField;
import com.antelopesystem.crudframework.ro.PagingRO;

import java.io.Serializable;
import java.util.List;

/**
 * Object that contain result of service action.
 */
public class ResultRO<Payload> implements Serializable {

	//------------------------ Constants -----------------------
	private static final long serialVersionUID = 1L;

	//------------------------ Fields --------------------------
	// true if operation was successfull
	private boolean success;

	// error description
	private String error;

	// result of operation
	private Payload result;

	private PagingRO paging;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fullErrorCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer shortErrorCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorField> errorFields;

	//------------------------ Public methods ------------------
	//------------------------ Constructors --------------------
	public ResultRO() {
		this.success = true;
	}

	//------------------------ Field's handlers ----------------
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Payload getResult() {
		return result;
	}

	public void setResult(Payload result) {
		this.result = result;
	}

	public PagingRO getPaging() {
		return paging;
	}

	public void setPaging(PagingRO paging) {
		this.paging = paging;
	}

	public String getFullErrorCode() {
		return fullErrorCode;
	}

	public void setFullErrorCode(String fullErrorCode) {
		this.fullErrorCode = fullErrorCode;
	}

	public Integer getShortErrorCode() {
		return shortErrorCode;
	}

	public void setShortErrorCode(Integer shortErrorCode) {
		this.shortErrorCode = shortErrorCode;
	}

	public List<ErrorField> getErrorFields() {
		return errorFields;
	}

	public void setErrorFields(List<ErrorField> errorFields) {
		this.errorFields = errorFields;
	}

	//------------------------ Other public methods ------------
	//------------------------ Private methods -----------------
}
