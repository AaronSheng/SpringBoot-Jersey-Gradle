package com.aaron.config

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import javax.ws.rs.ApplicationPath

/**
 * Created by Aaron Sheng on 2018/6/12.
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(JerseyAutoConfiguration::class)
@Profile("prod")
@ApplicationPath("/api")
open class JerseyConfig : ResourceConfig(), ApplicationContextAware, InitializingBean {
    private lateinit var applicationContext: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    override fun afterPropertiesSet() {
        val restResources = applicationContext.getBeansWithAnnotation(RestResource::class.java)
        restResources.values.forEach {
            register(it)
        }
    }
}