package target.com.targettestproj.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import target.com.targettestproj.R
import target.com.targettestproj.utils.ResourceUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getScreenTitle()
        intent.extras?.let { parseBundleData(it) }
    }

    open fun getScreenTitle() : String? = ResourceUtils.getString(R.string.app_name)

    open fun parseBundleData(bundle : Bundle) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventReceived(event: Event) {}

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    open fun initializeListView(listView : RecyclerView){
        listView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}