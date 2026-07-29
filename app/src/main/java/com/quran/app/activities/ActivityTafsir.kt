package com.quran.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.quran.app.R
import com.quran.app.activities.base.BaseActivity
import com.quran.app.compose.navigation.SettingRoutes
import com.quran.app.compose.screens.tafsir.TafsirReaderScreen
import com.quran.app.compose.theme.QuranAppTheme
import com.quran.app.compose.utils.readAppLocale
import com.quran.app.databinding.LytTafsirTextSizeBinding
import com.quran.app.utils.reader.ReaderTextSizeUtils
import com.quran.app.utils.reader.tafsir.TafsirManager
import com.quran.app.utils.simplified.SimpleSeekbarChangeListener
import com.quran.app.utils.univ.Keys
import com.quran.app.viewModels.TafsirReaderEvent
import com.quran.app.viewModels.TafsirReaderViewModel
import com.quran.app.widgets.bottomSheet.PeaceBottomSheet
import androidx.lifecycle.lifecycleScope
import com.quran.app.compose.utils.preferences.ReaderPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityTafsir : BaseActivity() {

    private val viewModel: TafsirReaderViewModel by viewModels()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initContent(intent)
    }

    override fun getLayoutResource() = 0

    override fun onActivityInflated(
        activityView: View,
        savedInstanceState: Bundle?
    ) {
        setContent {
            QuranAppTheme {
                TafsirReaderScreen(
                    showFontSizeDialog = { showFontSizeDialog() },
                )
            }
        }

        TafsirManager.prepare(this, false) {
            initContent(intent)
        }
    }

    private fun initContent(intent: Intent) {
        val tafsirKey = intent.getStringExtra("tafsirKey")
        val chapterNo = intent.getIntExtra(Keys.READER_KEY_CHAPTER_NO, 1)
        val verseNo = intent.getIntExtra(Keys.READER_KEY_VERSE_NO, 1)

        if (chapterNo < 1 || verseNo < 1) {
            finish()
            return
        }

        viewModel.onEvent(
            TafsirReaderEvent.Init(
                tafsirKey,
                chapterNo,
                verseNo,
            ),
        )
    }

    private fun showFontSizeDialog() {
        val binding = LytTafsirTextSizeBinding.inflate(layoutInflater)
        val locale = readAppLocale(this).platformLocale

        PeaceBottomSheet().apply {
            params.apply {
                headerTitleResource = R.string.titleReaderTextSizeTafsir
                contentView = binding.root
            }
        }.show(supportFragmentManager, "TafsirFontSize")

        lifecycleScope.launch {
            val multiplier = ReaderPreferences.getTafsirTextSizeMultiplier()
            withContext(Dispatchers.Main.immediate) {
                val text = String.format(
                    locale,
                    "%d%%",
                    ReaderTextSizeUtils.calculateProgressText(multiplier)
                )
                binding.progressText.text = text

                binding.seekBar.apply {
                    max = ReaderTextSizeUtils.maxProgress
                    progress = ReaderTextSizeUtils.calculateProgress(multiplier)
                    setOnSeekBarChangeListener(object : SimpleSeekbarChangeListener() {
                        override fun onProgressChanged(
                            seekBar: SeekBar,
                            progress: Int,
                            fromUser: Boolean,
                        ) {
                            val nProgress = ReaderTextSizeUtils.normalizeProgress(progress)
                            val t = String.format(locale, "%d%%", nProgress)
                            binding.progressText.text = t

                            viewModel.onEvent(
                                TafsirReaderEvent.UpdateTextSize(
                                    ReaderTextSizeUtils.calculateMultiplier(nProgress)
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}
