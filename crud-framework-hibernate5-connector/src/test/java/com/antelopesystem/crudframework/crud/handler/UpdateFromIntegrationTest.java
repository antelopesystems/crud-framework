package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateFromIntegrationTest extends BaseCrudIntegrationTest {

	@Test
	public void testUpdateFrom() {
		TestEntity testEntity = persistNewTestEntity();

		TestEntityRO testEntityRO = new TestEntityRO();
		testEntityRO.setId(testEntity.getId());
		testEntityRO.setGenericVariable(1999L);

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		TestEntity outcome = crudHandler.updateFrom(testEntity.getId(), testEntityRO, TestEntity.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testUpdateWithRO() {
		TestEntity testEntity = persistNewTestEntity();

		TestEntityRO testEntityRO = new TestEntityRO();
		testEntityRO.setId(testEntity.getId());
		testEntityRO.setGenericVariable(1999L);

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		TestEntityRO outcome = crudHandler.updateFrom(testEntity.getId(), testEntityRO, TestEntity.class, TestEntityRO.class)
				.execute();
		assertNotNull(outcome);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.getGenericVariable());
	}

	@Test
	public void testUpdateFromHooks() {
		HookTestDTO dto = new HookTestDTO();
		TestEntity testEntity = persistNewTestEntity();

		TestEntityRO testEntityRO = new TestEntityRO();
		testEntityRO.setId(testEntity.getId());
		testEntityRO.setGenericVariable(1999L);

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		crudHandler.updateFrom(testEntity.getId(), testEntityRO, TestEntity.class)
				.withPreHook((id, object) -> {
					assertEquals(testEntity.getId(), id);
					assertEquals(testEntityRO, object);
					dto.setPreHookCalled(true);
				})
				.withOnHook((entity, object) -> {
					assertEquals(testEntity, entity);
					assertEquals(1999L, entity.getGenericVariable());
					assertEquals(testEntityRO, object);
					dto.setOnHookCalled(true);
				})
				.withPostHook((entity) -> {
					assertEquals(1999L, entity.getGenericVariable());
					dto.setPostHookCalled(true);
				})
				.execute();
		assertTrue(dto.getPreHookCalled());
		assertTrue(dto.getOnHookCalled());
		assertTrue(dto.getPostHookCalled());
	}

}
