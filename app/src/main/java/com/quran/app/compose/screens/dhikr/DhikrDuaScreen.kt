package com.quran.app.compose.screens.dhikr

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quran.app.R
import com.quran.app.compose.components.common.AppBar
import com.quran.app.compose.theme.alpha
import com.quran.app.utils.reader.factory.ReaderFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DhikrDuaScreen() {
    val context = LocalContext.current as Activity
    val allItems = remember { DhikrRepository.getDhikrItems() }

    var selectedCategory by remember { mutableStateOf(DhikrCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var activeCounterItem by remember { mutableStateOf<DhikrDuaItem?>(null) }

    val filteredItems = remember(selectedCategory, searchQuery) {
        allItems.filter { item ->
            val matchesCategory = (selectedCategory == DhikrCategory.ALL || item.category == selectedCategory)
            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                val title = context.getString(item.titleRes)
                val trans = context.getString(item.translationRes)
                title.contains(searchQuery, ignoreCase = true) ||
                        trans.contains(searchQuery, ignoreCase = true) ||
                        item.arabicText.contains(searchQuery)
            }
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.titleDzikirDoa)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Input Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(text = stringResource(id = R.string.dhikr_search_hint)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dr_icon_search),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                painter = painterResource(id = R.drawable.dr_icon_close),
                                contentDescription = null
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.alpha(0.3f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.alpha(0.2f),
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.alpha(0.1f)
                )
            )

            // Category Chips Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DhikrCategory.entries.toTypedArray()) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(text = stringResource(id = category.titleRes)) },
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dhikr & Dua Items List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    DhikrDuaCard(
                        item = item,
                        onOpenCounter = { activeCounterItem = item },
                        onOpenQuran = { surah, verse, toVerse ->
                            if (toVerse != null) {
                                ReaderFactory.startVerseRange(context, surah, verse, toVerse)
                            } else {
                                ReaderFactory.startVerse(context, surah, verse)
                            }
                        }
                    )
                }
            }
        }
    }

    activeCounterItem?.let { item ->
        DhikrCounterDialog(
            item = item,
            onDismissRequest = { activeCounterItem = null }
        )
    }
}

@Composable
fun DhikrDuaCard(
    item: DhikrDuaItem,
    onOpenCounter: () -> Unit,
    onOpenQuran: (surah: Int, verse: Int, toVerse: Int?) -> Unit
) {
    var expandedVirtue by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.alpha(0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = item.titleRes),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (item.targetCount > 1) {
                    AssistChip(
                        onClick = onOpenCounter,
                        label = {
                            Text(
                                text = stringResource(id = R.string.dhikr_counter_target, item.targetCount),
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic Display Surface
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.alpha(0.35f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = item.arabicText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 22.sp,
                        lineHeight = 36.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    textAlign = TextAlign.Right
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Translation Text
            Text(
                text = stringResource(id = item.translationRes),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Virtue Description Accordion
            if (item.virtueRes != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedVirtue = !expandedVirtue }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.dr_icon_info),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = if (expandedVirtue) R.string.dhikr_hide_virtue else R.string.dhikr_show_virtue),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                AnimatedVisibility(visible = expandedVirtue) {
                    Text(
                        text = stringResource(id = item.virtueRes),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, lineHeight = 18.sp),
                        color = MaterialTheme.colorScheme.primary.alpha(0.85f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.primaryContainer.alpha(0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Open in Quran Button for Quranic Duas
                if (item.surahNo != null && item.verseNo != null) {
                    FilledTonalButton(
                        onClick = { onOpenQuran(item.surahNo, item.verseNo, item.toVerseNo) },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.dr_icon_read_quran),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(id = R.string.dhikr_open_in_quran),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Digital Tasbeeh Counter Button
                Button(
                    onClick = onOpenCounter,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_tasbih),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = R.string.dhikr_tasbeeh_counter),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
