package com.ae.apps.messagecounter.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ae.apps.common.views.EmptyRecyclerView
import com.ae.apps.messagecounter.R
import com.ae.apps.messagecounter.adapters.IgnoredNumbersAdapter
import com.ae.apps.messagecounter.data.AppDatabase
import com.ae.apps.messagecounter.data.listeners.IgnoreContactListener
import com.ae.apps.messagecounter.data.models.IgnoredNumber
import com.ae.apps.messagecounter.data.repositories.IgnoredNumbersRepository
import com.ae.apps.messagecounter.data.viewmodels.IgnoredNumbersViewModel
import com.ae.apps.messagecounter.data.viewmodels.IgnoredNumbersViewModelFactory
import kotlinx.android.synthetic.main.fragment_ignore_numbers.*
import java.util.*

/**
 * [Fragment] subclass that shows IgnoredNumbers
 *
 */
class IgnoreNumbersFragment : Fragment(), IgnoreContactListener {

    companion object {
        fun newInstance(): IgnoreNumbersFragment {
            return IgnoreNumbersFragment()
        }
    }

    private lateinit var mViewModel: IgnoredNumbersViewModel
    private lateinit var mAdapter: IgnoredNumbersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ignore_numbers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    private fun initViewModel() {
        val repository = IgnoredNumbersRepository.getInstance(AppDatabase
                .getInstance(requireContext()).ignoredNumbersDao())
        val factory = IgnoredNumbersViewModelFactory(repository)
        mViewModel = ViewModelProviders.of(this, factory).get(IgnoredNumbersViewModel::class.java)
        mAdapter = IgnoredNumbersAdapter(Collections.emptyList(), requireContext(), this)
    }

    private fun initUI() {
        btnShowIgnoreDialog.setOnClickListener(View.OnClickListener {
            // Show ignore contact dialog
        })

        val recyclerView = list as EmptyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mAdapter
        recyclerView.setEmptyView(empty_view)

        mViewModel.getIgnoredNumbers().observe(this, Observer { items ->
            run {
                mAdapter.setItems(items!!)
            }
        })
    }

    override fun onContactSelected(ignoredContact: IgnoredNumber) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIgnoredContactRemoved(ignoredContact: IgnoredNumber) {
        mViewModel.unIgnoreNumber(ignoredContact)
    }
}
