package ni.edu.uca.myuca.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.databinding.FragmentDetailsCoordBinding
import ni.edu.uca.myuca.ui.viewmodel.CoordsViewModel

class DetailsCoordFragment : BottomSheetDialogFragment() {

    private lateinit var coordinador: Coordinador

    //ViewModel
    private val viewModel: CoordsViewModel by activityViewModels {
        CoordsViewModel.factory
    }

    // El ViewBinding
    private var _binding: FragmentDetailsCoordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsCoordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = this.tag?.toInt()
        if (id != null) {
            viewModel.conseguirCoordinador(id)
        }

        viewModel.coord.observe(this.viewLifecycleOwner) { response ->
            if(response.isSuccessful){
                coordinador = response.body()!!
                bind(coordinador)
            } else {
                Toast.makeText(this.context, "Error de conexion", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bind(coordinador: Coordinador) {
        binding.apply {

            btnEditar.setOnClickListener {  }
            btnEliminar.setOnClickListener { mostrarDialogConfirmacion() }
        }
    }

    private fun eliminarCoord() {
        val id = this.tag?.toInt()
        if (id != null) {
            viewModel.eliminarCoordinador(id)
        }

        viewModel.deletedCoord.observe(this.viewLifecycleOwner) { response ->
            if(response.isSuccessful){
                println(response.body()!!)
            } else {
                Toast.makeText(this.context, "Error de conexion", Toast.LENGTH_LONG).show()
            }
        }
    }


/*
private fun entradasValidas(): Boolean {
        return viewModel.entradasValidas(
            binding.etNombres.text.toString(),
            binding.etApellidos.text.toString(),
            binding.etFechaNac.text.toString(),
            binding.etFacultad.text.toString(),
            binding.etTitulo.text.toString(),
            binding.etEmail.text.toString()
        )
    }
* */

    private fun mostrarDialogConfirmacion() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Quiere eliminar el siguiente coordinador?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                eliminarCoord()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}