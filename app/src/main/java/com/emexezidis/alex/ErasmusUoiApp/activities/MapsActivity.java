package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.adapters.PlacesCardviewAdapter;
import com.emexezidis.alex.ErasmusUoiApp.classes.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private ArrayList<Place> placeList;
    private RecyclerView recyclerView;

    // Used to understand when a scroll is a User Action rather
    // than made by the Marker Selection callback:
    private boolean realUserScroll = true;
    private boolean isFirstCameraMove = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity_layout);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.maps_activity_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        initializePlacesList();
        initializeCards();

        mapFragment.getMapAsync(this);
    }

    private void initializePlacesList() {

        placeList = new ArrayList<>();

        placeList.add(new Place(getString(R.string.place1_name), getString(R.string.place1_description), 39.619982, 20.845028, "8:00 - 14:00"));
        placeList.add(new Place(getString(R.string.place2_name), getString(R.string.place2_description), 39.616093, 20.842101, "7:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place3_name), "", 39.617735, 20.841031, "8:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place4_name), "", 39.617823, 20.838744, "8:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place5_name), "", 39.617613, 20.837666, "8:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place6_name), "", 39.617300, 20.838815, "8:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place7_name), "", 39.612773, 20.838219, "8:00 - 22:00"));
//        placeList.add(new Place(getString(R.string.place8_name), "", 39.617300, 20.838815, "8:00 - 22:00"));
        placeList.add(new Place(getString(R.string.place9_name), getString(R.string.place9_description), 39.662479, 20.851937, "Always open"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(false);
        } else {
            map.setMyLocationEnabled(true);
        }

        map.getUiSettings().setMapToolbarEnabled(false);
        setMarkerClickListener();
        attachPlacesToMap();

        moveCameraToCoordinates(placeList.get(0).getMapLatLng());
    }

    private void attachPlacesToMap() {

        for (Place place : placeList) {
            addMarkerToMap(place);
        }
    }

    private void addMarkerToMap(Place place) {

        Marker newMarker = map.addMarker(new MarkerOptions()
                .position(place.getMapLatLng())
                .title(place.getName()));

        newMarker.setTag(place);
    }

    private void moveCameraToCoordinates(LatLng coordinates) {

        CameraUpdate location;

        if (isFirstCameraMove) {
             location = CameraUpdateFactory.newLatLngZoom(
                    coordinates, 12);
        } else {
            location = CameraUpdateFactory.newLatLngZoom(
                    coordinates, 17);
        }

        isFirstCameraMove = false;
        map.animateCamera(location);
    }

    private void setMarkerClickListener() {

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Place place = (Place) marker.getTag();
                int position = ((PlacesCardviewAdapter)(recyclerView.getAdapter())).getPositionOfObject(place);
                realUserScroll = false;
                recyclerView.smoothScrollToPosition(position);
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Place place = (Place) marker.getTag();
                Intent intent = new Intent(getApplicationContext(), PlaceInfoScreen.class);
                intent.putExtra("place", place);
                startActivity(intent);
            }
        });
    }

    private void initializeCards() {

        PlacesCardviewAdapter cardsAdapter = new PlacesCardviewAdapter(placeList, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardsAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (realUserScroll) {

                        int positionOfVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstCompletelyVisibleItemPosition();

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(getCoordinatesFromPlaceList(positionOfVisibleItem), 14));
                    } else {
                        realUserScroll = true;
                    }

                }
            }
        });

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
    }

    // This is here to prevent some "ArrayOutOfBounds" exceptions:
    private LatLng getCoordinatesFromPlaceList(int position) {

        if(position >= 0 && position < placeList.size()) {
            return placeList.get(position).getMapLatLng();
        } else {
            return placeList.get(0).getMapLatLng();
        }
    }

    // This is used to provide back navigation through the toolbar
    // for 4.0 Android devices:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}