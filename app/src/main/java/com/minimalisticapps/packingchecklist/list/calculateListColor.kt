package com.minimalisticapps.packingchecklist.list

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

private fun subjectiveBrightness(color: Color): Double {
    return (0.21 * color.red) + (0.72 * color.green) + (0.07 * color.blue)
}

private fun randomColor(rnd: Random): Color {
    return Color(
        red = rnd.nextInt(256),
        blue = rnd.nextInt(256),
        green = rnd.nextInt(256),
    )
}

private fun distance(brightness: Double, min: Double, max: Double): Double {
    return min(abs(brightness - min), abs(brightness - max))
}

private fun randomColorWithGivenBrightness(name: String, min: Double, max: Double): Color {
    val rnd = Random(name.hashCode())

    var closestColor = randomColor(rnd)
    val closestBrightness = subjectiveBrightness(closestColor)
    var closestDistance = distance(closestBrightness, min, max)

    for (i in 1..40) {
        val color = randomColor(rnd)
        val brightness = subjectiveBrightness(color)
        if (min < brightness && brightness < max) {
            return color
        }

        val distance = distance(brightness, min, max)

        if (distance < closestDistance) {
            closestColor = color
            closestDistance = distance
        }
    }

    return closestColor
}

fun calculateListColor(name: String, darkTheme: Boolean): Color {
    if (darkTheme) {
        return randomColorWithGivenBrightness(name, 0.35, 0.5)
    }

    return randomColorWithGivenBrightness(name, 0.65, 0.75)
}
