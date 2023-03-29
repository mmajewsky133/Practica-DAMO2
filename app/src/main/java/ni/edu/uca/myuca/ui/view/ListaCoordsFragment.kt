package ni.edu.uca.myuca.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.myuca.ui.view.adapter.CoordListCardAdapter
import ni.edu.uca.myuca.databinding.FragmentListaCoordsBinding
import ni.edu.uca.myuca.ui.viewmodel.CoordsViewModel

class ListaCoordsFragment : Fragment() {

    //ViewModel
    private val viewModel: CoordsViewModel by activityViewModels {
        CoordsViewModel.factory
    }

    // El ViewBinding
    private var _binding: FragmentListaCoordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaCoordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = CoordListCardAdapter()
        val layoutManager = LinearLayoutManager(this.context)

        binding.rcvListaCoords.layoutManager = (layoutManager)
        binding.rcvListaCoords.adapter = adapter

        this.context?.let { context ->
            viewModel.conseguirDatos(context) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}