package com.telefonica.kmpappscatalog.presentation.appsCatalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.telefonica.kmpappscatalog.domain.LauncherApp
import com.telefonica.kmpappscatalog.presentation.appsDetails.AppsDetails
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.AppsCatalogUiState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCircularProgressIndicator
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class AppsCatalog : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.rememberNavigatorScreenModel { AppsCatalogScreenViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        AppsCatalogScreen(
            uiState,
            onAppClicked = { app ->
                navigator.push(AppsDetails(app))
            },
        )
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
internal fun AppsCatalogScreen(
    uiState: AppsCatalogUiState,
    onAppClicked: (LauncherApp) -> Unit,
) {
    AdaptiveScaffold(
        topBar = {
            AdaptiveTopAppBar(
                title = { Text("Apps Catalog") },
            )
        }
    ) { padding ->
        if (uiState is AppsCatalogUiState.Loaded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(padding),
            ) {
                items(uiState.apps) { app ->
                    AppCard(app, onAppClicked)
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                AdaptiveCircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun AppCard(
    app: LauncherApp,
    onAppClicked: (LauncherApp) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RectangleShape,
        onClick = { onAppClicked(app) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            KamelImage(
                resource = asyncPainterResource(data = app.icon),
                contentDescription = app.name,
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                app.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
