package com.example.marko.tester;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AddressActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.TESTER.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
    }

    public void onTextBoxClicked(View view){
        EditText target = (EditText) findViewById(R.id.editText);
        Button tButton = (Button) findViewById(R.id.textButton);
        TextView textBox = (TextView) findViewById(R.id.textt);
        TextView textBox2 = (TextView) findViewById(R.id.voiceText);

        if(target.getVisibility() == View.VISIBLE && tButton.getVisibility() == View.VISIBLE){
            target.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
        }else {
            target.setVisibility(View.VISIBLE);
            tButton.setVisibility(View.VISIBLE);
            textBox.setVisibility(View.INVISIBLE);
            textBox2.setVisibility(View.INVISIBLE);
        }
    }

    public void onPlainTextClicked(View view){
        EditText target = (EditText) findViewById(R.id.editText);
        Button tButton = (Button) findViewById(R.id.textButton);
        TextView textBox = (TextView) findViewById(R.id.textt);
        TextView textBox2 = (TextView) findViewById(R.id.voiceText);

        if(textBox.getVisibility() == View.VISIBLE){
            textBox.setVisibility(View.INVISIBLE);
        }else {
            target.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
            textBox2.setVisibility(View.INVISIBLE);
            textBox.setVisibility(View.VISIBLE);
        }
    }

    public void onVoiceSelected(View view) {
        EditText target = (EditText) findViewById(R.id.editText);
        Button tButton = (Button) findViewById(R.id.textButton);
        TextView textBox = (TextView) findViewById(R.id.textt);
        TextView textBox2 = (TextView) findViewById(R.id.voiceText);

        if(textBox2.getVisibility() == View.VISIBLE){
            textBox2.setVisibility(View.INVISIBLE);
        }else {
            target.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
            textBox.setVisibility(View.INVISIBLE);
            textBox2.setVisibility(View.VISIBLE);
        }
    }

    public void setDestination(View view){ // CHANGING THIS METHOD!!! ******************************************************************************************************************************************
        Intent intent = getIntent(); //is this needed??
        //String origin = intent.getStringExtra(MapsActivity.EXTRA_LOCATION);
        String origin = "43.7594986,-79.3094751";
        String route;
        String userDest;
        //LatLng userDest;
        final String[] buildings = {"ARC", "BTS", "BKS", "CUE", "CUI", "MON", "COP", "EPH", "ENG", "CED", "HEI", "ILC", "JOR", "KHE", "KHN", "KHS", "KHW", "LIB", "MRS", "MAC", "MER", "OKF", "OAK", "PIT", "POD", "RAC", "GER", "RCC", "RIC", "SHE", "IMA", "SID", "SCC", "SLC", "TRS", "VIC", "DSQ"};
        EditText editText = (EditText) findViewById(R.id.editText);
        String loc = editText.getText().toString(); //get the address they inputted
        boolean found = false;

        for(int looper = 0; looper < buildings.length; looper ++){ //check the available 3-digit codes
            if(loc.equalsIgnoreCase(buildings[looper])){
                //call the helper method
                userDest = getCoords(looper); //don't forget to change the return type to LatLng
                route = "https://www.google.com/maps/dir/?api=1&origin=" + origin + "&destination=" + userDest +"&travelmode=walking"; //this will need to be changed
                getRoute(view, route);

                found = true;
                break; //don't really need this, but no point in going through the entire loop
            }
        }

        if(!found) { //goes into this if it wasn't a 3-digit code
            //Location inputLoc = null; //probs don't need this
            Locale canada = new Locale("en", "CA");
            Geocoder convString = new Geocoder(this, canada);

            try{
                //get only one result for the inputted address
                List<Address> theResults = convString.getFromLocationName(loc, 10);

                if(theResults.size() == 0 ){ //something went wrong
                    Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_LONG).show();
                }else{ //here, put what needs to get done with the thing
                    for(Iterator<Address> iter = theResults.iterator(); iter.hasNext();){
                        Address holder = iter.next();
                        double currLng = holder.getLatitude();
                        double currLat = holder.getLongitude();


                        if(currLng < 43.67 && currLng > 43.64 && currLat > -79.39 && currLat < -79.36){ // it is within the box
                            userDest = holder.getLatitude() + "," + holder.getLongitude();
                            //userDest = new LatLng(currLng, currLat);
                            route = "https://www.google.com/maps/dir/?api=1&origin=" + origin + "&destination=" + userDest +"&travelmode=walking";
                            getRoute(view, route);

                            found = true;
                        }
                    }

                    if(!found){
                        Toast.makeText(getApplicationContext(), "Please try a different address", Toast.LENGTH_LONG).show();
                    }

                }
            }catch(IOException wrong){
                Toast.makeText(getApplicationContext(), "Please try a different address", Toast.LENGTH_LONG).show();
            }catch(IllegalArgumentException wrongAgain){
                Toast.makeText(getApplicationContext(), "Please try a different address", Toast.LENGTH_LONG).show();
            }
        }

    }

    private String getCoords(int marker){ //ADDED THIS METHOD ******************************************************************************************************************************************************
        if(marker == 0){
            return "43.659324,-79.37829899";
            //return new LatLng(43.659324, -79.37829899); //this is for when a LatLng is used instead of a string for the url
        }else if( marker == 1 ){
            return "43.6534366,-79.3823148";
            //return new LatLng(43.6534366, -79.3823148);
        }else if( marker == 2 ){
            return "43.6574725,-79.3802996";
            //return new LatLng(43.6574725, -79.3802996);
        }else if( marker == 3 ){
            return "43.6575548,-79.3768288";
            //return new LatLng(43.6575548, -79.3768288);
        }else if( marker == 4 ){
            return "43.6597855,-79.3795625";
            //return new LatLng(43.6597855, -79.3795625);
        }else if( marker == 5 ){
            return "43.6596676,-79.3782962";
            //return new LatLng(43.6596676, -79.3782962);
        }else if( marker == 6 ){
            return "43.6601442,-79.3767489";
            //return new LatLng(43.6601442, -79.3767489);
        }else if( marker == 7 ){
            return "43.6597097,-79.3775799";
            //return new LatLng(43.6597097, -79.3775799);
        }else if( marker == 8 ){
            return "43.6575655,-79.3772181";
            //return new LatLng(43.6575655, -79.3772181);
        }else if( marker == 9 ){
            return "43.6571890,-79.3797410";
            //return new LatLng(43.6571890, -79.3797410);
        }else if( marker == 10 || marker == 11 ){
            return "43.657607,-79.37870299";
            //return new LatLng(43.657607, -79.37870299);
        }else if( marker == 12 ){
            return "43.658478,-79.3808204";
            //return new LatLng(43.658478, -79.3808204);
        }else if( marker == 13 || marker == 14 || marker == 15 || marker == 16 ){
            return "43.658157,-79.37879659";
            //return new LatLng(43.658157, -79.37879659);
        }else if( marker == 17 ){
            return "43.6578767,-79.3804561";
            //return new LatLng(43.6578767, -79.3804561);
        }else if( marker == 18 ){
            return "43.6599274,-79.3886569";
            //return new LatLng(43.6599274, -79.3886569);
        }else if( marker == 19 ){
            return "43.6619296,-79.3802844";
            //return new LatLng(43.6619296, -79.3802844);
        }else if( marker == 20 || marker == 21 ){
            return "43.6577026,-79.3769050";
            //return new LatLng(43.6577026, -79.3769050);
        }else if( marker == 22 ){
            return "43.6579083,-79.3780668";
            //return new LatLng(43.6579083, -79.3780668);
        }else if( marker == 23 ){
            return "43.6592819,-79.3768130";
            //return new LatLng(43.6592819, -79.3768130);
        }else if( marker == 24 ){
            return "43.6578767,-79.3804561";
            //return new LatLng(43.6578767, -79.3804561);
        }else if( marker == 25 ){
            return "43.6580425,-79.3793415";
            //return new LatLng(43.6580425, -79.3793415);
        }else if( marker == 26 ){
            return "43.6600657,-79.3765157";
            //return new LatLng(43.6600657, -79.3765157);
        }else if( marker == 27 ){
            return "43.6587445,-79.3769299";
            //return new LatLng(43.6587445, -79.3769299);
        }else if( marker == 28 ){
            return "43.6577476,-79.3791835";
            //return new LatLng(43.6577476, -79.3791835);
        }else if( marker == 29 ){
            return "43.6600414,-79.3771506";
            //return new LatLng(43.6600414, -79.3771506);
        }else if( marker == 30 ){
            return "43.6577476,-79.3791835";
            //return new LatLng(43.6577476, -79.3791835);
        }else if( marker == 31 ){
            return "43.6574807,-79.3778820";
            //return new LatLng(43.6574807, -79.3778820);
        }else if( marker == 32 ){
            return "43.657827,-79.37844719";
            //return new LatLng(43.657827, -79.37844719);
        }else if( marker == 33 ){
            return "43.6577562,-79.3811627";
            //return new LatLng(43.6577562, -79.3811627);
        }else if( marker == 34 ){
            return "43.6555569,-79.3833209";
            //return new LatLng(43.6555569, -79.3833209);
        }else if( marker == 35 ){
            return "43.6569215,-79.3795169";
            //return new LatLng(43.6569215, -79.3795169);
        }else{
            return "43.6567455,-79.3804840";
            //return new LatLng(43.6567455, -79.3804840);
        }
    }

    public void getRoute(View view, String url){
        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //startActivity(intent);

        setContentView(R.layout.activity_web);
        WebView wv = findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);

        wv.setWebViewClient(new myWebClient());
        wv.loadUrl(url);
    }


    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

    }



}
