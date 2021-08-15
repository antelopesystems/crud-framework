package com.antelopesystem.crudframework.modelfilter.enums

enum class FilterFieldOperation {
    Equal,
    NotEqual,
    In,
    NotIn,
    GreaterThan,
    GreaterEqual,
    LowerThan,
    LowerEqual,
    Between,
    Contains,
    IsNull,
    IsNotNull,
    And,
    Or,
    Not,
    ContainsIn,
    NotContainsIn,
    RawJunction,
    StartsWith,
    EndsWith,
    Noop
}