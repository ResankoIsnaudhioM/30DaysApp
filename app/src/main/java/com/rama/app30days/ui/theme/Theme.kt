package com.rama.app30days.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightRecipeColorScheme = lightColorScheme(

    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryLightColor,
    onPrimaryContainer = PrimaryDarkColor,

    secondary = SecondaryColor,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryLightColor,
    onSecondaryContainer = SecondaryDarkColor,

    background = BackgroundLight,
    onBackground = TextLight,
    surface = SurfaceLight,

    tertiary = Orange500,
    onTertiary = Color.White,
    error = Color(0xFFB00020),
    onError = Color.White,
    surfaceVariant = PrimaryLightColor
)

private val DarkRecipeColorScheme = darkColorScheme(
    primary = PrimaryLightColor,
    onPrimary = Color.Black,
    primaryContainer = PrimaryDarkColor,
    onPrimaryContainer = PrimaryLightColor,

    secondary = SecondaryLightColor,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryDarkColor,
    onSecondaryContainer = SecondaryLightColor,

    background = BackgroundDark,
    onBackground = TextDark,
    surface = SurfaceDark,
    onSurface = TextDark,

    tertiary = Orange500,
    onTertiary = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    surfaceVariant = PrimaryDarkColor
)

@Composable
fun App30DaysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkRecipeColorScheme
        else -> LightRecipeColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}