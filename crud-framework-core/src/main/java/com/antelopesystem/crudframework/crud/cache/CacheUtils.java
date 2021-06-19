package com.antelopesystem.crudframework.crud.cache;


import java.util.function.Supplier;

public class CacheUtils {

	public static void removeFromCacheIfKeyContains(CrudCache cache, String subKey) {
		cache.remove(subKey);
	}

	public static Object getObjectAndCache(Supplier<Object> objectSupplier, String key, CrudCache cache) {
		if(cache == null) {
			return objectSupplier.get();
		}

		Object cached = cache.get(key);
		Object result;
		if(cached == null) {
			result = objectSupplier.get();
			cache.put(key, result);
		} else {
			result = cached;
		}

		return result;
	}

	// ------------------------ Private methods -----------------
}
