package org.komputing.kaptcha

import okhttp3.OkHttpClient

class HCaptcha(
    hCaptchaSecret: String,
    okHttpClient: OkHttpClient = createDefaultOKhttp(),
) : Captcha(hCaptchaSecret, okHttpClient, "https://hcaptcha.com/siteverify")