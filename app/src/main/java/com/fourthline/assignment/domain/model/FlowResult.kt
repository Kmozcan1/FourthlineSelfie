/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fourthline.assignment.domain.model

import androidx.lifecycle.MutableLiveData
import com.fourthline.assignment.domain.model.FlowResult.Success
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class FlowResult<out R> {

    data class Success<out T>(val data: T) : FlowResult<T>()
    data class Error(val exception: Throwable) : FlowResult<Nothing>()
    object Loading : FlowResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [FlowResult] is of type [Success] & holds non-null [Success.data].
 */
val FlowResult<*>.succeeded
    get() = this is Success && data != null

fun <T> FlowResult<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

val <T> FlowResult<T>.data: T?
    get() = (this as? Success)?.data

/**
 * Updates value of [liveData] if [FlowResult] is of type [Success]
 */
inline fun <reified T> FlowResult<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is Success) {
        liveData.value = data
    }
}
/**
 * Updates value of [MutableStateFlow] if [FlowResult] is of type [Success]
 */
inline fun <reified T> FlowResult<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Success) {
        stateFlow.value = data
    }
}
