package id.sobat.sobat.FragmentMain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.sobat.sobat.R
import id.sobat.sobat.AuthActivity
import java.util.HashMap
import id.sobat.sobat.Adapter.RvaChat
import id.sobat.sobat.Model.DataLocal

class ChatFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        // Setting toolbar
        setHasOptionsMenu(true)
        activity!!.findViewById<CardView>(R.id.search_bar).visibility = View.GONE
        val titleBar = activity!!.findViewById<TextView>(R.id.title_bar)
        titleBar.visibility = View.VISIBLE
        titleBar.text = getString(R.string.chat)

        mAuth = FirebaseAuth.getInstance()

        // Init rv
        getDbCons(view)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.home_menu, menu)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun initRvConselor(context: Context, list: List<HashMap<String, Any?>>, rv: RecyclerView) {
        val adapter = RvaChat(context, list)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager
    }

    private fun getDbCons(view: View) {
        val rvPopConselor = view.findViewById<RecyclerView>(R.id.rv_chat)

        db.collection("chats")
                .whereEqualTo("id_user", mAuth.currentUser?.uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val dataList: ArrayList<HashMap<String, Any?>> = ArrayList()
                        for (document in it.result) {
                            val data: HashMap<String, Any?> = hashMapOf(
                                    "id_chat" to document.id,
                                    "id_user" to document.data["id_user"],
                                    "id_cons" to document.data["id_cons"],
                                    "date" to document.data["date"],
                                    "name" to document.data["name"],
                                    "last_chat" to document.data["last_chat"],
                                    "photo" to document.data["photo"]
                            )
                            dataList.add(data)
                        }
                        initRvConselor(view.context, dataList, rvPopConselor)
                    } else {
                        Log.d(DataLocal.TAG_QUERY, "Error getting documents: ", it.exception)
                    }
                }
    }

}
