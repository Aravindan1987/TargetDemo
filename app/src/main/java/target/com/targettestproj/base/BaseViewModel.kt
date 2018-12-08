package target.com.targettestproj.base

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.widget.ImageView
import io.reactivex.disposables.CompositeDisposable
import target.com.targettestproj.utils.ImageDownloadUtils

open class BaseViewModel : ViewModel() {

    companion object {
        @BindingAdapter("app:image_url") @JvmStatic
        fun ImageView.loadImage(url:String) {
            ImageDownloadUtils.downloadImage(url, this)
        }
    }

    open var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}