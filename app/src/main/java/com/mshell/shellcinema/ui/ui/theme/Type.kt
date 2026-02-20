package com.mshell.shellcinema.ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.mshell.shellcinema.R

val Provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val PoppinsFamily = FontFamily(
    Font(googleFont = GoogleFont("Poppins"), fontProvider = Provider),
    Font(googleFont = GoogleFont("Poppins"), fontProvider = Provider, weight = FontWeight.Bold),
    Font(googleFont = GoogleFont("Poppins"), fontProvider = Provider, weight = FontWeight.SemiBold),
)
val BebasNeueFamily = FontFamily(
    Font(googleFont = GoogleFont("Bebas Neue"), fontProvider = Provider),
    Font(googleFont = GoogleFont("Bebas Neue"), fontProvider = Provider, weight = FontWeight.Bold),
    Font(googleFont = GoogleFont("Bebas Neue"), fontProvider = Provider, weight = FontWeight.SemiBold),
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = BebasNeueFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = BebasNeueFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = BebasNeueFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = BebasNeueFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = BebasNeueFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = BebasNeueFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = BebasNeueFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = BebasNeueFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = BebasNeueFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = PoppinsFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = PoppinsFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = PoppinsFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = PoppinsFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = PoppinsFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = PoppinsFamily),
)


