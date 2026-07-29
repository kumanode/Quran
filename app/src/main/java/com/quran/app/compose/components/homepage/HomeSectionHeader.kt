package com.quran.app.compose.components.homepage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quran.app.R


@Composable
fun HomeSectionHeader(
    icon: Int,
    title: Int,
    iconTint: Color? = colorScheme.primary,
    onViewAllClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconTint != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp),
            )
        } else {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = null,
                modifier = Modifier.size(18.dp),
            )
        }

        Text(
            text = stringResource(title),
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp),
            style = typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface,
        )

        if (onViewAllClick != null) {
            TextButton(
                onClick = onViewAllClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorScheme.primary,
                ),
                modifier = Modifier.height(30.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.strLabelViewAll),
                    style = typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}