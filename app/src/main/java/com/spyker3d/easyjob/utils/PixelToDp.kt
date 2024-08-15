package com.spyker3d.easyjob.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

private const val COEFFICIENT_PLATO_SUBTRACTION = 2
private const val COEFFICIENT_DIVIDER_FOR_UNIT = 0.25f
private const val COEFFICIENT_PIXEL_MULTIPLIER = 8.3f
fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)

fun convertDpToPixels(context: Context, dp: Float) = dp * context.resources.displayMetrics.density

fun exactDpToPx(context: Context, dp: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun exactDpToPxHeightBased(context: Context, dp: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return Math.round(dp * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun exactPxToDp(context: Context, px: Int): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun getCorrectionCoefficient(context: Context): Float {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return (displayMetrics.density - COEFFICIENT_PLATO_SUBTRACTION) /
        COEFFICIENT_DIVIDER_FOR_UNIT * COEFFICIENT_PIXEL_MULTIPLIER
}
