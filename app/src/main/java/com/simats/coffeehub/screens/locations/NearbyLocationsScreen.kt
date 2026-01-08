package com.simats.coffeehub.screens.locations
import com.simats.coffeehub.R

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/* -------------------- DATA -------------------- */

data class CafePlace(
    val name: String,
    val latLng: LatLng
)

data class PlacesResponse(
    val results: List<PlaceResult>?,
    val status: String
)

data class PlaceResult(
    val name: String?,
    val geometry: Geometry?
)

data class Geometry(
    val location: LocationLatLng?
)

data class LocationLatLng(
    val lat: Double,
    val lng: Double
)

/* -------------------- API -------------------- */

interface PlacesApi {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyCafes(
        @Query("location") location: String,
        @Query("radius") radius: Int = 2000,
        @Query("type") type: String = "cafe",
        @Query("key") apiKey: String
    ): PlacesResponse
}

/* -------------------- SCREEN -------------------- */

@Composable
fun NearbyLocationsScreen(nav: NavController) {

    val brown = Color(0xFF5C4033)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // HEADER
        Surface(color = brown, shadowElevation = 6.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable { nav.popBackStack() }
                )

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        "Nearby Coffee Shops",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Based on your live location",
                        color = Color.White.copy(0.7f),
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/maps/search/coffee+shops+near+me")
                            )
                            context.startActivity(intent)
                        }
                )
            }
        }

        CoffeeMapView()
    }
}

/* -------------------- MAP -------------------- */

@SuppressLint("MissingPermission")
@Composable
fun CoffeeMapView() {

    val context = LocalContext.current
    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var userLocation by remember {
        mutableStateOf(LatLng(13.0480, 80.1760)) // default
    }

    var cafes by remember { mutableStateOf<List<CafePlace>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissionGranted =
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                        it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }

    LaunchedEffect(Unit) {
        permissionGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 15f)
    }

    // GOOGLE MAP STYLE (MATCHES GOOGLE MAPS APP)
    val mapProperties = MapProperties(
        isMyLocationEnabled = permissionGranted,
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            context,
            R.raw.google_maps_light
        )
    )

    // Get user location
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            fusedClient.lastLocation.addOnSuccessListener { loc ->
                loc?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    // Fetch cafes
    LaunchedEffect(userLocation) {
        isLoading = true
        cafes = fetchNearbyCafes(userLocation)
        isLoading = false
    }

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            )
        ) {

            // USER MARKER
            Marker(
                state = MarkerState(userLocation),
                title = "You are here",
                icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_AZURE
                )
            )

            // CAFE MARKERS → NAVIGATION
            cafes.forEach { cafe ->
                Marker(
                    state = MarkerState(cafe.latLng),
                    title = cafe.name,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED
                    ),
                    onClick = {
                        val uri = Uri.parse(
                            "google.navigation:q=${cafe.latLng.latitude},${cafe.latLng.longitude}&mode=d"
                        )
                        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                            setPackage("com.google.android.apps.maps")
                        }
                        context.startActivity(intent)
                        true
                    }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF5C4033)
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.White,
            onClick = {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(userLocation, 15f)
            }
        ) {
            Icon(
                Icons.Default.MyLocation,
                contentDescription = null,
                tint = Color(0xFF5C4033)
            )
        }
    }
}

/* -------------------- NETWORK -------------------- */

suspend fun fetchNearbyCafes(location: LatLng): List<CafePlace> {
    return try {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PlacesApi::class.java)

        val response = api.getNearbyCafes(
            location = "${location.latitude},${location.longitude}",
            apiKey = "AIzaSyDf0H2XLAqbz5shS7V6S_6uZKXMixtoH3E"
        )

        if (response.status != "OK" || response.results.isNullOrEmpty()) {
            emptyList()
        } else {
            response.results.mapNotNull {
                val loc = it.geometry?.location
                if (it.name != null && loc != null)
                    CafePlace(it.name, LatLng(loc.lat, loc.lng))
                else null
            }
        }
    } catch (e: Exception) {
        emptyList()
    }
}
