package id.sobat.sobat.FragmentMain

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.*
import android.widget.TextView

import id.sobat.sobat.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // Setting toolbar
        setHasOptionsMenu(true)
        activity!!.findViewById<CardView>(R.id.search_bar).visibility = View.VISIBLE
        activity!!.findViewById<TextView>(R.id.title_bar).visibility = View.GONE
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.home_menu, menu)
    }

}
