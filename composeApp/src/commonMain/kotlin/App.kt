import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.telefonica.librarycatalogapi.AppsCatalogApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var apps by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            apps = AppsCatalogApi.createDefault().getApps().getOrNull().toString()
        }
        LazyColumn (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text("Apps: $apps")
            }
        }
    }
}
