package net.svichch.geekbrains.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.MarkerOptions
import net.svichch.geekbrains.maps.databinding.ItemListPositionBinding

class ListMarkerAdapter(private val listMarker: MutableList<MarkerOptions>, val adapterMarkerFun: AdapterMarkerFunctional) :
    RecyclerView.Adapter<ListMarkerAdapter.ViewHolder>() {

    // Размер списка
    override fun getItemCount() = listMarker.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    // Позиция
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMarker[position], position)
    }

    inner class ViewHolder(
        parent: ViewGroup,
        private val binding: ItemListPositionBinding = ItemListPositionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(marker: MarkerOptions, position: Int) {
            with(binding) {
                //titleId.text = marker.title
                titleId.text = marker.title
                bodyId.text=" ${marker.position.latitude.toString()} / ${marker.position.longitude.toString()}"
                imageDelete.setOnClickListener {adapterMarkerFun.removeMarker(marker) }

            }
        }
    }

    fun interface AdapterMarkerFunctional {
        fun removeMarker(marker: MarkerOptions)
    }
}