package com.hamza.ecommerce.ui.auth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamza.ecommerce.databinding.ItemCountryLayoutBinding
import com.hamza.ecommerce.ui.auth.models.CountryUIModel


class CountriesAdapter(
    private val countries: List<CountryUIModel>,
    private val countryClickListener: CountryClickListener
) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(private val binding: ItemCountryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CountryUIModel) {
            binding.country = category
            binding.root.setOnClickListener {
                countryClickListener.onCountryClicked(category)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            ItemCountryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size

}

interface CountryClickListener {
    fun onCountryClicked(country: CountryUIModel)
}