package com.alexiscrack3.spinny.clubs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexiscrack3.spinny.R
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_club.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ClubFragment : Fragment() {
    private val clubViewModel by viewModel<ClubViewModel>()

    companion object {
        const val CLUB_ID_KEY = "CLUB_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club, container, false)
    }

    override fun onStart() {
        super.onStart()
        val clubId = requireArguments().getString(CLUB_ID_KEY).orEmpty()
        clubViewModel.getClubId(clubId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                club_name_text_view.text = it.data.name
            }, {
                Timber.e(it)
            })
    }
}
