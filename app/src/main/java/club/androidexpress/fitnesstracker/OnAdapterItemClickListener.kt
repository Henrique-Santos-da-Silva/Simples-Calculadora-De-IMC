package club.androidexpress.fitnesstracker

import android.content.Context

interface OnAdapterItemClickListener {
    fun onLongClick(position: Int, type: String, id: Int, context: Context)
}