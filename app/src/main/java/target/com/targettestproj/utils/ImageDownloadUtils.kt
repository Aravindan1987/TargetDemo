package target.com.targettestproj.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import target.com.targettestproj.base.TargetApplication

class ImageDownloadUtils {

    companion object {
        fun downloadImage(url : String, imageView : ImageView) {
            Glide.with(TargetApplication.context).load(url).into(imageView)
        }
    }
}