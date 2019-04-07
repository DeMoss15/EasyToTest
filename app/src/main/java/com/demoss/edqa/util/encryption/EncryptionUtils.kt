package com.demoss.edqa.util.encryption

import android.util.Base64
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM_AES = "AES"
private const val ALGORITHM_AES_GCM = "AES/GCM/NoPadding"

fun String.encrypt(): String = encrypt(this.toByteArray()).run {
    Base64.encodeToString(this, Base64.DEFAULT)
}

fun String.decrypt(): String = Base64.decode(this, Base64.DEFAULT).run {
    String(decrypt(this))
}

private fun encrypt(data: ByteArray): ByteArray {
    val random = SecureRandom()
    val sKey = SecretKeySpec(ByteArray(16).apply { random.nextBytes(this) }, "AES")
    val iv = IvParameterSpec(ByteArray(12).apply { random.nextBytes(this) })
    val cipher = Cipher.getInstance(ALGORITHM_AES_GCM)
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

private fun decrypt(data: ByteArray): ByteArray {
    val buffer = ByteBuffer.wrap(data)
    val iv = ByteArray(12)
    buffer.get(iv)
    val encodedKey = ByteArray(16)
    buffer.get(encodedKey)
    val encrypted = ByteArray(buffer.remaining())
    buffer.get(encrypted)

    val sKey = SecretKeySpec(encodedKey, ALGORITHM_AES)
    val cipher = Cipher.getInstance(ALGORITHM_AES_GCM)
    val params = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, sKey, params)

    return cipher.doFinal(encrypted)
}