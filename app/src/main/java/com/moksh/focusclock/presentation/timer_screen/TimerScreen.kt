package com.moksh.focusclock.presentation.timer_screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moksh.focusclock.R
import com.moksh.focusclock.core.utils.DateFormats
import com.moksh.focusclock.presentation.timer_screen.viewmodel.TimerScreenEvent
import com.moksh.focusclock.presentation.timer_screen.viewmodel.TimerScreenState
import com.moksh.focusclock.presentation.timer_screen.viewmodel.TimerScreenViewModel

@Composable
fun TimerScreen(viewModel: TimerScreenViewModel = hiltViewModel(), isFullScreen: Boolean) {
    TimerScreenView(
        state = viewModel.timerState.collectAsState().value,
        event = viewModel::onEvent,
        isFullScreen = isFullScreen,
    )
}

@Composable
fun TimerScreenView(
    state: TimerScreenState,
    event: (TimerScreenEvent) -> Unit,
    isFullScreen: Boolean,
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        containerColor = Color.Black,
        contentColor = Color.White,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (!isFullScreen && isPortrait) {
                TimerActionButton(
                    isPortrait = true,
                    state = state,
                    onPlayPauseClick = {
                        event(TimerScreenEvent.PauseTimer)
                    },
                    onResetClick = { event(TimerScreenEvent.ResetTimer) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            if (isPortrait) {
                Column {
                    Text(
                        modifier = Modifier.pointerInput(state.isPaused) {
                            if (state.isPaused) {
                                detectTapGestures(
                                    onTap = { event(TimerScreenEvent.HourTextPressed) },
                                )
                            }
                        },
                        text = DateFormats.formatSecondsToHour(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 180.sp
                        )
                    )
                    Text(
                        modifier = Modifier.pointerInput(state.isPaused) {
                            if (state.isPaused) {
                                detectTapGestures(
                                    onTap = { event(TimerScreenEvent.MinuteTextPressed) },
                                )
                            }
                        },
                        text = DateFormats.formatSecondsToMinute(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 180.sp
                        )
                    )
                    Text(
                        modifier = Modifier.pointerInput(state.isPaused) {
                            if (state.isPaused) {
                                detectTapGestures(
                                    onTap = { event(TimerScreenEvent.SecondsTextPressed) },
                                )
                            }
                        },
                        text = DateFormats.formatSecondsToSeconds(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 180.sp
                        )
                    )
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    if(!isFullScreen)Spacer(modifier = Modifier.weight(25f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.pointerInput(state.isPaused) {
                                if (state.isPaused) {
                                    detectTapGestures(
                                        onTap = { event(TimerScreenEvent.HourTextPressed) },
                                    )
                                }
                            },
                            text = DateFormats.formatSecondsToHour(state.time) + ":",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 190.sp
                            )
                        )
                        Text(
                            modifier = Modifier.pointerInput(state.isPaused) {
                                if (state.isPaused) {
                                    detectTapGestures(
                                        onTap = { event(TimerScreenEvent.MinuteTextPressed) },
                                    )
                                }
                            },
                            text = DateFormats.formatSecondsToMinute(state.time) + ":",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 190.sp
                            )
                        )
                        Text(
                            modifier = Modifier.pointerInput(state.isPaused) {
                                if (state.isPaused) {
                                    detectTapGestures(
                                        onTap = { event(TimerScreenEvent.SecondsTextPressed) },
                                    )
                                }
                            },
                            text = DateFormats.formatSecondsToSeconds(state.time),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 190.sp
                            )
                        )
                    }
                    if(!isFullScreen){
                        Spacer(modifier = Modifier.weight(1f))
                        TimerActionButton(
                            isPortrait = false,
                            state = state,
                            onPlayPauseClick = {
                                event(TimerScreenEvent.PauseTimer)
                            },
                            onResetClick = { event(TimerScreenEvent.ResetTimer) }
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun TimerActionButton(
    state: TimerScreenState,
    onPlayPauseClick: () -> Unit,
    onResetClick: () -> Unit,
    isPortrait: Boolean,
) {
    if (isPortrait) {
        Column {
            FloatingButton(
                resourceId = R.drawable.restart,
                isVisible = state.isPaused,
                onClick = { onResetClick() },
            )
            Box(contentAlignment = Alignment.Center) {
                FloatingButton(
                    resourceId = R.drawable.play_arrow,
                    isVisible = state.isPaused,
                    onClick = { onPlayPauseClick() },
                )
                FloatingButton(
                    resourceId = R.drawable.pause_arrow,
                    isVisible = !state.isPaused,
                    onClick = { onPlayPauseClick() },
                )
            }
        }
    } else {
        Row{
            FloatingButton(
                resourceId = R.drawable.restart,
                isVisible = state.isPaused,
                onClick = { onResetClick() },
            )
            FloatingButton(
                resourceId = R.drawable.play_arrow,
                isVisible = state.isPaused,
                onClick = { onPlayPauseClick() },
            )
            FloatingButton(
                resourceId = R.drawable.pause_arrow,
                isVisible = !state.isPaused,
                onClick = { onPlayPauseClick() },
            )
        }
    }
}

@Composable
private fun FloatingButton(isVisible: Boolean = true, resourceId: Int, onClick: () -> Unit) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .size(75.dp)
                .clip(RoundedCornerShape(50))
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = resourceId),
                contentDescription = "play pause button",
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview(device = "spec:parent=pixel_5")
fun TimerScreenPreviewScreen() {
    TimerScreenView(
        state = TimerScreenState(12312, isPaused = true, isFullScreen = true),
        event = {},
        isFullScreen = false,
    )
}