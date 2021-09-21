package com.fourthline.assignment.domain.usecase.base

/**
 * Created by Kadir Mert Özcan on 15-Jun-21.
 *
 * Base class for use cases
 */
abstract class UseCase<in P, R> {
    operator fun invoke(parameters: P): R = execute(parameters)
    protected abstract fun execute(parameters: P): R
}