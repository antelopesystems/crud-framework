package com.antelopesystem.crudframework.crud.dataaccess.model

import java.io.Serializable

data class DataAccessorDTO(
        val accessorClazz: Class<out Any>,

        val accessorId: Serializable
)