package com.jjf.template.result

import androidx.room.Entity
import androidx.room.Ignore

/**
 * @author xj
 * date: 19-3-14
 * description :
 */

data class ProjectCategory(
        val `data`: List<Category>,
        val errorCode: Int,
        val errorMsg: String
)

@Entity(primaryKeys = ["id"])
data class Category(
        var id: Int = 0,
        @Ignore val courseId: Int,
        var name: String,
        @Ignore val order: Int,
        @Ignore val parentChapterId: Int,
        @Ignore val userControlSetTop: Boolean,
        @Ignore val visible: Int
) {
        constructor():this(0,0,"",0,0,false,0)
}