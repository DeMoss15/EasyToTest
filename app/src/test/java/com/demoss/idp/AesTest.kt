package com.demoss.idp

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesTest {

    @Test
    fun encyptDecrypt() {
        val plainText = "Hello World!"

        val enc = plainText.encrypt()
        print("\n encrypted: $enc")
        val dec = enc.decrypt()
        print("\n decrypted: $dec\n")

        assertEquals(plainText, dec)
    }

    fun String.encrypt(): String = encrypt(this.toByteArray()).run {
        Base64.getEncoder().encodeToString(this)
    }

    fun String.decrypt(): String = Base64.getDecoder().decode(this).run {
        String(decrypt(this))
    }

    fun encrypt(data: ByteArray): ByteArray {
        val random = SecureRandom()
        val sKey = SecretKeySpec(ByteArray(16).apply { random.nextBytes(this) }, "AES")
        val iv = IvParameterSpec(ByteArray(12).apply { random.nextBytes(this) })
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val params = GCMParameterSpec(128, iv.iv)
        cipher.init(Cipher.ENCRYPT_MODE, sKey, params)
        val encrypted = cipher.doFinal(data)

        val buffer = ByteBuffer.allocate(iv.iv.size + sKey.encoded.size + encrypted.size).apply {
            put(iv.iv)
            put(sKey.encoded)
            put(encrypted)
        }
        return buffer.array()
    }

    fun decrypt(data: ByteArray): ByteArray {
        val buffer = ByteBuffer.wrap(data)
        val iv = ByteArray(12)
        buffer.get(iv)
        val encodedKey = ByteArray(16)
        buffer.get(encodedKey)
        val encrypted = ByteArray(buffer.remaining())
        buffer.get(encrypted)

        val sKey = SecretKeySpec(encodedKey, "AES")
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val params = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, sKey, params)

        return cipher.doFinal(encrypted)
    }
}

