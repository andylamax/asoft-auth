package tz.co.asoft.auth.tools.channel

import kotlinx.coroutines.channels.Channel

class StatefulChannel<T>(private val channel: Channel<T> = Channel()) : Channel<T> by channel {

    private var value: T? = null
    
    override suspend fun send(element: T) {
        value = element
        channel.send(element)
    }

    fun value() = value
}