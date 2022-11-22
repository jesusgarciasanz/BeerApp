package com.example.beerapp.pojo

import com.squareup.moshi.Json
import java.util.*

data class Volume(
    val Value: Int,
    val Unit: String,
) {}

data class Method(
    val MashTemp: Temp,
    val Duration: Int,
) {}

data class Temp(
    val Value: Int,
    val Unit: String,
) {}


data class Amount(
    val Value: Int,
    val Unit: String,
) {}

data class Beer(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "tagline")
    val tagline: String,
    @Json(name = "first_brewed")
    var first_brewed: String,
    @Json(name = "description")
    var description: String,
    @Json(name = "image_url")
    var image_url: String,
    @Json(name = "abv")
    var abv: Float,
    @Json(name = "ibu")
    var ibu: Float,
/*   @Json(name = "target_fg")
    var TargetFg: Int,
    @Json(name = "target_og")
    var TargetOg: Integer,*/
    @Json(name = "ebc")
    var ebc: Float,
    @Json(name = "srm")
    var srm: Float,
    @Json(name = "ph")
    var ph: Float,
    @Json(name = "attenuation_level")
    var attenuation_level: Float,

/*  @Json(name = "volume")
    var Volume: Volume,
    @Json(name = "boil_volume")
    var BoilVolume: Volume,*/
) {
}