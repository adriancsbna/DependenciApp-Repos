package dam.android.dependeciapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import dam.android.dependeciapp.AsyncTasks.CargarUbicacionSQLite;
import dam.android.dependeciapp.AsyncTasks.GuardarUbicacionSQLite;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Pojo.Ubicacion;
import dam.android.dependeciapp.R;

/**
 * Created by adria on 11/02/2018.
 */
@SuppressLint("ValidFragment")
public class MapFragment extends Fragment implements OnMapReadyCallback {
    private final String[] PERMISSIONS_MAPS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_MAPS = 1;

    private GoogleMap mMap;
    private CargarUbicacionSQLite cargarUbicacionSQLite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        askForMapPermission();
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (isFocusOnMapFragment()) {
            Toast.makeText(getContext(), R.string.maps_this_may_take_a_few_seconds, Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    private void askForMapPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_MAPS, REQUEST_MAPS);
            }
        }
    }

    // @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForMapPermission();
        } else {
            mMap.setMyLocationEnabled(true);
            DependenciaDBManager.UbicacionesDBManager db = new DependenciaDBManager.UbicacionesDBManager(getContext());
            db.createTableIfNotExist();
            cargarUbicacionSQLite = new CargarUbicacionSQLite();
            cargarUbicacionSQLite.execute(getContext());
            Ubicacion ubicacion = null;
            try {
                ubicacion = (Ubicacion) cargarUbicacionSQLite.get();
            } catch (InterruptedException e) {
                Log.e("ASYNCTASK_CARGAR_UBI", e.getMessage());
            } catch (ExecutionException e) {
                Log.e("ASYNCTASK_CARGAR_UBI", e.getMessage());
            }
            LatLng lastKnownLocation = (ubicacion == null) ? null : new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
            MyLocationListener myLocationListener = new MyLocationListener(lastKnownLocation);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, myLocationListener);
            cargarUbicacionSQLite = null;
        }
    }
        @Override
        @SuppressLint("MissingPermission")
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;
            enableMyLocation();
        }

    public boolean isFocusOnMapFragment() {
        FragmentActivity fa = getActivity();
        if (fa == null) {
            return false;
        }
        Fragment f = fa.getSupportFragmentManager().getPrimaryNavigationFragment();
        if (f == null) {
            return false;
        }
        return (f instanceof MapFragment);
    }

    public LatLng getMyLastLocation() {
        Ubicacion ubicacion = null;
        cargarUbicacionSQLite = new CargarUbicacionSQLite();
        cargarUbicacionSQLite.execute(getContext());
        try {
            ubicacion = (Ubicacion) cargarUbicacionSQLite.get();
        } catch (InterruptedException e) {
            Log.e("ASYNCTASK_CARGAR_UBI", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ASYNCTASK_CARGAR_UBI", e.getMessage());
        }

        LatLng myLastLocation = (ubicacion == null) ? null : new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
        cargarUbicacionSQLite = null;
        return myLastLocation;
    }

    private class MyLocationListener implements LocationListener {

        private static final float ZOOM_BUILDING_LEVEL = 16;

        private LatLng myLocation;
        private Marker myMarker;
        private GuardarUbicacionSQLite guardarUbicacion;

        @SuppressLint("MissingPermission")
        public MyLocationListener(LatLng lastKnownlocation) {

            if (lastKnownlocation != null) {
                myLocation = lastKnownlocation;
                setMarkerOnLatLng(myLocation);
            }
        }

        @Override
        public void onLocationChanged(Location loc) {
            myLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
            setMarkerOnLatLng(myLocation);
        }

        public void setMarkerOnLatLng(LatLng locationLatLng) {
            if (locationLatLng.latitude != 0.0 && locationLatLng.longitude != 0.0) {
                try {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(locationLatLng.latitude, locationLatLng.longitude, 1);
                    if (!list.isEmpty()) {
                        Address address = list.get(0);
                        String addressLine = address.getAddressLine(0);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, ZOOM_BUILDING_LEVEL));
                        if (myMarker != null) {
                            myMarker.remove();
                        }
                        myMarker = mMap.addMarker(new MarkerOptions().position(locationLatLng).title(getString(R.string.maps_marker_you_are_here)).snippet(addressLine));
                        myMarker.showInfoWindow();

                        guardarUbicacion = new GuardarUbicacionSQLite(getContext(), locationLatLng, addressLine);
                        guardarUbicacion.execute();
                        guardarUbicacion = null;
                    }
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            if (isFocusOnMapFragment()) {
                Toast.makeText(getContext(), R.string.gps_disabled, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (isFocusOnMapFragment()) {
                Toast.makeText(getContext(), R.string.gps_enabled, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (isFocusOnMapFragment()) {
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(getContext(), R.string.maps_provider_out_of_service, Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(getContext(), R.string.maps_provider_temporarily_unavailable, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }

    }
}
