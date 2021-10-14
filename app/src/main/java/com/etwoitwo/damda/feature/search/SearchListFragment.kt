package com.etwoitwo.damda.feature.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentSearchListBinding
import com.etwoitwo.damda.model.dataclass.Search

private const val ARG_PARAM1 = "param1"

class SearchListFragment : Fragment() {
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!


    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(param1 == "gs") {
            val dataList = arrayListOf<Search>(
                Search("코스닥", "078930", "GS", 45000),
                Search("코스닥", "078935", "GS우", 37150),
                Search("코스피", "083450", "GST", 25550)
            )

            val adapter = SearchListAdapter(dataList)
            binding.recyclerViewSearch.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}