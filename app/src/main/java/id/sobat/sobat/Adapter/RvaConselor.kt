package id.sobat.sobat.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import id.sobat.sobat.Model.DataLocal
import id.sobat.sobat.R
import java.util.HashMap

class RvaConselor(context: Context, private val listCons: List<HashMap<String, Any?>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val listenerClick = View.OnClickListener {
        val vHolder = it.tag as VhConselor
        val idUser = listCons[vHolder.adapterPosition]["id_user"].toString()
        Toast.makeText(context, idUser, Toast.LENGTH_SHORT).show()
//        val intent = Intent(context, DetailCons::class.java)
//        intent.putExtra(DataLocal.DATA_KEY_SHARE, idUser)
//        context.startActivity(intent)
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= listCons.size) {
            return 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 0) {
            view = inflater.inflate(R.layout.conselor, parent, false)
            return VhConselor(view)
        }
        view = inflater.inflate(R.layout.loading, parent, false)
        return VhLoading(view)
    }

    override fun getItemCount(): Int {
        return listCons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val holder0 = holder as VhConselor
            val layoutParams0 = holder0.lnCons?.layoutParams
            layoutParams0?.width = DataLocal.width / 3
            layoutParams0?.height = DataLocal.width / 2
            holder0.lnCons?.layoutParams = layoutParams0

            var name = listCons[position]["name"].toString()
            if (name.length > 12) {
                name = name.substring(0, 10) + ".."
            }
            val point = listCons[position]["point"].toString() + " Point"
            holder0.lnCons?.setOnClickListener(listenerClick)
            holder0.lnCons?.tag = holder0
            holder0.tvNameCons?.text = name
            holder0.tvPointCons?.text = point
            Picasso.get()
                    .load(listCons[position]["photo"].toString())
                    .placeholder(R.drawable.ic_photo_camera)
                    .error(R.drawable.ic_warning)
                    .into(holder0.ivCons)
        } else {
            val holder1 = holder as VhLoading
            val layoutParams1 = holder1.pbLoading?.layoutParams
            layoutParams1?.width = DataLocal.width / 3
            layoutParams1?.height = DataLocal.width / 2
            holder1.pbLoading?.layoutParams = layoutParams1
        }
    }

}