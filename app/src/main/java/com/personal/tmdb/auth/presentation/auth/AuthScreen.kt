package com.personal.tmdb.auth.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.ApplySystemBarsTheme
import com.personal.tmdb.core.util.applyStatusBarsTheme
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.tmdbRadialDarkBlue
import com.personal.tmdb.ui.theme.tmdbRadialDarkPurple
import com.personal.tmdb.ui.theme.tmdbRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navigateBack: () -> Unit,
    preferencesState: State<PreferencesState>,
) {
    ApplySystemBarsTheme(applyLightStatusBars = true)

    val view = LocalView.current
    val context = LocalContext.current
    val darkTheme = isSystemInDarkTheme()
    DisposableEffect(key1 = Unit) {
        onDispose {
            applyStatusBarsTheme(view, context, preferencesState.value.darkTheme ?: darkTheme)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*No title*/ },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = backgroundLight
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        contentColor = backgroundLight
    ) { innerPadding ->
        val radialGradient by remember {
            mutableStateOf(
                object : ShaderBrush() {
                    override fun createShader(size: Size): Shader {
                        val biggerDimension = maxOf(size.height, size.width)
                        return RadialGradientShader(
                            colors = listOf(tmdbRadialDarkPurple, tmdbRadialDarkBlue),
                            center = Offset.Zero,
                            radius = biggerDimension
                        )
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(radialGradient)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.pipes_red),
                contentDescription = "Red pipes",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 16.dp, top = 22.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.offset(y = 48.dp),
                        text = stringResource(id = R.string.tmdb),
                        fontSize = 86.sp,
                        fontWeight = FontWeight.Black,
                        fontStyle = FontStyle.Italic
                    )
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.deadpool),
                        contentDescription = "Deadpool"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.join_community),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.join_benefits),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = backgroundLight),
                    border = BorderStroke(1.dp, backgroundLight)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.or),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tmdbRed,
                        contentColor = backgroundLight
                    )
                ) {
                    Text(text = stringResource(id = R.string.join_tmdb))
                }
            }
        }
    }
}