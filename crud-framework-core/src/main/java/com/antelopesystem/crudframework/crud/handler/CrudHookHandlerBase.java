package com.antelopesystem.crudframework.crud.handler;

import com.antelopesystem.crudframework.crud.hooks.interfaces.CRUDHooks;
import com.antelopesystem.crudframework.crud.model.EntityMetadataDTO;
import com.antelopesystem.crudframework.model.BaseCrudEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CrudHookHandlerBase implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private CrudHelper crudHelper;

	private Map<Class<? extends CRUDHooks>, Map<Class<? extends BaseCrudEntity>, List<CRUDHooks>>> hooksMap;

	protected abstract List<Class<? extends CRUDHooks>> getHookTargetClasses();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	private void init() {
		hooksMap = new HashMap<>();
		for(Class<? extends CRUDHooks> targetClass : getHookTargetClasses()) {
			Map<String, ? extends CRUDHooks> crudHooksMap = applicationContext.getBeansOfType(targetClass);

			for(Map.Entry<String, ? extends CRUDHooks> entry : crudHooksMap.entrySet()) {
				hooksMap.computeIfAbsent(targetClass, x -> new HashMap<>()).computeIfAbsent(entry.getValue().getType(), x -> new ArrayList<>()).add(entry.getValue());
			}

		}
	}

	@SuppressWarnings("unchecked")
	protected final <ID extends Serializable, Entity extends BaseCrudEntity<ID>, HooksType extends CRUDHooks<ID, Entity>> List<HooksType> getHooks(Class<HooksType> crudHooksClazz, Class<Entity> entityClazz) {
		List<CRUDHooks> crudHooks = new ArrayList<>();
		EntityMetadataDTO metadataDTO = crudHelper.getEntityMetadata(entityClazz);
		Set<HooksType> matchingAnnotationHooks = (Set<HooksType>) metadataDTO.getHooksFromAnnotations()
				.stream()
				.filter(annotationHook -> crudHooksClazz.isAssignableFrom(annotationHook.getClass()))
				.collect(Collectors.toSet());

		crudHooks.addAll(matchingAnnotationHooks);
		if(hooksMap.containsKey(crudHooksClazz) && hooksMap.get(crudHooksClazz).containsKey(entityClazz)) {
			crudHooks.addAll(hooksMap.get(crudHooksClazz).get(entityClazz));
		}
		return (List) crudHooks;
	}


}
