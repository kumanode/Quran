package com.quran.app.compose.components

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.peacedesign.android.utils.AppBridge
import com.quran.app.R
import com.quran.app.activities.ActivityAbout
import com.quran.app.activities.ActivityBookmark
import com.quran.app.activities.ActivityDonate
import com.quran.app.activities.ActivityExportImport
import com.quran.app.activities.ActivitySettings
import com.quran.app.activities.ActivityStorageCleanup
import com.quran.app.compose.components.dialogs.SimpleTooltip
import com.quran.app.compose.theme.alpha
import com.quran.app.utils.app.InfoUtils
import verticalFadingEdge

private data class IndexMenuItemGroup(
    val items: List<IndexMenuItem>,
)

private data class IndexMenuItem(
    @param:DrawableRes val icon: Int,
    @param:StringRes val title: Int,

    val iconTint: Color? = null,
    val textColor: Color? = null,
    val onClick: (ctx: Context) -> Unit,
)

@Composable
private fun getItems(): List<IndexMenuItemGroup> {
    return listOf<IndexMenuItemGroup>(
        IndexMenuItemGroup(
            listOf(
                IndexMenuItem(
                    R.drawable.ic_bookmarks,
                    R.string.strTitleBookmarks,
                    onClick = {
                        it.startActivity(Intent(it, ActivityBookmark::class.java))
                    }
                ),
                IndexMenuItem(
                    R.drawable.dr_icon_settings,
                    R.string.strTitleSettings,
                    onClick = {
                        it.startActivity(Intent(it, ActivitySettings::class.java))
                    }
                ),
                IndexMenuItem(
                    R.drawable.icon_clean,
                    R.string.titleStorageCleanup,
                    onClick = {
                        it.startActivity(Intent(it, ActivityStorageCleanup::class.java))
                    }
                ),
                IndexMenuItem(
                    R.drawable.icon_import_export,
                    R.string.titleExportData,
                    onClick = {
                        it.startActivity(Intent(it, ActivityExportImport::class.java))
                    }
                ),
            )
        ),
        IndexMenuItemGroup(
            listOf(
                IndexMenuItem(
                    R.drawable.dr_icon_info,
                    R.string.strTitleAboutUs,
                    onClick = {
                        it.startActivity(Intent(it, ActivityAbout::class.java))
                    }
                ),

                IndexMenuItem(
                    R.drawable.ic_donate,
                    R.string.donate,
                    iconTint = colorScheme.primary,
                    onClick = {
                        it.startActivity(Intent(it, ActivityDonate::class.java))
                    }
                ),
            )
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexMenuButton() {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(true)

    val config = LocalConfiguration.current
    val sheetMaxWidth = config.screenWidthDp.dp * .95f
    val sheetWidthDiff = (config.screenWidthDp.dp - sheetMaxWidth) / 2

    SimpleTooltip(text = stringResource(R.string.strTitleMenu)) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = {
                showMenu = true
            }
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.dr_icon_hamburger
                ),
                contentDescription = stringResource(R.string.strTitleMenu),
                tint = colorScheme.onSurface
            )
        }
    }


    if (!showMenu) return

    ModalBottomSheet(
        modifier = Modifier
            .padding(
                bottom = sheetWidthDiff
            ),
        onDismissRequest = {
            showMenu = false
        },
        sheetState = sheetState,
        containerColor = Color.Transparent,
        contentColor = colorScheme.onSurface,
        shape = shapes.large,
        scrimColor = Color.Black.alpha(0.5f),
        dragHandle = null,
        sheetMaxWidth = minOf(sheetMaxWidth, BottomSheetDefaults.SheetMaxWidth),
        contentWindowInsets = {
            WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical)
        }
    ) {
        IndexMenuContent {
            showMenu = false
        }
    }
}


@Composable
fun IndexMenuContent(
    onClose: () -> Unit
) {
    val config = LocalConfiguration.current

    val maxMenuHeight = config.screenHeightDp.dp * 0.8f
    val scrollState = rememberScrollState()
    val items = getItems()

    Column(
        modifier = Modifier
            .heightIn(max = maxMenuHeight)
            .background(colorScheme.surface, RoundedCornerShape(20.dp))
            .border(
                1.dp,
                colorScheme.outlineVariant.alpha(0.5f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.dr_icon_hamburger),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )

            Text(
                text = stringResource(id = R.string.strTitleMenu),
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .weight(1f),
                style = typography.titleLarge,
            )

            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                onClick = onClose
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dr_icon_close),
                    contentDescription = stringResource(id = R.string.strDescClose),
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = colorScheme.outlineVariant,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .verticalFadingEdge(scrollState, color = colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(top = 12.dp, bottom = 20.dp)
            ) {
                items.forEachIndexed { groupIndex, group ->
                    group.items.forEachIndexed { _, item ->
                        IndexMenuItemRow(item, onClose)
                    }

                    if (groupIndex < items.lastIndex) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = colorScheme.outlineVariant,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IndexMenuItemRow(
    item: IndexMenuItem,
    onClose: () -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                item.onClick(context)
                onClose()
            })
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = item.iconTint ?: LocalContentColor.current
        )

        Spacer(modifier = Modifier.width(17.dp))

        Text(
            text = stringResource(id = item.title),
            color = item.textColor ?: colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

