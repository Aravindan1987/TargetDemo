package target.com.targettestproj.activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import target.com.targettestproj.R
import target.com.targettestproj.base.BaseActivity
import target.com.targettestproj.base.BaseViewModel
import target.com.targettestproj.base.Event
import target.com.targettestproj.constants.IntentConstants
import target.com.targettestproj.databinding.ActivityMainBinding
import target.com.targettestproj.databinding.ItemGitUserBinding
import target.com.targettestproj.model.GitAccount
import target.com.targettestproj.task.APIBuilder
import target.com.targettestproj.utils.ImageDownloadUtils

class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var adapter : GitUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel
        initializeObservers()
        initializeView()
        viewModel.createView()
    }

    private fun initializeObservers() {
        viewModel.trendingRepoList.observe(this, Observer { list ->
            list?.let { onListObtained(it) }
        })
    }

    private fun initializeView() {
        initializeListView(binding.foodListView)
    }

    private fun onListObtained(list : List<GitAccount>) {
        if (binding.foodListView.adapter == null) {
            adapter = GitUserListAdapter(list)
            binding.foodListView.adapter = adapter
        } else {
            adapter.gitAccountsList = list
            adapter.notifyDataSetChanged()
        }
    }

    override fun onEventReceived(event: Event) {
        super.onEventReceived(event)
        when (event) {
            is GitAccountCLickedEvent -> {
                val intent = Intent(this, GitAccountDetailActivity::class.java)
                intent.putExtra(IntentConstants.GIT_ACCOUNT, event.gitAccount)
                startActivity(intent)
            }
        }
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
        observable?.let { compositeDisposable.add(it) }
    }

    private fun handleResult(list : List<GitAccount>?) {
        list?.let { trendingRepoList.postValue(it) }
    }
}

class GitUserListAdapter(var gitAccountsList: List<GitAccount>) : RecyclerView.Adapter<GitUserListAdapter.FoodListViewHolder> () {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FoodListViewHolder {
        val itemBinding = ItemGitUserBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return FoodListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(p0: FoodListViewHolder, p1: Int) {
        val item = gitAccountsList[p1]
        p0.itemView.setOnClickListener { EventBus.getDefault().post(
            GitAccountCLickedEvent(
                item
            )
        ) }
        p0.bind(item)
    }

    override fun getItemCount(): Int  = gitAccountsList.size

    class FoodListViewHolder(private val binding : ItemGitUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(gitAccount: GitAccount) {
            binding.viewModel = GitUserViewModel(gitAccount)
            binding.executePendingBindings()
        }
    }

    class GitUserViewModel(gitAccount : GitAccount) {

        companion object {
            @BindingAdapter("app:image_url") @JvmStatic
            fun ImageView.loadImage(url:String) {
                ImageDownloadUtils.downloadImage(url, this)
            }
        }

        val name = gitAccount.name
        val username = gitAccount.username
        val imageUrl : String? = gitAccount.avatar
    }
}

class GitAccountCLickedEvent(val gitAccount: GitAccount) : Event()