package com.jjf.template.result

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * @author xj
 * date: 18-11-7
 * description :
 */
@Entity(primaryKeys = ["login"])
data class User(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String?,
        @field:SerializedName("name")
        val name: String? = null,
        @field:SerializedName("company")
        val company: String?,
        @field:SerializedName("repos_url")
        val reposUrl: String? = null,
        @field:SerializedName("blog")
        val blog: String? = null
)
