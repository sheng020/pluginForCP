package com.example.lib

import org.apache.commons.io.FileSystemUtils
import org.apache.commons.io.FileUtils
import java.io.File
import javax.crypto.Cipher;


/**
 * Create by chenjunsheng on 2024/3/18
 */


fun convertFile(files: File, output: File, key: String) {
    val fileBytes = FileUtils.readFileToByteArray(files)
    //val fileBytes = FileIOUtils.readFile2BytesByStream(it)
    val encryptBytes = RSAUtils.encryptByPublicKey(fileBytes, key)
    FileUtils.writeByteArrayToFile(output, addLibHeader(encryptBytes))
}

fun convertFileAes(files: File, output: File, key: String) {
    val fileBytes = FileUtils.readFileToByteArray(files)
    //val fileBytes = FileIOUtils.readFile2BytesByStream(it)
    val encryptBytes = AESUtils.encrypt(fileBytes, key)
    FileUtils.writeByteArrayToFile(output, addLibHeader(encryptBytes!!))
}

fun convertString(string: String, output: File, key: String) {
    val encryptBytes = RSAUtils.encryptByPublicKey(string.toByteArray(), key)
    FileUtils.writeByteArrayToFile(output, addLibHeader(encryptBytes))
}

fun addLibHeader(inputArray: ByteArray): ByteArray {
    val header1 = byteArrayOf(0x7f, 0x45, 0x4c, 0x46, 0x02, 0x01, 0x01)
    val header2 = ByteArray(9) {
        0x00
    }
    return header1 + header2 + inputArray
}
