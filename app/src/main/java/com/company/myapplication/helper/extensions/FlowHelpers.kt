package com.company.myapplication.helper.extensions

import androidx.lifecycle.LifecycleCoroutineScope
import com.company.myapplication.helper.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.launchWhenResumedIn(scope: LifecycleCoroutineScope): Job = scope.launchWhenResumed {
    collect()
}

fun <T> Flow<Event<T>>.onEachEventWhenResumed(
    scope: LifecycleCoroutineScope,
    action: suspend (T) -> Unit
) = onEachEvent { action(it) }
    .launchWhenResumedIn(scope)

fun <T> Flow<T>.onEachWhenResumed(
    scope: LifecycleCoroutineScope,
    action: suspend (T) -> Unit
) = onEach { action(it) }
    .launchWhenResumedIn(scope)


fun <T> Flow<Event<T>>.onEachEvent(block: suspend (T) -> Unit) = onEach {
    val content = it.getContent() ?: return@onEach
    block(content)
}

fun <T> Flow<T>.shared(coroutineScope: CoroutineScope) = shareIn(
    coroutineScope,
    SharingStarted.Lazily,
    1
)

fun <T> Flow<T>.retryPeriodically(interval: Long = 2000L) = retry { true.also { delay(interval) } }