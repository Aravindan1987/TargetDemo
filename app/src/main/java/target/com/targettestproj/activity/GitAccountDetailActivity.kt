package target.com.targettestproj.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import target.com.targettestproj.R
import target.com.targettestproj.base.BaseActivity
import target.com.targettestproj.base.BaseViewModel
import target.com.targettestproj.constants.IntentConstants
import target.com.targettestproj.databinding.ActivityGitAccountDetailBinding
import target.com.targettestproj.model.GitAccount
import target.com.targettestproj.utils.ResourceUtils

class GitAccountDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityGitAccountDetailBinding
    private lateinit var viewModel : GitAccountDetailViewModel
    private var gitAccount: GitAccount? = null

    override fun parseBundleData(bundle: Bundle) {
        super.parseBundleData(bundle)
        gitAccount = bundle.getParcelable(IntentConstants.GIT_ACCOUNT)
    }

    override fun getScreenTitle(): String? = ResourceUtils.getString(R.string.title_account_details)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_git_account_detail)
        viewModel = GitAccountDetailViewModel(gitAccount)
        binding.viewModel = viewModel
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

class GitAccountDetailViewModel(gitAccount: GitAccount?) : BaseViewModel() {
    val userName = gitAccount?.username
    val name = gitAccount?.name
    val url = gitAccount?.url
    val imageUrl = gitAccount?.avatar

    val repoName = gitAccount?.repo?.name
    val description = gitAccount?.repo?.description
    val repoUrl = gitAccount?.repo?.url
}