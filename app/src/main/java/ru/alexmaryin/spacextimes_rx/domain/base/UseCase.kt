package ru.alexmaryin.spacextimes_rx.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type: Any {

    abstract suspend fun run(params: Params): Either<Exception, Type>

    suspend operator fun invoke(params: Params, onSuccess: (Type) -> Unit, onFailure: (Exception) -> Unit) {
        val result = run(params)
        coroutineScope {
            launch(Dispatchers.Main) {
                result.fold(
                    failed = { onFailure(it) },
                    succeeded = { onSuccess(it) }
                )
            }
        }
    }
}