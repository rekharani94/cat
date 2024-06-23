package me.intuit.cat.domain.model

import com.google.gson.annotations.Expose
import java.io.Serializable



data class Breed(val id: String,var imageId:String, val name: String?, val description: String?,
                 val origin: String?, val temperament: String?, val hypoallergenic: Int,var wikipedia_url: String?,)

