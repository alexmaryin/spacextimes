package ru.alexmaryin.spacextimes_rx.data.repository

import android.util.Log
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*
import ru.alexmaryin.spacextimes_rx.utils.*
import java.io.IOException
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(
    private val remoteApi: SpaceXApi,
    private val localApi: ApiLocal,
    private val networkHelper: NetworkHelper,
    ) {

    private inline fun <reified T, R> fetchItems(
        noinline apiCallback: suspend () -> Response<R>,
        processCallbacks: List<suspend (List<T>?) -> Unit> = emptyList(),
    ) = flow {
        emit(Loading)
        if (networkHelper.isNetworkConnected()) {
            try {
                apiCallback().apply {
                    if (isSuccessful) {
                        try {
                            emit(Success(when (body()) {
                                is List<*> -> (body() as List<*>).apply {
                                    processCallbacks.forEach { process -> process(this.map { it as T }) }
                                }
                                is ApiResponse<*> -> ((body() as ApiResponse<*>).docs as List<*>).apply {
                                    processCallbacks.forEach { process -> process(this.map { it as T }) }
                                }
                                else -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                            }))
                        } catch (e: IOException) {
                            emit(Error("Translator error: ${e.localizedMessage}", ErrorType.REMOTE_TRANSLATOR_ERROR))
                        }
                    } else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
                }
            } catch (e: Exception) { emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR)) }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
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

    fun getCapsules(processCallbacks: List<suspend (List<Capsules>?) -> Unit>) = fetchItems(remoteApi::getCapsules, processCallbacks)
    fun getCapsuleById(id: String) = fetchItemById(id, remoteApi::getCapsuleById, localApi::getCapsuleById)

    fun getCores(processCallbacks: List<suspend (List<Cores>?) -> Unit>) = fetchItems(remoteApi::getCores, processCallbacks)
    fun getCoreById(id: String) = fetchItemById(id, remoteApi::getCoreById, localApi::getCoreById)

    fun getCrew() = fetchItems<Crews, List<Crews>>(remoteApi::getCrew)
    fun getCrewById(id: String) = fetchItemById(id, remoteApi::getCrewById, localApi::getCrewById)

    fun getDragons(processCallbacks: List<suspend (List<Dragon>?) -> Unit>) = fetchItems(remoteApi::getDragons, processCallbacks)
    fun getDragonById(id: String) = fetchItemById(id, remoteApi::getDragonById, localApi::getDragonById)

    fun getRockets(processCallbacks: List<suspend (List<Rocket>?) -> Unit>) = fetchItems(remoteApi::getRockets, processCallbacks)
    fun getRocketById(id: String) = fetchItemById(id, remoteApi::getRocketById, localApi::getRocketById)

    fun getLaunchPads(processCallbacks: List<suspend (List<LaunchPads>?) -> Unit>) = fetchItems(remoteApi::getLaunchPads, processCallbacks)
    fun getLaunchPadById(id: String) = fetchItemById(id, remoteApi::getLaunchPadById, localApi::getLaunchPadById)

    fun getLandingPads(processCallbacks: List<suspend (List<LandingPads>?) -> Unit>) = fetchItems(remoteApi::getLandingPads, processCallbacks)
    fun getLandingPadById(id: String) = fetchItemById(id, remoteApi::getLandingPadById, localApi::getLandingPadById)

    fun getLaunches(processCallbacks: List<suspend (List<Launches>?) -> Unit>) = fetchItems(remoteApi::getLaunches, processCallbacks)
    fun getLaunchById(id: String) = fetchItemById(id, remoteApi::getLaunchById, localApi::getLaunchById)

    fun getPayloadById(id: String) = fetchItemById(id, remoteApi::getPayloadById, localApi::getPayloadById)

    fun getHistoryEvents(processCallbacks: List<suspend (List<History>?) -> Unit>) = fetchItems(remoteApi::getHistoryEvents, processCallbacks)
}