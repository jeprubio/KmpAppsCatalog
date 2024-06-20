package com.telefonica.kmpappscatalog.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.telefonica.kmpappscatalog.domain.LauncherApp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class AppsDetails(private val app: LauncherApp) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AppDetailsScreen(
            app,
            back = { navigator.pop() }
        )
    }
}

@Composable
private fun AppDetailsScreen(app: LauncherApp, back: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Heading(app)
            Text(
                app.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 36.dp, bottom = 8.dp)
            )
            Text(
                text = app.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        }
        Footer()
        CloseButton(
            back = back,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        )
    }
}

@Composable
private fun Heading(app: LauncherApp) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f)
    ) {
        KamelImage(
            resource = asyncPainterResource(data = app.icon),
            contentDescription = app.name,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )

        AppIcon(app)
    }
}

@Composable
private fun BoxScope.AppIcon(app: LauncherApp) {
    KamelImage(
        resource = asyncPainterResource(data = app.icon),
        contentDescription = app.name,
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(start = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(4.dp)
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
private fun CloseButton(back: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { back() },
        modifier = modifier
            .clip(CircleShape)
            .alpha(0.7f)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
        )
    }
}

@Composable
fun BoxScope.Footer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        HorizontalDivider()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { /* TODO */ }
        ) {
            Text("Install")
        }
    }

}
