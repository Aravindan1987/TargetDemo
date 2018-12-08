package target.com.targettestproj.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

open class BaseActivity : AppCompatActivity() {

    open fun initializeListView(listView : RecyclerView){
        listView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager
    }
}