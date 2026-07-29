package com.quran.app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.quran.app.db.DatabaseProvider
import com.quran.app.utils.mediaplayer.RecitationController
import com.quran.app.utils.mediaplayer.RecitationServiceState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class RecitationPlayerViewModel(private val application: Application) :
    AndroidViewModel(application) {
    val controller = RecitationController.getInstance(application)
    val repository get() = DatabaseProvider.getQuranRepository(application)

    init {
        controller.connect()
    }

    override fun onCleared() {
        controller.disconnect()
        super.onCleared()
    }

    val state: StateFlow<RecitationServiceState> = controller.state

    val isPlaying: StateFlow<Boolean> = controller.isPlayingState

    val isLoading: StateFlow<Boolean> = combine(
        controller.state,
        controller.isBufferingState,
    ) { currentState, isBuffering ->
        currentState.resolvingChapterNo != null || isBuffering
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = controller.isLoading,
    )
}
