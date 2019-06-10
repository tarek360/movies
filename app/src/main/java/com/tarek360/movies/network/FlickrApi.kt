package com.tarek360.movies.network

import com.tarek360.movies.BuildConfig
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("/services/rest")
    fun searchPhotos(
        @Query("text") movieTitle: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20,
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_API_KEY,
        @Query("method") method: String = "flickr.photos.search",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Observable<FlickrResponse?>
}

data class FlickrResponse(
    @SerializedName("photos") val flickrPhotos: FlickrPhotos,
    @SerializedName("stat") val stat: String
)

data class FlickrPhotos(@SerializedName("photo") val photos: List<FlickrPhoto>)

data class FlickrPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: String,
    @SerializedName("farm") val farm: Int,
    @SerializedName("title") val title: String,
    @SerializedName("ispublic") val isPublic: Int,
    @SerializedName("isfriend") val isFriend: Int,
    @SerializedName("isfamily") val isFamily: Int
)