package id.sobat.sobat.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import id.sobat.sobat.R

class VhChat(view: View): RecyclerView.ViewHolder(view) {
    val lnChat = view.findViewById<LinearLayout?>(R.id.ln_chat)
    val ivChat = view.findViewById<ImageView?>(R.id.iv_chat)
    val tvNameChat = view.findViewById<TextView?>(R.id.tv_name_chat)
    val tvTextChat = view.findViewById<TextView?>(R.id.tv_text_chat)
    val tvDateChat = view.findViewById<TextView?>(R.id.tv_date_chat)
}