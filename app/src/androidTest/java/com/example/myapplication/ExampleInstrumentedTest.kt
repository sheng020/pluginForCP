package com.example.myapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.blankj.utilcode.util.FileIOUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)

        val privateKeyFile = File("converts/private")
        val publicKeyFile = File("converts/public")
        print("public file path:${publicKeyFile.absolutePath}")
        val keyPair = RSAUtils.genKeyPair()
        val publicKey = keyPair["RSAPublicKey"] as RSAPublicKey
        val privateKey = keyPair["RSAPrivateKey"] as RSAPrivateKey

        FileIOUtils.writeFileFromBytesByStream(privateKeyFile, privateKey.encoded)
        FileIOUtils.writeFileFromBytesByStream(publicKeyFile, publicKey.encoded)
    }
}