import androidx.compose.ui.window.ComposeUIViewController
import com.telefonica.kmpappscatalog.presentation.App
import com.telefonica.kmpappscatalog.presentation.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}