package ru.alexmaryin.spacextimes_rx.data.repository

import android.util.Log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.model.lists.Cores
import ru.alexmaryin.spacextimes_rx.utils.*
import java.net.SocketTimeoutException
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(
    private val remoteApi: SpaceXApi,
    private val localApi: ApiLocal,
    private val networkHelper: NetworkHelper,
    ) {

    private fun <R> fetchItems(
        apiCallback: suspend () -> Response<R>,
    ) = flow {
        emit(Loading)
        if (networkHelper.isNetworkConnected()) {
            apiCallback().apply {
                if (isSuccessful) {
                    emit(Success(when (body()) {
                        is List<*> -> body() as List<*>
                        is ApiResponse<*> -> (body() as ApiResponse<*>).docs
                        else -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                    }))
                } else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
            }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
    }.catch { e ->
        when (e) {
            is SocketTimeoutException -> emit(Error("Server timeout", ErrorType.REMOTE_API_ERROR))
            else -> emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR))
        }
    }

    private inline fun <reified T, R> fetchItemById(
        id: String,
        noinline apiCallback: suspend (String) -> Response<R>,
        noinline localApiCallback: (String) -> T? = { null },
    ) = flow {
        emit(Loading)
        localApiCallback(id)?.let {
            emit(Success(it))
            Log.d("REPOSITORY", "fetchItemById loaded local $it")
        } ?: if (networkHelper.isNetworkConnected()) {
            apiCallback(id).apply {
                if (isSuccessful) emit(Success(when (body()) {
                    is T -> body()
                    is ApiResponse<*> -> (body() as ApiResponse<*>).docs.first() as T
                    else -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                })) else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))

                Log.d("REPOSITORY", "fetchItemById loaded remote ${this.body()}")
            }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
    }

    fun getCapsules() = fetchItems(remoteApi::getCapsules)
    fun getCapsuleById(id: String) = fetchItemById(id, remoteApi::getCapsuleById, localApi::getCapsuleById)

    fun getCores() = fetchItems(remoteApi::getCores)
        .map { it.toListOf<Cores>()?.sortedWith(compareBy(Cores::block, Cores::serial))?.reversed()?.toSuccess() ?: it }
    fun getCoreById(id: String) = fetchItemById(id, remoteApi::getCoreById, localApi::getCoreById)

    fun getCrew() = fetchItems(remoteApi::getCrew)
    fun getCrewById(id: String) = fetchItemById(id, remoteApi::getCrewById, localApi::getCrewById)

    fun getDragons() = fetchItems(remoteApi::getDragons)
    fun getDragonById(id: String) = fetchItemById(id, remoteApi::getDragonById, localApi::getDragonById)

    fun getRockets() = fetchItems(remoteApi::getRockets)
    fun getRocketById(id: String) = fetchItemById(id, remoteApi::getRocketById, localApi::getRocketById)

    fun getLaunchPads() = fetchItems(remoteApi::getLaunchPads)
    fun getLaunchPadById(id: String) = fetchItemById(id, remoteApi::getLaunchPadById, localApi::getLaunchPadById)

    fun getLandingPads() = fetchItems(remoteApi::getLandingPads)
    fun getLandingPadById(id: String) = fetchItemById(id, remoteApi::getLandingPadById, localApi::getLandingPadById)

    fun getLaunches() = fetchItems(remoteApi::getLaunches)
    fun getLaunchById(id: String) = fetchItemById(id, remoteApi::getLaunchById, localApi::getLaunchById)

    fun getPayloadById(id: String) = fetchItemById(id, remoteApi::getPayloadById, localApi::getPayloadById)

    fun getHistoryEvents() = fetchItems(remoteApi::getHistoryEvents)
}