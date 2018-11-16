package com.example.calibraint.runtimespotpermissionsample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

class PermissionHelper : ActivityCompat.OnRequestPermissionsResultCallback {

    private var requestCode: Int = 0
    private var fragment: Fragment? = null
    private var activity: AppCompatActivity? = null
    private var context: Context? = null
    private var listener: PermissionListener? = null

    private val REQUEST_CODE_SMS = 111
    private val REQUEST_CODE_CONTACT = 121
    private val REQUEST_CODE_LOCATION_WO_STORAGE = 131
    private val REQUEST_CODE_LOCATION_WITH_STORAGE = 141
    private val REQUEST_CODE_STORAGE = 151


    private val STORAGE_PERMISSION_ARRAY = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val SMS_PERMISSION_ARRAY = arrayOf(Manifest.permission.RECEIVE_SMS)
    private val LOCATION_WITH_PERMISSION_ARRAY = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val LOCATION_WITHOUT_PERMISSION_ARRAY =
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    private val CONTACT_PERMISSION_ARRAY = arrayOf(Manifest.permission.READ_CONTACTS)

    enum class PermissionType {
        STORAGE_PERMISSION,
        SMS_PERMISSION,
        LOCATION_WITH_STORAGE,
        LOCATION_WITHOUT_STORAGE,
        CONTACT_PERMISSION
    }

    interface PermissionListener {
        fun onPermissionGranted()

        fun onPermissionDenied()
    }

    private fun getContext(): Context? {
        if (activity != null) {
            return activity
        } else if (fragment != null) {
            return fragment!!.context
        }
        return null
    }

    /**
     *
     * @param fragment    context of the fragment
     */
    constructor(fragment: Fragment) {
        this.fragment = fragment
        context = getContext()
    }


    /**
     *
     * @param activity    context of the activity
     */
    constructor(activity: AppCompatActivity) {
        this.activity = activity
        context = getContext()
    }


    /** it will get permission array based on the type
     *
     * @param requestCode
     *
     **/

    private fun getPermissionArray(requestCode: PermissionType): Array<String>? {
        when (requestCode) {
            PermissionType.STORAGE_PERMISSION -> {
                this.requestCode = REQUEST_CODE_STORAGE
                return STORAGE_PERMISSION_ARRAY
            }
            PermissionType.SMS_PERMISSION -> {
                this.requestCode = REQUEST_CODE_SMS
                return SMS_PERMISSION_ARRAY
            }
            PermissionType.LOCATION_WITH_STORAGE -> {
                this.requestCode = REQUEST_CODE_LOCATION_WITH_STORAGE
                return LOCATION_WITH_PERMISSION_ARRAY
            }
            PermissionType.LOCATION_WITHOUT_STORAGE -> {
                this.requestCode = REQUEST_CODE_LOCATION_WO_STORAGE
                return LOCATION_WITHOUT_PERMISSION_ARRAY
            }
            PermissionType.CONTACT_PERMISSION -> {
                this.requestCode = REQUEST_CODE_CONTACT
                return CONTACT_PERMISSION_ARRAY
            }
        }
    }

    /**
     * this method is check wether the given permission is Granted or not
     * */

    fun isPermissionGranted(permissionType: PermissionType): Boolean {
        val permissionArray = getPermissionArray(permissionType)
        var allPermissionsGranted = true
        var i = 0
        val mPermissionLength = permissionArray!!.size
        while (i < mPermissionLength) {
            val permission = permissionArray[i]
            if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false
                break
            }
            i++
        }
        return allPermissionsGranted
    }


    fun openPermissionDialog(permissionType: PermissionType, listner: PermissionListener) {
        this.listener = listner

        val permissionArray = getPermissionArray(permissionType)
        var allPermissionsGranted = true
        var i = 0
        val mPermissionLength = permissionArray!!.size
        while (i < mPermissionLength) {
            val permission = permissionArray[i]
            if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false
                break
            }
            i++
        }
        if (!allPermissionsGranted) {
            if (activity != null) {
                ActivityCompat.requestPermissions(activity!!, permissionArray, requestCode)
            } else fragment?.requestPermissions(permissionArray, requestCode)
        } else {
            listener!!.onPermissionGranted()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            var allPermissionGranted = true
            if (grantResults.size == permissions.size) {
                for (i in permissions.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allPermissionGranted = false
                        break
                    }
                }
            } else {
                allPermissionGranted = false
            }
            if (allPermissionGranted) {
                listener!!.onPermissionGranted()
            } else {
                listener!!.onPermissionDenied()
            }
        }
    }


}