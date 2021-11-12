package net.svichch.geekbrains.maps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import net.svichch.geekbrains.maps.geo.GeoData
import net.svichch.geekbrains.maps.geo.GeoLocation
import net.svichch.geekbrains.maps.geo.GeoPermission
import net.svichch.geekbrains.maps.geo.IGeo

class GoogleMapUtil(var mainActivity: MainActivity) {

    private lateinit var googleMap: GoogleMap
    private lateinit var currentMarker: Marker

    fun callback(): OnMapReadyCallback {
        return OnMapReadyCallback {
            onMapReady(it)
        }
    }

    private fun onMapReady(googleMapIn: GoogleMap) {
        googleMap = googleMapIn

        setDefaultCamera()
        addMarkerOnClickForMap()

        if (GeoPermission().isPemissions(mainActivity)) {
            goToMapGeoLocation()
        }
    }

    private fun addMarkerOnClickForMap() {
        googleMap.setOnMapLongClickListener { latLng ->
            googleMap.addMarker(
                MarkerOptions().position(
                    latLng
                )
            )
        }
    }

    private fun setDefaultCamera() {
        val sydney = LatLng(-34.0, 151.0)
        currentMarker = googleMap.addMarker(MarkerOptions().position(sydney))!!
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun goToMapGeoLocation() {
        mainActivity.applicationContext?.let {
            GeoLocation().requestLocation(it, object : IGeo {
                override fun geoDataResult(geoData: GeoData) {
                    goTo(geoData)
                }
            })
        }
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
}