package com.moksh.focusclock.presentation.clock_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moksh.focusclock.core.theme.ubuntuFont
import com.moksh.focusclock.core.utils.DateFormats
import com.moksh.focusclock.presentation.clock_screen.viewmodel.ClockScreenState
import com.moksh.focusclock.presentation.clock_screen.viewmodel.ClockScreenViewModel

@Composable
fun ClockScreen(viewModel: ClockScreenViewModel = hiltViewModel()) {
    ClockScreenView(state = viewModel.clockState.collectAsState().value)
}

@Composable
fun ClockScreenView(state: ClockScreenState) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    Scaffold(
        containerColor = Color.Black,
        contentColor = Color.White,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {
            if (isPortrait) {
                Column {
                    Text(
                        text = DateFormats.millisToHour(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 180.sp
                        )
                    )
                    Text(
                        text = DateFormats.millisToMinute(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 180.sp
                        )
                    )
                    val style = SpanStyle(
                        fontSize = 180.sp,
                        fontFamily = ubuntuFont
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = style) {
                                append(DateFormats.millisToSecond(state.time))
                            }
                            withStyle(style = style.copy(fontSize = 60.sp)) {
                                append(DateFormats.millisToAmPm(state.time))
                            }
                        },
                    )
                    Text(
                        text = DateFormats.millisToDay(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 55.sp
                        )
                    )
                }

            } else {
                val style = SpanStyle(
                    fontSize = 170.sp,
                    fontFamily = ubuntuFont
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.weight(25f))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = style) {
                                append(DateFormats.millisToHour(state.time) + ":")
                                append(DateFormats.millisToMinute(state.time) + ":")
                                append(DateFormats.millisToSecond(state.time))
                            }
                            withStyle(style = style.copy(fontSize = 55.sp)) {
                                append(DateFormats.millisToAmPm(state.time))
                            }
                        },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = DateFormats.millisToDay(state.time),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 55.sp
                        )
                    )
                }
            }
        }
    }

}

@Composable
@Preview(device = "spec:parent=pixel_5")
fun ClockScreenPreview() {
    ClockScreenView(state = ClockScreenState())
}