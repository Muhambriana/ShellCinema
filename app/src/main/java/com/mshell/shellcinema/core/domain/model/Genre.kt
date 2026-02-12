package com.mshell.shellcinema.core.domain.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @field:SerializedName("genres")
    val genres: List<Genre>? = null
)

data class Genre(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
