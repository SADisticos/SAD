package com.example.isomapp

import kotlin.properties.Delegates

object ConnectionState {
    var refreshListListeners = ArrayList<() -> Unit>()

    // fires off every time value of the property changes
    var state: String by Delegates.observable("initial value") { property, oldValue, newValue ->
        refreshListListeners.forEach {
            it()
        }
    }
}