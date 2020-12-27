package ru.alexmaryin.spacextimes_rx.data.model.capsule

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CapsuleStatusDeserializer: JsonDeserializer<CapsuleStatus> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CapsuleStatus {
        val stringValue = json?.asString
        for (enum in CapsuleStatus.values())
            if (enum.serialized == stringValue)
                return enum
        throw IllegalArgumentException("Unknown capsule status: $stringValue")
    }

}