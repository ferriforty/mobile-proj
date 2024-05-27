package com.example.mobile_proj.ui.screens.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView(navController: NavHostController) {
    val context = LocalContext.current

    Scaffold (
        topBar = { TopAppBar(navController, Route.ViewMap, null) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.Start
            ) {
                OsmdroidMapView()

            }
        }
    )
    getLocationName("aa", context)
    Configuration.getInstance().userAgentValue = "MapApp"

}

fun getLocationName(locationName: String, context: Context): GeoPoint{
    try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val geoResults: List<Address> = geocoder.getFromLocationName(
            "",
            10,
        ).orEmpty()
        if (geoResults.isNotEmpty()) {
            val addr = geoResults[0]
            return GeoPoint(addr.latitude, addr.longitude)
        }else{
            Toast.makeText(context,"Location Not Found",Toast.LENGTH_LONG).show()
        }
    } catch (e: java.lang.Exception) {
        print(e.message)
    }
    return GeoPoint(0.0,0.0)
}

@Composable
fun OsmdroidMapView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val startPoint = getLocationName("", context)
            val startMarker = Marker(mapView)
            startMarker.position = startPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(startMarker)
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.setScrollableAreaLimitDouble(BoundingBox(85.0, 180.0, -85.0, -180.0))
            mapView.maxZoomLevel = 20.0
            mapView.minZoomLevel = 4.0
            //mapView.controller.animateTo(GeoPoint(locationManager.getLastKnownLocation(locationManager.getProviders(LocationManager.GPS_PROVIDER))))
            mapView.controller.setZoom(6.0)
            mapView.isHorizontalMapRepetitionEnabled = false
            mapView.isVerticalMapRepetitionEnabled = false
            mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            mapView
        }
    )
}