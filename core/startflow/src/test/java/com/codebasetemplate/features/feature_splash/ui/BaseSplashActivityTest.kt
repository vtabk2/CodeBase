package com.codebasetemplate.features.feature_splash.ui

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class BaseSplashActivityTest {

    @Test
    fun `offline splash waits for internet and skips fetch callbacks`() {
        val source = readSplashSource()

        assertTrue(
            Regex(
                """private fun startSplashPrerequisites\(\) \{\s*if \(!isNetworkConnected\(\)\) \{\s*waitForInternet\(\)\s*return\s*\}.*remoteConfigRepository\.fetchAndActive\(\)""",
                RegexOption.DOT_MATCHES_ALL
            ).containsMatchIn(source)
        )
        assertTrue(
            Regex(
                """FetchRemoteConfigState\.Complete -> \{\s*if \(!isNetworkConnected\(\)\) \{\s*waitForInternet\(\)\s*return@collectFlowOn\s*\}\s*handleRemoteConfigReady\(\)""",
                RegexOption.DOT_MATCHES_ALL
            ).containsMatchIn(source)
        )
        assertTrue(
            Regex(
                """ConsentFormUiResource\.Complete -> \{.*if \(!isNetworkConnected\(\)\) \{\s*waitForInternet\(\)\s*return@collectFlowOn\s*\}\s*baseViewModel\.isRequestEuConsentComplete = true""",
                RegexOption.DOT_MATCHES_ALL
            ).containsMatchIn(source)
        )
        assertTrue(
            Regex(
                """override fun onNetworkChange\(isNetworkConnected: Boolean\).*if \(!isNetworkConnected && !baseViewModel\.isSplashAdsFlowStarted\) \{\s*waitForInternet\(\)\s*\}""",
                RegexOption.DOT_MATCHES_ALL
            ).containsMatchIn(source)
        )
    }

    private fun readSplashSource(): String {
        val relativePath = "src/main/java/com/codebasetemplate/features/feature_splash/ui/BaseSplashActivity.kt"
        val sourceFile = sequenceOf(File(relativePath), File("core/startflow/$relativePath"))
            .firstOrNull(File::isFile)
            ?: error("Cannot find $relativePath")
        return sourceFile.readText()
    }
}