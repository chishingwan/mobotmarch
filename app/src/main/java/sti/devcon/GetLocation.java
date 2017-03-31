package sti.devcon;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetLocation extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        ,LocationListener {

    SQLiteDatabase db;

    Cursor c;
    Geocoder geocoder;
    List<Address> addresses;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest    ;
    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;
    LatLng coord;
    ArrayList<String> msgs;
    ArrayList<String> pnumbers;
    ArrayList<String> pname;
    String phoneNo;
    SmsManager smsManager;
    Button btn;

    String address;
    String city;
    String zip;
    String state;
    String country;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

        }
        catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            showMessage("error",errors.toString());
        }
        finally {

        }
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToContact();
            }
        });
    }


    public void sendToContact(){
            smsManager =SmsManager.getDefault();
            try {
                geocoder = new Geocoder(this, Locale.getDefault());
                getCoordinates();
            }
            catch(Exception e){
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                showMessage("error",errors.toString());
            }
            finally {
                try {
                    addresses = geocoder.getFromLocation(coord.getLatitude(), coord.getLongitude(), 1);
                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    zip = addresses.get(0).getPostalCode();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    String msg = address + ", " + city + ", " + zip + ", " + state + ", " + country;
                    msg += coord.getLatitude() + ", " + coord.getLongitude();
                    msgs = smsManager.divideMessage(msg);
                    showMessage("msg", msg);
                    send();
                }
                catch(Exception e){
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    showMessage("error",errors.toString());
                }
                finally {
                    new sendSMS().execute();
                }
            }
    }

    public void send(){
        try {
            db = openOrCreateDatabase("contactDB", Context.MODE_PRIVATE, null);
            db.execSQL("Create table if not exists contactsTbl (phoneNo varchar,contactName varchar);");
            c = db.rawQuery("Select * from contactsTbl;", null);
            if (c==null) {
                db.execSQL("insert into contactsTbl values('09967185309','NG');");
                db.execSQL("insert into contactsTbl values('09758883369','NG');");
            }
            Cursor c2 = db.rawQuery("Select * from contactsTbl",null);
            String x="";

            c2.moveToFirst();
            pnumbers = new ArrayList<>();

            while (!c2.isAfterLast()) {
                pnumbers.add(c2.getString(0));

                c2.moveToNext();
            }
            c2.close();
        }
        catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            showMessage("error",errors.toString());
        }
        c.close();
        db.close();
    }

    public void getCoordinates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                RationaleDialog.newInstance(MY_PERMISSION_ACCESS_FINE_LOCATION, true)
                        .show(this.getSupportFragmentManager(), "dialog");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }

        }
        startLocationUpdates();
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null) {
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            coord = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }


    public void onConnected(Bundle dataBundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                RationaleDialog.newInstance(MY_PERMISSION_ACCESS_FINE_LOCATION, true)
                        .show(this.getSupportFragmentManager(), "dialog");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }

        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

        coord = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static class RationaleDialog extends DialogFragment {

        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

        private boolean mFinishActivity = false;

        public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
            RationaleDialog dialog = new RationaleDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_rationale_location)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // After click on Ok, request the permission.
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    requestCode);
                            // Do not finish the Activity while requesting permission.
                            mFinishActivity = false;
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (mFinishActivity) {
                Toast.makeText(getActivity(),
                        R.string.permission_required_toast,
                        Toast.LENGTH_SHORT)
                        .show();
                getActivity().finish();
            }
        }
    }


    public void showMessage(String title,String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    class sendSMS extends AsyncTask<Void,Integer,Integer> {
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected  void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
        }

        protected Integer doInBackground (Void... params){
            smsManager = SmsManager.getDefault();
            final int numSend =pnumbers.size();

            try {
               for(int i=0;i<numSend;i++){
                    try {
                        Thread.sleep(150);
                    }
                    catch(Exception e){

                    }
                    finally {
                        phoneNo = pnumbers.get(i);

                        smsManager.sendMultipartTextMessage(phoneNo, null, msgs, null, null);
                        showToast(phoneNo);

                    }
               }
            } catch (Exception e) {
                final StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sent To" + errors.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

    public void showToast(String pn)
    {
        final String phonenum = pn;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {

                Toast.makeText(getApplicationContext(), "Sent To" + phonenum,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
