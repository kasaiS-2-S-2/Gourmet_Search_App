package com.kasai.gourmet_search_app.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kasai.gourmet_search_app.R
import com.kasai.gourmet_search_app.service.model.Gourmet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            //検索画面のFragment
            val searchGourmetFragment = SearchGourmetFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, searchGourmetFragment, TAG_OF_SEARCH_GOURMET_FRAGMENT)
                    .commit()
        }
    }

    fun showGourmetList(key: String, lat: String, lng: String, range: String, format: String) {
        //店舗一覧のFragment
        val gourmetListFragment = GourmetListFragment.forGourmetList(key, lat, lng, range, format)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("gourmet_list")
            .replace(R.id.fragment_container, gourmetListFragment, null)
            .commit()
    }

    fun showGourmet(key: String, gourmet: Gourmet.Results.Shop, format: String) {
        //店舗詳細のFragment
        val gourmetFragment = GourmetFragment.forGourmet(key, gourmet.id, format)
        supportFragmentManager
                .beginTransaction()
                .addToBackStack("gourmet")
                .replace(R.id.fragment_container, gourmetFragment, null)
                .commit()
    }
}
