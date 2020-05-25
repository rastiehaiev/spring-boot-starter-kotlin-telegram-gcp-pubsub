package com.sbrati.spring.boot.starter.kotlin.telegram.gcp.pubsub

import com.sbrati.spring.boot.starter.gcp.pubsub.GcpPubSubSubscriber
import com.sbrati.spring.boot.starter.kotlin.telegram.manager.TelegramManager
import com.sbrati.telegram.domain.Event
import org.springframework.beans.factory.annotation.Autowired

open class GcpPubSubTelegramSubscriber<T>(type: Class<T>, topicName: String) : GcpPubSubSubscriber<T>(type, topicName) {

    @Autowired
    private lateinit var telegramManager: TelegramManager

    override fun process(event: Event<T>?) {
        if (event != null) {
            telegramManager.onEvent(event)
        }
    }
}