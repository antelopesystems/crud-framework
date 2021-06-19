package com.antelopesystem.crudframework.crud.configuration.properties

import com.antelopesystem.crudframework.crud.handler.CrudHelperImpl
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(CrudFrameworkProperties.CONFIGURATION_PREFIX)
class CrudFrameworkProperties {
    /**
     * Whether or not to enable default transformers for the field mapper
     * When set to `true`, the framework will register several of its transformers for their type pairs
     * See [CrudHelperImpl.initFieldMapper]
     */
    var defaultTransformersEnabled: Boolean = true

    companion object {
        const val CONFIGURATION_PREFIX = "crudframework"
    }
}