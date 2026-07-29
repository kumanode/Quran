package com.quran.app.compose.screens.onboarding

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quran.app.R
import com.quran.app.compose.theme.alpha
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val onboardingIcons = listOf(
    R.drawable.dr_icon_language,
    R.drawable.dr_icon_theme,
    R.drawable.dr_icon_translations,
    R.drawable.dr_icon_tafsir,
)

// Accent gradient per onboarding step
private val stepGradientColors = listOf(
    listOf(0xFF0EA880.toLong(), 0xFF0284C7.toLong()), // Language - emerald to blue
    listOf(0xFF7C3AED.toLong(), 0xFFDB2777.toLong()), // Theme - violet to pink
    listOf(0xFF0284C7.toLong(), 0xFF0EA880.toLong()), // Translations - blue to emerald
    listOf(0xFFD97706.toLong(), 0xFFDC2626.toLong()), // Tafsir - amber to red
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    val items = listOf(
        R.string.strTitleAppLanguage to R.string.onboardDescLanguage,
        R.string.strTitleTheme to R.string.onboardDescTheme,
        R.string.strLabelSelectTranslations to R.string.onboardDescTranslations,
        R.string.strTitleSelectTafsir to R.string.onboardDescTafsir,
    )
    val pageCount = items.size

    var savedPage by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = savedPage,
        initialPageOffsetFraction = 0f,
        pageCount = { pageCount },
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collectLatest { savedPage = it }
    }

    BackHandler {
        if (pagerState.currentPage > 0) {
            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
        } else {
            activity?.finish()
        }
    }

    val lastPage = pageCount - 1
    val page = pagerState.currentPage

    val gradients = stepGradientColors[page]
    val gradientStart = androidx.compose.ui.graphics.Color(gradients[0])
    val gradientEnd = androidx.compose.ui.graphics.Color(gradients[1])

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header with gradient accent background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                gradientStart.copy(alpha = 0.15f),
                                colorScheme.background.copy(alpha = 0f),
                            )
                        )
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 28.dp, top = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(
                            onClick = onComplete,
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text(
                                stringResource(R.string.strLabelSkip),
                                style = MaterialTheme.typography.labelLarge,
                                color = colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    // Icon box with gradient bg
                    AnimatedContent(
                        targetState = page,
                        transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(200)) },
                        label = "icon",
                    ) { p ->
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            androidx.compose.ui.graphics.Color(stepGradientColors[p][0]),
                                            androidx.compose.ui.graphics.Color(stepGradientColors[p][1]),
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (p != 3) Icon(
                                painter = painterResource(onboardingIcons[p]),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = androidx.compose.ui.graphics.Color.White,
                            ) else Icon(
                                painter = painterResource(onboardingIcons[p]),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = null,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    AnimatedContent(
                        targetState = page,
                        transitionSpec = {
                            (fadeIn(tween(220)) + slideInVertically { it / 4 }) togetherWith
                                    (fadeOut(tween(180)) + slideOutVertically { -it / 4 })
                        },
                        label = "onboardingTitle",
                    ) { p ->
                        val item = items.get(p)

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = stringResource(item.first),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(item.second),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

            // Content pager
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false,
                    verticalAlignment = Alignment.Top,
                ) { pageIndex ->
                    when (pageIndex) {
                        0 -> OnboardingLanguagePage()
                        1 -> OnboardingThemePage()
                        2 -> OnboardingTranslationsPage()
                        3 -> OnboardingTafsirPage()
                    }
                }
            }

            // Bottom navigation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.surfaceContainer)
                    .navigationBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 14.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (pagerState.currentPage > 0) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.dr_icon_arrow_left),
                                contentDescription = stringResource(R.string.strLabelBack),
                                tint = colorScheme.onSurface,
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(48.dp))
                    }

                    // Animated dots indicator
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(pageCount) { i ->
                            val selected = i == pagerState.currentPage
                            val dotWidth by animateDpAsState(
                                targetValue = if (selected) 24.dp else 6.dp,
                                animationSpec = tween(300),
                                label = "dotW",
                            )

                            if (selected) {
                                Box(
                                    modifier = Modifier
                                        .height(6.dp)
                                        .width(dotWidth)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(gradientStart, gradientEnd)
                                            )
                                        ),
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(colorScheme.outlineVariant.alpha(0.45f)),
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (pagerState.currentPage == lastPage) {
                                onComplete()
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary,
                        ),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 13.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp,
                        ),
                    ) {
                        Text(
                            if (pagerState.currentPage == lastPage) {
                                stringResource(R.string.strLabelStart)
                            } else {
                                stringResource(R.string.strLabelNext)
                            },
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
