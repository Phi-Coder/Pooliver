package com.example.poolliver.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.poolliver.Adapters.PageAdapter;
import com.example.poolliver.PriceEstimation;
import com.example.poolliver.R;
import com.example.poolliver.database.dataHolder;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment implements OnMapReadyCallback {

    private static final int TAG_CODE_PERMISSION_LOCATION = 12;
    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationClient;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private final int GPS_REQUEST_CODE = 2;
    private final int AUTOCOMPLETE_FROM_REQUEST_CODE = 3;
    private final int AUTOCOMPLETE_TO_REQUEST_CODE = 4;
    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;

    Marker currentMarker;
    Marker DestMarker;
    Geocoder geocoder;
    LatLng currentlatLng, destlatLng;
    FloatingActionButton fab;
    Button EstmPrice;
    Spinner ProductType;
    String Item;
    EditText From, To;

    @SuppressLint({"VisibleForTests", "ClickableViewAccessibility"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        // finding the view
        fab = view.findViewById(R.id.fab);
        EstmPrice = view.findViewById(R.id.CalcPrice);
        ProductType = (Spinner) view.findViewById(R.id.productType);
        From = view.findViewById(R.id.from);
        To = view.findViewById(R.id.to);

        requestLocationPermission();

        /* INITIATE MAP FRAGMENT */
        initMap();
        mLocationClient = new FusedLocationProviderClient(getContext());
        getCurrLoc();


        /* DROP DOWN MENU TO CHOOSE THE TYPE OF PRODUCT USER WANT TO DELIVER*/
        List<String> categories = new ArrayList<>();
        categories.add("Food");
        categories.add("Medicine");
        categories.add("Clothing");
        categories.add("Electronics");
        categories.add("Other");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        ProductType.setAdapter(dataAdapter);
        ProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(-1);
            }
        });



        /* PLACE API AUTO COMPLETE ON FROM AND TO EDITTEXT */
        Places.initialize(getContext(), getString(R.string.key));


        /* NEED BILLING ACCOUNT TO USE PLACES API */

//        From.setOnTouchListener((v, event) -> {
//            List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//
//            Intent placeIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//                    .build(getContext());
//            startActivityForResult(placeIntent, AUTOCOMPLETE_FROM_REQUEST_CODE);
//            return true;
//        });

//        To.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//
//                Intent placeIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
//                        .build(getContext());
//                startActivityForResult(placeIntent, AUTOCOMPLETE_TO_REQUEST_CODE);
//                return true;
//            }
//        });


        /* PRICE ESTIMATION SCREEN */
        EstmPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Location loc1 = new Location("");
                loc1.setLatitude(currentlatLng.latitude);
                loc1.setLongitude(currentlatLng.longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(26.842281);
                loc2.setLongitude(75.830548);


                float distance = loc1.distanceTo(loc2) / 1000;
                int PriceFinal = (int) (distance * 5);
                Toast.makeText(getContext(), String.valueOf(distance), Toast.LENGTH_SHORT).show();

                Intent priceEstIntent = new Intent(getContext(), PriceEstimation.class);
                if (To.getText().toString().isEmpty()) {
                    To.setError("destination address needed");
                } else {
                    priceEstIntent.putExtra("pickupAddress", String.valueOf(From.getText()));
                    priceEstIntent.putExtra("price", PriceFinal);
                    priceEstIntent.putExtra("productType", String.valueOf(Item));
                    priceEstIntent.putExtra("dropAddress", String.valueOf(To.getText().toString()));
                    startActivity(priceEstIntent);
                }


            }
//
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        return view;
    }


    // hard coded lat long for marker display on google map whenever map starts
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
            return;
        }
        googleMap.setMyLocationEnabled(true);


    }

    private void initMap() {
        if (isGpsEnable()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.maps);
            mapFragment.getMapAsync(this);
        }
    }

    // function for checking if GPS is enable or not
    private boolean isGpsEnable() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnable) {
            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Gps Permission")
                    .setMessage("Gps is required for this app")
                    .setPositiveButton("yes", (dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    })
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private void getCurrLoc() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("permissions", "location permission");
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                gotoLocation(location.getLatitude(), location.getLongitude());
                Log.d("getCurrLoc", String.valueOf(location.getLatitude()) + " + " + String.valueOf(location.getLongitude()));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void gotoLocation(double latitude, double longitude) {

        currentlatLng = new LatLng(latitude, longitude);
        if (currentMarker == null) {
            MarkerOptions options = new MarkerOptions();
            options.position(currentlatLng);
            options.title("Your Position");
            currentMarker = mMap.addMarker(options);
        } else {
            currentMarker.setPosition(currentlatLng);
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentlatLng, 18);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> myaddress = geocoder.getFromLocation(latitude, longitude, 1);
            String address = myaddress.get(0).getAddressLine(0);
            String city = myaddress.get(0).getLocality();
            From.setText(address + " " + city);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GPS_REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnable) {
                Toast.makeText(getContext(), "GPS is enable", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "GPS is not enable", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == AUTOCOMPLETE_FROM_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            String name = place.getName();
            currentlatLng = place.getLatLng();

            if (currentMarker == null) {
                MarkerOptions options2 = new MarkerOptions();
                options2.title("Pickup Point");
                options2.position(currentlatLng);

                currentMarker = mMap.addMarker(options2);
            } else {
                currentMarker.setPosition(currentlatLng);

            }

            From.setText(place.getAddress());

        } else if (requestCode == AUTOCOMPLETE_TO_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            String name = place.getName();
            destlatLng = place.getLatLng();

            if (DestMarker == null) {
                MarkerOptions options1 = new MarkerOptions();
                options1.title("Drop Point");
                options1.position(destlatLng);

                DestMarker = mMap.addMarker(options1);

            } else {
                DestMarker.setPosition(destlatLng);
            }
            To.setText(name);
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}