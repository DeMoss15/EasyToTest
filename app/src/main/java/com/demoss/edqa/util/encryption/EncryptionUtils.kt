package com.demoss.edqa.util.encryption

import android.util.Base64
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM_AES: String = "AES"
private const val ALGORITHM_AES_GCM: String = "AES/GCM/NoPadding"
private const val KEY_LENGTH: Int = 16
private const val IV_LENGTH: Int = 12
private const val GCM_LENGTH: Int = 128

fun String.encrypt(): String = encrypt(this.toByteArray()).run {
    Base64.encodeToString(this, Base64.DEFAULT)
}

fun String.decrypt(): String = Base64.decode(this, Base64.DEFAULT).run {
    String(decrypt(this))
}

private fun encrypt(data: ByteArray): ByteArray {
    val random = SecureRandom()
    val sKey = SecretKeySpec(ByteArray(KEY_LENGTH).apply { random.nextBytes(this) }, ALGORITHM_AES)
    val iv = IvParameterSpec(ByteArray(IV_LENGTH).apply { random.nextBytes(this) })
    val cipher = Cipher.getInstance(ALGORITHM_AES_GCM)
    val params = GCMParameterSpec(GCM_LENGTH, iv.iv)
    cipher.init(Cipher.ENCRYPT_MODE, sKey, params)
    val encrypted = cipher.doFinal(data)

    return ByteBuffer.allocate(iv.iv.size + sKey.encoded.size + encrypted.size).apply {
        put(iv.iv)
        put(sKey.encoded)
        put(encrypted)
    }.array()
}

private fun decrypt(data: ByteArray): ByteArray {
    val buffer = ByteBuffer.wrap(data)
    val iv = ByteArray(IV_LENGTH)
    buffer.get(iv)
    val encodedKey = ByteArray(KEY_LENGTH)
    buffer.get(encodedKey)
    val encrypted = ByteArray(buffer.remaining())
    buffer.get(encrypted)

    val sKey = SecretKeySpec(encodedKey, ALGORITHM_AES)
    val cipher = Cipher.getInstance(ALGORITHM_AES_GCM)
    val params = GCMParameterSpec(GCM_LENGTH, iv)
    cipher.init(Cipher.DECRYPT_MODE, sKey, params)

    return cipher.doFinal(encrypted)
}