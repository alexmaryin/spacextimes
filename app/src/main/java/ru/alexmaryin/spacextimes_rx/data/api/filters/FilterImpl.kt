package ru.alexmaryin.spacextimes_rx.data.api.filters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess

private inline fun <reified T> Flow<Result>.flowFilter(crossinline predicate: (T) -> Boolean): Flow<Result> = map { result ->
    if (result is Success<*>) {
        result.toListOf<T>()!!.filter { predicate(it) }.toSuccess()
    } else result
}

fun Flow<Result>.filterLaunchesWith(filter: Set<String>): Flow<Result> = flowFilter<Launch> { launch ->
    (launch.upcoming == "Upcoming" in filter || launch.upcoming != "Past" in filter) &&
            (launch.success == "Successfully" in filter || launch.success != "Failed" in filter)
}


infix fun Flow<Result>.filterCoresWith(filter: Set<String>): Flow<Result> = flowFilter<Core> { core ->
    (core.totalFlights > 0 == "Flying" in filter || core.totalFlights == 0 == "Not flying" in filter) &&
            (core.status < CoreStatus.INACTIVE == "Active" in filter || core.status >= CoreStatus.INACTIVE == "Inactive" in filter)
}

infix fun Flow<Result>.filterCapsulesWith(filter: Set<String>): Flow<Result> = flowFilter<Capsule> { capsule ->
    (capsule.totalFlights > 0 == "Flying" in filter || capsule.totalFlights == 0 == "Not flying" in filter) &&
            (capsule.status < CapsuleStatus.UNKNOWN == "Active" in filter || capsule.status >= CapsuleStatus.UNKNOWN == "Inactive" in filter)
}
