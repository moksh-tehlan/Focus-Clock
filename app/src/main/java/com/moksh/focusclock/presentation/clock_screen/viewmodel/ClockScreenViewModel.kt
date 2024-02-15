package com.moksh.focusclock.presentation.clock_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClockScreenViewModel @Inject constructor() : ViewModel() {

    private val _clockState = MutableStateFlow(ClockScreenState())
    val clockState = _clockState.asStateFlow()

    init {
        startClock()
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis()
                _clockState.value = _clockState.value.copy(
                    time = currentTime
                )
                delay(1000)
            }
        }
    }

}