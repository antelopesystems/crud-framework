package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class UpdateTests extends BaseCrudTest {

	@Test
	public void testUpdate() {
		TestEntity testEntity = persistNewTestEntity();
		testEntity.setGenericVariable(1999L);

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		TestEntity outcome = crudHandler.update(testEntity)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testUpdateWithRO() {
		TestEntity testEntity = persistNewTestEntity();
		testEntity.setGenericVariable(1999L);

		TestEntityRO expectedOutcome = new TestEntityRO();
		expectedOutcome.setGenericVariable(1999L);

		TestEntityRO outcome = crudHandler.update(testEntity, TestEntityRO.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testUpdateHooks() {
		HookTestDTO dto = new HookTestDTO();
		TestEntity testEntity = persistNewTestEntity();
		testEntity.setGenericVariable(1999L);

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		crudHandler.update(testEntity)
				.withPreHook((entity) -> {
					assertEquals(testEntity, entity);
					dto.setPreHookCalled(true);
				})
				.withOnHook((entity) -> {
					assertEquals(testEntity, entity);
					dto.setOnHookCalled(true);
				})
				.withPostHook((entity) -> {
					assertEquals(testEntity, entity);
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}
}
