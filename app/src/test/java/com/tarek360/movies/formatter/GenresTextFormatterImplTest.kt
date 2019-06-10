package com.tarek360.movies.formatter

import com.tarek360.movies.shouldEqual
import org.junit.Test

class GenresTextFormatterImplTest {

    private val formatter by lazy {
        GenresTextFormatterImpl()
    }

    @Test
    fun formatGenres() {
        // Arrange
        val input = listOf("aaa", "bbb", "ccc")

        // Act
        val output = formatter.format(input)

        // Assert
        output shouldEqual "aaa, bbb, ccc."
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
