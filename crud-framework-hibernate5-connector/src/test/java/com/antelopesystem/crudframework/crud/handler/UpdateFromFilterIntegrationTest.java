package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.modelfilter.FilterFields;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdateFromFilterIntegrationTest extends BaseCrudIntegrationTest {

	@Test
	public void testUpdateFrom() {
		TestEntity testEntity = persistNewTestEntity();
		TestEntity testEntity2 = persistNewTestEntity();

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);

		List<TestEntity> outcome = crudHandler.updateFrom(new DynamicModelFilter().add(FilterFields.inLong("id", Arrays.asList(testEntity.getId(), testEntity2.getId()))), TestEntity.class)
				.withOnHook((e) -> e.setGenericVariable(1999L))
				.execute();
		assertEquals(outcome.size(), 2);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.get(0).getGenericVariable());
		assertEquals(expectedOutcome.getGenericVariable(), outcome.get(1).getGenericVariable());
	}

	@Test
	public void testUpdateWithRO() {
		TestEntity testEntity = persistNewTestEntity();
		TestEntity testEntity2 = persistNewTestEntity();

		TestEntity expectedOutcome = new TestEntity();
		expectedOutcome.setGenericVariable(1999L);


		List<TestEntityRO> outcome = crudHandler.updateFrom(new DynamicModelFilter().add(FilterFields.inLong("id", Arrays.asList(testEntity.getId(), testEntity2.getId()))), TestEntity.class, TestEntityRO.class)
				.withOnHook((e) -> e.setGenericVariable(1999L))
				.execute();

		assertEquals(outcome.size(), 2);
		assertEquals(expectedOutcome.getGenericVariable(), outcome.get(0).getGenericVariable());
		assertEquals(expectedOutcome.getGenericVariable(), outcome.get(1).getGenericVariable());
	}
}
