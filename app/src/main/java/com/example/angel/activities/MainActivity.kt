package com.example.angel.activities

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.angel.R
import com.example.angel.services.GpsServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val JOB_ID = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = slideLayout_main
        val pagerAdapter = MyPageAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1


        ///stopLocationUpdate()

        askForPermissions()
        startLocationUpdate()


    }

    private fun askForPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 121)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 121)
        }
    }

    private fun startLocationUpdate() {
        val componentName = ComponentName(this, GpsServices::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000)    ///Android does not support this lower than 15 min
            .build()

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(jobInfo)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.e("[Main]", "Task Scheduling Succeded")
        } else {
            Log.e("[Main]", "Task Scheduling failed")
        }

    }


}


class MyPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        Log.e("[TEST]", position.toString())
        return when (position) {
            0 -> QrFragment()
            1 -> MainFragment()
            2 -> CameraFragment()
            else -> {
                Log.e("[Main]", "something went really wrong")
                throw Exception()
            }
        }
    }

}
