package pe.edu.upeu.primereval.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.upeu.primereval.R
import pe.edu.upeu.primereval.databinding.FragmentEmpresasListBinding
import pe.edu.upeu.primereval.ui.home.adapters.EmpresaAdapter
import pe.edu.upeu.primereval.utils.Constants.EXTRAS_BICHO
import pe.edu.upeu.primereval.utils.DataState
import pe.edu.upeu.primereval.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmpresasListFragment : Fragment() {

    private var _binding : FragmentEmpresasListBinding? = null
    private val binding get() =_binding!!

    private val viewModel: EmpresasViewModel by viewModels()

    @Inject
    lateinit var bichosAdapter: EmpresaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEmpresasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initRecyclerView()
        initObservers()
        initListeners()
    }

    private fun init(){
        viewModel.getAllBichos()
    }

    private fun initObservers(){
        viewModel.getBichosState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    bichosAdapter.submitList(dataState.data)
                }
                is DataState.Error -> {
                    activity?.toast("Oops, algo salio mal, intente de nuevo")
                }

                else -> Unit
            }
        })

        viewModel.deleteBichoState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    activity?.toast("Empresa eliminado correctamente")
                    viewModel.getAllBichos()
                }
                is DataState.Error -> {
                    activity?.toast("Oops, algo salio mal, intente de nuevo")
                }

                else -> Unit
            }
        })
    }

    private fun initRecyclerView()  = binding.rvBichos.apply {
        adapter = bichosAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_bichosListFragment_to_addEditBichoFragment)
        }
        bichosAdapter.setItemClickListener {
            val bundle = bundleOf(EXTRAS_BICHO to it)
            findNavController().navigate(R.id.action_bichosListFragment_to_addEditBichoFragment, bundle )
        }
        bichosAdapter.setDeleteClickListener { bicho ->
            viewModel.deleteBicho(bicho.id)
        }
    }


}