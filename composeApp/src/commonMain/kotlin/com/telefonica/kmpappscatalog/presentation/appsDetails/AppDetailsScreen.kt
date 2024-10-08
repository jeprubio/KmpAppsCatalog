package com.telefonica.kmpappscatalog.presentation.appsDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.telefonica.kmpappscatalog.AppInstallation
import com.telefonica.kmpappscatalog.OpenExternal
import com.telefonica.kmpappscatalog.domain.entities.LauncherApp
import com.telefonica.kmpappscatalog.presentation.appsCatalog.component.AppButton
import com.telefonica.kmpappscatalog.presentation.appsCatalog.component.OutlinedAppButton
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.AppsDetailsUiState
import com.telefonica.kmpappscatalog.presentation.appsDetails.model.IsAppInstalled
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCircularProgressIndicator
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.koin.compose.koinInject

private const val HEADING_ASPECT_RATIO = 4f / 3f
private const val CLOSE_BUTTON_ALPHA = 0.7f

class AppsDetails(private val app: LauncherApp) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { AppsDetailsScreenViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(app) {
            viewModel.checkAppInstalled(app)
        }
        AppDetailsScreen(
            app,
            uiState,
            back = { navigator.pop() },
        )
    }
}

@Composable
private fun AppDetailsScreen(
    app: LauncherApp,
    uiState: AppsDetailsUiState,
    back: () -> Unit,
) {
    var footerHeightDp = remember { 0.dp }
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Heading(app)
            }
            item {
                Body(app, uiState)
            }
            item {
                Spacer(
                    modifier = Modifier.height(footerHeightDp)
                )
            }
        }
        Footer(
            app = app,
            uiState = uiState,
            modifier = Modifier.onSizeChanged { size ->
                footerHeightDp = with(localDensity) { size.height.toDp() }
            }
        )
        CloseButton(
            back = back,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        )
    }
}

@Composable
private fun Heading(app: LauncherApp) {
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(HEADING_ASPECT_RATIO)
    ) {
        CoilImage(
            imageModel = { app.icon },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )

        AppIcon(app, Modifier.padding(start = 16.dp))
    }
}

@Composable
fun Body(
    app: LauncherApp,
    uiState: AppsDetailsUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 36.dp, bottom = 8.dp)
    ) {
        Text(
            app.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        if (uiState.isAppInstalled == IsAppInstalled.Loaded(true)) {
            Text(
                "Installed",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 100))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }
    }
    Text(
        text = app.description,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(16.dp)
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun BoxScope.Footer(
    app: LauncherApp,
    uiState: AppsDetailsUiState,
    modifier: Modifier = Modifier,
    openExternal: OpenExternal = koinInject(),
) {
    val appInstallation = koinInject<AppInstallation>()
    Column(
        modifier = modifier
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (uiState.isAppInstalled) {
                IsAppInstalled.Loading -> {
                    AdaptiveCircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is IsAppInstalled.Error -> {
                    Text("Error: ${uiState.isAppInstalled.error.message}")
                }

                is IsAppInstalled.Loaded -> {
                    val installed = uiState.isAppInstalled.isInstalled
                    if (!installed) {
                        OutlinedActionButton(
                            title = "Install",
                            buttonAction = { openExternal.openUrl(app.androidInstallUrl, app.iosInstallUrl) },
                        )
                    } else {
                        ActionButton(
                            title = "Open",
                            buttonAction = { openExternal.openApp(app.androidPackage, app.iosScheme) },
                        )
                        if (appInstallation.shouldShowUninstallButton()) {
                            OutlinedActionButton(
                                title = "Uninstall",
                                buttonAction = { appInstallation.uninstallApp(app.androidPackage, app.iosScheme) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.AppIcon(
    app: LauncherApp,
    modifier: Modifier = Modifier
) {
    CoilImage(
        imageModel = { app.icon },
        modifier = modifier
            .align(Alignment.BottomStart)
            .clip(RoundedCornerShape(12.dp))
            .border(4.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .size(64.dp)
    )
}

@Composable
private fun CloseButton(back: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { back() },
        modifier = modifier
            .clip(CircleShape)
            .alpha(CLOSE_BUTTON_ALPHA)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
        )
    }
}

@Composable
private fun ActionButton(
    title: String,
    buttonAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppButton(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        onClick = { buttonAction() },
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun OutlinedActionButton(
    title: String,
    buttonAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedAppButton(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        onClick = { buttonAction() },
        modifier = modifier
            .fillMaxWidth()
    )
}
