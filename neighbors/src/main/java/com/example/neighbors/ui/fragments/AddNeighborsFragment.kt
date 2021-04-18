package com.example.neighbors.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.databinding.AddNeighborBinding
import com.example.neighbors.di.DI
import com.example.neighbors.models.Neighbor
import java.util.concurrent.Executors


class AddNeighborsFragment : Fragment() {

    lateinit var binding: AddNeighborBinding

    /**
     * Fonction permettant de définir une vue à attachée à un fragment
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_neighbor, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? NavigationListener)?.let {
            it.updateTitle(R.string.add_neighbor)
        }

        detectChanges()
        addNeighborForm()
    }

    private fun detectChanges() {
        val fragment = this

        with(binding) {

            binding.textImage.doAfterTextChanged {
                if (!android.webkit.URLUtil.isValidUrl(textImage.text.toString())) {
                    textImage.setError(getString(R.string.error_link))
                } else {
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(120))

                    Glide.with(fragment)
                            .load(textImage.text.toString())
                            .apply(requestOptions)
                            .placeholder(R.drawable.ic_baseline_perm_identity_24)
                            .error(R.drawable.ic_baseline_perm_identity_24)
                            .into(imageView)
                }
                enableButton()
            }

            textAbout.doAfterTextChanged {
                enableButton()
            }

            textAdress.doAfterTextChanged {
                enableButton()
            }

            textName.doAfterTextChanged {
                enableButton()
            }

            textPhone.doAfterTextChanged {
                if (!((textPhone.text!!.startsWith("06") || textPhone.text!!.startsWith("07")) && textPhone.text!!.count() == 10)) {
                    textPhone.setError(getString(R.string.error_phone))
                }
                enableButton()
            }

            textWebSite.doAfterTextChanged {
                if (!android.webkit.URLUtil.isValidUrl(textWebSite.text.toString())) {
                    textWebSite.setError(getString(R.string.error_link))
                }
                enableButton()
            }
        }
    }

    private fun enableButton() {
        with(binding) {
            saveButton.isEnabled = !textAbout.text.isNullOrEmpty() &&
                    ((textPhone.text!!.startsWith("06") || textPhone.text!!.startsWith("07")) && textPhone.text!!.count() == 10) &&
                    !textAdress.text.isNullOrEmpty() &&
                    !textName.text.isNullOrEmpty() &&
                    !textPhone.text.isNullOrEmpty() &&
                    android.webkit.URLUtil.isValidUrl(textWebSite.text.toString()) &&
                    android.webkit.URLUtil.isValidUrl(textImage.text.toString())
        }
    }

    private fun addNeighborForm() {
        with(binding) {
            saveButton.setOnClickListener {
                val neighbor = Neighbor(
                        id = 0,
                        name = textName.text.toString(),
                        avatarUrl = textImage.text.toString(),
                        address = textAdress.text.toString(),
                        phoneNumber = textPhone.text.toString(),
                        aboutMe = textAbout.text.toString(),
                        favorite = false,
                        webSite = textWebSite.text.toString()
                )

                Executors.newSingleThreadExecutor().execute {
                    DI.repository.addNeighbor(neighbor)
                }

                (activity as? NavigationListener)?.let {
                    it.showFragment(ListNeighborsFragment())
                }
            }
        }
    }
}
