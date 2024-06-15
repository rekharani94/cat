
package me.intuit.cat.presentation.base

import android.databinding.tool.writer.ViewBinding

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<VM : BaseViewModel, viewBinding : ViewBinding> : AppCompatActivity() {
/*
    @Inject
    lateinit var viewModel: VM


     protected lateinit var binding: ViewBinding
     private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        checkInternet()
        setContentView(binding.root)
        setupUI()
        setupObserver()
        return binding.root
    }

    private fun checkInternet() {
        isConnected = NetworkUtils.isNetworkAvailable(binding.root)
        if (!isConnected)
            Toast.makeText(context?.applicationContext,"No internet connection!", Toast.LENGTH_SHORT).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun getViewBinding(): ViewBinding

    protected abstract fun setupUI()

    protected abstract fun setupObserver()*/

}
