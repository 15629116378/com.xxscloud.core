package com.xxscloud.core
import com.google.gson.*
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.text.DateFormat
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.TreeMap


/**
 * Json 序列化工具类库
 */
object JsonUtils {


    class MapTypeAdapter : JsonDeserializer<JsonObject> {
        override fun deserialize(p0: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): JsonObject {
            val treeMap = JsonObject()
            val jsonObject = p0!!.asJsonObject
            val entrySet = jsonObject.entrySet()
            for (entry in entrySet) {
                //treeMap[entry.key] = entry.value
            }
            return treeMap
        }
    }

    val GSON = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .setVersion(1.0)
            .create()


    fun <T> parseObject(json: String, type: Class<T>): T {
        return GSON.fromJson(json, type)
    }

    fun parseObject(json: String?): JsonObject {
        return GSON.fromJson(json, object : TypeToken<JsonObject>() {

        }.type) as JsonObject
    }

    fun <T> parseArrayObject(json: String, type: Class<T>): List<T> {
        return GSON.fromJson(json, object : TypeToken<List<T>>() {

        }.getType())
    }

    fun parseArrayObject(json: String): JsonArray {
        return GSON.fromJson(json, object : TypeToken<List<JsonObject>>() {

        }.getType())
    }

    fun stringify(obj: Any?): String {
        if (obj == null) {
            return "{}"
        }
        return GSON.toJson(obj)
    }
}