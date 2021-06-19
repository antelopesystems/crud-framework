package com.antelopesystem.crudframework.jpa.model

import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField
import com.antelopesystem.crudframework.jpa.ro.AbstractJpaCrudRO
import com.antelopesystem.crudframework.jpa.ro.AbstractJpaUpdatableCrudRO
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class AbstractJpaUpdatableCrudEntity : AbstractJpaCrudEntity() {
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column
    @MappedField(target = AbstractJpaUpdatableCrudRO::class)
    val creationTime: Date = Date()

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Version
    @MappedField(target = AbstractJpaUpdatableCrudRO::class)
    var lastUpdateTime: Date = Date()
}