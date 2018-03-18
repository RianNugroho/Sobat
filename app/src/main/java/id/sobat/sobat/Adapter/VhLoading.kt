package id.sobat.sobat.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import id.sobat.sobat.R

class VhLoading(view: View): RecyclerView.ViewHolder(view) {
    val pbLoading = view.findViewById<ProgressBar?>(R.id.pb_loading)
}