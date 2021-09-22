package com.fourthline.assignment.domain.usecase.base

import com.fourthline.assignment.domain.model.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber


/**
 * Created by Kadir Mert Özcan on 15-Jun-21.
 *
 * Base class for use cases that utilize Flow
 * https://github.com/google/iosched/blob/main/shared/src/main/java/com/google/samples/apps/iosched/shared/domain/FlowUseCase.kt
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): Flow<UseCaseResult<R>> = execute(parameters)
        .catch { e ->
            Timber.e("Exception happened while executing, cause: ${e.message}")
            emit(UseCaseResult.Error(e))
        }
        .flowOn(coroutineDispatcher)

    protected abstract suspend fun execute(parameters: P): Flow<UseCaseResult<R>>
}