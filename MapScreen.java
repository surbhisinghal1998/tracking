package akaalwebsoft.com.wsmtrackerinjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetCoordinatebyDeviceIdListViewModle;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetCordinatedeviceidViewModel;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetLimitCordinateViewModel;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCoordinateByDeviceIdListmodle;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCordinateDeviceId;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetLimitedCoordinatemodle;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback {
    String id, deviceid, token, auth;
    Double lattitudd, longitude;
    private GoogleMap mMap;

    GetCordinatedeviceidViewModel getCordinatedeviceidViewModel;
    GetLimitCordinateViewModel getLimitCordinateViewModel;
    GetCoordinatebyDeviceIdListViewModle getCoordinatebyDeviceIdListViewModle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        getCordinatedeviceidViewModel = new ViewModelProvider(MapScreen.this).get(GetCordinatedeviceidViewModel.class);
        getLimitCordinateViewModel = new ViewModelProvider(MapScreen.this).get(GetLimitCordinateViewModel.class);
        getCoordinatebyDeviceIdListViewModle = new ViewModelProvider(MapScreen.this).get(GetCoordinatebyDeviceIdListViewModle.class);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getIntentdata() {
        id = getIntent().getStringExtra("id");
        deviceid = getIntent().getStringExtra("deviceid");
        token = getIntent().getStringExtra("token");
        auth = getIntent().getStringExtra("auth");

//        Log.e("id", id);
//        longitude = getIntent().getStringExtra("longitude");
//        lattitudd = getIntent().getStringExtra("latitude");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * <p>
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getIntentdata();

        getCordinatedeviceidViewModel.getCordinateDeviceIdLiveData(MapScreen.this, id, deviceid, token, auth).observe(MapScreen.this, new Observer<GetCordinateDeviceId>() {
            @Override
            public void onChanged(GetCordinateDeviceId getCordinateDeviceId) {
                if (getCordinateDeviceId.getStatuscode().equalsIgnoreCase("Success")) {
                    for (int i = 0; i < getCordinateDeviceId.getData().size(); i++) {
                        lattitudd = Double.valueOf(getCordinateDeviceId.getData().get(i).getLatitude());
                        longitude = Double.valueOf(getCordinateDeviceId.getData().get(i).getLongitude());
                        LatLng sydney = new LatLng(lattitudd, longitude);
                        if (getCordinateDeviceId.getData().get(i).getSpeed().equals("null") || getCordinateDeviceId.getData().get(i).getSpeed().equals("0")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(sydney)
                                    .title("Speed " + getCordinateDeviceId.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.stopbus)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        } else {
                            mMap.addMarker(new MarkerOptions()
                                    .position(sydney)
                                    .title("Speed " + getCordinateDeviceId.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.runbus)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    }
                }
            }
        });


        getCoordinatebyDeviceIdListViewModle.getCordinateDeviceIdLiveData(MapScreen.this, id, deviceid, token, auth).observe(this, new Observer<GetCoordinateByDeviceIdListmodle>() {
            @Override
            public void onChanged(GetCoordinateByDeviceIdListmodle getCoordinateByDeviceIdListmodle) {
                if (getCoordinateByDeviceIdListmodle.getStatuscode().equalsIgnoreCase("Success")) {
                    for (int i = 0; i < getCoordinateByDeviceIdListmodle.getData().size(); i++) {
                        lattitudd = Double.valueOf(getCoordinateByDeviceIdListmodle.getData().get(i).getLatitude());
                        longitude = Double.valueOf(getCoordinateByDeviceIdListmodle.getData().get(i).getLongitude());
                        LatLng sydney = new LatLng(lattitudd, longitude);
                        if (getCoordinateByDeviceIdListmodle.getData().get(i).getSpeed().equals("null") || getCoordinateByDeviceIdListmodle.getData().get(i).getSpeed().equals("0")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(sydney)
                                    .title("Speed " + getCoordinateByDeviceIdListmodle.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.stopbus)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        } else {
                            mMap.addMarker(new MarkerOptions()
                                    .position(sydney)
                                    .title("Speed " + getCoordinateByDeviceIdListmodle.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.runbus)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    }

                } else {
//                    Toast.makeText(MapScreen.this, getCoordinateByDeviceIdListmodle.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


//        getLimitCordinateViewModel.getCordinateDeviceIdLiveData(MapScreen.this, "1", deviceid, "10", "Jul 02, 2022", token, auth).observe(MapScreen.this, new Observer<GetLimitedCoordinatemodle>() {
//            @Override
//            public void onChanged(GetLimitedCoordinatemodle getLimitedCoordinatemodle) {
//                for (int i = 0; i < getLimitedCoordinatemodle.getData().size(); i++) {
//                    lattitudd = Double.valueOf(getLimitedCoordinatemodle.getData().get(i).getLatitude());
//                    longitude = Double.valueOf(getLimitedCoordinatemodle.getData().get(i).getLongitude());
//                    LatLng sydney = new LatLng(lattitudd, longitude);
//                    if (getLimitedCoordinatemodle.getData().get(i).getSpeed().equals("null") || getLimitedCoordinatemodle.getData().get(i).getSpeed().equals("0")) {
//                        mMap.addMarker(new MarkerOptions()
//                                .position(sydney)
//                                .title("Speed " + getLimitedCoordinatemodle.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.stopbus)));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                    } else {
//                        mMap.addMarker(new MarkerOptions()
//                                .position(sydney)
//                                .title("Speed " + getLimitedCoordinatemodle.getData().get(i).getSpeed().toString()).icon(BitmapFromVector(getApplicationContext(), R.drawable.runbus)));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                    }
//                }
//
//            }
//        });

        // Add a marker in Sydney and move the camera

    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}





