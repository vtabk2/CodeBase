package com.core.ads.admob

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class AdmobManagerTest {

    @Test
    fun `consent flow replays last state for splash late collectors`() {
        val source = readAdmobManagerSource()
        val hasReplayOneSharedFlow = Regex(
            """_requestConsentFlow\s*=\s*MutableSharedFlow<ConsentFormUiResource>\([^)]*replay\s*=\s*1""",
            RegexOption.DOT_MATCHES_ALL
        ).containsMatchIn(source)
        val hasStateFlow = Regex(
            """_requestConsentFlow\s*=\s*MutableStateFlow<ConsentFormUiResource>\("""
        ).containsMatchIn(source)

        assertTrue(
            "requestConsentFlow must replay the last state so Splash does not hang after network settings pause it",
            hasReplayOneSharedFlow || hasStateFlow
        )
    }

    private fun readAdmobManagerSource(): String {
        val relativePath = "src/main/java/com/core/ads/admob/AdmobManager.kt"
        val sourceFile = sequenceOf(File(relativePath), File("core/ads/$relativePath"))
            .firstOrNull(File::isFile)
            ?: error("Cannot find $relativePath")
        return sourceFile.readText()
    }
}