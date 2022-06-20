package com.rai.hearthstonecard.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rai.hearthstonecard.R
import com.rai.hearthstonecard.databinding.FragmentFilterCardBinding
import com.rai.hearthstonecard.domain.model.Constants
import com.rai.hearthstonecard.domain.model.Filters


class FilterCardFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterCardBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<FilterCardFragmentArgs>()

    private var mana: String = ""
    private var attack: String = ""
    private var health: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentFilterCardBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setFilterStats(R.array.mana_count, manaCostSpinner)
            setFilterStats(R.array.attack_count, attackSpinner)
            setFilterStats(R.array.health_count, healthSpinner)

            setSpinnerValue(manaCostSpinner, mapToSpinnerValue(args.filter.mana))
            setSpinnerValue(attackSpinner, mapToSpinnerValue(args.filter.attack))
            setSpinnerValue(healthSpinner, mapToSpinnerValue(args.filter.health))

            buttonCancel.setOnClickListener {
                onCancelClicked()
            }
            buttonOk.setOnClickListener {
                onSearchClicked()
            }
        }
    }

    private fun showToast(
        context: Context = requireContext(),
        message: String,
        duration: Int = Toast.LENGTH_LONG,
    ) {
        Toast.makeText(context, message, duration).show()
    }


    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    private fun onSearchClicked() {
        val filters = Filters(mana, attack, health)
        setFragmentResult(
            Constants.REQUEST_KEY,
            bundleOf(Constants.EXTRA_KEY to filters)
        )
        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }

    private fun setSpinnerValue(spinner: Spinner, value: Int) {
        for (i in 0 until spinner.adapter.count) {
            if (i == value) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun setFilterStats(array: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            array,
            R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_text)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val result = when (p2) {
                    0 -> ""
                    1 -> "0"
                    2 -> "1"
                    3 -> "2"
                    4 -> "3"
                    5 -> "4"
                    6 -> "5"
                    7 -> "6"
                    8 -> "7"
                    9 -> "8"
                    10 -> "9"
                    11 -> "10"
                    else -> ""
                }
                when (array) {
                    R.array.mana_count -> {
                        mana = result
                    }
                    R.array.attack_count -> {
                        attack = result
                    }
                    R.array.health_count -> {
                        health = result
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                showToast(message = getString(R.string.nothing_selected))
            }
        }
    }

    private fun mapToSpinnerValue(value: String): Int {
        return when (value) {
            "0" -> 1
            "1" -> 2
            "2" -> 3
            "3" -> 4
            "4" -> 5
            "5" -> 6
            "6" -> 7
            "7" -> 8
            "8" -> 9
            "9" -> 10
            "10" -> 11
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}