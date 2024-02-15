package com.moksh.focusclock.presentation.stopwatch_screen.viewmodel

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
class StopwatchScreenViewModel @Inject constructor() : ViewModel() {

    private val _stopwatchState = MutableStateFlow(StopwatchScreenState())
    val stopwatchState = _stopwatchState.asStateFlow()

    private var timerJob: Job? = null

    fun onEvent(event: StopwatchScreenEvent) {
        when (event) {
            is StopwatchScreenEvent.StartStopwatch -> {
                startTimer()
            }

            is StopwatchScreenEvent.StopStopwatch -> {
                stopTimer()
            }

            is StopwatchScreenEvent.PauseStopwatch -> {
                if (_stopwatchState.value.isPaused) {
                    if (timerJob?.isCancelled == true || timerJob == null) {
                        startTimer()
                    }
                    resumeTimer()
                } else {
                    pauseTimer()
                }
            }

            is StopwatchScreenEvent.ResetStopwatch -> {
                resetTimer()
            }

        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                withContext(Dispatchers.Main) {
                    if (!_stopwatchState.value.isPaused) {
                        updateTimer()
                    }
                }
                delay(1000) // Delay for 1 second
            }
        }
    }

    private fun updateTimer() {
        _stopwatchState.value = _stopwatchState.value.copy(
            time = _stopwatchState.value.time + 1
        )
    }

    private fun pauseTimer() {
        _stopwatchState.value = _stopwatchState.value.copy(isPaused = true)
    }

    private fun resumeTimer() {
        _stopwatchState.value = _stopwatchState.value.copy(isPaused = false)
    }

    private fun resetTimer() {
        stopTimer()
        _stopwatchState.value = _stopwatchState.value.copy(time = 0)
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }
}