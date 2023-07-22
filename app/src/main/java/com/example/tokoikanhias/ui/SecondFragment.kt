package com.example.tokoikanhias.ui

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tokoikanhias.R
import com.example.tokoikanhias.application.ikanapp
import com.example.tokoikanhias.databinding.FragmentSecondBinding
import com.example.tokoikanhias.model.Ikan
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment() : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val ikanViewModel: ikanViewModel by viewModels {
        ikanViewModelFactory((applicationContext as ikanapp).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var ikan: Ikan? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        //binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

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
                //
                if (ikan == null) {
                    val ikan = Ikan(0, name.toString(), type.toString(), Address.toString(),currentLatLang?.latitude, currentLatLang?.longitude)
                    ikanViewModel.insert(ikan)
                } else {
                    val ikan = Ikan(ikan?.id!!, name.toString(), type.toString(), Address.toString(),currentLatLang?.latitude, currentLatLang?.longitude)
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // implement drag marker
        mMap.setOnMarkerDragListener(this)

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }


    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // mengecek jika permission tidak disetujui maka akan berhenti di kondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

//untuk test current Location coba run di device langsung atau build apknya terus install di hp masing-masing
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"

                    if (ikan != null) {
                        title = ikan?.name.toString()
                        val newCurrentLocation = LatLng(ikan?.latitude!!, ikan?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val markerOptions = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.fish))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang, 15f))
                }
            }
    }
}

