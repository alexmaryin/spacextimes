package ru.alexmaryin.spacextimes_rx.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

class ApiServiceImpl: ApiService {
    override fun getCapsules(): Single<List<Capsule>> {
        return Rx2AndroidNetworking.get(SpacexUrls.Base + SpacexUrls.AllCapsules)
            .build()
            .getObjectListSingle(Capsule::class.java)
    }
}