package com.example.tokoikanhias.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokoikanhias.R
import com.example.tokoikanhias.application.ikanapp
import com.example.tokoikanhias.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val ikanViewModel: ikanViewModel by viewModels {
        ikanViewModelFactory((applicationContext as ikanapp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ikanListAdapter {Ikan ->
            // ini list yang bisa di klik dan mendapatkan data toko jadi tidak null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(Ikan)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        ikanViewModel.allikan.observe(viewLifecycleOwner) { ikan ->
            ikan.let {
                if (ikan.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.IlustrationImageView.visibility = View.VISIBLE
                } else {
                    binding.emptyTextView.visibility = View.GONE
                    binding.IlustrationImageView.visibility = View.GONE
                }
                adapter.submitList(ikan)
            }
        }


        binding.fab.setOnClickListener {
            //ini button tambah jadi toko pasti null
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
        binding.ContackButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FiveFragment)
        }
        binding.Catalog.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FourFragment)
        }
        binding.About.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThreeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}