package info.free.scp.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import info
import java.io.*


@RequiresApi(api = Build.VERSION_CODES.Q)
fun getPublicFileUri(context: Context, dirName: String, fileName: String, mimeType: String) {
    val values = ContentValues()
    values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
    values.put(MediaStore.Files.FileColumns.TITLE, fileName)
//        values.put(MediaStore.Files.FileColumns.MIME_TYPE, mimeType)
    values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/" + dirName)
    val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val resolver = context.contentResolver
    val insertUri = resolver.insert(external, values)
    info(insertUri.toString())
}

@Throws(IOException::class)
fun copyFileFromUri(context: Context, fileUri: Uri, outputFile: File): Boolean {
    val resolver = context.contentResolver
    val inputStream = resolver.openInputStream(fileUri)
    val outputStream = outputFile.outputStream()
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return true
}


