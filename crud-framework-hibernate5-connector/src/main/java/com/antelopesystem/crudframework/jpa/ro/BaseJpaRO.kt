package com.antelopesystem.crudframework.jpa.ro

import com.antelopesystem.crudframework.ro.BaseRO

/**
 * Date: 10.01.13 Time: 20:27
 *
 * @author Shani Holdengreber
 * @author thewizkid@gmail.com
 */
abstract class BaseJpaRO : BaseRO<Long>() {
    init {
        id = 0L
    }
}