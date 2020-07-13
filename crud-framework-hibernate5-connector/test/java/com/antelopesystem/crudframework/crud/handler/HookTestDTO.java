package com.antelopesystem.crudframework.crud.handler;

public class HookTestDTO {

	private boolean preHookCalled = false;

	private boolean onHookCalled = false;

	private boolean postHookCalled = false;

	public boolean getPreHookCalled() {
		return preHookCalled;
	}

	public void setPreHookCalled(boolean preHookCalled) {
		this.preHookCalled = preHookCalled;
	}

	public boolean getOnHookCalled() {
		return onHookCalled;
	}

	public void setOnHookCalled(boolean onHookCalled) {
		this.onHookCalled = onHookCalled;
	}

	public boolean getPostHookCalled() {
		return postHookCalled;
	}

	public void setPostHookCalled(boolean postHookCalled) {
		this.postHookCalled = postHookCalled;
	}
}
