package com.moksh.focusclock.presentation.timer_screen.viewmodel

data class TimerScreenState(
    val time: Long = 1500,
    val isPaused: Boolean = true,
    val isFullScreen: Boolean = false,
)