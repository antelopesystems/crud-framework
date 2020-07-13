package com.antelopesystem.crudframework.utils;


import org.springframework.cache.Cache;

import java.util.function.Supplier;

public class CacheUtils {

	public static void removeFromCacheIfKeyContains(Cache cache, String subKey) {
		cache.evict(subKey);
	}

	public static Object getObjectAndCache(Supplier<Object> objectSupplier, String key, Cache cache) {
		if(cache == null) {
			return objectSupplier.get();
		}

		Cache.ValueWrapper cached = cache.get(key);
		Object result;
		if(cached == null) {
			result = objectSupplier.get();
			cache.put(key, result);
		} else {
			result = cached.get();
		}

		return result;
	}

	// ------------------------ Private methods -----------------
}
