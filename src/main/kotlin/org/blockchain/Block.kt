package org.blockchain

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

data class Block(
    val index: Int,
    val timestamp: Long = Date().time,
    val proof: Long,
    val previousHash: String
){

    fun toJson(): String {
        val mapper = ObjectMapper()
        var json = ""
        try {
            json = mapper.writeValueAsString(this)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
        return json
    }


}


