package com.dota.pearl17;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

        SearchBox search;
        GoogleMap mMap;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            search = (SearchBox) findViewById(R.id.searchbox);
            for(int x = Constants.names.length-1; x >=0 ; x--){
                SearchResult option = new SearchResult(Constants.names[x],getResources().getDrawable(android.R.drawable.ic_menu_directions));
                search.addSearchable(option);
            }
            search.setLogoText("Lost Somewhere? ");
            search.setMenuListener(new SearchBox.MenuListener(){
                @Override
                public void onMenuClick() {
                    onBackPressed();
                }
            });
            search.setDrawerLogo(R.mipmap.ic_launcher);
            search.setLogoTextColor(Color.GRAY);
            search.setSearchListener(new SearchBox.SearchListener(){

                @Override
                public void onSearchOpened() {
                    //Use this to tint the screen
                    search.setHint("Type here for suggestions...");
                }

                @Override
                public void onSearchClosed() {
                    //Use this to un-tint the screen
                }

                @Override
                public void onSearchTermChanged(String s) {

                }

                @Override
                public void onSearch(String searchTerm) {
                    goToTheLocation(searchTerm);
                }

                @Override
                public void onResultClick(SearchResult result){
                    //React to a result being clicked
                    goToTheLocation(search);
                }

                @Override
                public void onSearchCleared() {

                }
            });
        }

        private void goToTheLocation(String searchTerm) {
            searchTerm = searchTerm.toLowerCase();
            LatLng latLng = null;
            for (int i = Constants.names.length-1; i >=0 ; i--) {
                if (searchTerm.equals(Constants.names[i].toLowerCase())) {
                    latLng = new LatLng(Constants.latitudes[i], Constants.longitudes[i]);
                }
            }
            if(latLng!=null) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(0)
                        .bearing(-90)
                        .tilt(60)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate, 1000, null);
            }else{
                Toast.makeText(getApplicationContext(),searchTerm+" not found",Toast.LENGTH_SHORT).show();
            }
        }

        private void goToTheLocation(SearchBox search) {
            String s= search.getSearchText();
            Log.e("search",s);
            s=s.toLowerCase();
            LatLng latLng =null;
            for(int i=Constants.names.length-1;i>=0;i--){
                if(s.equals(Constants.names[i].toLowerCase())){
                    latLng=new LatLng(Constants.latitudes[i],Constants.longitudes[i]);
                }
            }
            if (latLng!=null) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(20)
                        .bearing(-90)
                        .tilt(60)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate, 1000, null);
            }else{
                Toast.makeText(getApplicationContext(),s+"not found",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            IconGenerator factory = new IconGenerator(getApplicationContext());
            factory.setStyle(factory.STYLE_BLUE);
            mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style);
            mMap.setMapStyle(style);
            for (int i = 0; i < Constants.names.length; i++) {
                Bitmap icon = factory.makeIcon(Constants.names[i]);
                LatLng location = new LatLng(Constants.latitudes[i], Constants.longitudes[i]);
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon)).position(location));
            }

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Constants.latitudes[0], Constants.longitudes[0]))
                    .zoom(18)
                    .bearing(180)
                    .tilt(60)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        public static final String PREFS_NAME = "MyPrefsFile1";
        public CheckBox dontShowAgain;

        @Override
        protected void onStart() {
            AlertDialog.Builder adb = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.myDialog));
            LayoutInflater adbInflater = LayoutInflater.from(getApplicationContext());
            View eulaLayout = adbInflater.inflate(R.layout.dontshowdialog, null);
            dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
            adb.setView(eulaLayout);
            adb.setTitle("Get Directions");
            adb.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
            adb.setMessage("Tap marker to avail options at bottom right corner.");
            adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String checkBoxResult = "NOT checked";
                    if (dontShowAgain.isChecked())
                        checkBoxResult = "checked";
                    SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("skipMessage", checkBoxResult);
                    editor.apply();
                    dialog.dismiss();
                }
            });

            SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
            String skipMessage = settings.getString("skipMessage", "NOT checked");
            if (!skipMessage.equals("checked"))
                adb.show();
            super.onStart();
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_down);
        }
    }

