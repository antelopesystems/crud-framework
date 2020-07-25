package com.antelopesystem.crudframework.utils.component.startup;

import com.antelopesystem.crudframework.utils.component.startup.annotation.PostStartUp;
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Shani on 27/12/2018.
 */
public class PostStartupHandler implements ApplicationListener<ContextRefreshedEvent> {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationContext applicationContext;

	private boolean wasLoaded = false;

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if(wasLoaded) {
			return;
		}

		try {
			wasLoaded = true;

			Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Component.class);
			TreeMap<Integer, List<MethodHandlerDTO>> methodsByPriority = new TreeMap<>();
			for(Map.Entry<String, Object> entry : beans.entrySet()) {
				Object handler = entry.getValue();
				if(handler instanceof TargetClassAware) {
					handler = ((Advised) handler).getTargetSource().getTarget();
				}

				List<Method> methods = ReflectionUtils.getMethods(handler.getClass());


				for(Method method : methods) {
					PostStartUp annotation = AnnotationUtils.findAnnotation(method, PostStartUp.class);
					if(annotation != null) {
						List<MethodHandlerDTO> methodsOfPriority = methodsByPriority.get(annotation.priority());
						if(methodsOfPriority == null) {
							methodsOfPriority = new ArrayList<>();
							methodsByPriority.put(annotation.priority(), methodsOfPriority);
						}

						methodsOfPriority.add(new MethodHandlerDTO(method, handler));
					}
				}
			}

			for(Map.Entry<Integer, List<MethodHandlerDTO>> entry : methodsByPriority.descendingMap().entrySet()) {
				for(MethodHandlerDTO dto : entry.getValue()) {
					try {
						dto.method.setAccessible(true);
						dto.method.invoke(dto.handler);
						dto.method.setAccessible(false);
					} catch(Exception e) {
						log.error("Failed to load postStartUp of bean - " + dto.handler.getClass().getSimpleName(), e);
					}
				}
			}

		} catch(Exception e) {
			log.error("Failed postStartUp process", e);
		}
	}

	class MethodHandlerDTO {

		Method method;

		Object handler;

		public MethodHandlerDTO(Method method, Object handler) {
			this.method = method;
			this.handler = handler;
		}
	}
}
