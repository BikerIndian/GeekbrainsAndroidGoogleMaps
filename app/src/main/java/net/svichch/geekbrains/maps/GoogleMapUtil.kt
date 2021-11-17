package net.svichch.geekbrains.maps

import android.location.Geocoder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import net.svichch.geekbrains.maps.geo.GeoData
import net.svichch.geekbrains.maps.geo.GeoLocation
import net.svichch.geekbrains.maps.geo.GeoPermission
import net.svichch.geekbrains.maps.geo.IGeo
import java.io.IOException

class GoogleMapUtil() {

    private lateinit var googleMap: GoogleMap
    private lateinit var currentMarker: Marker
    private lateinit var mainActivity: MainActivity
    private val markers = mutableListOf<MarkerOptions>()

    fun callback(mainActivityin: MainActivity): OnMapReadyCallback {
        mainActivity = mainActivityin
        return OnMapReadyCallback {
            onMapReady(it)
        }
    }

    private fun onMapReady(googleMapIn: GoogleMap) {
        googleMap = googleMapIn

        googleMap.clear() // Очистка
        setDefaultCamera() // Точка по умалчанию
        addSaveMarkers()
        if (GeoPermission().isPemissions(mainActivity)) {
            goToMapGeoLocation()
        }
        addMarkerOnClickForMap()
    }

    private fun addSaveMarkers() {
        markers.forEach { googleMap.addMarker(it) }
    }

    private fun addMarkerOnClickForMap() {
        googleMap.setOnMapLongClickListener { latLng ->
            addMarker(
                MarkerOptions().position(
                    latLng
                )
            )
        }
    }

    private fun setDefaultCamera() {
        val sydney = LatLng(-34.0, 151.0)
        currentMarker = googleMap
            .addMarker(
                MarkerOptions().position(sydney)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)) // Цвет маркера
                    .alpha(0.7f) // прозрачности маркера

            )!!
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun goToMapGeoLocation() {

        GeoLocation().requestLocation(mainActivity.applicationContext, object : IGeo {
            override fun geoDataResult(geoData: GeoData) {
                goTo(geoData)
            }
        })
    }

    fun goTo(geoData: GeoData) {

        // Переместить метку на текущую позицию
        val currentPosition = LatLng(geoData.latitude, geoData.longitude)

        currentMarker?.setPosition(currentPosition)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                currentPosition,
                12.toFloat()
            )
        )
    }

    private fun addMarker(marker: MarkerOptions) {
        markers.add(marker)
        getAddress(marker)
        googleMap.addMarker(
            marker
        )
    }

    // Получаем адрес по координатам
    private fun getAddress(marker: MarkerOptions) {
        val geocoder = Geocoder(mainActivity.applicationContext)
        // Поскольку geocoder работает по интернету, создадим отдельный поток
        Thread {
            try {
                val addresses =
                    geocoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)
                marker.title(addresses[0].getAddressLine(0))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun getMarkerList(): MutableList<MarkerOptions> {
        return markers
    }
}