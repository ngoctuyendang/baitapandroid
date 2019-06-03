package com.simplemobiletools.musicplayer.dialogs

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.commons.extensions.setupDialogStuff
import com.simplemobiletools.commons.helpers.*
import com.simplemobiletools.musicplayer.R
import com.simplemobiletools.musicplayer.extensions.config
import kotlinx.android.synthetic.main.dialog_change_sorting.view.*

class ChangeSortingDialog(val activity: Activity, val callback: () -> Unit) {
    private var currSorting = 0
    var config = activity.config
    var view: View = activity.layoutInflater.inflate(R.layout.dialog_change_sorting, null)

    init {
        AlertDialog.Builder(activity)
                .setPositiveButton(R.string.ok) { dialog, which -> dialogConfirmed() }
                .setNegativeButton(R.string.cancel, null)
                .create().apply {
                    activity.setupDialogStuff(view, this, R.string.sort_by)
                }

        currSorting = config.sorting
        setupSortRadio()
    }

    private fun setupSortRadio() {
        val sortingRadio = view.sorting_dialog_radio_sorting

        val sortBtn = when {
            currSorting and SORT_BY_TITLE != 0 -> sortingRadio.sorting_dialog_radio_title

            else -> sortingRadio.sorting_dialog_radio_artist
        }
        sortBtn.isChecked = true
    }


    private fun dialogConfirmed() {
        val sortingRadio = view.sorting_dialog_radio_sorting
        var sorting = when (sortingRadio.checkedRadioButtonId) {
            R.id.sorting_dialog_radio_title -> SORT_BY_TITLE
            R.id.sorting_dialog_radio_artist -> SORT_BY_ARTIST
            else -> SORT_BY_DURATION
        }


        config.sorting = sorting
        callback()
    }
}
