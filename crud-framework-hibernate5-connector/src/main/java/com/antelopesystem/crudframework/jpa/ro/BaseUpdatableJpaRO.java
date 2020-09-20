package com.antelopesystem.crudframework.jpa.ro;

/**
 * Date: 10.01.13 Time: 20:27
 *
 * @author Shani Holdengreber
 * @author thewizkid@gmail.com
 */
public abstract class BaseUpdatableJpaRO extends BaseJpaRO {
	private static final long serialVersionUID = 1L;

	private long lastUpdateTime;

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
