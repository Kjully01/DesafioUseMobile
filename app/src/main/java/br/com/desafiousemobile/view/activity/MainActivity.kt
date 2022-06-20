package br.com.desafiousemobile.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.desafiousemobile.R
import br.com.desafiousemobile.databinding.ActivityMainBinding
import br.com.desafiousemobile.view.fragments.FavoriteFragment
import br.com.desafiousemobile.view.fragments.HomeFragment
import br.com.desafiousemobile.view.fragments.RegistrationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment = HomeFragment()

    private var title: String = "Home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.navHost, HomeFragment()).commit()
        val bottomNav: BottomNavigationView = binding.bottomNavigation
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        val actionBar = supportActionBar
        actionBar?.title = title
    }

        private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    currentFragment = HomeFragment()
                    title = "Home"
                }
                R.id.registration -> {
                    currentFragment = RegistrationFragment()
                    actionBar?.title = "Cadastrar"
                }
                R.id.favorite -> {
                    currentFragment = FavoriteFragment()
                    title = "Favoritos"
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.navHost, currentFragment).commit()
            true
        }


}
