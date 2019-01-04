package white.zac.hw4;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<UFOPosition> oldPositions;
    private List<UFOPosition> newPositions;
    private BitmapDescriptor ufo;
    LatLng startLatLng = new LatLng(38.9073, -77.0365);
    LatLngBounds mapBounds = new LatLngBounds(startLatLng, startLatLng);

    private List<Marker> markers;
    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ufo = BitmapDescriptorFactory.fromResource(R.drawable.red_ufo);

        markers = new ArrayList<>();
        oldPositions = new ArrayList<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private UFOReporter.Stub reporter = new UFOReporter.Stub() {

        @Override
        public void report(List<UFOPosition> positions) throws RemoteException {

            newPositions = positions;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //updating the map
                    for(int i = 0; i < newPositions.size(); i++) {
                        UFOPosition ufoPosition = newPositions.get(i);
                        LatLng newLatLng = new LatLng(ufoPosition.getLat(), ufoPosition.getLon());

                        //ship has already been reported
                        if(alreadyReported(ufoPosition)){

                            int shipNumber = ufoPosition.getShipNumber();

                            //find the ship's old position
                            UFOPosition oldPos = new UFOPosition(); // attributes are null
                            for(UFOPosition old: oldPositions){
                                if(old.getShipNumber() == shipNumber){
                                    oldPos = old;
                                }
                            }

                            //attributes shouldn't be null by now
                            LatLng oldLatLng = new LatLng(oldPos.getLat(), oldPos.getLon());

                            //draw polyline from old location to new location
                            line = mMap.addPolyline(new PolylineOptions()
                                    .add(oldLatLng, newLatLng)
                                    .color(getResources().getColor(R.color.polyLineColor))
                                    .width(5)
                            );

                        }
                        //update map bounds
                        mapBounds = mapBounds.including(newLatLng);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds, (int) getResources().getDimension(R.dimen.mapPadding)));

                    }
                    //remove all markers
                    for (Marker marker : markers) {
                        marker.remove();
                    }

                    //add markers from new list
                    for(UFOPosition ufop: newPositions){
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                    .anchor(0.5f, 0.5f)
                                    .icon(ufo)
                                    .title("Ship #" + ufop.getShipNumber())
                                    .snippet("Lat/Lng: " + ufop.getLat() + ", " + ufop.getLon())
                                    .position(new LatLng(ufop.getLat(), ufop.getLon()))
                            );
                        markers.add(marker);
                    }

                    //set oldPositions to new positions
                    oldPositions.clear();
                    oldPositions.addAll(newPositions);
                }

            });

        }

    };
    //check to see if new ufoPosition has already been reported
    private boolean alreadyReported(UFOPosition ufoPosition) {
        for(int i = 0; i < oldPositions.size(); i++){
            if(oldPositions.get(i).getShipNumber() == ufoPosition.getShipNumber()){
                return true;
            }
        }
        return false;
    }


    private AlienService2 alienService2;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            alienService2 = AlienService2.Stub.asInterface(service);
            try {
                alienService2.add(reporter);

            } catch (RemoteException e) {
                Log.e(getClass().getSimpleName(), "Cannot add reporter", e);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            alienService2 = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent();
        intent.setClassName("white.zac.hw4", "white.zac.hw4.AlienService2Impl");
        System.out.println("about to bind......");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent(this, AlienService2Impl.class);
        stopService(intent);
        super.onStop();
    }

}


