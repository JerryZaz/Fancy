package us.hnry.fancy.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import us.hnry.fancy.R
import us.hnry.fancy.fragments.StockDetailFragment
import us.hnry.fancy.utils.Utility

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailActivity : AppCompatActivity() {
    private val fragmentTagDetail = "fragment_stock_detail"

    companion object {
        fun startIntent(context: Context, symbol: String): Intent {
            val intent = Intent(context, StockDetailActivity::class.java)
            intent.putExtra(Utility.QUOTE_INTENT, symbol)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailFragment: StockDetailFragment
        if (savedInstanceState == null) {
            detailFragment = StockDetailFragment()
            fragmentManager.beginTransaction().add(R.id.detail_container, detailFragment, fragmentTagDetail).commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}