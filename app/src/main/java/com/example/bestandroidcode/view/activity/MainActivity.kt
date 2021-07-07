package com.example.bestandroidcode.view.activity

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.bestandroidcode.R
import com.example.bestandroidcode.databinding.ActivityMainBinding
import com.example.bestandroidcode.databinding.BackdropMenuBinding
import com.example.bestandroidcode.di.injector.Injectable
import com.example.bestandroidcode.utils.NavigationIconClickListener
import com.example.bestandroidcode.viewModel.MyFavoriteViewModel
import com.example.bestandroidcode.viewModel.TheCatViewModel
import com.example.bestandroidcode.viewModel.factory.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector, Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private lateinit var viewDataBinding: ActivityMainBinding
    private lateinit var includeViewDataBinding: BackdropMenuBinding

    private val navigationIconClickListener by lazy {
        NavigationIconClickListener(
            this@MainActivity,
            viewDataBinding.appBar,
            viewDataBinding.fragmentContainer,
            AccelerateDecelerateInterpolator(),
            ContextCompat.getDrawable(this@MainActivity.baseContext!!, R.drawable.the_cat_menu),
            ContextCompat.getDrawable(this@MainActivity.baseContext!!, R.drawable.the_cat_close)
        )
    }

    private lateinit var theCatViewModel: TheCatViewModel
    private lateinit var myFavoriteViewModel: MyFavoriteViewModel

    private val exitAppAlertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle(R.string.exit_app_title)
            setMessage(R.string.exit_app_message)
            setPositiveButton(R.string.exit_app_exit) { dialog, _ ->
                dialog.dismiss()
                finishAffinity()
            }

            setNegativeButton(R.string.exit_app_cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navInflater: NavInflater
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewDataBinding.root
        setContentView(view)

        includeViewDataBinding = viewDataBinding.backdropMenu

        initAppBar()
        theCatViewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(TheCatViewModel::class.java)
        myFavoriteViewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(MyFavoriteViewModel::class.java)
        initNavigationController()
    }

    private fun initAppBar() {
        setSupportActionBar(viewDataBinding.appBar)
        viewDataBinding.appBar.setNavigationOnClickListener(navigationIconClickListener)

    }

    private fun initNavigationController() {
        navHostFragment = nav_search_fragment as NavHostFragment
        navController = navHostFragment.navController
        navInflater = navController.navInflater
        navGraph = navInflater.inflate(R.navigation.nav_main)
    }

    override fun onStart() {
        super.onStart()
        includeViewDataBinding.myFavoriteButton.setOnClickListener {
            navigationIconClickListener.onClick()
            navGraph.startDestination = R.id.myFavoriteFragment
            navController.apply {
                graph = navGraph
            }
        }

        includeViewDataBinding.searchButton.setOnClickListener {
            navigationIconClickListener.onClick()
            navGraph.startDestination = R.id.searchFragment
            navController.apply {
                graph = navGraph
            }
        }
    }

    override fun onBackPressed() {
        if (!exitAppAlertDialog.isShowing) exitAppAlertDialog.show()
    }
}