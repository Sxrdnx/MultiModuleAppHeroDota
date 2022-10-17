package com.example.core

sealed class ProgressBarState{
    object Loading: ProgressBarState()

    object Idle: ProgressBarState()
}
