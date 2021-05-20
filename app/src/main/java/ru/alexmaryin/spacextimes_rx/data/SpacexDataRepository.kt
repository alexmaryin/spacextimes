package ru.alexmaryin.spacextimes_rx.data

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocal
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.LaunchFilter
import ru.alexmaryin.spacextimes_rx.utils.*
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(
    private val remoteApi: SpaceXApi,
    private val localApi: ApiLocal,
    private val networkHelper: NetworkHelper,
    ) {

    private inline fun <reified T> Response<*>.successList(): List<T> = when (body()) {
        is List<*> -> (body() as List<*>).map { it as T }
        is ApiResponse<*> -> ((body() as ApiResponse<*>).docs as List<*>).map { it as T }
        else -> throw TypeCastException("Unexpected response type")
    }

    private inline fun <reified T> Response<*>.successItem(): T = when (body()) {
        is T -> body() as T
        is ApiResponse<*> -> (body() as ApiResponse<*>).docs.first() as T
        else -> throw TypeCastException("Unexpected response type")
    }

    private inline fun <reified T, R> fetchItems(
        noinline apiCallback: suspend () -> Response<R>,
        noinline localApiCallback: suspend () -> List<T> = { emptyList() },
        noinline saveApiCallback: suspend (List<T>) -> Unit = {},
    ) = flow {
        emit(Loading)
        // First, try to load local data
        localApiCallback().apply { if (isNotEmpty()) emit(Success(this)) }
        // Then, try to fetch refreshed data from server and save to local cache after fetching
        if (networkHelper.isNetworkConnected()) {
            apiCallback().apply {
                if (isSuccessful) successList<T>().apply {
                    emit(Success(this))
                    saveApiCallback(this)
                }
                else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
            }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
    }.catch { e ->
        when (e) {
            is TypeCastException -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
            is SocketTimeoutException -> emit(Error("Server timeout", ErrorType.REMOTE_API_ERROR))
            else -> emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR))
        }
    }

    private inline fun <reified T, R> fetchItemById(
        id: String,
        noinline apiCallback: suspend (String) -> Response<R>,
        noinline localApiCallback: suspend (String) -> T? = { null },
    ) = flow {
        emit(Loading)
        localApiCallback(id)?.let { emit(Success(it)) } ?: if (networkHelper.isNetworkConnected()) {
            apiCallback(id).apply {
                if (isSuccessful) emit(Success(successItem<T>()))
                    else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
            }
        } else emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
    }.catch { e ->
        when (e) {
            is TypeCastException -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
            else -> emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR))
        }
    }

    fun getCapsules() = fetchItems(remoteApi::getCapsules, localApi::getCapsules, localApi::saveCapsules)
    fun getCapsuleById(id: String) = fetchItemById(id, remoteApi::getCapsuleById, localApi::getCapsuleById)

    fun getCores() = fetchItems(remoteApi::getCores, localApi::getCores, localApi::saveCores)
        .map { it.toListOf<Core>()?.sortedWith(compareBy(Core::block, Core::serial))?.reversed()?.toSuccess() ?: it }

    fun getCoreById(id: String) = fetchItemById(id, remoteApi::getCoreById, localApi::getCoreById)

    fun getCrew() = fetchItems(remoteApi::getCrew, localApi::getCrew)
    fun getCrewById(id: String) = fetchItemById(id, remoteApi::getCrewById, localApi::getCrewById)

    fun getDragons() = fetchItems(remoteApi::getDragons, localApi::getDragons)
    fun getDragonById(id: String) = fetchItemById(id, remoteApi::getDragonById, localApi::getDragonById)

    fun getRockets() = fetchItems(remoteApi::getRockets, localApi::getRockets)
    fun getRocketById(id: String) = fetchItemById(id, remoteApi::getRocketById, localApi::getRocketById)

    fun getLaunchPads() = fetchItems(remoteApi::getLaunchPads, localApi::getLaunchPads)
    fun getLaunchPadById(id: String) = fetchItemById(id, remoteApi::getLaunchPadById, localApi::getLaunchPadById)

    fun getLandingPads() = fetchItems(remoteApi::getLandingPads, localApi::getLandingPads)
    fun getLandingPadById(id: String) = fetchItemById(id, remoteApi::getLandingPadById, localApi::getLandingPadById)

    fun getLaunches() = fetchItems(remoteApi::getLaunches, localApi::getLaunches)
    fun getLaunchById(id: String) = fetchItemById(id, remoteApi::getLaunchById, localApi::getLaunchById)

    suspend fun filterLaunches(launchFilter: Set<LaunchFilter>) = localApi.getLaunches().run {
        val result = filter { launch ->
            (launch.upcoming == LaunchFilter.Upcoming in launchFilter || launch.upcoming != LaunchFilter.Past in launchFilter) &&
                    (launch.success == LaunchFilter.Successfully in launchFilter || launch.success != LaunchFilter.Failed in launchFilter)
        }
        Pair(result,  size)
    }

    suspend fun getNextLaunch(filter: Set<LaunchFilter>) = filterLaunches(filter).first.run {
        val position = indexOfLast {
            it.datePrecision >= DatePrecision.DAY && it.dateLocal > Calendar.getInstance().time
        }
        if (position > 0) Pair(position, get(position)) else null
    }

    fun getPayloadById(id: String) = fetchItemById(id, remoteApi::getPayloadById, localApi::getPayloadById)

    fun getHistoryEvents() = fetchItems(remoteApi::getHistoryEvents, localApi::getHistoryEvents)
}