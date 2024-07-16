package com.telefonica.kmpappscatalog.presentation.appsCatalog.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults.tintedButtonColors
import io.github.alexzhirkevich.cupertino.adaptive.AdaptationScope
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.CupertinoButtonAdaptation
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.MaterialButtonAdaptation

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    border: BorderStroke? = null,
    adaptation: AdaptationScope<CupertinoButtonAdaptation, MaterialButtonAdaptation>.() -> Unit = {},
) {
    AdaptiveButton(
        onClick = onClick,
        modifier = modifier,
        border = border,
        adaptation = adaptation,
    ) {
        Text(
            text = text,
            style = style,
            color = color,
        )
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun OutlinedAppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    AppButton(
        text = text,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = onClick,
        modifier = modifier,
        style = style,
        color = color,
        adaptation = {
            cupertino {
                colors = tintedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                )
            }
            material {
                colors = ButtonDefaults.outlinedButtonColors()
            }
        },
    )
}
