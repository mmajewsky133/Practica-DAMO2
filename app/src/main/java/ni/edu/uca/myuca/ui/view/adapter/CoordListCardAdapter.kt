package ni.edu.uca.myuca.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.databinding.ItemCoordsBinding

class CoordListCardAdapter :
    ListAdapter<Coordinador, CoordListCardAdapter.CoordViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordViewHolder {
        val binding = ItemCoordsBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return CoordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class CoordViewHolder(private val binding: ItemCoordsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coordinador: Coordinador) {
            binding.apply {
                tvInfoCoord.text = parseInfoGeneral(coordinador)
                tvFacultad.text = coordinador.facultad
                tvEmail.text = coordinador.email
                tvFechaNacCoord.text = parseFechaNac(coordinador.fechaNac)
            }
        }

        private fun parseInfoGeneral(coordinador: Coordinador): String {
            return "${coordinador.titulo} ${coordinador.nombres} ${coordinador.apellidos}"
        }

        private fun parseFechaNac(fechaNac: String): String {
            return fechaNac //Pasa el formato en string a uno mas entendible
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Coordinador>() {
            override fun areItemsTheSame(oldItem: Coordinador, newItem: Coordinador): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Coordinador, newItem: Coordinador): Boolean {
                return oldItem.email == newItem.email
            }
        }
    }
}