package com.hussein.openweather.ui.theme.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlidingText(text: String) {
    AnimatedContent(targetState = text, transitionSpec = {
        addAnimation().using(
            SizeTransform(clip = false)
        )
    }, label = "") { text ->
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 1200): ContentTransform {
    return (slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    )).togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    ))
}