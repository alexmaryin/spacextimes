package ru.alexmaryin.spacextimes_rx.data.api.filters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexmaryin.spacextimes_rx.data.model.Launch
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
