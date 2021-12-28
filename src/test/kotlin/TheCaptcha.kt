import com.google.common.truth.ExpectFailure
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test
import org.komputing.kaptcha.Captcha
import org.komputing.kaptcha.createDefaultOKhttp

class TheHCaptcha {

    @Test
    fun returnsTrueForValidatedCAPTCHA() = runBlocking {
        val mockWebServer = MockWebServer()
        val response = """{"success":true,"challenge_ts":"2021-12-28T20:04:28.000000Z","hostname":"localhost","credit":false}"""
        mockWebServer.enqueue(MockResponse().setBody(response))

        val captcha = Captcha("foo", createDefaultOKhttp(), postURL = mockWebServer.url("").toString())

        assertThat(captcha.verifyCaptcha("foo")).isTrue()
    }

    @Test
    fun returnsFalseForValidatedCAPTCHA() = runBlocking {
        val mockWebServer = MockWebServer()
        val response = """{"success":false,"challenge_ts":"2021-12-28T20:04:28.000000Z","hostname":"localhost","credit":false}"""
        mockWebServer.enqueue(MockResponse().setBody(response))

        val captcha = Captcha("foo", createDefaultOKhttp(), postURL = mockWebServer.url("").toString())

        assertThat(captcha.verifyCaptcha("foo")).isFalse()
    }
}