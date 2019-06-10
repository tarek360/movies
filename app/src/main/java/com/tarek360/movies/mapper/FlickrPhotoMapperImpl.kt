package com.tarek360.movies.mapper

import com.tarek360.movies.network.FlickrPhoto

class FlickrPhotoMapperImpl : FlickrPhotoMapper {

    override fun map(flickrPhotos: List<FlickrPhoto>): List<String> {
        return flickrPhotos.map {
            buildUrl(
                farm = it.farm,
                server = it.server,
                id = it.id,
                secret = it.secret
            )
        }
    }

    private fun buildUrl(
        farm: Int,
        server: String,
        id: String,
        secret: String
    ) = "http://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
}

