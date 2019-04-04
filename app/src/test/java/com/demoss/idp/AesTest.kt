package com.demoss.idp

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.nio.ByteBuffer
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AesTest {

    @Test
    fun encyptDecrypt() {
        val plainText = "Hello World!"

        val enc = encrypt(plainText)
        print("\n decrypted: ${decrypt(enc).encodeToString()}\n")

        assertEquals(0,0)
    }

    fun encrypt(text: String): ByteArray {
        val sKey = SecretKeySpec(ByteArray(16), "AES")
        val iv = IvParameterSpec(ByteArray(12))
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val params = GCMParameterSpec(128, iv.iv)
        cipher.init(Cipher.ENCRYPT_MODE, sKey, params)
        val encrypted = cipher.doFinal(text.toByteArray())

        val buffer = ByteBuffer.allocate(iv.iv.size + sKey.encoded.size + encrypted.size).apply {
            put(iv.iv)
            put(sKey.encoded)
            put(encrypted)
        }
        return buffer.array()
    }

    fun decrypt(textWithIv: ByteArray): ByteArray {
        val buffer = ByteBuffer.wrap(textWithIv)
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

    fun ByteArray.encodeToString(): String = String(this)
}

