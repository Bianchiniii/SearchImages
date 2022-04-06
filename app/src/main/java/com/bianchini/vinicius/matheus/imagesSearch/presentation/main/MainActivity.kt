package com.bianchini.vinicius.matheus.imagesSearch.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bianchini.vinicius.matheus.imagesSearch.databinding.ActivityMainBinding
import com.bianchini.vinicius.matheus.imagesSearch.framework.di.InernetAvailablity
import com.bianchini.vinicius.matheus.imagesSearch.framework.di.NetworkStatus
import com.bianchini.vinicius.matheus.imagesSearch.framework.di.NetworkStatusHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNetworkObserver()
        setToastMessageObserver()
        setSwipeRefreshBehavior()
        initRecyclerImages()
    }

    private fun setSwipeRefreshBehavior() {
        binding.swipeContainer.setOnRefreshListener {
            if (!binding.swipeContainer.isRefreshing) return@setOnRefreshListener
            else {
                mainAdapter.submitList(listOf())
                model.getAllImages()
                model.toastMessage.postValue("Imagens atualizadas ✌️")
                binding.swipeContainer.isRefreshing = false
            }
        }
    }

    private fun setToastMessageObserver() {
        model.toastMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNetworkObserver() {
        if (InernetAvailablity.isOnline()) binding.relativeLayoutConexaoInternet.visibility =
            View.GONE
        else binding.relativeLayoutConexaoInternet.visibility = View.VISIBLE
        NetworkStatusHelper(this).observe(this) { networkStatus ->
            if (NetworkStatus.Available == networkStatus) {
                binding.relativeLayoutConexaoInternet.visibility = View.GONE
            } else if (NetworkStatus.Unavailable == networkStatus) {
                binding.relativeLayoutConexaoInternet.visibility = View.VISIBLE
            }
        }
    }


    private fun initRecyclerImages() {
        binding.recyclerViewImages.run {
            setHasFixedSize(true)
            adapter = mainAdapter
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@MainActivity, 4)
        }

        model.imageResponse.observe(this) { response ->
            if (response != null) {
                binding.textView.visibility = View.GONE
                mainAdapter.submitList(response.data)
            } else {
                binding.textView.visibility = View.VISIBLE
            }
        }
    }
}