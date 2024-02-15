package com.moksh.focusclock.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moksh.focusclock.R

val ubuntuFont = FontFamily(
    Font(R.font.ubuntu_regular,FontWeight.Normal),
    Font(R.font.ubuntu_bold,FontWeight.Bold),
    Font(R.font.ubuntu_medium,FontWeight.Medium),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),

    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),

    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
    ),

    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
    ),

    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
    ),
    labelLarge = TextStyle(
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),

    labelMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
    ),

    labelSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = TextStyle(
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
    ),
)