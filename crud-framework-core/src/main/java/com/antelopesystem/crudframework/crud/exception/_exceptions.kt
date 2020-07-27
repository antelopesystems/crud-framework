package com.antelopesystem.crudframework.crud.exception

open class CrudException(message: String = "General Error") : RuntimeException(message)

class CrudInvalidStateException(message: String = "Invalid State") : CrudException(message)

class CrudReadException(message: String = "Read Failed") : CrudException(message)

class CrudUpdateException(message: String = "Update Failed") : CrudException(message)

class CrudCreateException(message: String = "Creation Failed") : CrudException(message)

class CrudDeleteException(message: String = "Deletion Failed") : CrudException(message)

class CrudTransformationException(message: String = "Transformation Failed") : CrudException(message)

class CrudValidationException(message: String = "Validation Failed") : CrudException(message)