package ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.app.component.VersionTable

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel by remember { mutableStateOf(AppViewModel()) }
        LaunchedEffect(viewModel) { viewModel.loadVersions() }

        val uiState by viewModel.state.collectAsState()

        MaterialTheme(colors = darkColors()) {
            Surface {
                Box(modifier = Modifier.fillMaxSize()) {
                    when (val state = uiState) {
                        AppViewModel.State.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        is AppViewModel.State.Success -> {
                            VersionTable(
                                versions = state.versions,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        AppViewModel.State.Failed -> {
                            Text(
                                text = "Loading Error!!",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}