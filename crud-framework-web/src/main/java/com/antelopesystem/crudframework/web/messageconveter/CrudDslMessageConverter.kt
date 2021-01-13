package com.antelopesystem.crudframework.web.messageconveter

import com.antelopesystem.crudframework.dsl.CrudDsl
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.AbstractHttpMessageConverter

class CrudDslMessageConverter(
    private val crudDsl: CrudDsl
) : AbstractHttpMessageConverter<DynamicModelFilter>(MediaType("application", "cql")) {
    override fun supports(clazz: Class<*>): Boolean {
            return DynamicModelFilter::class.java.isAssignableFrom(clazz)
    }

    override fun writeInternal(t: DynamicModelFilter, outputMessage: HttpOutputMessage) {
    }

    override fun readInternal(clazz: Class<out DynamicModelFilter>, inputMessage: HttpInputMessage): DynamicModelFilter {
        return crudDsl.parseInput(inputMessage.body);
    }
}