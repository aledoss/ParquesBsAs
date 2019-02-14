package com.example.ndiaz.parquesbsas.helpers

import android.content.Context
import android.net.Uri
import android.support.v4.content.FileProvider
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadImageManager(private var context: Context, private val imageFilePath: String,
                         private val imageName: String) {

    private val imageFile = File(imageFilePath)

    private fun getUriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(context,
                "com.example.android.fileprovider",
                file
        )
    }

    fun getMultipartBody(): MultipartBody.Part {
        return MultipartBody.Part.createFormData("file", imageName, getRequestFile())
    }

    private fun getRequestFile(): RequestBody {
        return RequestBody.create(
                MediaType.parse(context.contentResolver.getType(getUriFromFile(imageFile))),
                imageFile)
    }

    fun getDescription(): RequestBody {
        return RequestBody.create(MultipartBody.FORM, "Imagen del reclamo")
    }

}