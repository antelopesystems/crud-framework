package com.antelopesystem.crudframework.crud.hooks;

import java.util.List;

public class HooksDTO<PreHook, OnHook, PostHook> {

	private List<PreHook> preHooks;

	private List<OnHook> onHooks;

	private List<PostHook> postHooks;


	public HooksDTO(List<PreHook> preHooks, List<OnHook> onHooks, List<PostHook> postHooks) {
		this.preHooks = preHooks;
		this.onHooks = onHooks;
		this.postHooks = postHooks;
	}

	public List<PreHook> getPreHooks() {
		return preHooks;
	}

	public List<OnHook> getOnHooks() {
		return onHooks;
	}

	public List<PostHook> getPostHooks() {
		return postHooks;
	}
}
