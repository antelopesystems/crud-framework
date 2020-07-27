package com.antelopesystem.crudframework.ro;

import java.util.Objects;

/**
 * Date: 10.01.13 Time: 20:27
 *
 * @author Shani Holdengreber
 * @author thewizkid@gmail.com
 */
public abstract class BaseUpdatableRO<ID> extends BaseRO<ID> {

	//------------------------ Constants -----------------------
	private static final long serialVersionUID = 1L;

	//------------------------ Fields --------------------------
	private long lastUpdateTime;

	//------------------------ Public methods ------------------
	//------------------------ Constructors --------------------
	//------------------------ Field's handlers ----------------
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
		if(!super.equals(o)) {
			return false;
		}
		BaseUpdatableRO that = (BaseUpdatableRO) o;
		return lastUpdateTime == that.lastUpdateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), lastUpdateTime);
	}


	//------------------------ Private methods -----------------
}
