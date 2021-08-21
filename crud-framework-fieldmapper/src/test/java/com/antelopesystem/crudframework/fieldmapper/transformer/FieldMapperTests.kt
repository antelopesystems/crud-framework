package com.antelopesystem.crudframework.fieldmapper.transformer

import com.antelopesystem.crudframework.fieldmapper.FieldMapper
import com.antelopesystem.crudframework.fieldmapper.annotation.DefaultMappingTarget
import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.util.*

class FieldMapperTests {

    @Test
    fun `verify exception is thrown if a default transformer pair is registered twice`() {
        val fieldMapper = FieldMapper()
        val defaultTransformer = object : DateToLongTransformer() {
            override fun isDefault(): Boolean {
                return true
            }
        }

        expectThrows<IllegalStateException> {
            fieldMapper
                .registerTransformer(defaultTransformer::class.java, defaultTransformer)
            fieldMapper
                .registerTransformer(defaultTransformer::class.java, defaultTransformer)
        }
    }

    @Test
    internal fun `test implicit default transformer`() {
        val fieldMapper = FieldMapper()
        val defaultTransformer = object : DateToLongTransformer() {
            override fun isDefault(): Boolean {
                return true
            }
        }
        fieldMapper
            .registerTransformer(defaultTransformer::class.java, defaultTransformer)

        val sourceClass = ExampleSourceClass(Date(1))
        val destinationClass = ExampleDestinationClass()
        fieldMapper.processMappedFields(sourceClass, destinationClass)

        expectThat(destinationClass.dateAsLong)
            .isEqualTo(1L)
    }
}

@DefaultMappingTarget(ExampleDestinationClass::class)
private class ExampleSourceClass(
    @MappedField(mapTo = "dateAsLong")
    var date: Date
)

private class ExampleDestinationClass {
    var dateAsLong: Long? = null
}

