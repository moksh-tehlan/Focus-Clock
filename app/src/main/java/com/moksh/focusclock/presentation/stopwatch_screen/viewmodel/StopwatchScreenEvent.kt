package com.moksh.focusclock.presentation.stopwatch_screen.viewmodel

sealed class StopwatchScreenEvent {
    data object StartStopwatch : StopwatchScreenEvent()
    data object StopStopwatch : StopwatchScreenEvent()
    data object PauseStopwatch : StopwatchScreenEvent()
    data object ResetStopwatch : StopwatchScreenEvent()
}