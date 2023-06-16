package com.example.plantcare.helper

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.plantcare.R
import com.ujizin.camposer.state.ImageCaptureResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor



private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun saveImageToGallery(contentResolver: ContentResolver, bitmap: Bitmap, displayName: String) {
    val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY) ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
    }

    val imageUri = contentResolver.insert(imageCollection, contentValues) ?: return

    contentResolver.openOutputStream(imageUri).use { outputStream ->
        if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
            throw IOException("Failed to save bitmap.")
        }
    }
}

fun takePhoto(
    context: Context,
    filename: String,
    imageCapture: ImageCapture,
    outpuDirectory: File,
    executor: Executor,
    onImageCapture: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
){
    val photoFile = File.createTempFile(
        "temp",
        SimpleDateFormat(filename, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback{
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val uri = Uri.fromFile(photoFile)

            try {
                onImageCapture(uri)
            }catch (e: Exception){
                Log.d("TAG", "onImageSaved: $e")
            }
        }

        override fun onError(exception: ImageCaptureException) {
            Log.d("Camera", "onError: $exception")
            onError(exception)
        }
    })
}

//
//fun uriToFile (selectedImg: ImageCaptureResult.Success, context: Context): File {
//    val contentResolver: ContentResolver = context.contentResolver
//    val myFile = createCustomTempFile(context)
//
//    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
//    val outputStream: OutputStream = FileOutputStream(myFile)
//    val buf = ByteArray(1024)
//    var len: Int
//    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
//    outputStream.close()
//    inputStream.close()
//
//    return myFile
//}


