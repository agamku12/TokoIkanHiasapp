package com.example.tokoikanhias.ui

import android.content.Context
import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tokoikanhias.R
import com.example.tokoikanhias.application.ikanapp
import com.example.tokoikanhias.databinding.FragmentSecondBinding
import com.example.tokoikanhias.model.Ikan

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val ikanViewModel: ikanViewModel by viewModels {
        ikanViewModelFactory((applicationContext as ikanapp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var ikan: Ikan? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ikan = args.ikan
        // kita cek jika ikan null maka tampilan deflaut nambah nama
        // jika ikan tidak nul tampilan sedikit berubah ada tombol hapus
        if (ikan != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(ikan?.name)
            binding.TypeeditText.setText(ikan?.type)
            binding.AddresseditText.setText(ikan?.address)
        }
        val name = binding.nameEditText.text
        val type = binding.TypeeditText.text
        val Address = binding.AddresseditText.text
        binding.saveButton.setOnClickListener {
            // kita kasih kondisi jika data kosong tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (type.isEmpty()) {
                Toast.makeText(context, "Jenis tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (Address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (ikan == null){
                    val ikan = Ikan( 0, name.toString(), type.toString(), Address.toString())
                    ikanViewModel.insert(ikan)
                }else {
                    val ikan = Ikan(ikan?.id!!, name.toString(), type.toString(), Address.toString())
                    ikanViewModel.update(ikan)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            ikan?.let { ikanViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }
}

