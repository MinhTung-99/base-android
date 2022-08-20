package com.base_clean_architecture.data.response

import com.google.gson.annotations.SerializedName

data class Coffee(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("description")
    var description: String? = null,

    /*@SerializedName("ingredients")
    var ingredients: List<String>? = null,*/

    @SerializedName("id")
    var id: Int? = null
)