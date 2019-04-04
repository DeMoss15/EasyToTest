package com.demoss.idp

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.security.MessageDigest
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class ShaTest {
    @Test
    fun testSha256() {
        val text = "Hello World!"
        val enc = encryptWithSha256(text)
        assertEquals(enc, encryptWithSha256(text))
    }

    fun encryptWithSha256(plainText: String): String = MessageDigest.getInstance("SHA-256")
        .digest(plainText.toByteArray())
        .encodeToString()

    @Test
    fun testHmacSha256() {
        val alogrithm = "HmacSHA256"
        val text = "Hello World!"
        val salt = KeyGenerator.getInstance(alogrithm).generateKey().encoded.encodeToString()
        val enc = encryptWithHmacSha256(salt, text)
        assertEquals(enc, encryptWithHmacSha256(salt, text))
    }

    fun encryptWithHmacSha256(salt: String, plainText: String): String {
        val algorithm = "HmacSHA256"
        val sKey = SecretKeySpec(salt.toByteArray(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(sKey)
        return mac.doFinal(plainText.toByteArray()).encodeToString()
    }

    fun ByteArray.encodeToString(): String = Base64.getEncoder().encodeToString(this)
}