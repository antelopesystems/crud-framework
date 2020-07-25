package com.antelopesystem.crudframework.utils.cluster.lock

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "distributed_lock")
data class LockDocument(
        @Indexed(unique = true)
        var name: String,

        @Indexed(name="heartbeat", expireAfterSeconds=10)
        var heartbeat: Date = Date()
) {
        @Id
        lateinit var id: ObjectId
}