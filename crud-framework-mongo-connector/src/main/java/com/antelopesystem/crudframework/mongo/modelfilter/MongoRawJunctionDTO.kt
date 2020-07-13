package com.antelopesystem.crudframework.mongo.modelfilter

import com.antelopesystem.crudframework.modelfilter.BaseRawJunction
import org.springframework.data.mongodb.core.query.Criteria

class MongoRawJunctionDTO(junction: Criteria) : BaseRawJunction<Criteria>(junction) {
}