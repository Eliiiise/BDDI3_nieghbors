package com.example.neighbors.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbors.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.adapters.ListNeighborHandler
import com.example.neighbors.adapters.ListNeighborsAdapter
import com.example.neighbors.databinding.ListNeighborsFragmentBinding
import com.example.neighbors.di.DI
import com.example.neighbors.models.Neighbor
import com.example.neighbors.viewmodels.NeighborViewModel
import java.util.concurrent.Executors

class ListNeighborsFragment : Fragment(), ListNeighborHandler {

    private lateinit var recyclerView: RecyclerView
    lateinit var binding: ListNeighborsFragmentBinding
    private lateinit var viewModel: NeighborViewModel
    var persitent: Boolean = false

    /**
     * Fonction permettant de définir une vue à attacher à un fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_neighbors_fragment, container, false)
        recyclerView = binding.root.findViewById(R.id.neighbors_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NeighborViewModel::class.java)

        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.list_neighbor)
        }

        setHasOptionsMenu(true)

        setData()

        onAddNeighbor()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_type_data, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_no_persistent -> {
                persitent = false
                DI.repository.dataSourceInMemory(persitent)
                setData()
            }

            R.id.menu_persistent -> {
                persitent = true
                DI.repository.dataSourceInMemory(persitent)
                setData()
            }

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun setData() {
        viewModel.neighbors.observe(viewLifecycleOwner) {
            val adapter = ListNeighborsAdapter(it, this)
            binding.neighborsList.adapter = adapter
        }
    }

    private fun delete(neighbor: Neighbor) {
        Executors.newSingleThreadExecutor().execute {
            DI.repository.deleteNeighbor(neighbor)
        }

        binding.neighborsList.adapter?.notifyDataSetChanged()
    }

    override fun onDeleteNeibor(neighbor: Neighbor) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(getString(R.string.confirm_delete))
                setMessage(getString(R.string.message_delete))
                setPositiveButton(
                        R.string.ok
                ) { _, _ ->
                    delete(neighbor)
                }
                setNegativeButton(
                        R.string.cancel
                ) { _, _ ->
                }
            }

            builder.create()
        }
        alertDialog?.show()
    }

    override fun onAddFavorite(neighbor: Neighbor) {
        Executors.newSingleThreadExecutor().execute {
            DI.repository.onFavorite(neighbor)
        }
    }

    override fun goWebsite(neighbor: Neighbor) {
        val url = Uri.parse("http://" + neighbor.webSite)
        val i = Intent(Intent.ACTION_VIEW, url)
        startActivity(i)
    }

    override fun onAddNeighbor() {
        binding.neighborAdd.setOnClickListener {
            (activity as? NavigationListener)?.let {
                it.showFragment(AddNeighborsFragment())
            }
        }
    }

    override fun onViewDetails(neighbor: Neighbor) {
        (activity as? NavigationListener)?.let {
            it.showFragment(DetailsNeighborsFragment(neighbor))
        }
    }
}
