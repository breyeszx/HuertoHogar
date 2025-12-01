package com.brzdev.huertohogar.data.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep // Evita que R8 elimine o modifique esta clase
data class WeatherResponse(
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("name") val name: String
)

@Keep
data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Int
)

@Keep
data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)