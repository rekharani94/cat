package me.intuit.cat.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Breed(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("wikipedia_url")
    @Expose
    var wikipedia_url: String? = null,
    @SerializedName("reference_image_id")
    @Expose
    var reference_image_id: String? = null,
    @SerializedName("alt_names")
    @Expose
    var alt_names: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null
):Serializable

/*@SerializedName("temperament")
@Expose
var temperament: String? = null,
@SerializedName("origin")
@Expose
var origin: String? = null,
@SerializedName("country_code")
@Expose
var country_code: String? = null,*/
