package ru.alexmaryin.spacextimes_rx.data.repository

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.model.*
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
        noinline processTranslate: suspend (List<T>?) -> Unit = {},
    ) = flow {
        emit(Loading)
        if (networkHelper.isNetworkConnected()) {
            try {
                apiCallback().apply {
                    if (isSuccessful) {
                        try {
                            emit(Success(when (body()) {
                                is List<*> -> (body() as List<*>).apply { processTranslate(this.map { it as T }) }
                                is ApiResponse<*> -> (body() as ApiResponse<*>).docs.apply { processTranslate(this.map { it as T }) }
                                else -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                            }))
                        } catch (e: IOException) {
                            emit(Error("Translator error: ${e.localizedMessage}", ErrorType.REMOTE_TRANSLATOR_ERROR))
                        }
//                    TODO("Save to local")
                    } else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
                }
            } catch (e: IOException) { emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.REMOTE_API_ERROR)) }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
//        TODO("Let's load from cache if network is unavailable.")
    }

    private fun <T> fetchItemById(
        id: String,
        apiCallback: suspend (String) -> Response<T>,
        localApiCallback: (String) -> T? = { null },
    ) = flow {
        emit(Loading)
        localApiCallback(id)?.let {
            emit(Success(it))
        } ?: if (networkHelper.isNetworkConnected()) {
            apiCallback(id).apply {
                if (isSuccessful) emit(Success(body())) else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
            }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
    }

    fun getCapsules(processTranslate: suspend (List<Capsule>?) -> Unit) = fetchItems(remoteApi::getCapsules, processTranslate)
    fun getCapsuleById(id: String) = fetchItemById(id, remoteApi::getCapsuleById, localApi::getCapsuleById)

    fun getCores(processTranslate: suspend (List<Core>?) -> Unit) = fetchItems(remoteApi::getCores, processTranslate)
    fun getCoreById(id: String) = fetchItemById(id, remoteApi::getCoreById, localApi::getCoreById)

    fun getCrew() = fetchItems<Crew, List<Crew>>(remoteApi::getCrew)
    fun getCrewById(id: String) = fetchItemById(id, remoteApi::getCrewById, localApi::getCrewById)

    fun getDragons(processTranslate: suspend (List<Dragon>?) -> Unit) = fetchItems(remoteApi::getDragons, processTranslate)
    fun getDragonById(id: String) = fetchItemById(id, remoteApi::getDragonById, localApi::getDragonById)

    fun getRockets(processTranslate: suspend (List<Rocket>?) -> Unit) = fetchItems(remoteApi::getRockets, processTranslate)
    fun getRocketById(id: String) = fetchItemById(id, remoteApi::getRocketById, localApi::getRocketById)

    fun getLaunchPads(processTranslate: suspend (List<LaunchPad>?) -> Unit) = fetchItems(remoteApi::getLaunchPads, processTranslate)
    fun getLaunchPadById(id: String) = fetchItemById(id, remoteApi::getLaunchPadById, localApi::getLaunchPadById)

    fun getLandingPads(processTranslate: suspend (List<LandingPad>?) -> Unit) = fetchItems(remoteApi::getLandingPads, processTranslate)
    fun getLandingPadById(id: String) = fetchItemById(id, remoteApi::getLandingPadById, localApi::getLandingPadById)

    fun getLaunches() = fetchItems<Launch, ApiResponse<Launch>>(remoteApi::getLaunches)
    fun getLaunchById(id: String) = fetchItemById(id, remoteApi::getLaunchById, localApi::getLaunchById)

}