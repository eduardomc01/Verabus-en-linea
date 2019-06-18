package com.example.buslive;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public Handler mHandler;

    GoogleMap mMap;
    Marker marker;
    Geocoder geocoder;


    double geoLat[] = {
            19.159559,
            19.159666,
            19.160527,
            19.162301,
            19.163715,
            19.165002,
            19.166041,
            19.166745,

            19.169354,
            19.170484,
            19.171285,
            19.172197,
            19.173484,
            19.174898,
            19.175982,
            19.175647,
            19.175384,
            19.175126,
            19.174705,
            19.174279,

            19.174950,
            19.175667,
            19.176270,
            19.176792,
            19.177358,
            19.178103,
            19.178640,
            19.179470,
            19.180014,
            19.180711,
            19.181315,
            19.181829,
            19.182508,
            19.183131,
            19.183584,
            19.184190,
            19.184532,
            19.184468,
            19.184328,

            0.0

    };

    double geoLon[] = {
            -96.111619,
            -96.109909,
            -96.109118,
            -96.109402,
            -96.109649,
            -96.110802,
            -96.112712,
            -96.114262,

            -96.116290,
            -96.117341,
            -96.118060,
            -96.118827,
            -96.119658,
            -96.120200,
            -96.120710,
            -96.122775,
            -96.124416,
            -96.126063,
            -96.128751,

            -96.130451,
            -96.130610,
            -96.130712,
            -96.130811,
            -96.130902,
            -96.131017,
            -96.131148,
            -96.131222,
            -96.131366,
            -96.131478,
            -96.131583,
            -96.131647,
            -96.131712,
            -96.131852,
            -96.131977,
            -96.132059,
            -96.132155,
            -96.132235,
            -96.132722,
            -96.133297,
            0.0
    };


    int i = 0;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return v;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Permisos();

        miUbicacion();




    }

    public void Permisos(){
/*
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

            }
        }
*/

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {


                }
                return;
            }




        }
    }



    public void Marcador(double lat, double log) {

        LatLng position = new LatLng(lat, log);

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());

        Date date = new Date();

        //String fecha = dateFormat.format(date);

        //CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(position, 16.5f);

        if (marker != null) marker.remove();

        marker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Unidad")
                .snippet("En linea, Fecha y Hora: " + date)
                .zIndex(-1.0f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .bearing(170)
                .zoom(16.5f)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void MarcadorBanderas() {

        LatLng begin = new LatLng(19.159559,  -96.111619);

        marker = mMap.addMarker(new MarkerOptions()
                .position(begin)
                .title("Inicio")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.verde)));


        LatLng finish = new LatLng(19.184328, -96.133297);

        marker = mMap.addMarker(new MarkerOptions()
                .position(finish)
                .title("Fin")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.roja)));

    }


    private void actualizarUbicacion(Location location){


        if(location != null){

            Marcador(geoLat[i], geoLon[i]);
            //direction(location);

        }

        MarcadorBanderas();
    }
/*
    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
    List<Address> list = geocoder.getFromLocation(geoLat[i], geoLon[i], 1);

                    if (!list.isEmpty()) {

        Address address = list.get(0);

        Toast.makeText(getActivity(), "Direccion: " + address.getAddressLine(0), Toast.LENGTH_SHORT).show();
*/

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if(geoLat[i] == 0.0 && geoLon[i] == 0.0) {

                mMap.clear();

                i = 0;

            } else {

                actualizarUbicacion(location);


                i += 1;
            }
        }



        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

            Toast.makeText(getActivity(),"Ubicacion activada", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderDisabled(String s) {

            Toast.makeText(getActivity(), "Ubicacion Desactivada", Toast.LENGTH_LONG).show();

        }




    };


    public void direction(Location location) {

        List<String> prueba = Collections.singletonList("San patricio ST.");

        if (geoLat[i] != 0.0 && geoLon[i] != 0.0) {

           geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> list = null;

            try {

                list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1); //19.184328, -96.133297,

                Toast.makeText(getActivity(), "Direccion: " + geoLat[i], Toast.LENGTH_SHORT).show();

            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

        private void miUbicacion() {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);


    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
