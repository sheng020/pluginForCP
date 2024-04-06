package com.browser.feedui.initializer.inner

import android.content.Context
import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.security.Security
import java.security.SecureRandom




/**
 * Create by chenjunsheng on 2024/3/15
 */
class Utils {

    companion object {
        fun generateRandomIV(): String {
            val ranGen = SecureRandom()
            val aesKey = ByteArray(16)
            ranGen.nextBytes(aesKey)
            val result = StringBuffer()
            for (b in aesKey)  {
                result.append(String.format("%02x", b))
            }
            return if (16 > result.toString().length) {
                result.toString()
            } else {
                result.toString().substring(0, 16)
            }
        }

        fun getSoFilePath(context: Context, soName: String) {
            //val iv = generateRandomIV()
            //Log.d("cjslog", "get so file path:${iv}")
            val applicationInfo = context.applicationInfo
            val libDir = applicationInfo.nativeLibraryDir
            //val soFilePath = libDir + "/" + soName
            File(libDir).listFiles().forEach {
                /*if (!it.name.contains(soName)) {
                    return@forEach
                }*/
                Log.d("cjslog", "list file name:${it.name}")

                //FileUtils.copy(it, File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), it.name + ".e"))

                //val bytes = org.apache.commons.io.FileUtils.readFileToByteArray(it)
                //val decryptBytes = AESUtils.decrypt(bytes)
                //org.apache.commons.io.FileUtils.writeByteArrayToFile(File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), it.name), decryptBytes)

                //org.apache.commons.io.FileUtils.copyFileToDirectory(it, File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "libs_output"))
                org.apache.commons.io.FileUtils.copyFile(it, File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), it.name))
            }



        }
    }

    fun copyFileToSDCard(context: Context, sourceFile: File, destFileName: String) {
        try {
            if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
                // SD卡状态不是已挂载，无法读写
                return
            }
            //val sourceFile = File(sourceFilePath)
            if (!sourceFile.exists()) {
                // 源文件不存在
                return
            }
            val destPath =
                Environment.getExternalStorageDirectory().absolutePath + File.separator + destFileName
            val sourceChannel: FileChannel = FileInputStream(sourceFile).channel
            val destChannel: FileChannel = FileOutputStream(File(destPath)).channel
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size())

            sourceChannel.close()
            destChannel.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


        }
