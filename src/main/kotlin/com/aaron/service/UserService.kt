package com.aaron.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

/**
 * Created by Aaron Sheng on 2018/6/12.
 */
@Service
class UserService @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun getUser(id: String): String {
        redisTemplate.opsForValue().set(id, id)
        return redisTemplate.opsForValue().get(id)
    }
}