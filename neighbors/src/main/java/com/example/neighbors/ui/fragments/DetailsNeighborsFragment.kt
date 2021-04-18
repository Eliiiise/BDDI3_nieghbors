package com.example.neighbors.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.neighbors.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.databinding.DetailsNeighborBinding
import com.example.neighbors.di.DI
import com.example.neighbors.models.Neighbor
import java.util.concurrent.Executors

class DetailsNeighborsFragment(neighbor: Neighbor) : Fragment() {
    lateinit var binding: DetailsNeighborBinding
    var neighborSelected = neighbor
    var isFavorite: Boolean = false

    /**
     * Fonction permettant de définir une vue à attachée à un fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_neighbor, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.add_neighbor)
        }

        neighborSelected?.let { initData(it) }
    }

    private fun initData(neighbor: Neighbor) {
        binding.detailsName.text = neighbor.name
        binding.detailsPhone.text = neighbor.phoneNumber
        binding.detailsAdress.text = neighbor.address
        binding.detailsSite.text = neighbor.webSite
        binding.detailsBio.text = neighbor.aboutMe

        isFavorite = neighbor.favorite
        loadImagefavorite()

        Glide.with(this).load(neighbor.avatarUrl).placeholder(R.drawable.ic_baseline_perm_identity_24).error(R.drawable.ic_baseline_perm_identity_24).into(binding.detailsImage)

        binding.detailsLikeButton.setOnClickListener {
            onAddFavorite(neighbor)
        }
    }

    private fun loadImagefavorite() {
        if (isFavorite) {
            Glide.with(this).load(R.drawable.ic_baseline_favorite_24).placeholder(R.drawable.ic_baseline_favorite_24).error(R.drawable.ic_baseline_favorite_24).into(binding.detailsLikeButton)
        } else {
            Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24).placeholder(R.drawable.ic_baseline_favorite_border_24).error(R.drawable.ic_baseline_favorite_border_24).into(binding.detailsLikeButton)
        }
    }

    private fun onAddFavorite(neighbor: Neighbor) {
        Executors.newSingleThreadExecutor().execute {
            DI.repository.onFavorite(neighbor)
        }

        isFavorite = !isFavorite
        loadImagefavorite()
    }
}
