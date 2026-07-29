package com.quran.app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.quran.app.db.DatabaseProvider
import com.quran.app.utils.mediaplayer.RecitationController
import com.quran.app.utils.reader.FontResolver


open class ReaderProviderViewModel(private val application: Application) : AndroidViewModel(application) {
    val controller = RecitationController.getInstance(application)
    val userRepository get() = DatabaseProvider.getUserRepository(application)
    val repository get() = DatabaseProvider.getQuranRepository(application)
    val fontResolver = FontResolver.getInstance(application)
    val externalQuranDb get() = DatabaseProvider.getExternalQuranDatabase(application)
}
