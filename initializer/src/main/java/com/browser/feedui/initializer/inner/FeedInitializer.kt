package com.browser.feedui.initializer.inner

import android.content.Context
import androidx.startup.Initializer
import com.browser.feedui.initializer.inner.Utils.Companion.getSoFilePath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by chenjunsheng on 2024/3/12
 */
class FeedInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        //LocalSourceStore.init(context)
        //RetrofitClientManager.init(context)
        FeedProvider.init(context)
        CoroutineScope(Dispatchers.IO).launch {
            getSoFilePath(context, "libnamemap.so")
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}