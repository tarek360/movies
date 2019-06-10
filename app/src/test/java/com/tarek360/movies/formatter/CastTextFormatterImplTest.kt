package com.tarek360.movies.formatter

import com.tarek360.movies.shouldEqual
import org.junit.Test

class CastTextFormatterImplTest {

    private val formatter by lazy {
        CastTextFormatterImpl()
    }

    @Test
    fun formatCast() {
        // Arrange
        val input = listOf("aaa", "bbb", "ccc")

        // Act
        val output = formatter.format(input)

        // Assert
        output shouldEqual "- aaa.\n- bbb.\n- ccc."
    }

    @Test
    fun formatEmpty() {
        // Arrange
        val input = emptyList<String>()

        // Act
        val output = formatter.format(input)

        // Assert
        output shouldEqual ""
    }
}
