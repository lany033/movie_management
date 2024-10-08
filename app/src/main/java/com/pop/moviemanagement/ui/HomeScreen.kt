package com.pop.moviemanagement.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseUser
import com.pop.moviemanagement.R
import com.pop.moviemanagement.data.Movie
import com.pop.moviemanagement.model.repository.MovieRepository
import com.pop.moviemanagement.ui.navigation.AuthGraph
import com.pop.moviemanagement.ui.navigation.BottomBarNavItem
import com.pop.moviemanagement.utils.AuthManager
import com.pop.moviemanagement.utils.FirestoreManager

@Composable
fun HomeScreen(
    onClickLoginSession: () -> Unit,
    authManager: AuthManager,
    navigation: NavController
) {
    val viewModel: MovieViewModel = viewModel {
        MovieViewModel(MovieRepository())
    }


    val state by viewModel.state.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var user = authManager.getCurrentUser()



    var onLogoutConfirmed: () -> Unit = {
        authManager.signOut()
        navigation.navigate(AuthGraph.LOGIN) {
            popUpTo(BottomBarNavItem.Home.route) {
                inclusive = true
            }
        }
        val currentUser = authManager.getCurrentUser()
        if (currentUser == null) {
            // El usuario no está logueado
            Log.d("AuthCheck", "No user logged in")
        } else {
            // El usuario está logueado
            Log.d("AuthCheck", "User logged in: ${currentUser.uid}")
        }
    }

    fun onclickLogout() {
        showDialog = true
    }

    Scaffold(topBar = {
        MyTopBar(
            user = user,
            onClickLoginSession = { onClickLoginSession() },
            onClickLogout = { onclickLogout() }
        )
    }) {
        Card(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), shape = RectangleShape
        ) {
            HorizontalPagerHomeScreen(state.movies)
            Box() {
                if (showDialog) {
                    LogoutDialog(onConfirmLogout = {
                        onLogoutConfirmed()
                        showDialog = false
                    }, onDismiss = { showDialog = false })
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cerrar sesión") },
        text = { Text("¿Estás seguro que deseas cerrar sesión?") },
        confirmButton = {
            Button(
                onClick = onConfirmLogout
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    user: FirebaseUser?,
    onClickLoginSession: () -> Unit,
    onClickLogout: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (user?.photoUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user?.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Imagen",
                        placeholder = painterResource(id = R.drawable.profile),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Foto de perfil por defecto",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (!user?.displayName.isNullOrEmpty()) "Hola ${user?.displayName}" else "Bienvenidx",
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (!user?.email.isNullOrEmpty()) "${user?.email}" else "Anónimo",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        actions = {
            if (user != null) {
                IconButton(onClick = { onClickLogout() }) {
                    Icon(Icons.Outlined.ExitToApp, contentDescription = "Cerrar sesión")
                }
            } else {
                IconButton(onClick = { onClickLoginSession() }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "account")
                }
            }

        }

    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerHomeScreen(movieList: List<Movie>) {

    val movieListPopularity = movieList.filter { it.vote_average > 7.5 }
    val pagerState = rememberPagerState(pageCount = { movieListPopularity.size })

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill
        ) {
            if (movieListPopularity.isEmpty()) {
                Text(text = "is empty")
            } else {
                CarrouselItem(
                    image = movieListPopularity[it].poster_path,
                    title = movieListPopularity[it].title,
                    description = movieListPopularity[it].overview,
                )
            }
        }
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            colors = CardDefaults.cardColors(containerColor = Color.Red)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Most Popular", color = White)
            }
        }
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(movieListPopularity.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) White else LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(color, CircleShape)
                            .size(10.dp)
                    )
                }
            }
        }

    }
}


@Composable
fun CarrouselItem(image: String, title: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/original/${image}"),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                        startY = 0f,
                        endY = 900f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                color = White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = description,
                color = White,
                fontSize = 12.sp,
                maxLines = 2,
                lineHeight = 12.sp
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

