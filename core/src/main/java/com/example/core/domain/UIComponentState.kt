package com.example.core.domain

sealed class UIComponentState{
    object Show: UIComponentState()

    object Hide: UIComponentState()
}
