package com.example.mobile_proj.ui.screens.map

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.gps.utils.Coordinates
import com.example.gps.utils.LocationService
import com.example.gps.utils.StartMonitoringResult
import com.example.mobile_proj.R
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.utils.PermissionStatus
import com.example.mobile_proj.utils.rememberPermission
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView(navController: NavHostController) {

    val context = LocalContext.current

    val locationService = LocationService(context)

    var showLocationDisabledAlert by remember { mutableStateOf(false) }
    var showPermissionPermanentlyDeniedAlert by remember { mutableStateOf(false) }

    val locationPermission = rememberPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) { status ->
        when (status) {
            PermissionStatus.Granted -> {
                val res = locationService.requestCurrentLocation()
                showLocationDisabledAlert = res == StartMonitoringResult.GPSDisabled
            }
            PermissionStatus.Denied -> {}
            PermissionStatus.PermanentlyDenied -> showPermissionPermanentlyDeniedAlert = true
            PermissionStatus.Unknown -> {}
        }
    }

    fun requestLocation() {
        if (locationPermission.status.isGranted) {
            val res = locationService.requestCurrentLocation()
            showLocationDisabledAlert = res == StartMonitoringResult.GPSDisabled
        } else {
            locationPermission.launchPermissionRequest()
        }
    }

    Scaffold (
        topBar = { TopAppBar(navController, Route.ViewMap, null) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.Start
            ) {
                requestLocation()
                if (locationService.coordinates != null) {
                    OsmdroidMapView(coordinates = locationService.coordinates)
                }
            }
        }
    )

    Configuration.getInstance().userAgentValue = "MapApp"

    if (showLocationDisabledAlert) {
        AlertDialog(
            title = { Text("Location disabled") },
            text = { Text("Location must be enabled to get your current location in the app.") },
            confirmButton = {
                TextButton(onClick = {
                    locationService.openLocationSettings()
                    showLocationDisabledAlert = false
                }) {
                    Text("Enable")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLocationDisabledAlert = false
                }) {
                    Text("Dismiss")
                }
            },
            onDismissRequest = {
                showLocationDisabledAlert = false
            }
        )
    }

    if (showPermissionPermanentlyDeniedAlert) {
        AlertDialog(
            title = { Text("Location permission is required.") },
            text = { Text("Go to Settings") },
            confirmButton = {
                TextButton(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.setData(uri)
                    context.startActivity(intent)
                    showPermissionPermanentlyDeniedAlert = false
                }) {
                    Text("Go to App Settings")
                }
            },
            onDismissRequest = { showPermissionPermanentlyDeniedAlert = false }
        )
    }
}

@Composable
fun OsmdroidMapView(coordinates: Coordinates?) {

    val gyms = stringArrayResource(id = R.array.gyms)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.maxZoomLevel = 24.0
            mapView.minZoomLevel = 15.0
            mapView.controller.setZoom(15.0)
            mapView.isHorizontalMapRepetitionEnabled = false
            mapView.isVerticalMapRepetitionEnabled = false
            mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            if (coordinates != null) {
                mapView.setScrollableAreaLimitDouble(BoundingBox(
                    coordinates.latitude + 0.05,
                    coordinates.longitude + 0.03,
                    coordinates.latitude - 0.05,
                    coordinates.longitude - 0.03
                ))
            }
            val startPoint = coordinates?.let { GeoPoint(coordinates.latitude, it.longitude) }
            mapView.controller.setCenter(startPoint)
            for (marker in gyms) {
                val coords = marker.split(",")
                val startMarker = Marker(mapView)
                startMarker.position = GeoPoint(coords[0].toDouble(), coords[1].toDouble())
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(startMarker)
            }

            mapView
        }
    )


}