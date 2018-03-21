package id.sobat.sobat.FragmentMain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.sobat.sobat.Adapter.RvaConselor
import id.sobat.sobat.AuthActivity
import id.sobat.sobat.Model.DataLocal
import id.sobat.sobat.R
import java.util.HashMap
import kotlin.collections.ArrayList
import id.sobat.sobat.ProfileActivity
import id.sobat.sobat.NotificationActivity
import id.sobat.sobat.SearchActivity

class HomeFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Setting toolbar
        setHasOptionsMenu(true)
        activity!!.findViewById<CardView>(R.id.search_bar).visibility = View.VISIBLE
        activity!!.findViewById<TextView>(R.id.title_bar).visibility = View.GONE

        mAuth = FirebaseAuth.getInstance()

        // Set focus
        val root = view.findViewById<CardView>(R.id.curhat)
        root.requestFocus()

        // Init rv
        getDbCons(view)

        val btnCurhat = view.findViewById<Button>(R.id.btn_curhat)
        val etCurhat = view.findViewById<EditText>(R.id.et_curhat)
        btnCurhat.setOnClickListener {
            val curhat = etCurhat.text.toString()
            val dataCurhat: HashMap<String, Any?> = hashMapOf(
                    "accept" to false,
                    "from" to mAuth.currentUser?.uid,
                    "date" to FieldValue.serverTimestamp(),
                    "text" to curhat,
                    "name" to DataLocal.user["name"],
                    "avatar" to DataLocal.user["avatar"]
            )
            db.collection("public_problems").document().set(dataCurhat)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(view.context, getString(R.string.success_curhat), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(view.context, getString(R.string.error_database), Toast.LENGTH_SHORT).show()
                            Log.w(DataLocal.TAG_QUERY, "Error getting documents.", it.exception)
                        }
                    }
        }

        // Refresh layout
        val srHome = view.findViewById<SwipeRefreshLayout>(R.id.sr_home)
        srHome.setOnRefreshListener {
            getDbCons(view)
            srHome.isRefreshing = false
        }

        // Search
        val sHome = activity!!.findViewById<CardView>(R.id.search_bar)
        sHome.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(DataLocal.DATA_KEY_SHARE, "conselor")
            context?.startActivity(intent)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.notification_home -> {
                val intent = Intent(context, NotificationActivity::class.java)
                context?.startActivity(intent)
                return true
            }
            R.id.profile_home -> {
                val intent = Intent(context, ProfileActivity::class.java)
                context?.startActivity(intent)
                return true
            }
        }

        return false
    }

    private fun initRvConselors(context: Context, list: List<HashMap<String, Any?>>, rv: RecyclerView) {
        val adapter = RvaConselor(context, list)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv.layoutManager = linearLayoutManager
    }

    private fun getDbCons(view: View) {
        val rvPopConselors = view.findViewById<RecyclerView>(R.id.rv_pop_conselors)

        db.collection("conselors")
                .orderBy("point", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val dataList: ArrayList<HashMap<String, Any?>> = ArrayList()
                        for (document in it.result) {
                            val data: HashMap<String, Any?> = hashMapOf(
                                    "id_user" to document.id,
                                    "name" to document.data["name"],
                                    "point" to document.data["point"],
                                    "photo" to document.data["photo"]
                            )
                            dataList.add(data)
                        }
                        initRvConselors(view.context, dataList, rvPopConselors)
                    } else {
                        Log.d(DataLocal.TAG_QUERY, "Error getting documents: ", it.exception)
                    }
                }
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
}
