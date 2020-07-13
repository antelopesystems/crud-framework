package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowTests extends BaseCrudTest {

	@Test
	public void testShowWhenValidId() {
		TestEntity testEntity = crudHandler.show(1L, TestEntity.class)
				.execute();
		assertNotNull(testEntity);
		assertEquals(readOnlyEntity1, testEntity);
	}

	@Test
	public void testShowHooks() {
		HookTestDTO dto = new HookTestDTO();
		crudHandler.show(1L, TestEntity.class)
				.withPreHook((id) -> {
					assertEquals(1L, (long) id);
					dto.setPreHookCalled(true);
				})
				.withOnHook((entity) -> {
					assertNotNull(entity);
					assertEquals(readOnlyEntity1, entity);
					dto.setOnHookCalled(true);
				})
				.withPostHook((entity) -> {
					assertNotNull(entity);
					assertEquals(readOnlyEntity1, entity);
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}


	@Test
	public void testShowWhenInvalidId() {
		TestEntity testEntity = crudHandler.show(1999L, TestEntity.class)
				.execute();
		assertNull(testEntity);
	}

	@Test
	public void testShowWithRO() {
		TestEntityRO testEntityRO = crudHandler.show(1L, TestEntity.class, TestEntityRO.class)
				.execute();
		assertNotNull(testEntityRO);
		assertEquals(readOnlyEntityRO1, testEntityRO);
	}

	@Test
	public void testShowWithPersistCopy() {
		long originalCurrency = readOnlyEntity1.getLongCurrency();
		TestEntity testEntity = crudHandler.show(1L, TestEntity.class)
				.persistCopy()
				.execute();
		testEntity.setLongCurrency(99999L);
		assertNotNull(testEntity.saveOrGetCopy());
		assertEquals(originalCurrency, ((TestEntity) testEntity.saveOrGetCopy()).getLongCurrency());
	}

}
