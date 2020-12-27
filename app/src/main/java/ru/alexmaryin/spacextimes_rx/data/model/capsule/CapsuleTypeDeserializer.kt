package ru.alexmaryin.spacextimes_rx.data.model.capsule

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CapsuleTypeDeserializer: JsonDeserializer<CapsuleType> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CapsuleType {
        val stringValue = json?.asString
        for (enum in CapsuleType.values())
            if (enum.serialized == stringValue)
                return enum
        throw IllegalArgumentException("Unknown capsule status: $stringValue")
    }
}