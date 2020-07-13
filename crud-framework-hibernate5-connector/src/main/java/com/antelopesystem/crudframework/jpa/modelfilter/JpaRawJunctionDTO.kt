package com.antelopesystem.crudframework.modelfilter

import org.hibernate.criterion.Junction
import java.util.stream.Collectors

class JpaRawJunctionDTO(junction: Junction, var requestedAliases: Set<String> = emptySet()) : BaseRawJunction<Junction>(junction) {
    init {
        this.requestedAliases = requestedAliases.stream().map { requestAlias: String -> "$requestAlias." }.collect(Collectors.toSet())
    }
}