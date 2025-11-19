package com.example.faceverification

import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class PassportApiClientTest {

    @Test
    fun `verify returns mock result`() = runBlocking {
        val client = PassportApiClient()
        val face = mock(Face::class.java)
        val result = client.verify(face)
        assertEquals(true, result.isSuccess)
        assertEquals("Verification successful. Name: John Doe, Passport ID: 123456789", result.message)
    }
}
