package com.ae.apps.messagecounter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import com.ae.apps.messagecounter.fragments.NoAccessFragment
import com.ae.apps.messagecounter.fragments.SentCountFragment

/**
 * Main Entry point to the application
 */
class MainActivity : AppCompatActivity() {

    private val PERMISSION_CHECK_REQUEST_CODE = 8000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions:Array<String> = arrayOf(Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_SMS)
        checkPermissions(permissions)
    }

    private fun checkPermissions(permissions: Array<String>){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkAllPermissions(permissions)){
                onPermissionGranted()
            } else {
                requestPermissions(permissions, PERMISSION_CHECK_REQUEST_CODE)
            }
        } else {
            onPermissionGranted()
        }
    }

    private fun checkAllPermissions(permissions:Array<String>): Boolean {
        for (permissionName in permissions) {
            if (PackageManager.PERMISSION_GRANTED != PermissionChecker.checkSelfPermission(this, permissionName)) {
                return false
            }
        }
        return true
    }

    private fun onPermissionGranted() {
        showFragmentContent(SentCountFragment.newInstance())
    }

    private fun onPermissionNotGranted(requestCode: Int, permissions: Array<String>, grantResults: IntArray){
        showFragmentContent(NoAccessFragment.newInstance())
    }

    private fun showFragmentContent(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CHECK_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted()
                } else {
                    onPermissionNotGranted(requestCode, permissions, grantResults)
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}