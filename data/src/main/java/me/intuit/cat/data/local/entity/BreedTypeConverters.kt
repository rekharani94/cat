package me.intuit.cat.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.intuit.cat.domain.model.Breed
import java.lang.reflect.Type


class BreedTypeConverters {
    @TypeConverter
    fun fromStringListToJSON(breedList: List<String>): String {
        return Gson().toJson(breedList)
    }
    @TypeConverter
    fun fromJSONToStringList(json: String): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(json,listType)
    }

    @TypeConverter
    fun fromBreedListToJSON(breedList: List<Breed>): String {
        return Gson().toJson(breedList)
    }
    @TypeConverter
    fun fromJSONToBreedList(json: String): List<Breed> {
        val listType: Type = object : TypeToken<ArrayList<Breed?>?>() {}.type
        return Gson().fromJson(json,listType)
    }
}