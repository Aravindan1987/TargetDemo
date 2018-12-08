package target.com.targettestproj

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import target.com.targettestproj.base.BaseViewModel
import target.com.targettestproj.databinding.ActivityMainBinding
import target.com.targettestproj.model.GitAccount
import target.com.targettestproj.task.APIBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        initializeObservers()
        viewModel.createView()
    }

    private fun initializeObservers() {
        viewModel.trendingRepoList.observe(this, Observer { list ->
            list?.let { onListObtained(it) }
        })
    }

    private fun onListObtained(list : List<GitAccount>) {

    }
}

class MainActivityViewModel : BaseViewModel() {
    val trendingRepoList = MutableLiveData<List<GitAccount>>()

    fun createView() {
        getFoodProducts()
    }

    private fun getFoodProducts() {
        val language = "java"
        val duration = "weekly"
        val foodListObservable = APIBuilder.getInstance().apiService?.getFoodItems(language, duration)

        val observable = foodListObservable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(this::handleResult)
    }

    private fun handleResult(list : List<GitAccount>?) {
        list?.let { trendingRepoList.postValue(it) }
    }
}