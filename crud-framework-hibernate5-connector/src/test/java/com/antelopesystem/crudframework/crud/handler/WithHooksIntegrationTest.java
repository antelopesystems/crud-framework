package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.exception.CrudInvalidStateException;
import com.antelopesystem.crudframework.crud.model.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class WithHooksIntegrationTest extends BaseCrudIntegrationTest {

	@Autowired
	private CrudHelper crudHelper;

	@Test
	public void testKotlinNestedWithHooks() {
		EntityMetadataDTO metadataDTO = new EntityMetadataDTO(TestKotlinEntity.class);
		assertEquals(Collections.singleton(GenericPersistentHooks.class), metadataDTO.getHookTypesFromAnnotations());
	}

	@Test(expected = CrudInvalidStateException.class)
	public void testNonComponentPersistentHooksClass() {
		crudHelper.getEntityMetadata(TestKotlinEntity.class);
	}
}
