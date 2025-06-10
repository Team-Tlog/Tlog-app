package com.tlog.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

object FirebaseImageUploader {

    // path -> ${System.currentTimeMillis()}_${UUID.randomUUID()} => 동시에 올라가는 극한의 경우도 생각해서 RandomUUID 추가 (뒤에 추가함으로 시간으로 정렬도 가능할 수 있게)
    const val MAX_WIDTH = 1280
    const val MAX_HEIGHT = 1280


    suspend fun uploadWebpImage(
        context: Context,
        uri: Uri,
        path: String //= "images/review/${System.currentTimeMillis()}_${UUID.randomUUID()}.webp"
    ): String = withContext(Dispatchers.IO) {

        // URI → Bitmap으로 변환 -> 크다면 리사이즈
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val resizedBitmap = if (bitmap.width > MAX_WIDTH || bitmap.height > MAX_HEIGHT) {
            val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val (targetWidth, targetHeight) = if (aspectRatio > 1) {
                MAX_WIDTH to (MAX_WIDTH / aspectRatio).toInt()
            } else {
                (MAX_HEIGHT * aspectRatio).toInt() to MAX_HEIGHT
            }
            bitmap.scale(targetWidth, targetHeight, filter = true)
        } else {
            bitmap
        }

        // Bitmap → WEBP ByteArray으로 변환
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 90, outputStream)
        val webpBytes = outputStream.toByteArray()

        // 메타데이터 설정 -> 캐시 사용 (알아보니 업로드에는 지장 없음ㅠ)
        val metadata = StorageMetadata.Builder()
            .setCacheControl("public,max-age=86400")
            .build()


        val uploadStart = System.currentTimeMillis()

        // Firebase Storage 업로드
        val storageRef = FirebaseStorage.getInstance().reference.child(path)
        storageRef.putBytes(webpBytes, metadata).await()

        val uploadEnd = System.currentTimeMillis()
        Log.d("ImageDebug", "Firebase 업로드 소요 시간: ${uploadEnd - uploadStart} ms")

        // URL 반환 후 return
        val downloadUri = storageRef.downloadUrl.await()
        return@withContext downloadUri.toString()
    }

}