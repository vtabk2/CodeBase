package com.core.config.data

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class RemoteConfigRepositoryImplTest {

    @Test
    fun `fetch state flow replays last state for splash late collectors`() {
        val source = readRepositorySource()
        val hasReplayOneSharedFlow = Regex(
            """_fetchStateCompleteFlow\s*=\s*MutableSharedFlow<FetchRemoteConfigState>\([^)]*replay\s*=\s*1""",
            RegexOption.DOT_MATCHES_ALL
        ).containsMatchIn(source)
        val hasStateFlow = Regex(
            """_fetchStateCompleteFlow\s*=\s*MutableStateFlow<FetchRemoteConfigState>\("""
        ).containsMatchIn(source)

        assertTrue(
            "fetchStateCompleteFlow must replay the last state so Splash late collectors do not hang",
            hasReplayOneSharedFlow || hasStateFlow
        )
    }

    private fun readRepositorySource(): String {
        val relativePath = "src/main/java/com/core/config/data/RemoteConfigRepositoryImpl.kt"
        val sourceFile = sequenceOf(File(relativePath), File("core/config/$relativePath"))
            .firstOrNull(File::isFile)
            ?: error("Cannot find $relativePath")
        return sourceFile.readText()
    }
}