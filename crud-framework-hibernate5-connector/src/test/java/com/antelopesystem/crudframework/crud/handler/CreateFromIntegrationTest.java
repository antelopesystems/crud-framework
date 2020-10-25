package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class CreateFromIntegrationTest extends BaseCrudIntegrationTest {

	@Test
	public void testCreateFrom() {
		TestEntity expectedOutcome = new TestEntity();
		TestEntity outcome = crudHandler.createFrom(new TestEntityRO(), TestEntity.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testCreateFromWithRO() {
		TestEntityRO expectedOutcome = new TestEntityRO();
		TestEntityRO outcome = crudHandler.createFrom(new TestEntityRO(), TestEntity.class, TestEntityRO.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testCreateFromHooks() {
		HookTestDTO dto = new HookTestDTO();

		TestEntityRO mockRO = new TestEntityRO();

		crudHandler.createFrom(new TestEntityRO(), TestEntity.class)
				.withPreHook((object) -> {
					assertEquals(mockRO, object);
					dto.setPreHookCalled(true);
				})
				.withOnHook((entity, object) -> {
					assertNotNull(entity);
					assertEquals(mockRO, object);
					assertEquals(mockRO.getGenericVariable(), entity.getGenericVariable());
					dto.setOnHookCalled(true);
				})
				.withPostHook((entity) -> {
					assertNotNull(entity);
					assertTrue(entity.getId() > 0);
					assertEquals(mockRO.getGenericVariable(), entity.getGenericVariable());
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}
}
