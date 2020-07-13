package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.model.TestEntity;
import com.antelopesystem.crudframework.crud.model.TestEntityRO;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;
import com.antelopesystem.crudframework.modelfilter.FilterFields;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;

public abstract class BaseCrudTest extends BaseIntegrationTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	protected CrudHandler crudHandler;

	protected static TestEntity readOnlyEntity1;

	protected static TestEntityRO readOnlyEntityRO1;

	protected static TestEntity readOnlyEntity2;

	protected static TestEntityRO readOnlyEntityRO2;

	protected static DynamicModelFilter entity1and2AscendingFilter = new DynamicModelFilter().add(FilterFields.inLong("genericVariable", Arrays.asList(10L, 20L))).setOrderBy("id").setOrderDesc(false);

	protected static DynamicModelFilter entity1filter = new DynamicModelFilter().add(FilterFields.eq("genericVariable", 10L));

	protected static DynamicModelFilter invalidFilter = new DynamicModelFilter().add(FilterFields.eq("genericVariable", 30L));

	private static boolean dataLoaded = false;

	private static Object lock = new Object();

	@Before
	public void setUp() throws Exception {
		synchronized(lock) {
			if(dataLoaded) {
				return;
			}
			dataLoaded = true;
			readOnlyEntity1 = new TestEntity("test1,test2,test3", 10000, 10, new Date(10000));
			readOnlyEntity2 = new TestEntity("test4,test5,test6", 20000, 20, new Date(20000));
			readOnlyEntityRO1 = new TestEntityRO(Arrays.asList("test1", "test2", "test3"), 100.0, 10, "10", 10000);
			readOnlyEntityRO2 = new TestEntityRO(Arrays.asList("test4", "test5", "test6"), 200.0, 20, "20", 20000);

			entityManager.createNativeQuery("truncate test_entity");
			entityManager.createNativeQuery("alter table test_entity modify id bigint auto_increment;")
					.executeUpdate();
			entityManager.persist(readOnlyEntity1);
			entityManager.persist(readOnlyEntity2);

			readOnlyEntityRO1.setId(readOnlyEntity1.getId());
			readOnlyEntityRO1.setCreationTime(readOnlyEntity1.getCreationTime().getTime());

			readOnlyEntityRO2.setId(readOnlyEntity2.getId());
			readOnlyEntityRO2.setCreationTime(readOnlyEntity2.getCreationTime().getTime());
			entityManager.flush();
		}
	}

	public TestEntity persistNewTestEntity() {
		TestEntity testEntity = new TestEntity();
		entityManager.persist(testEntity);
		entityManager.flush();
		return testEntity;
	}


}
