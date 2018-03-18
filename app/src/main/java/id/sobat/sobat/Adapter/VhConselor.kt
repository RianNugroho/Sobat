package id.sobat.sobat.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import id.sobat.sobat.R

class VhConselor(view: View): RecyclerView.ViewHolder(view) {
    val lnCons = view.findViewById<LinearLayout?>(R.id.ln_cons)
    val ivCons = view.findViewById<ImageView?>(R.id.iv_cons)
    val tvNameCons = view.findViewById<TextView?>(R.id.tv_name_cons)
    val tvPointCons = view.findViewById<TextView?>(R.id.tv_point_cons)
}