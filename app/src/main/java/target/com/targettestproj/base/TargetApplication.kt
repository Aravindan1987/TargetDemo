package target.com.targettestproj.base

import android.app.Application
import android.content.Context

class TargetApplication : Application() {
    companion object {
        private  var targetContext: TargetApplication? = null

        val context: Context
            get() = targetContext!!
    }

    override fun onCreate() {
        super.onCreate()
        targetContext = this
    }
}