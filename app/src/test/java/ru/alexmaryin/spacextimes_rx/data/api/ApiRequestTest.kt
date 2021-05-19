package ru.alexmaryin.spacextimes_rx.data.api

import org.junit.Test
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiOptions
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiRequest
import ru.alexmaryin.spacextimes_rx.data.model.api.PopulatedObject

class ApiRequestTest {

    @Test
    fun `show how default Api Request looks`() {

        val dragonWithCapsule = PopulatedObject(path = "dragon", populate = PopulatedObject(path = "capsule"))

        val populateLaunchDetails = ApiOptions(
            populate = listOf(
                PopulatedObject(path = "rocket crew capsules payloads launchpad"),
                PopulatedObject(path = "cores", populate = PopulatedObject(path = "core")),
                PopulatedObject(path = "payloads", populate = dragonWithCapsule)
            )
        )

        val request = ApiRequest(options = populateLaunchDetails)
        println(request.toString())
    }
}