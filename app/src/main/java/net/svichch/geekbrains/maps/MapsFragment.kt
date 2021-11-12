package net.svichch.geekbrains.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import net.svichch.geekbrains.maps.databinding.FragmentMapsBinding
import net.svichch.geekbrains.maps.geo.GeoPermission

class MapsFragment : Fragment() {

    companion object {
        fun newInstance() = MapsFragment()
    }

    private lateinit var mapsBinding: FragmentMapsBinding
    private lateinit var googleMapUtil: GoogleMapUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        googleMapUtil = GoogleMapUtil((requireActivity() as MainActivity))
        mapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
        return mapsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(googleMapUtil.callback())

        mapsBinding.btnListPosition.setOnClickListener {
            toListPositionFragment()
        }

    }

    // Это результат запроса у пользователя пермиссии
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        if (GeoPermission.isPermissionsResult(requestCode, grantResults)) {
            googleMapUtil.goToMapGeoLocation()
        }
    }

    // Переход на фрагмент список маркеров
    private fun toListPositionFragment() {
        (requireActivity() as MainActivity).navigateTo(ListPositionFragment.newInstance())
    }
}