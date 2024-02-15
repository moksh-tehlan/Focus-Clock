package com.moksh.focusclock.presentation.base

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.moksh.focusclock.R
import com.moksh.focusclock.presentation.clock_screen.ClockScreen
import com.moksh.focusclock.presentation.stopwatch_screen.StopwatchScreen
import com.moksh.focusclock.presentation.timer_screen.TimerScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseScreen(
    baseViewModel: BaseViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 1)
    val view = LocalView.current
    val window = (view.context as Activity).window
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    var isFullScreen by rememberSaveable { mutableStateOf(false) }
    var openAlertDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        baseViewModel.loadRewarded(context)
        if (isFullScreen) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        HorizontalPager(state = pagerState, userScrollEnabled = !isFullScreen) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                isFullScreen = if (isFullScreen) {
                                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                                    false
                                } else {
                                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                                    true
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                when (page) {
                    0 -> StopwatchScreen(isFullScreen = isFullScreen)
                    1 -> ClockScreen()
                    2 -> TimerScreen(isFullScreen = isFullScreen)
                }
            }
        }
        if (!isFullScreen) {
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { openAlertDialog = true }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = "info button",
                    tint = Color.White
                )
            }
        }
        if (openAlertDialog) {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false
                    scope.launch {
                        baseViewModel.showRewarded(
                            context = context,
                            onAdDismissed = {},
                            onRewardEarned = {
                            })
                    }
                },
                dialogTitle = "Developer Request",
                dialogText = "Empower the developer by watching a quick ad at your convenience. Your support keeps this app free for everyone. The choice is yours 🥺",
                icon = Icons.Default.Info
            )
        }
    }

}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(
                text = dialogText,
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Watch an ad")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}