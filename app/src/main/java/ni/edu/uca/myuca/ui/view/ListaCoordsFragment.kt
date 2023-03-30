package ni.edu.uca.myuca.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.myuca.R
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

        initListeners()
        initRecyclerView()
    }

    private fun initListeners() {
        binding.fabNuevoCoord.setOnClickListener() {
            it.findNavController().navigate(R.id.action_listaCoordsFragment_to_addCoordsFragment)
        }

        //Detecta Scrolling del Recycler View
        binding.rcvListaCoords.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //Detecta la direccion en la cual se mueve el Recycler View
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //Si hay movimiento hacia abajo, el FAB se encoje
                if (dy > 0) {
                    binding.fabNuevoCoord.shrink()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //Si no se puede scroll de manera vertical y el estado del Recycler View esta Idle
                //Indica que el Recycler View ha llegado al tope de arriba
                if (!recyclerView.canScrollVertically(-1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    //Vuelve a extender el FAB
                    binding.fabNuevoCoord.extend()
                }
            }
        })
    }

    private fun initRecyclerView() {
        viewModel.conseguirCoordinadores()

        val adapter = CoordListCardAdapter {
            DetailsCoordFragment().show(childFragmentManager, it.id.toString())
        }
        val layoutManager = LinearLayoutManager(this.context)

        binding.rcvListaCoords.layoutManager = (layoutManager)
        binding.rcvListaCoords.adapter = adapter

        viewModel.listaCoords.observe(this.viewLifecycleOwner) { response ->
            if(response.isSuccessful){
                adapter.submitList(response.body()?.coordinadores)
            } else {
                Toast.makeText(this.context, "Error de conexion", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}