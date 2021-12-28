package org.komputing.kaptcha

import kotlinx.coroutines.delay
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.Duration

open class Captcha(
    private val hCaptchaSecret: String,
    private val okHttpClient: OkHttpClient,
    private val postURL: String
) {

    suspend fun verifyCaptcha(hCaptchaResponse: String): Boolean {
        val request = Request.Builder()
            .url(postURL)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .post(
                FormBody.Builder()
                    .add("response", hCaptchaResponse)
                    .add("secret", hCaptchaSecret)
                    .build()
            )
            .build()


        var attempts = 0

        while (true) {

            try {
                val response = okHttpClient.newCall(request).execute()
                if (response.code == 200) {
                    return (response.body?.string()?.contains("\"success\":true") == true)
                }
            } catch (ioe: IOException) {
                // can happen - we will retry
            }

            if (attempts == 3) return false

            attempts++
            delay(500L * attempts)
        }

    }

}

internal fun createDefaultOKhttp() = OkHttpClient.Builder()
    .connectTimeout(Duration.ofSeconds(5))
    .readTimeout(Duration.ofSeconds(10))
    .build()