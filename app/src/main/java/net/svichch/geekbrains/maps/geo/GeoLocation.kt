package net.svichch.geekbrains.maps.geo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

class GeoLocation {

    // Запрос координат
    fun requestLocation(context : Context, geo : IGeo ) {
        // Если пермиссии все таки нет - то просто выйдем, приложение не имеет смысла
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        // Получить менеджер геолокаций
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        // получим наиболее подходящий провайдер геолокации по критериям
        // Но можно и самому назначать какой провайдер использовать.
        // В основном это LocationManager.GPS_PROVIDER или LocationManager.NETWORK_PROVIDER
        // но может быть и LocationManager.PASSIVE_PROVIDER, это когда координаты уже кто-то недавно получил.
        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10f, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    geo.geoDataResult(GeoData(location.latitude,location.longitude, location.accuracy))
                    println("------------------------------>")
                    //location.latitude Широта
                    //location.longitude Долгота
                    //location.accuracy Точность


                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            })
        }
    }
}