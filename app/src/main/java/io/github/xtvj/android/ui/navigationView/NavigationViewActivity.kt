package io.github.xtvj.android.ui.navigationView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.material.internal.EdgeToEdgeUtils
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.databinding.ActivityNavigationViewBinding
import io.github.xtvj.android.interfaces.OnClickHandle

class NavigationViewActivity : BaseActivity<ActivityNavigationViewBinding>(R.layout.activity_navigation_view), OnClickHandle {


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //抽屉展开时状态栏不遮挡
        EdgeToEdgeUtils.applyEdgeToEdge(
            window, true,
            resources.getColor(R.color.purple_500, null),
            resources.getColor(R.color.purple_500, null)
        )
        binding.onClickHandle = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_view, menu)
        return true
    }

    override fun onClick(view: View) {
        when (view) {
            binding.mbNavigationView -> {
                if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                    binding.drawerLayout.closeDrawer(binding.navView, true)
                } else {
                    binding.drawerLayout.openDrawer(binding.navView, true)
                }
            }
        }
    }

}