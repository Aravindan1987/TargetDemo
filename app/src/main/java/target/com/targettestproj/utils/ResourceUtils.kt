package target.com.targettestproj.utils

import android.support.v4.content.ContextCompat
import target.com.targettestproj.base.TargetApplication

class ResourceUtils {
    companion object {
        fun getString(resourceId : Int) : String = TargetApplication.context.getString(resourceId)

        fun getDrawable(resourceId : Int) = ContextCompat.getDrawable(TargetApplication.context, resourceId)
    }
}