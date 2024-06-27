package com.telefonica.kmpappscatalog.presentation.appsCatalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import com.telefonica.kmpappscatalog.openApp
import com.telefonica.kmpappscatalog.openUrl
import com.telefonica.kmpappscatalog.presentation.appsCatalog.UILayoutType.Grid
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.AppsCatalogUiState
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.CatalogDataState
import com.telefonica.kmpappscatalog.presentation.appsDetails.AppsDetails
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCircularProgressIndicator
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmpappscatalog.composeapp.generated.resources.Res
import kmpappscatalog.composeapp.generated.resources.ic_grid
import kmpappscatalog.composeapp.generated.resources.ic_list
import org.jetbrains.compose.resources.painterResource

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
            onLayoutTypeSelected = viewModel::setLayoutType,
        )
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
internal fun AppsCatalogScreen(
    uiState: AppsCatalogUiState,
    onAppClicked: (LauncherApp) -> Unit,
    onLayoutTypeSelected: (UILayoutType) -> Unit,
) {
    AdaptiveScaffold(
        topBar = {
            AppCatalogToolbar(
                uiState,
                onLayoutTypeSelected,
            )
        }
    ) { padding ->
        if (uiState.catalogDataState is CatalogDataState.Loaded) {
            if (uiState.uiLayoutType == Grid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.padding(padding),
                ) {
                    items(uiState.catalogDataState.apps) { app ->
                        AppCard(
                            app = app,
                            onAppClicked = onAppClicked
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.padding(padding),
                ) {
                    items(uiState.catalogDataState.apps) { app ->
                        ExtendedAppCard(
                            app = app,
                            onAppClicked = onAppClicked
                        )
                    }
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

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppCatalogToolbar(uiState: AppsCatalogUiState, onLayoutTypeSelected: (UILayoutType) -> Unit) {
    AdaptiveTopAppBar(
        title = { Text("Apps Catalog") },
        actions = {
            if (uiState.catalogDataState is CatalogDataState.Loaded) {
                if (uiState.uiLayoutType == Grid) {
                    IconButton(onClick = { onLayoutTypeSelected(UILayoutType.List) }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_list),
                            contentDescription = "List",
                        )
                    }
                } else {
                    IconButton(onClick = { onLayoutTypeSelected(Grid) }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_grid),
                            contentDescription = "Grid",
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ExtendedAppCard(
    app: LauncherApp,
    onAppClicked: (LauncherApp) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        app = app,
        onAppClicked = null,
        modifier = modifier,
    ) {
        Text(
            app.description,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (app.isInstalled) {
                AdaptiveButton(onClick = { openApp(app.androidPackage, app.iosScheme) }) {
                    Text(
                        "Open",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            } else {
                AdaptiveButton(onClick = { openUrl(app.androidInstallUrl, app.iosInstallUrl) }) {
                    Text(
                        "Install",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
            TextButton(onClick = { onAppClicked(app) }) {
                Text(
                    "More info",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun AppCard(
    app: LauncherApp,
    modifier: Modifier = Modifier,
    onAppClicked: ((LauncherApp) -> Unit)? = null,
    bottom: @Composable (() -> Unit)? = null,
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RectangleShape,
        modifier = modifier.fillMaxWidth()
            .then(
                if (onAppClicked != null)
                    Modifier.clickable { onAppClicked(app) }
                else Modifier
            ),
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
            if (bottom != null) {
                Spacer(modifier = Modifier.height(8.dp))
                bottom()
            }
        }
    }
}

enum class UILayoutType {
    Grid,
    List, ;

    fun toLayoutType(): LayoutType {
        return when (this) {
            Grid -> LayoutType.Grid
            List -> LayoutType.List
        }
    }
}

fun LayoutType.toUILayoutType(): UILayoutType {
    return when (this) {
        LayoutType.Grid -> Grid
        LayoutType.List -> UILayoutType.List
    }
}
