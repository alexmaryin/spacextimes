package ru.alexmaryin.spacextimes_rx.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.api.local.ApiLocal
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.api.ApiResponse
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.enums.DatePrecision
import ru.alexmaryin.spacextimes_rx.di.SettingsRepository
import ru.alexmaryin.spacextimes_rx.utils.*
import java.net.SocketTimeoutException
import java.util.*
import javax.inject.Inject

class SpacexDataRepository @Inject constructor(
    private val remoteApi: SpaceXApi,
    private val localApi: ApiLocal,
    private val networkHelper: NetworkHelper,
    private val settings: SettingsRepository,
) {

    private inline fun <reified T> fetchItems(
        noinline apiCallback: suspend () -> Response<ApiResponse<T>>,
        noinline localApiCallback: suspend () -> List<T> = { emptyList() },
        noinline saveApiCallback: suspend (List<T>) -> Unit = {},
    ) = settings.checkNeedSync(T::class.simpleName!!).take(1).transform { needSync ->
        Log.d("REPOSITORY", "Requesting for ${T::class.simpleName!!} at ${Calendar.getInstance().time}")
        var remoteFailed = true
        emit(Loading)
        localApiCallback().run {
            if (needSync || isEmpty()) {
                if (networkHelper.isNetworkConnected()) {
                    apiCallback().apply {
                        if (isSuccessful) body()?.docs?.apply {
                            settings.saveLastSync(T::class.simpleName!!)
                            settings.armSynchronize = false
                            remoteFailed = false
                            emit(Success(this))
                            saveApiCallback(this)
                            Log.d("REPOSITORY", "Fetched from internet list of ${T::class.simpleName} total count $size")
                        }
                        else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
                    }
                } else {
                    emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
                }
            }

            if (remoteFailed)
                emit(if (isNotEmpty()) Success(this) else Error("No local saved data", ErrorType.NO_SAVED_DATA))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            when (e) {
                is TypeCastException -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                is SocketTimeoutException -> emit(Error("Server timeout", ErrorType.REMOTE_API_ERROR))
                else -> emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR))
            }
        }

    private inline fun <reified T : HasStringId> fetchItemById(
        id: String,
        noinline apiCallback: suspend (String) -> Response<ApiResponse<T>>,
        noinline localApiCallback: suspend (String) -> T? = { null },
        noinline saveApiCallback: suspend (T) -> Unit = {}
    ) = flow {
        Log.d("REPOSITORY", "Requesting for ${T::class.simpleName!!} with id:$id at ${Calendar.getInstance().time}")
        var remoteFailed = true
        emit(Loading)
        localApiCallback(id).run {
            if (this == null || settings.armSynchronize) {
                if (networkHelper.isNetworkConnected()) {
                    apiCallback(id).apply {
                        if (isSuccessful) body()?.docs?.apply {
                            settings.armSynchronize = false
                            remoteFailed = false
                            emit(Success(first()))
                            saveApiCallback(first())
                            Log.d("REPOSITORY", "Fetched from internet ${T::class.simpleName} with id:${first().id}")
                        }
                        else emit(Error(errorBody().toString(), ErrorType.REMOTE_API_ERROR))
                    }
                } else  emit(Error("No internet connection!", ErrorType.NO_INTERNET_CONNECTION))
            }

            if (remoteFailed)
                emit(if (this != null) Success(this) else Error("No local saved data", ErrorType.NO_SAVED_DATA))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            when (e) {
                is TypeCastException -> emit(Error("Unexpected response type", ErrorType.OTHER_ERROR))
                else -> emit(Error(e.localizedMessage ?: "Unknown error", ErrorType.OTHER_ERROR))
            }
        }

    fun getCapsules() = fetchItems(remoteApi::getCapsules, localApi::getCapsules, localApi::saveCapsules)
    fun getCapsuleById(id: String) = fetchItemById(id, remoteApi::getCapsuleById, localApi::getCapsuleById, localApi::saveCapsuleDetails)

    fun getCores() = fetchItems(remoteApi::getCores, localApi::getCores, localApi::saveCores)
        .map { it.toListOf<Core>()?.sortedWith(compareBy(Core::block, Core::serial))?.reversed()?.toSuccess() ?: it }

    fun getCoreById(id: String) = fetchItemById(id, remoteApi::getCoreById, localApi::getCoreById, localApi::saveCoreDetails)

    fun getCrew() = fetchItems(remoteApi::getCrew, localApi::getCrew, localApi::saveCrew)
    fun getCrewById(id: String) = fetchItemById(id, remoteApi::getCrewById, localApi::getCrewById, localApi::saveCrewDetails)

    fun getDragons() = fetchItems(remoteApi::getDragons, localApi::getDragons, localApi::saveDragons)
    fun getDragonById(id: String) = fetchItemById(id, remoteApi::getDragonById, localApi::getDragonById)

    fun getRockets() = fetchItems(remoteApi::getRockets, localApi::getRockets, localApi::saveRockets)
    fun getRocketById(id: String) = fetchItemById(id, remoteApi::getRocketById, localApi::getRocketById)

    fun getLaunchPads() = fetchItems(remoteApi::getLaunchPads, localApi::getLaunchPads, localApi::saveLaunchPads)
    fun getLaunchPadById(id: String) = fetchItemById(id, remoteApi::getLaunchPadById, localApi::getLaunchPadById)

    fun getLandingPads() = fetchItems(remoteApi::getLandingPads, localApi::getLandingPads, localApi::saveLandingPads)
    fun getLandingPadById(id: String) = fetchItemById(id, remoteApi::getLandingPadById, localApi::getLandingPadById)

    fun getLaunches() = fetchItems(remoteApi::getLaunches, localApi::getLaunches, localApi::saveLaunches)
    fun getLaunchById(id: String) = fetchItemById(id, remoteApi::getLaunchById, localApi::getLaunchById, localApi::saveLaunchDetails)
    fun getNextLaunch(launches: List<Launch>) = launches.indexOfLast { it.datePrecision >= DatePrecision.DAY && it.dateLocal > Calendar.getInstance().time }

    fun getPayloadById(id: String) = fetchItemById(id, remoteApi::getPayloadById, localApi::getPayloadById)

    fun getHistoryEvents() = fetchItems(remoteApi::getHistoryEvents, localApi::getHistoryEvents, localApi::saveHistoryEvents)
}