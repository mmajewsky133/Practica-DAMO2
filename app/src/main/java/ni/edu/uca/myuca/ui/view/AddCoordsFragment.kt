package ni.edu.uca.myuca.ui.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import ni.edu.uca.myuca.data.model.Coordinador
import ni.edu.uca.myuca.databinding.FragmentAddCoordsBinding
import ni.edu.uca.myuca.ui.viewmodel.CoordsViewModel

class AddCoordsFragment : Fragment() {

    //ViewModel
    private val viewModel: CoordsViewModel by activityViewModels {
        CoordsViewModel.factory
    }

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Escoja su fecha de nacimiento")
        .build()

    // El ViewBinding
    private var _binding: FragmentAddCoordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCoordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btnAgregar.isEnabled = false

            btnAgregar.setOnClickListener {
                agregarCoord()
                limpiar()
            }

            etFechaNac.setOnClickListener{
                datePicker.show(childFragmentManager, "datepicker")
                datePicker.addOnPositiveButtonClickListener {
                    etFechaNac.setText(datePicker.selection.toString())
                }
            }

            etNombres.addTextChangedListener(coordinadorTextWatcher)
            etApellidos.addTextChangedListener(coordinadorTextWatcher)
            etFechaNac.addTextChangedListener(coordinadorTextWatcher)
            etFacultad.addTextChangedListener(coordinadorTextWatcher)
            etTitulo.addTextChangedListener(coordinadorTextWatcher)
            etEmail.addTextChangedListener(coordinadorTextWatcher)
        }
    }

    private fun agregarCoord() {
        if (entradasValidas()) {
            viewModel.agregarCoordinador(
                Coordinador(
                    0, binding.etNombres.text.toString().trim(),
                    binding.etApellidos.text.toString().trim(),
                    binding.etFechaNac.text.toString().trim(),
                    binding.etTitulo.text.toString().trim(),
                    binding.etEmail.text.toString().trim(),
                    binding.etFacultad.text.toString().trim()
                )
            )
        }
    }

    private fun limpiar() {
        with(binding) {
            etNombres.text = null
            etApellidos.text = null
            etFechaNac.text = null
            etFacultad.text = null
            etTitulo.text = null
            etEmail.text = null
            etNombres.requestFocus()
        }
    }

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

    private val coordinadorTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            binding.btnAgregar.isEnabled = entradasValidas()
        }

        override fun afterTextChanged(p0: Editable?) {}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Cerrar Teclado
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}