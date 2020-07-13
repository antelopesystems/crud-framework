package com.antelopesystem.crudframework.jpa.ro;

import java.io.Serializable;
import java.util.Objects;

/**
 * Date: 10.01.13 Time: 20:27
 *
 * @author Shani Holdengreber
 * @author thewizkid@gmail.com
 */
public abstract class BaseRO implements Serializable {

	//------------------------ Constants -----------------------
	private static final long serialVersionUID = 1L;

	//------------------------ Fields --------------------------s
	private long id;

	private long creationTime;

	//------------------------ Public methods ------------------
	//------------------------ Constructors --------------------
	//------------------------ Field's handlers ----------------
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	//------------------------ Other public methods ------------

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseRO baseRO = (BaseRO) o;
		return id == baseRO.id &&
				creationTime == baseRO.creationTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, creationTime);
	}


	//------------------------ Private methods -----------------
}
