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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.skydoves.landscapist.coil3.CoilImage
import com.telefonica.kmpappscatalog.OpenExternal
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.kmpappscatalog.domain.entities.LayoutType
import com.telefonica.kmpappscatalog.presentation.appsCatalog.UILayoutType.Grid
import com.telefonica.kmpappscatalog.presentation.appsCatalog.component.AppButton
import com.telefonica.kmpappscatalog.presentation.appsCatalog.component.OutlinedAppButton
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.AppsCatalogUiState
import com.telefonica.kmpappscatalog.presentation.appsCatalog.model.CatalogDataState
import com.telefonica.kmpappscatalog.presentation.appsDetails.AppsDetails
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCircularProgressIndicator
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTextButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmpappscatalog.composeapp.generated.resources.Res
import kmpappscatalog.composeapp.generated.resources.ic_grid
import kmpappscatalog.composeapp.generated.resources.ic_list
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

private const val ROUNDED_CORNERS = 100

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
            onFilterSelected = viewModel::setFilter,
        )
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
internal fun AppsCatalogScreen(
    uiState: AppsCatalogUiState,
    onAppClicked: (LauncherApp) -> Unit,
    onLayoutTypeSelected: (UILayoutType) -> Unit,
    onFilterSelected: (String) -> Unit,
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
            Column(modifier = Modifier.padding(padding)) {
                Filters(uiState.catalogDataState, uiState, onFilterSelected)
                if (uiState.uiLayoutType == Grid) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(uiState.catalogDataState.filteredApps) { app ->
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
                    ) {
                        items(uiState.catalogDataState.filteredApps) { app ->
                            ExtendedAppCard(
                                app = app,
                                onAppClicked = onAppClicked
                            )
                        }
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

@Composable
private fun Filters(
    catalogDataState: CatalogDataState.Loaded,
    uiState: AppsCatalogUiState,
    onFilterSelected: (String) -> Unit
) {
    LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        items(catalogDataState.filters) { filter ->
            FilterChip(
                label = { Text(filter) },
                selected = filter == uiState.selectedFilter,
                modifier = Modifier.padding(end = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedLabelColor = MaterialTheme.colorScheme.primary,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                shape = RoundedCornerShape(ROUNDED_CORNERS),
                onClick = { onFilterSelected(filter) },
            )
        }
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
private fun AppCatalogToolbar(uiState: AppsCatalogUiState, onLayoutTypeSelected: (UILayoutType) -> Unit) {
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
private fun ExtendedAppCard(
    app: LauncherApp,
    onAppClicked: (LauncherApp) -> Unit,
    modifier: Modifier = Modifier,
    appOpenExternal: OpenExternal = koinInject(),
) {
    val isAppInstalled by app.isInstalled.collectAsState()
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
            if (isAppInstalled) {
                AppButton(
                    text = "Open",
                    onClick = { appOpenExternal.openApp(app.androidPackage, app.iosScheme) }
                )
            } else {
                OutlinedAppButton(
                    text = "Install",
                    onClick = { appOpenExternal.openUrl(app.androidInstallUrl, app.iosInstallUrl) }
                )
            }
            AdaptiveTextButton(onClick = { onAppClicked(app) }) {
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
            CoilImage(
                imageModel = { app.icon },
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
