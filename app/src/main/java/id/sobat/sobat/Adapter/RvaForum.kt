package id.sobat.sobat.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import id.sobat.sobat.R
import java.util.*

class RvaForum(context: Context, private val listForum: List<HashMap<String, Any?>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val listenerClick = View.OnClickListener {
        val vHolder = it.tag as VhForum
        val idUser = listForum[vHolder.adapterPosition]["id_forum"].toString()
        Toast.makeText(context, idUser, Toast.LENGTH_SHORT).show()
//        val intent = Intent(context, DetailCons::class.java)
//        intent.putExtra(DataLocal.DATA_KEY_SHARE, idUser)
//        context.startActivity(intent)
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= listForum.size) {
            return 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 0) {
            view = inflater.inflate(R.layout.forum, parent, false)
            return VhForum(view)
        }
        view = inflater.inflate(R.layout.loading, parent, false)
        return VhLoading(view)
    }

    override fun getItemCount(): Int {
        return listForum.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val holder0 = holder as VhForum

            var title = listForum[position]["title"].toString()
            if (title.length > 24) {
                title = title.substring(0, 24) + "..."
            }
            var text = listForum[position]["text"].toString()
            if (text.length > 56) {
                text = text.substring(0, 56) + "..."
            }
            val date = listForum[position]["date"].toString()

            holder0.lnForum?.setOnClickListener(listenerClick)
            holder0.lnForum?.tag = holder0
            holder0.tvTitleForum?.text = title
            holder0.tvTextForum?.text = text
            holder0.tvDateForum?.text = date
            Picasso.get()
                    .load(listForum[position]["photo"].toString())
                    .placeholder(R.drawable.ic_photo_camera)
                    .error(R.drawable.ic_warning)
                    .into(holder0.ivForum)
        }
    }

}