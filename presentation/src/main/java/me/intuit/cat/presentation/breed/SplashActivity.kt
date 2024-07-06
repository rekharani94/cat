package me.intuit.cat.presentation.breed

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.intuit.cat.presentation.R
import me.intuit.cat.presentation.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

     var handler=   android.os.Handler()
    /*var runnable:Runnable = Runnable{
        var intent=  Intent(this@SplashActivity, CatsListActivity::class.java)
        startActivity(intent)
        finish()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }*/
       /* handler.postDelayed(runnable, 3000)
        var topAnimation =
            AnimationUtils.loadAnimation(this, me.intuit.cat.presentation.R.anim.slide_down);
        var bottomAnimation =
            AnimationUtils.loadAnimation(this, me.intuit.cat.presentation.R.anim.bottom_up);
        var middleAnimation =
            AnimationUtils.loadAnimation(this, me.intuit.cat.presentation.R.anim.fade_in);
       binding.gifView.animation = topAnimation
        binding.tvWelcome.animation = bottomAnimation*/



    }

    override fun onPause() {
        super.onPause()
        if(handler!=null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}