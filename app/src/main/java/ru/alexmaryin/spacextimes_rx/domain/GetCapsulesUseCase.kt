package ru.alexmaryin.spacextimes_rx.domain

import retrofit2.Response
import ru.alexmaryin.spacextimes_rx.data.base.Either
import ru.alexmaryin.spacextimes_rx.data.base.Failure
import ru.alexmaryin.spacextimes_rx.data.base.Success
import ru.alexmaryin.spacextimes_rx.data.base.UseCase
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.repository.SpacexDataRepository

class GetCapsulesUseCase : UseCase<Response<List<Capsule>>, SpacexDataRepository>() {

    override suspend fun run(params: SpacexDataRepository): Either<Exception, Response<List<Capsule>>> {
        return try {
            Success(params.getCapsules())
        } catch (e: java.lang.Exception) {
            Failure(e)
        }
    }
}