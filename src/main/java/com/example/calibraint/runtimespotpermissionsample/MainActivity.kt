package com.example.calibraint.runtimespotpermissionsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var permissionHelper: PermissionHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionHelper = PermissionHelper(this)

        btn_storage.setOnClickListener(this)
        btn_sms.setOnClickListener(this)
        btn_location_storage.setOnClickListener(this)
        btn_location_alone.setOnClickListener(this)
        btn_contacts.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionHelper!!.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_storage.id -> {
                if (permissionHelper!!.isPermissionGranted(PermissionHelper.PermissionType.STORAGE_PERMISSION))
                    showToast("Storage Permission Granted")
                else permissionHelper!!.openPermissionDialog(PermissionHelper.PermissionType.STORAGE_PERMISSION,
                    object : PermissionHelper.PermissionListener {
                        override fun onPermissionGranted() {
                            showToast("Storage Permission Granted")
                        }

                        override fun onPermissionDenied() {
                            showToast("Storage Permission Denied")

                        }
                    })
            }
            btn_sms.id -> {
                if (permissionHelper!!.isPermissionGranted(PermissionHelper.PermissionType.SMS_PERMISSION))
                    showToast("SMS Permission Granted")
                else permissionHelper!!.openPermissionDialog(PermissionHelper.PermissionType.SMS_PERMISSION,
                    object : PermissionHelper.PermissionListener {
                        override fun onPermissionGranted() {
                            showToast("SMS Permission Granted")
                        }

                        override fun onPermissionDenied() {
                            showToast("SMS Permission Denied")

                        }
                    })
            }
            btn_location_storage.id -> {
                if (permissionHelper!!.isPermissionGranted(PermissionHelper.PermissionType.LOCATION_WITH_STORAGE))
                    showToast("Location and Storage Permission Granted")
                else permissionHelper!!.openPermissionDialog(PermissionHelper.PermissionType.LOCATION_WITH_STORAGE,
                    object : PermissionHelper.PermissionListener {
                        override fun onPermissionGranted() {
                            showToast("Location and Storage Permission Granted")
                        }

                        override fun onPermissionDenied() {
                            showToast("Location and Storage Permission Denied")

                        }
                    })
            }
            btn_location_alone.id -> {
                if (permissionHelper!!.isPermissionGranted(PermissionHelper.PermissionType.LOCATION_WITHOUT_STORAGE))
                    showToast("Location Permission Granted")
                else permissionHelper!!.openPermissionDialog(PermissionHelper.PermissionType.LOCATION_WITH_STORAGE,
                    object : PermissionHelper.PermissionListener {
                        override fun onPermissionGranted() {
                            showToast("Location Permission Granted")
                        }

                        override fun onPermissionDenied() {
                            showToast("Location Permission Denied")

                        }
                    })
            }
            btn_contacts.id -> {
                if (permissionHelper!!.isPermissionGranted(PermissionHelper.PermissionType.CONTACT_PERMISSION))
                    showToast("Contacts Permission Granted")
                else permissionHelper!!.openPermissionDialog(PermissionHelper.PermissionType.CONTACT_PERMISSION,
                    object : PermissionHelper.PermissionListener {
                        override fun onPermissionGranted() {
                            showToast("Contacts Permission Granted")
                        }

                        override fun onPermissionDenied() {
                            showToast("Contacts Permission Denied")

                        }
                    })
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
