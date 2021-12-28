package org.komputing.kaptcha

import okhttp3.OkHttpClient

class ReCaptcha(
    hCaptchaSecret: String,
    okHttpClient: OkHttpClient = createDefaultOKhttp(),
) : Captcha(hCaptchaSecret, okHttpClient, "https://www.google.com/recaptcha/api/siteverify")