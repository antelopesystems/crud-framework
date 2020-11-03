package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.enums.ShowByMode;
import com.antelopesystem.crudframework.crud.exception.CrudReadException;
import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowByIntegrationTest extends BaseCrudIntegrationTest {

	@Test
	public void testShowByWhenValidFilter() {
		TestEntity testEntity = crudHandler.showBy(entity1filter, TestEntity.class)
				.execute();
		assertNotNull(testEntity);
		assertEquals(readOnlyEntity1, testEntity);
	}

	@Test
	public void testShowByHooks() {
		HookTestDTO dto = new HookTestDTO();
		crudHandler.showBy(entity1filter, TestEntity.class)
				.withPreHook((filter) -> {
					assertEquals(entity1filter, filter);
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
	public void testShowWhenInvalidFilter() {
		TestEntity testEntity = crudHandler.showBy(invalidFilter, TestEntity.class)
				.execute();
		assertNull(testEntity);
	}

	@Test
	public void testShowByWithRO() {
		TestEntityRO testEntityRO = crudHandler.showBy(entity1filter, TestEntity.class, TestEntityRO.class)
				.execute();
		assertNotNull(testEntityRO);
		assertEquals(readOnlyEntityRO1, testEntityRO);
	}

	@Test
	public void testShowByWithReturnFirstMode() {
		TestEntity testEntity = crudHandler.showBy(entity1and2AscendingFilter, TestEntity.class, ShowByMode.RETURN_FIRST)
				.execute();
		assertNotNull(testEntity);
		assertEquals(readOnlyEntity1, testEntity);
	}

	@Test(expected = CrudReadException.class)
	public void testShowByWithThrowExceptionMode() {
		crudHandler.showBy(entity1and2AscendingFilter, TestEntity.class, ShowByMode.THROW_EXCEPTION)
				.execute();
	}

	@Test
	public void testShowByWithRandomMode() {
		TestEntity testEntity = crudHandler.showBy(entity1and2AscendingFilter, TestEntity.class, ShowByMode.RETURN_RANDOM)
				.execute();
		assertNotNull(testEntity);
	}

	@Test
	public void testShowByWithModeAndRO() {
		TestEntityRO testEntityRO = crudHandler.showBy(entity1and2AscendingFilter, TestEntity.class, TestEntityRO.class, ShowByMode.RETURN_FIRST)
				.execute();
		assertNotNull(testEntityRO);
		assertEquals(readOnlyEntityRO1, testEntityRO);
	}

	@Test
	public void testShowByWithPersistCopy() {
		long originalCurrency = readOnlyEntity1.getLongCurrency();
		TestEntity testEntity = crudHandler.showBy(entity1filter, TestEntity.class)
				.persistCopy()
				.execute();
		testEntity.setLongCurrency(99999L);
		assertNotNull(testEntity.saveOrGetCopy());
		assertEquals(originalCurrency, ((TestEntity) testEntity.saveOrGetCopy()).getLongCurrency());
	}
}
