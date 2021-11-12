package net.svichch.geekbrains.maps.geo

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class GeoPermission() {

    companion object{

        private val PERMISSION_REQUEST_CODE = 10

        // Проверка результата запроса у пользователя пермиссии
        fun isPermissionsResult(
            requestCode: Int,
            grantResults: IntArray
        ): Boolean {
            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.size == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    return true
                }
            }
            return false
        }
    }

    // Запрос пермиссий
    fun isPemissions(activity: Activity): Boolean {
        // Проверим на пермиссии, и если их нет, запросим у пользователя
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            // пермиссии нет, будем запрашивать у пользователя
            requestLocationPermissions(activity)
            return false
        }
    }

    // Запрос разрешения для геолокации
    private fun requestLocationPermissions(activity: Activity) {

        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            || !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Запросим эти две пермиссии у пользователя
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }




}