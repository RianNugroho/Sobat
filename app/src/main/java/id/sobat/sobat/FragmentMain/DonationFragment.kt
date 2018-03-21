package id.sobat.sobat.FragmentMain

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.sobat.sobat.AuthActivity
import id.sobat.sobat.R
import id.sobat.sobat.DonationActivity
import id.sobat.sobat.Model.DataLocal

class DonationFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_donation, container, false)
        // Setting toolbar
        setHasOptionsMenu(true)
        activity!!.findViewById<CardView>(R.id.search_bar).visibility = View.GONE
        val titleBar = activity!!.findViewById<TextView>(R.id.title_bar)
        titleBar.visibility = View.VISIBLE
        titleBar.text = getString(R.string.donation)

        mAuth = FirebaseAuth.getInstance()

        val btnDonationMoney = view.findViewById<Button>(R.id.btn_donation_money)
        val btnDonationAds = view.findViewById<Button>(R.id.btn_donation_ads)
        val btnDonationExpert = view.findViewById<Button>(R.id.btn_donation_expert)

        btnDonationMoney.setOnClickListener {
            val intent = Intent(context, DonationActivity::class.java)
            intent.putExtra(DataLocal.DATA_KEY_SHARE, "money")
            view.context.startActivity(intent)
        }

        btnDonationAds.setOnClickListener {
            val intent = Intent(context, DonationActivity::class.java)
            intent.putExtra(DataLocal.DATA_KEY_SHARE, "ads")
            view.context.startActivity(intent)
        }

        btnDonationExpert.setOnClickListener {
            val intent = Intent(context, DonationActivity::class.java)
            intent.putExtra(DataLocal.DATA_KEY_SHARE, "expert")
            view.context.startActivity(intent)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.donasi_menu, menu)
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
