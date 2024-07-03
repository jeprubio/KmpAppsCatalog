import androidx.compose.ui.window.ComposeUIViewController
import com.telefonica.kmpappscatalog.di.initKoin
import com.telefonica.kmpappscatalog.presentation.App

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}