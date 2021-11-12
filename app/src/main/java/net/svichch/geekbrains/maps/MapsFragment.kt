package net.svichch.geekbrains.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.svichch.geekbrains.maps.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {

    companion object {
        fun newInstance() = MapsFragment()
    }

    private lateinit var mapsBinding: FragmentMapsBinding

    private val callback = OnMapReadyCallback {
        onMapReady(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsBinding = FragmentMapsBinding.inflate(inflater, container, false)
        return mapsBinding.root
    }

    private fun onMapReady(googleMap: GoogleMap) {

        googleMap.setOnMapLongClickListener { latLng ->
            googleMap.addMarker(
                MarkerOptions().position(
                    latLng
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        mapsBinding.btnListPosition.setOnClickListener {
            toListPositionFragment()
        }

    }

    // Переход на фрагмент список маркеров
    private fun toListPositionFragment() {
        (requireActivity() as MainActivity).navigateTo(ListPositionFragment.newInstance())
    }
}