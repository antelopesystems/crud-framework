package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import com.antelopesystem.crudframework.ro.PagingDTO;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IndexTests extends BaseCrudTest {

	@Test
	public void testIndexWhenValidFilter() {
		PagingDTO<TestEntity> testEntities = crudHandler.index(entity1filter, TestEntity.class)
				.execute();
		assertNotNull(testEntities);
		assertNotNull(testEntities.getData());
		assertEquals(Arrays.asList(readOnlyEntity1), testEntities.getData());
	}

	@Test
	public void testIndexHooks() {
		HookTestDTO dto = new HookTestDTO();
		crudHandler.index(entity1filter, TestEntity.class)
				.withPreHook((filter) -> {
					assertEquals(entity1filter, filter);
					dto.setPreHookCalled(true);
				})
				.withOnHook((filter, result) -> {
					assertEquals(entity1filter, filter);
					assertEquals(readOnlyEntity1, result.getData().get(0));
					dto.setOnHookCalled(true);
				})
				.withPostHook((filter, result) -> {
					assertEquals(entity1filter, filter);
					assertEquals(readOnlyEntity1, result.getData().get(0));
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}

	@Test
	public void testIndexWhenInvalidFilter() {
		PagingDTO<TestEntity> testEntities = crudHandler.index(invalidFilter, TestEntity.class)
				.execute();
		assertNotNull(testEntities);
		assertNotNull(testEntities.getData());
		assertTrue(testEntities.getData().isEmpty());
	}

	@Test
	public void testIndexWithRO() {
		PagingDTO<TestEntityRO> testEntities = crudHandler.index(entity1and2AscendingFilter, TestEntity.class, TestEntityRO.class)
				.execute();
		assertNotNull(testEntities);
		assertNotNull(testEntities.getData());
		assertEquals(Arrays.asList(readOnlyEntityRO1, readOnlyEntityRO2), testEntities.getData());
	}

	@Test
	public void testIndexWithPersistCopy() {
		long originalCurrency = readOnlyEntity1.getLongCurrency();

		PagingDTO<TestEntity> entities = crudHandler.index(entity1filter, TestEntity.class)
				.persistCopy()
				.execute();
		TestEntity testEntity = entities.getData().get(0);
		testEntity.setLongCurrency(99999L);
		assertNotNull(testEntity.saveOrGetCopy());
		assertEquals(originalCurrency, ((TestEntity) testEntity.saveOrGetCopy()).getLongCurrency());
	}
}
