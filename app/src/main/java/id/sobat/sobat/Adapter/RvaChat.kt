package id.sobat.sobat.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.sobat.sobat.ChatActivity
import id.sobat.sobat.Model.DataLocal
import id.sobat.sobat.R
import java.util.*

class RvaChat(context: Context, private val listChat: List<HashMap<String, Any?>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val listenerClick = View.OnClickListener {
        val vHolder = it.tag as VhChat
        val idUser = listChat[vHolder.adapterPosition]["id_chat"].toString()
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(DataLocal.DATA_KEY_SHARE, idUser)
        context.startActivity(intent)
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= listChat.size) {
            return 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 0) {
            view = inflater.inflate(R.layout.chat, parent, false)
            return VhChat(view)
        }
        view = inflater.inflate(R.layout.loading, parent, false)
        return VhLoading(view)
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val holder0 = holder as VhChat

            var nameCons = listChat[position]["name"].toString()
            if (nameCons.length > 24) {
                nameCons = nameCons.substring(0, 24) + "..."
            }
            var lastChat = listChat[position]["last_chat"].toString()
            if (lastChat.length > 56) {
                lastChat = lastChat.substring(0, 56) + "..."
            }
            val date = listChat[position]["date"].toString()

            holder0.clChat?.setOnClickListener(listenerClick)
            holder0.clChat?.tag = holder0
            holder0.tvNameChat?.text = nameCons
            holder0.tvTextChat?.text = lastChat
            holder0.tvDateChat?.text = date
            Picasso.get()
                    .load(listChat[position]["photo"].toString())
                    .placeholder(R.drawable.ic_photo_camera)
                    .error(R.drawable.ic_warning)
                    .into(holder0.ivChat)
        }
    }

}