package net.svichch.geekbrains.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.svichch.geekbrains.maps.databinding.FragmentListPositionBinding
import net.svichch.geekbrains.maps.databinding.FragmentMapsBinding

class ListPositionFragment : Fragment() {

    companion object {
        fun newInstance() = ListPositionFragment()
    }

    private lateinit var listPosition: FragmentListPositionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listPosition = FragmentListPositionBinding.inflate(inflater, container, false)
        return listPosition.root
    }
}