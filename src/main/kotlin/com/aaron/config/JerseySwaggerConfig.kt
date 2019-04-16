package com.aaron.config

import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import io.swagger.jaxrs.listing.SwaggerSerializers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Created by Aaron Sheng on 2018/6/12.
 */

@Configuration
@ConditionalOnWebApplication
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(JerseyAutoConfiguration::class)
@Profile("!prod")
class JerseySwaggerConfig : JerseyConfig() {
    @Value("\${spring.application.desc:#{null}}")
    private val applicationDesc: String? = null

    @Value("\${spring.application.version:#{null}}")
    private val applicationVersion: String? = null

    @Value("\${spring.application.packageName:#{null}}")
    private val packageName: String? = null

    @PostConstruct
    fun init() {
        configSwagger()
        register(SwaggerSerializers::class.java)
        register(ApiListingResource::class.java)
    }

    private fun configSwagger() {
        if (packageName != null && packageName.isNotBlank()) {
            BeanConfig().apply {
                title = applicationDesc
                version = applicationVersion
                resourcePackage = packageName
                scan = true
                basePath = "/api"
            }
        }
    }
}