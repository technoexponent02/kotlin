package com.technoexponent.sampleandroidapp.Constant

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.technoexponent.sampleandroidapp.R


import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays

import pub.devrel.easypermissions.AfterPermissionGranted

/**
 * Created by mobile08 on 3/21/2017.
 */

object EasyPermissions {

    private val TAG = "EasyPermissions"

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   sendgiftidea ore more permissions, such as `android.Manifest.permission.CAMERA`.
     * @return true if all permissions are already granted, false if at least sendgiftidea permission
     * is not yet granted.
     */
    fun hasPermissions(context: Context, vararg perms: String): Boolean {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default")
            return true
        }

        for (perm in perms) {
            val hasPerm = ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
            if (!hasPerm) {
                return false
            }
        }

        return true
    }

    /**
     * Request a set of permissions, showing rationale if the system requests it.
     *
     * @param object      Activity or Fragment requesting permissions. Should implement
     * [ActivityCompat.OnRequestPermissionsResultCallback]
     * or
     * [android.support.v13.app.FragmentCompat.OnRequestPermissionsResultCallback]
     * @param rationale   a message explaining why the application needs this set of permissions, will
     * be displayed if the user rejects the request the first time.
     * @param requestCode request code to track this request, must be < 256.
     * @param perms       a set of permissions to be requested.
     */
    fun requestPermissions(`object`: Any, rationale: String, body: String, showMsg: Boolean,
                           requestCode: Int, vararg perms: String) {
        requestPermissions(`object`, rationale, body, showMsg,
                android.R.string.ok,
                android.R.string.cancel,
                requestCode, *perms)
    }

    /**
     * Request a set of permissions, showing rationale if the system requests it.
     *
     * @param object         Activity or Fragment requesting permissions. Should implement
     * [ActivityCompat.OnRequestPermissionsResultCallback]
     * or
     * [android.support.v13.app.FragmentCompat.OnRequestPermissionsResultCallback]
     * @param rationale      a message explaining why the application needs this set of permissions, will
     * be displayed if the user rejects the request the first time.
     * @param positiveButton custom text for positive button
     * @param negativeButton custom text for negative button
     * @param requestCode    request code to track this request, must be < 256.
     * @param perms          a set of permissions to be requested.
     */
    fun requestPermissions(`object`: Any, rationale: String, body: String, showMsg: Boolean,
                           @StringRes positiveButton: Int,
                           @StringRes negativeButton: Int,
                           requestCode: Int, vararg perms: String) {

        checkCallingObjectSuitability(`object`)

        var shouldShowRationale = false
        for (perm in perms) {
            shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(`object`, perm)
        }

        if (shouldShowRationale) {
            val activity = getActivity(`object`) ?: return
            if (showMsg) {
                val dialog = AlertDialog.Builder(activity)
                        .setTitle(rationale)
                        .setMessage(body)
                        .setPositiveButton(positiveButton) { dialog, which -> executePermissionsRequest(`object`, perms, requestCode) }
                        .setNegativeButton(negativeButton) { dialog, which ->
                            // act as if the permissions were denied
                            if (`object` is PermissionCallbacks) {
                                `object`.onPermissionsDenied(requestCode, Arrays.asList(*perms))
                            }
                        }


                val alert = dialog.create()
                alert.show()
                val nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                nbutton.setTextColor(ContextCompat.getColor(`object` as Context, R.color.colorPrimary))
                nbutton.text = "DENY"
                val pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                pbutton.setTextColor(ContextCompat.getColor(`object`, R.color.colorPrimary))
                pbutton.text = "ALLOW"
                val alertTitleId = alert.context.resources.getIdentifier("alertTitle", "id", "android")
                val alertTitle = alert.window!!.decorView.findViewById<View>(alertTitleId) as TextView
                alertTitle.setTextColor(ContextCompat.getColor(`object`, R.color.colorPrimary))
                //                alertTitle.setMaxLines(5);
                //                alertTitle.setText(rationale,TextView.BufferType.NORMAL);

                /* TextView textView = new TextView((Context)object);
                textView.setText(rationale);
                textView.setTextColor(ContextCompat.getColor((Context) object,R.color.primary));
                dialog.setCustomTitle(textView);*/

            } else {
                executePermissionsRequest(`object`, perms, requestCode)
            }
        } else {
            executePermissionsRequest(`object`, perms, requestCode)
        }
    }

    /**
     * Check if at least sendgiftidea permission in the list of denied permissions has been permanently
     * denied (user clicked "Never ask again").
     *
     * @param object            Activity or Fragment requesting permissions.
     * @param deniedPermissions list of denied permissions, usually from
     * [PermissionCallbacks.onPermissionsDenied]
     * @return `true` if at least sendgiftidea permission in the list was permanently denied.
     */
    fun somePermissionPermanentlyDenied(`object`: Any,
                                        deniedPermissions: List<String>): Boolean {

        for (deniedPermission in deniedPermissions) {
            if (permissionPermanentlyDenied(`object`, deniedPermission)) {
                return true
            }
        }

        return false
    }

    /**
     * Check if a permission has been permanently denied (user clicked "Never ask again").
     *
     * @param object           Activity or Fragment requesting permissions.
     * @param deniedPermission denied permission.
     * @return `true` if the permissions has been permanently denied.
     */
    fun permissionPermanentlyDenied(`object`: Any, deniedPermission: String): Boolean {
        return !shouldShowRequestPermissionRationale(`object`, deniedPermission)
    }

    /**
     * Handle the result of a permission request, should be called from the calling Activity's
     * [ActivityCompat.OnRequestPermissionsResultCallback.onRequestPermissionsResult]
     * method.
     *
     *
     * If any permissions were granted or denied, the `object` will receive the appropriate
     * callbacks through [PermissionCallbacks] and methods annotated with
     * [AfterPermissionGranted] will be run if appropriate.
     *
     * @param requestCode  requestCode argument to permission result callback.
     * @param permissions  permissions argument to permission result callback.
     * @param grantResults grantResults argument to permission result callback.
     * @param receivers    an array of objects that have a method annotated with [AfterPermissionGranted]
     * or implement [PermissionCallbacks].
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                   grantResults: IntArray, vararg receivers: Any) {

        // Make a collection of granted and denied permissions from the request.
        val granted = ArrayList<String>()
        val denied = ArrayList<String>()
        for (i in permissions.indices) {
            val perm = permissions[i]
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm)
            } else {
                denied.add(perm)
            }
        }

        // iterate through all receivers
        for (`object` in receivers) {
            // Report granted permissions, if any.
            if (!granted.isEmpty()) {
                if (`object` is PermissionCallbacks) {
                    `object`.onPermissionsGranted(requestCode, granted)
                }
            }

            // Report denied permissions, if any.
            if (!denied.isEmpty()) {
                if (`object` is PermissionCallbacks) {
                    `object`.onPermissionsDenied(requestCode, denied)
                }
            }

            // If 100% successful, call annotated methods
            if (!granted.isEmpty() && denied.isEmpty()) {
                runAnnotatedMethods(`object`, requestCode)
            }
        }

    }

    @TargetApi(23)
    private fun shouldShowRequestPermissionRationale(`object`: Any, perm: String): Boolean {
        return if (`object` is Activity) {
            ActivityCompat.shouldShowRequestPermissionRationale(`object`, perm)
        } else (`object` as? Fragment)?.shouldShowRequestPermissionRationale(perm)
                ?: ((`object` as? android.app.Fragment)?.shouldShowRequestPermissionRationale(perm)
                        ?: false)
    }

    @TargetApi(23)
    fun executePermissionsRequest(`object`: Any, perms: Array<String>, requestCode: Int) {
        checkCallingObjectSuitability(`object`)

        if (`object` is Activity) {
            ActivityCompat.requestPermissions(`object`, perms, requestCode)
        } else if (`object` is Fragment) {
            `object`.requestPermissions(perms, requestCode)
        } else if (`object` is android.app.Fragment) {
            `object`.requestPermissions(perms, requestCode)
        }
    }

    @TargetApi(11)
    private fun getActivity(`object`: Any): Activity? {
        return `object` as? Activity ?: if (`object` is Fragment) {
            `object`.activity
        } else if (`object` is android.app.Fragment) {
            `object`.activity
        } else {
            null
        }
    }

    private fun runAnnotatedMethods(`object`: Any, requestCode: Int) {
        var clazz: Class<*> = `object`.javaClass
        if (isUsingAndroidAnnotations(`object`)) {
            clazz = clazz.superclass
        }
        for (method in clazz.declaredMethods) {
            if (method.isAnnotationPresent(AfterPermissionGranted::class.java)) {
                // Check for annotated methods with matching request code.
                val ann = method.getAnnotation(AfterPermissionGranted::class.java)
                if (ann.value() == requestCode) {
                    // Method must be void so that we can invoke it
                    if (method.parameterTypes.size > 0) {
                        throw RuntimeException(
                                "Cannot execute non-void method " + method.name)
                    }

                    try {
                        // Make method accessible if private
                        if (!method.isAccessible) {
                            method.isAccessible = true
                        }
                        method.invoke(`object`)
                    } catch (e: IllegalAccessException) {
                        Log.e(TAG, "runDefaultMethod:IllegalAccessException", e)
                    } catch (e: InvocationTargetException) {
                        Log.e(TAG, "runDefaultMethod:InvocationTargetException", e)
                    }

                }
            }
        }
    }

    private fun checkCallingObjectSuitability(`object`: Any) {
        // Make sure Object is an Activity or Fragment
        val isActivity = `object` is Activity
        val isSupportFragment = `object` is Fragment
        val isAppFragment = `object` is android.app.Fragment
        val isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        if (!(isSupportFragment || isActivity || isAppFragment && isMinSdkM)) {
            if (isAppFragment) {
                throw IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment")
            } else {
                throw IllegalArgumentException("Caller must be an Activity or a Fragment.")
            }
        }
    }

    private fun isUsingAndroidAnnotations(`object`: Any): Boolean {
        if (!`object`.javaClass.simpleName.endsWith("_")) {
            return false
        }

        try {
            val clazz = Class.forName("org.androidannotations.api.view.HasViews")
            return clazz.isInstance(`object`)
        } catch (e: ClassNotFoundException) {
            return false
        }

    }

    interface PermissionCallbacks : ActivityCompat.OnRequestPermissionsResultCallback {

        fun onPermissionsGranted(requestCode: Int, perms: List<String>)

        fun onPermissionsDenied(requestCode: Int, perms: List<String>)

    }
}
