package com.example.core.domain

sealed class FilterOrder{
    object Ascending: FilterOrder()

    object Descending: FilterOrder()
}
