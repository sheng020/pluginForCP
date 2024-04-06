package com.example.lib

import org.apache.commons.net.util.Base64
import org.gradle.internal.impldep.org.bouncycastle.asn1.bsi.BSIObjectIdentifiers.algorithm
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Create by chenjunsheng on 2024/3/18
 */
object AESUtils {
    private val log = LoggerFactory.getLogger(AESUtils::class.java)

    // 密钥
    var key = "AD42F6697B035B7580E4FEF93BE20BAD" //长度必须为16、24、32位，即128bit、192bit、256bit
    private const val charset = "utf-8"

    // 偏移量
    private const val offset = 16
    private const val transformation = "AES/CBC/PKCS5Padding"
    private const val algorithm = "AES"

    @JvmStatic
    fun generateRandomString(length: Int): String {
        val uuid = UUID.randomUUID().toString()
        // Example: f47ac10b-58cc-4372-a567-0e02b2c3d479
        val withoutDashes = uuid.replace("-", "") // Remove dashes
        return if (length <= withoutDashes.length) {
            withoutDashes.substring(0, length)
        } else {
            // If you need exactly 32 characters, you may need to combine or extend the logic here
            // for example by repeating the generation process or by suffixing/prefixing.
            // Following is a basic extension to meet the required length (simple example, might not be very random):
            withoutDashes + generateRandomString(length - withoutDashes.length)
        }
    }


    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key 加密密码
     * @return
     */
    /**
     * 加密
     *
     * @param content
     * @return
     */
    @JvmOverloads
    fun encrypt(content: ByteArray, key: String = this.key): ByteArray? {
        try {
            val skey =
                SecretKeySpec(key.toByteArray(), algorithm)
            val iv =
                IvParameterSpec(key.toByteArray(), 0, offset)
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv) // 初始化
            return cipher.doFinal(content) // 加密
        } catch (e: Exception) {
            //log.error(ErrorMessage.ENCRYPTION_FAILED.getErrorInfo());
            e.printStackTrace()
        }
        return null
    }
    /**
     * AES（256）解密
     *
     * @param content 待解密内容
     * @param key 解密密钥
     * @return 解密之后
     * @throws Exception
     */
    /**
     * 解密
     *
     * @param content
     * @return
     */
    @JvmOverloads
    fun decrypt(content: String?, key: String = this.key): String? {
        try {
            val skey = SecretKeySpec(key.toByteArray(), algorithm)
            val iv = IvParameterSpec(key.toByteArray(), 0, offset)
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, skey, iv) // 初始化
            val result = cipher.doFinal(Base64().decode(content))
            return String(result) // 解密
        } catch (e: Exception) {
            //log.error(ErrorMessage.DECRYPTION_FAILED.getErrorInfo());
            e.printStackTrace()
        }
        return null
    }
}
