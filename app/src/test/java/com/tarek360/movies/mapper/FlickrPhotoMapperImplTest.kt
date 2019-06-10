package com.tarek360.movies.mapper

import com.tarek360.movies.network.FlickrPhoto
import com.tarek360.movies.shouldEqual
import org.junit.Test


class FlickrPhotoMapperImplTest {

    private val mapper by lazy {
        FlickrPhotoMapperImpl()
    }

    @Test
    fun map() {

        // Arrange
        val input = listOf(FlickrPhoto("777", "", "123", "aaa", 99, "", 1, 1, 1))

        // Act
        val output = mapper.map(input)

        // Assert
        output.size shouldEqual 1
        output.first() shouldEqual "http://farm99.static.flickr.com/aaa/777_123.jpg"
    }
}
