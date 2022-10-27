package pe.edu.upeu.primereval.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upeu.primereval.R
import pe.edu.upeu.primereval.databinding.ActivityHomeBinding
import pe.edu.upeu.primereval.ui.login.LoginActivity
import pe.edu.upeu.primereval.utils.Constants.USER_LOGGED_IN_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}