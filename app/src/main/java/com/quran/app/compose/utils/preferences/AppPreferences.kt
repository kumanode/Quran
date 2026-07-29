@file:OptIn(ExperimentalCoroutinesApi::class)

package com.quran.app.compose.utils.preferences

import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.stringPreferencesKey
import com.quran.app.compose.utils.preferences.DataStoreManager
import com.quran.app.compose.utils.preferences.PrefKey
import com.quran.app.utils.app.ResourceDownloadProxy
import kotlinx.coroutines.ExperimentalCoroutinesApi

object AppPreferences {
    val KEY_DOWNLOAD_PROXY =
        PrefKey(stringPreferencesKey("resource_download_proxy"), ResourceDownloadProxy.DEFAULT.name)

    fun getResourceDownloadProxy(): ResourceDownloadProxy {
        return DataStoreManager.read(KEY_DOWNLOAD_PROXY).let { ResourceDownloadProxy.fromValue(it) }
    }

    suspend fun setResourceDownloadProxy(src: ResourceDownloadProxy) {
        DataStoreManager.write(KEY_DOWNLOAD_PROXY, src.value)
    }

    @Composable
    fun observeResourceDownloadProxy(): ResourceDownloadProxy {
        return DataStoreManager.observe(KEY_DOWNLOAD_PROXY)
            .let { ResourceDownloadProxy.fromValue(it) }
    }
}
