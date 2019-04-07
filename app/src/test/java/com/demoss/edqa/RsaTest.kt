package com.demoss.edqa

import junit.framework.Assert
import org.junit.Test
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.util.*
import javax.crypto.Cipher

const val ALGORITHM = "RSA"

class RsaTest {

    @Test
    fun runRsaTest() {
        val plainText = "Hello World!"

        val enc = plainText.encrypt()
        print("\n encrypted: $enc")
        val dec = enc.decrypt()
        print("\n decrypted: $dec\n")

        Assert.assertEquals(plainText, dec)
    }

    fun String.encrypt(): String = encrypt(this.toByteArray()).run {
        Base64.getEncoder().encodeToString(this)
    }

    fun String.decrypt(): String = Base64.getDecoder().decode(this).run {
        String(decrypt(this))
    }

    lateinit var privateKey: PrivateKey

    fun encrypt(data: ByteArray): ByteArray {
        val keyPair = KeyPairGenerator.getInstance(ALGORITHM).genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private
        this.privateKey = privateKey
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(data)
    }

    fun decrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(data)
    }
}