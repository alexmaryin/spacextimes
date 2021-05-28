package ru.alexmaryin.spacextimes_rx.data.api.filters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus
import ru.alexmaryin.spacextimes_rx.utils.Result
import ru.alexmaryin.spacextimes_rx.utils.Success
import ru.alexmaryin.spacextimes_rx.utils.toListOf
import ru.alexmaryin.spacextimes_rx.utils.toSuccess

infix fun Flow<Result>.filterLaunchesWith(filter: Set<String>): Flow<Result> = map { result ->
    if (result is Success<*>) {
        result.toListOf<Launch>()!!.filter { launch ->
            (launch.upcoming == "Upcoming" in filter || launch.upcoming != "Past" in filter) &&
                    (launch.success == "Successfully" in filter || launch.success != "Failed" in filter)
        }.toSuccess()
    } else {
        result
    }
}

infix fun Flow<Result>.filterCoresWith(filter: Set<String>): Flow<Result> = map { result ->
    if (result is Success<*>) {
        result.toListOf<Core>()!!.filter { core ->
            (core.totalFlights > 0 == "Flying" in filter || core.totalFlights == 0 == "Not flying" in filter) &&
                    (core.status < CoreStatus.INACTIVE == "Active" in filter || core.status >= CoreStatus.INACTIVE == "Inactive" in filter)
        }.toSuccess()
    } else {
        result
    }
}
