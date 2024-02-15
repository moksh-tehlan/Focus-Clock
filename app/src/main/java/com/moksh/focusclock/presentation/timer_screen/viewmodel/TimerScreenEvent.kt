package com.moksh.focusclock.presentation.timer_screen.viewmodel

sealed class TimerScreenEvent {
    data object StartTimer : TimerScreenEvent()
    data object StopTimer : TimerScreenEvent()
    data object PauseTimer : TimerScreenEvent()
    data object ResetTimer : TimerScreenEvent()
    data object HourTextPressed : TimerScreenEvent()
    data object MinuteTextPressed : TimerScreenEvent()
    data object SecondsTextPressed : TimerScreenEvent()
    data object ToggleFullScreen : TimerScreenEvent()
}