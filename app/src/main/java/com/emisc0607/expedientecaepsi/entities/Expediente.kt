package com.emisc0607.expedientecaepsi.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Expediente(
    var id: String = "",
    var name: String = "",
    var imgUrl: String = ""
)
