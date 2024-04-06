package com.browser.feedui.initializer.inner

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by chenjunsheng on 2024/3/13
 */
class FeedProvider {

    companion object {

        fun init(context: Context) {
            val uri = Uri.parse("content://com.feed.provider")
            val contentValues =  ContentValues()
            contentValues.put("_id", 0)
            val contentResolver = context.getContentResolver()
            val pm = context.packageManager
            if (uri.authority?.let { pm.resolveContentProvider(it, PackageManager.MATCH_ALL) } != null) {
                val application = context.applicationContext as Application
                application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                    override fun onActivityCreated(
                        activity: Activity,
                        savedInstanceState: Bundle?
                    ) {
                        Log.d("cjslog", "activity create")
                    }

                    override fun onActivityStarted(activity: Activity) {}

                    override fun onActivityResumed(activity: Activity) {}

                    override fun onActivityPaused(activity: Activity) {}

                    override fun onActivityStopped(activity: Activity) {}

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

                    override fun onActivityDestroyed(activity: Activity) {}

                })
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        contentResolver.insert(uri, contentValues)
                    } catch (_: Exception) {

                    }
                }
            }
        }
    }
}