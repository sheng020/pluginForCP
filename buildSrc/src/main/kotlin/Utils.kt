import org.apache.commons.io.FileSystemUtils
import org.apache.commons.io.FileUtils
import java.io.File
import javax.crypto.Cipher;


/**
 * Create by chenjunsheng on 2024/3/18
 */


fun convertFiles(files: Array<File>, output: File) {
    files.forEach {
        val fileBytes = FileUtils.readFileToByteArray(it)
        //val fileBytes = FileIOUtils.readFile2BytesByStream(it)
        val encryptBytes = AESUtils.encrypt(fileBytes)
        FileUtils.writeByteArrayToFile( File(output, it.name), encryptBytes)
        //val result = EncryptUtils.encryptAES(fileBytes, "YourEncryptionKe".toByteArray(), "AES/ECB/PKCS7Padding", null)
        //Log.d("cjslog", "result aes:${result.size}")

        //FileIOUtils.writeFileFromBytesByStream(File(output, it.name), result)
    }
}

fun convertString(string: String, output: File) {
    val encryptBytes = AESUtils.encrypt(string.toByteArray())
    FileUtils.writeByteArrayToFile( output, encryptBytes)
}
