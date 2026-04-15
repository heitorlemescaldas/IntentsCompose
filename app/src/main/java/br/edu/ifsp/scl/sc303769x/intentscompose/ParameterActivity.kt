package br.edu.ifsp.scl.sc303769x.intentscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc303769x.intentscompose.ui.theme.IntentsComposeTheme

class ParameterActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class) // Anotação adicionada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedParameter = intent.getStringExtra("EXTRA_PARAMETER") ?: ""

        setContent {
            IntentsComposeTheme {
                var textState by remember { mutableStateOf(receivedParameter) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("ParameterActivity") }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = textState,
                            onValueChange = { textState = it },
                            label = { Text("Parâmetro") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                val returnIntent = Intent().apply {
                                    putExtra("EXTRA_PARAMETER", textState)
                                }
                                setResult(RESULT_OK, returnIntent)
                                finish()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Salvar e Sair")
                        }
                    }
                }
            }
        }
    }
}