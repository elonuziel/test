package com.matanh.transfer.server

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ChatRepository {
    private val _messages = mutableListOf<ChatMessage>()
    val messages: List<ChatMessage> get() = _messages.toList()

    private val _lastUpdateFlow = MutableStateFlow(0L)
    val lastUpdateFlow = _lastUpdateFlow.asStateFlow()

    fun addMessage(text: String) {
        val msg = ChatMessage(text = text, timestamp = System.currentTimeMillis())
        _messages.add(msg)
        if (_messages.size > 100) {
            _messages.removeAt(0)
        }
        _lastUpdateFlow.value = System.currentTimeMillis()
    }
}
