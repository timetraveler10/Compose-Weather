package com.hussein.openweather.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface RestartableStateFlow<out T> : StateFlow<T> {
    fun restart()
}

interface SharingRestartable : SharingStarted {
    fun restart()
}

private data class RestartableStateFlowImpl(
    private val sharingStarted: SharingStarted
) : SharingRestartable {
    private val restartFlow = MutableSharedFlow<SharingCommand>(extraBufferCapacity = 2)

    override fun restart() {
        restartFlow.tryEmit(SharingCommand.STOP_AND_RESET_REPLAY_CACHE)
        restartFlow.tryEmit(SharingCommand.START)
    }

    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> {
        return merge(restartFlow, sharingStarted.command(subscriptionCount))
    }
}

fun <T> Flow<T>.restartableStateIn(
    scope: CoroutineScope,
    started: SharingStarted,
    initialValue: T
): RestartableStateFlow<T> {
    val sharingRestartable = RestartableStateFlowImpl(started)
    val stateFlow = stateIn(scope = scope, started = started, initialValue = initialValue)
    return object : RestartableStateFlow<T>, StateFlow<T> by stateFlow {
        override fun restart() {
            sharingRestartable.restart()
        }
    }
}

fun <T> Flow<T>.asMutableStateFlow(
    scope: CoroutineScope,
    initialValue: T,
    sharingStarted: SharingStarted
): MutableStateFlow<T> {
    val flow = MutableStateFlow(initialValue)
    scope.launch {
        this@asMutableStateFlow.collect(flow)
    }
    return flow
}