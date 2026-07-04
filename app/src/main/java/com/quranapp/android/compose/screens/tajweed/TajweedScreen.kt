package com.quranapp.android.compose.screens.tajweed

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quranapp.android.R
import com.quranapp.android.compose.components.common.AppBar
import com.quranapp.android.compose.theme.alpha

@Composable
fun TajweedScreen() {
    val context = LocalContext.current as Activity

    val rules = remember {
        listOf(
            TajweedRule("nun_sukun", R.string.tajweed_rule_1_title, R.string.tajweed_rule_1_desc, null, listOf(
                TajweedRule("nun_sukun_1", R.string.tajweed_rule_1_1_title, R.string.tajweed_rule_1_1_desc, R.string.tajweed_rule_1_1_example),
                TajweedRule("nun_sukun_2", R.string.tajweed_rule_1_2_title, R.string.tajweed_rule_1_2_desc, R.string.tajweed_rule_1_2_example),
                TajweedRule("nun_sukun_3", R.string.tajweed_rule_1_3_title, R.string.tajweed_rule_1_3_desc, R.string.tajweed_rule_1_3_example),
                TajweedRule("nun_sukun_4", R.string.tajweed_rule_1_4_title, R.string.tajweed_rule_1_4_desc, R.string.tajweed_rule_1_4_example),
                TajweedRule("nun_sukun_5", R.string.tajweed_rule_1_5_title, R.string.tajweed_rule_1_5_desc, R.string.tajweed_rule_1_5_example)
            )),
            TajweedRule("mim_sukun", R.string.tajweed_rule_2_title, R.string.tajweed_rule_2_desc, null, listOf(
                TajweedRule("mim_sukun_1", R.string.tajweed_rule_2_1_title, R.string.tajweed_rule_2_1_desc, R.string.tajweed_rule_2_1_example),
                TajweedRule("mim_sukun_2", R.string.tajweed_rule_2_2_title, R.string.tajweed_rule_2_2_desc, R.string.tajweed_rule_2_2_example),
                TajweedRule("mim_sukun_3", R.string.tajweed_rule_2_3_title, R.string.tajweed_rule_2_3_desc, R.string.tajweed_rule_2_3_example)
            )),
            TajweedRule("idgham", R.string.tajweed_rule_3_title, R.string.tajweed_rule_3_desc, null, listOf(
                TajweedRule("idgham_1", R.string.tajweed_rule_3_1_title, R.string.tajweed_rule_3_1_desc, R.string.tajweed_rule_3_1_example),
                TajweedRule("idgham_2", R.string.tajweed_rule_3_2_title, R.string.tajweed_rule_3_2_desc, R.string.tajweed_rule_3_2_example),
                TajweedRule("idgham_3", R.string.tajweed_rule_3_3_title, R.string.tajweed_rule_3_3_desc, R.string.tajweed_rule_3_3_example)
            )),
            TajweedRule("madd", R.string.tajweed_rule_4_title, R.string.tajweed_rule_4_desc, null, listOf(
                TajweedRule("madd_1", R.string.tajweed_rule_4_1_title, R.string.tajweed_rule_4_1_desc, R.string.tajweed_rule_4_1_example),
                TajweedRule("madd_2", R.string.tajweed_rule_4_2_title, R.string.tajweed_rule_4_2_desc, R.string.tajweed_rule_4_2_example),
                TajweedRule("madd_3", R.string.tajweed_rule_4_3_title, R.string.tajweed_rule_4_3_desc, R.string.tajweed_rule_4_3_example),
                TajweedRule("madd_4", R.string.tajweed_rule_4_4_title, R.string.tajweed_rule_4_4_desc, R.string.tajweed_rule_4_4_example)
            )),
            TajweedRule("ra", R.string.tajweed_rule_5_title, R.string.tajweed_rule_5_desc, null, listOf(
                TajweedRule("ra_1", R.string.tajweed_rule_5_1_title, R.string.tajweed_rule_5_1_desc, R.string.tajweed_rule_5_1_example),
                TajweedRule("ra_2", R.string.tajweed_rule_5_2_title, R.string.tajweed_rule_5_2_desc, R.string.tajweed_rule_5_2_example)
            )),
            TajweedRule("qalqalah", R.string.tajweed_rule_6_title, R.string.tajweed_rule_6_desc, null, listOf(
                TajweedRule("qalqalah_1", R.string.tajweed_rule_6_1_title, R.string.tajweed_rule_6_1_desc, R.string.tajweed_rule_6_1_example),
                TajweedRule("qalqalah_2", R.string.tajweed_rule_6_2_title, R.string.tajweed_rule_6_2_desc, R.string.tajweed_rule_6_2_example)
            )),
            TajweedRule("gharib", R.string.tajweed_rule_7_title, R.string.tajweed_rule_7_desc, null, listOf(
                TajweedRule("gharib_1", R.string.tajweed_rule_7_1_title, R.string.tajweed_rule_7_1_desc, R.string.tajweed_rule_7_1_example),
                TajweedRule("gharib_2", R.string.tajweed_rule_7_2_title, R.string.tajweed_rule_7_2_desc, R.string.tajweed_rule_7_2_example)
            ))
        )
    }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.titleTajweedRules)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(rules) { rule ->
                TajweedExpandableCard(rule = rule)
            }
        }
    }
}

@Composable
fun TajweedExpandableCard(rule: TajweedRule, isSubRule: Boolean = false) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "rotateIcon")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (isSubRule) 8.dp else 12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSubRule) MaterialTheme.colorScheme.surfaceVariant.alpha(0.3f) else MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSubRule) 0.dp else 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.alpha(if (isSubRule) 0.3f else 0.5f))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(if (isSubRule) 12.dp else 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = rule.titleRes),
                    style = if (isSubRule) MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp) else MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.dr_icon_chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (isSubRule) 20.dp else 24.dp)
                        .rotate(rotation),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            AnimatedVisibility(visible = expanded) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = if (isSubRule) 12.dp else 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.alpha(0.5f)
                    )
                    Text(
                        text = stringResource(id = rule.descRes),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = if (isSubRule) 14.sp else 15.sp,
                            lineHeight = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(if (isSubRule) 12.dp else 16.dp)
                    )
                    
                    if (rule.exampleRes != null) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = if (isSubRule) 12.dp else 16.dp, end = if (isSubRule) 12.dp else 16.dp, bottom = if (isSubRule) 12.dp else 16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.alpha(0.5f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = rule.exampleRes),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 22.sp,
                                    lineHeight = 32.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                    
                    if (rule.subRules.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rule.subRules.forEach { subRule ->
                                TajweedExpandableCard(rule = subRule, isSubRule = true)
                            }
                        }
                    }
                }
            }
        }
    }
}
