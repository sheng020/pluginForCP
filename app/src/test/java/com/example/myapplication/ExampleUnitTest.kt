package com.example.myapplication

import com.blankj.utilcode.util.FileIOUtils
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val privateKeyFile = File("converts/private")
        val publicKeyFile = File("converts/public")
        print("public file path:${publicKeyFile.absolutePath}")
        val keyPair = RSAUtils.genKeyPair()

        val privateKey = RSAUtils.getPrivateKey(keyPair)
        val publicKey = RSAUtils.getPublicKey(keyPair)
        FileIOUtils.writeFileFromBytesByStream(privateKeyFile, privateKey.toByteArray())
        FileIOUtils.writeFileFromBytesByStream(publicKeyFile, publicKey.toByteArray())
    }
}