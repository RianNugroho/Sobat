package id.sobat.sobat.FragmentMain

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.*
import android.widget.TextView

import id.sobat.sobat.R
import ss.com.bannerslider.banners.Banner
import ss.com.bannerslider.banners.RemoteBanner
import ss.com.bannerslider.views.BannerSlider

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Setting toolbar
        setHasOptionsMenu(true)
        activity!!.findViewById<CardView>(R.id.search_bar).visibility = View.VISIBLE
        activity!!.findViewById<TextView>(R.id.title_bar).visibility = View.GONE

        // Set focus
        val root = view.findViewById<CardView>(R.id.curhat)
        root.requestFocus()

        bannerToday(view)

        return view
    }

    private fun bannerToday(view: View) {
        val bannerSlider = view.findViewById<BannerSlider>(R.id.bs_today)
        val banners = ArrayList<Banner>()
        // Add banner using image url
        banners.add(RemoteBanner("http://eskipaper.com/images/image-2.jpg"))
        banners.add(RemoteBanner("https://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg"))
        bannerSlider.setBanners(banners)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.home_menu, menu)
    }

}
