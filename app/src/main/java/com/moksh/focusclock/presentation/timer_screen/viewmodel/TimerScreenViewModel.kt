package com.moksh.focusclock.presentation.timer_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimerScreenViewModel @Inject constructor() : ViewModel() {

    private val _timerState = MutableStateFlow(TimerScreenState())
    val timerState = _timerState.asStateFlow()

    private var timerJob: Job? = null

    fun onEvent(event: TimerScreenEvent) {
        when (event) {
            is TimerScreenEvent.StartTimer -> {
                startTimer()
            }

            is TimerScreenEvent.StopTimer -> {
                stopTimer()
            }

            is TimerScreenEvent.PauseTimer -> {
                if (_timerState.value.isPaused) {
                    if (timerJob?.isCancelled == true || timerJob == null) {
                        startTimer()
                    }
                    resumeTimer()
                } else {
                    pauseTimer()
                }
            }

            is TimerScreenEvent.ResetTimer -> {
                resetTimer()
            }

            is TimerScreenEvent.HourTextPressed -> {
                increaseHours()
            }

            is TimerScreenEvent.MinuteTextPressed -> {
                increaseMinutes()
            }

            is TimerScreenEvent.SecondsTextPressed -> {
                increaseSeconds()
            }

            is TimerScreenEvent.ToggleFullScreen -> {
                _timerState.value = _timerState.value.copy(
                    isFullScreen = !_timerState.value.isFullScreen
                )
            }
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                withContext(Dispatchers.Main) {
                    if (!_timerState.value.isPaused) {
                        updateTimer()
                    }
                }
                delay(1000) // Delay for 1 second
            }
        }
    }

    private fun updateTimer() {
        if (_timerState.value.time != 0L)
            _timerState.value = _timerState.value.copy(
                time = _timerState.value.time - 1
            )
        else
            timerJob?.cancel()
    }

    private fun pauseTimer() {
        _timerState.value = _timerState.value.copy(isPaused = true)
    }

    private fun resumeTimer() {
        _timerState.value = _timerState.value.copy(isPaused = false)
    }

    private fun resetTimer() {
        stopTimer()
        _timerState.value = _timerState.value.copy(time = 1500)
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    private fun increaseHours() {
        val currentTime = _timerState.value.time
        val currentHour = currentTime / 3600
        val currentMinute = (currentTime - (currentHour * 3600)) / 60
        val currentSeconds = currentTime - (currentHour * 3600) - (currentMinute * 60)
        val newHour = (currentHour + 1) % 12
        val newTime = newHour * 3600 + currentMinute * 60 + currentSeconds
        _timerState.value = _timerState.value.copy(
            time = newTime
        )
    }

    private fun increaseMinutes() {
        val currentTime = _timerState.value.time
        val currentHour = currentTime / 3600
        val currentMinute = (currentTime - (currentHour * 3600)) / 60
        val currentSeconds = currentTime - (currentHour * 3600) - (currentMinute * 60)
        val newMinute = (currentMinute + 1) % 60
        val newTime = currentHour * 3600 + newMinute * 60 + currentSeconds
        _timerState.value = _timerState.value.copy(
            time = newTime
        )
    }

    private fun increaseSeconds() {
        val currentTime = _timerState.value.time
        val currentHour = currentTime / 3600
        val currentMinute = (currentTime - (currentHour * 3600)) / 60
        val currentSeconds = currentTime - (currentHour * 3600) - (currentMinute * 60)
        val newSeconds = (currentSeconds + 1) % 60
        val newTime = currentHour * 3600 + currentMinute * 60 + newSeconds
        _timerState.value = _timerState.value.copy(
            time = newTime
        )
    }
}