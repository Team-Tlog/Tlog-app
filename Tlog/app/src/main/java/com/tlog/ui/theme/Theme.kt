package com.tlog.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.tlog.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun TlogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val MainFont = FontFamily(
    Font(R.font.main_font_medium, FontWeight.Medium),
    Font(R.font.main_font_bold, FontWeight.Bold),
    Font(R.font.main_font_extra_bold, FontWeight.ExtraBold),
    Font(R.font.main_font_semi_bold, FontWeight.SemiBold),
    Font(R.font.main_font_extra_light, FontWeight.ExtraLight),
    Font(R.font.main_font_thin, FontWeight.Thin),
    Font(R.font.main_font_regular, FontWeight.Normal),
    Font(R.font.main_font_light, FontWeight.Light),
    Font(R.font.main_font_black, FontWeight.Black)
)

val SubFont = FontFamily(
    Font(R.font.sub_font, FontWeight.Normal)
)

