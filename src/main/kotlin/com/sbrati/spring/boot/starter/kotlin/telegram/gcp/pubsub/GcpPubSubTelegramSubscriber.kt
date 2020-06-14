package com.sbrati.spring.boot.starter.kotlin.telegram.gcp.pubsub

import com.sbrati.spring.boot.starter.gcp.pubsub.GcpPubSubSubscriber
import com.sbrati.spring.boot.starter.kotlin.telegram.manager.TelegramManager
import com.sbrati.telegram.domain.Event
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

open class GcpPubSubTelegramSubscriber<T>(type: Class<T>, topicName: String) : GcpPubSubSubscriber<T>(type, topicName) {

    private val log by LoggerDelegate()

    @Autowired
    private lateinit var telegramManager: TelegramManager

    override fun process(event: Event<T>?) {
        if (event != null) {
            telegramManager.onEvent(event)
        } else {
            log.warn("Received an empty event.")
        }
    }
}

private class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    override fun getValue(thisRef: R, property: KProperty<*>): Logger =
        LoggerFactory.getLogger(getClassForLogging(thisRef.javaClass))
}

private fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf { it.kotlin.companionObject?.java == javaClass } ?: javaClass
}