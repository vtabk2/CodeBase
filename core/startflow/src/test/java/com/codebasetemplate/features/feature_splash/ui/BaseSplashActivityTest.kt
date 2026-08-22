package com.codebasetemplate.features.feature_splash.ui

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class BaseSplashActivityTest {

    @Test
    fun `network reconnect resumes splash while waiting for internet`() {
        val source = readSplashSource()

        assertTrue(source.contains("isWaitingForInternet = true"))
        assertTrue(
            Regex(
                """override fun onNetworkChange\(isNetworkConnected: Boolean\).*if \(isNetworkConnected && isWaitingForInternet\).*startSplashPrerequisites\(\)""",
                RegexOption.DOT_MATCHES_ALL
            ).containsMatchIn(source)
        )
        assertTrue(
            Regex(
                """private fun startSplashPrerequisites\(\) \{\s*isWaitingForInternet = false\s*onSplashStatusChanged\(SplashStatus\.FetchingRemoteConfig\)"""
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