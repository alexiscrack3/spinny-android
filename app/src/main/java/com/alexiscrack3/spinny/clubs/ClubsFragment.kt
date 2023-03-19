package com.alexiscrack3.spinny.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.api.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class ClubsFragment : Fragment() {
    @Inject lateinit var clubsService: ClubsService
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_clubs, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ClubsAdapter()
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        fetchClubs()
    }

    private fun fetchClubs() {
        clubsService.getClubs()?.enqueue(object :
            Callback<ApiResponse<List<ClubData>>?> {
            override fun onResponse(
                call: Call<ApiResponse<List<ClubData>>?>,
                apiResponse: Response<ApiResponse<List<ClubData>>?>
            ) {
                if (apiResponse.isSuccessful) {
                    val result = apiResponse.body()
                    val recyclerView = view as RecyclerView
                    val adapter = recyclerView.adapter as ClubsAdapter
                    adapter.swap(result?.data.orEmpty())
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<ClubData>>?>, t: Throwable) {
                print(t.message)
            }
        })
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ClubsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}