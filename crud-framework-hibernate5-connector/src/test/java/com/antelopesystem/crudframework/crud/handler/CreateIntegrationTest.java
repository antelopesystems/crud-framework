package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CreateIntegrationTest extends BaseCrudIntegrationTest {

	@Test
	public void testCreate() {
		TestEntity expectedOutcome = new TestEntity();
		TestEntity outcome = crudHandler.create(new TestEntity())
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testCreateWithRO() {
		TestEntityRO expectedOutcome = new TestEntityRO();
		TestEntityRO outcome = crudHandler.create(new TestEntity(), TestEntityRO.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testCreateHooks() {
		HookTestDTO dto = new HookTestDTO();

		TestEntity mockEntity = new TestEntity();
		crudHandler.create(new TestEntity())
				.withPreHook((entity) -> {
					assertEquals(mockEntity, entity);
					dto.setPreHookCalled(true);
				})
				.withOnHook((entity) -> {
					assertNotNull(entity);
					assertEquals(mockEntity, entity);
					dto.setOnHookCalled(true);
				})
				.withPostHook((entity) -> {
					assertNotNull(entity);
					assertTrue(entity.getId() > 0);
					assertEquals(mockEntity.getGenericVariable(), entity.getGenericVariable());
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}
}
