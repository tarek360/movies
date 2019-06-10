package com.tarek360.movies.mapper

import com.tarek360.movies.network.FlickrPhoto

interface FlickrPhotoMapper {
    fun map(flickrPhotos: List<FlickrPhoto>): List<String>
}

