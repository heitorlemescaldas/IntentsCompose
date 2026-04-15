package br.edu.ifsp.scl.sc303769x.intentscompose

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import br.edu.ifsp.scl.sc303769x.intentscompose.ui.theme.IntentsComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntentsComposeTheme {
                var parameter by remember { mutableStateOf("") }
                var expandedMenu by remember { mutableStateOf(false) }

                val context = LocalContext.current

                val parameterLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        result.data?.getStringExtra("EXTRA_PARAMETER")?.let {
                            parameter = it
                        }
                    }
                }

                val callPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$parameter"))
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Permissão necessária!", Toast.LENGTH_SHORT).show()
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Intents Compose") },
                            actions = {
                                IconButton(onClick = { expandedMenu = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                                }

                                DropdownMenu(
                                    expanded = expandedMenu,
                                    onDismissRequest = { expandedMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Set Parameter") },
                                        onClick = {
                                            expandedMenu = false
                                            val intent = Intent(context, ParameterActivity::class.java).apply {
                                                putExtra("EXTRA_PARAMETER", parameter)
                                            }
                                            parameterLauncher.launch(intent)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("View") },
                                        onClick = {
                                            expandedMenu = false
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(parameter))
                                            context.startActivity(intent)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Dial") },
                                        onClick = {
                                            expandedMenu = false
                                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$parameter"))
                                            context.startActivity(intent)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Call") },
                                        onClick = {
                                            expandedMenu = false
                                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$parameter"))
                                                context.startActivity(intent)
                                            } else {
                                                callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                                            }
                                        }
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Parâmetro Atual:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = parameter.ifEmpty { "Nenhum parâmetro definido" },
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}