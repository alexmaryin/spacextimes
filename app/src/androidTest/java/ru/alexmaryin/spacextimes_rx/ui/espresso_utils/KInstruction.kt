package ru.alexmaryin.spacextimes_rx.ui.espresso_utils

import android.os.Bundle

interface KInstruction {
    val dataContainer: Bundle?
    fun getDescription(): String
    fun checkCondition(): Boolean
}