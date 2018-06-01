package com.technoexponent.sampleandroidapp.UtilityClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import me.iwf.photopicker.PhotoPicker;


/**
 * Created by Pahari on 9/1/2016.
 */
public class StaticMethod {


    static ProgressDialog mProgressDialog;

    /*md5 converter*/
    public static String md5(String characters){

        String password = characters;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

             System.out.println("Digest(in hex format):: " + sb.toString());


            //System.out.println("Digest(in hex format):: " + hexString.toString());
            return sb.toString();
        }
        catch (Exception e)
        {
            return "NA";    }



    }
/*hide keyBoad*/
    public static void  hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        //  Hiding Keyboard for first time
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
/*Checking Internet Connection*/
    public static boolean isConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return (info != null && info.isConnected());
    }

   /* *//*initialization snackbar*//*
    public static void initializeSnackBar(CoordinatorLayout mCoordinatorLayout,
                                          Snackbar mSnackbar,
                                          TextView mTextViewSnackbar,
                                          Activity activity)
    {
        mCoordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.coordinatorLayout);
        mSnackbar = Snackbar.make(mCoordinatorLayout, "", Snackbar.LENGTH_LONG);
        View snackBarView = mSnackbar.getView();
        snackBarView.setBackgroundColor(Color.BLACK);
        mTextViewSnackbar = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        mTextViewSnackbar.setTextColor(Color.RED);
    }*/

   // logout menuuser
    public static void logout(Activity activity)
    {
        /*SharedPreferenceHelper.deleteAllSharedPreference(activity.getApplicationContext());
        Intent goLoginPage=new Intent(activity,LoginRegisterActivity.class);
        goLoginPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(goLoginPage);
        activity.finish();
        activity.overridePendingTransition( R.anim.slide_in,R.anim.slide_out);*/
    }

    public static String appInfo(Context context)
    {

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info.versionName;
    }

    public static boolean isEnableLocation(Context context)
    {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

       /* try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}*/

        return gps_enabled;

    }

    public static void showProcessDialog(Activity activity, String msg) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    public static void hideProgressDialog() {


        if(mProgressDialog!=null)
        {
            mProgressDialog=null;
        }
        else
        {
            mProgressDialog.hide();
        }
    }

   /* public static void showDialog(Activity activity, String Sms)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txt= (TextView) dialog.findViewById(R.id.txt);
        TextView txt_ok= (TextView) dialog.findViewById(R.id.txt_ok);
        txt.setText(Sms);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }*/



    public static void showAlertDialog(final Context activity, String title, String body)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                       // finish();
                        dialog.cancel();
                    }
                });
               /* .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
*/
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static String unixSecondsToDateTime(String unixtime)
    {
        long unixSeconds = Long.parseLong(unixtime);
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
      //  SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static String unixSecondsToDateTime1(String unixtime)
    {
        long unixSeconds = Long.parseLong(unixtime);
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        //  SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static long dateTimeToUnixtime(String date)
    {
        DateFormat dfm = new SimpleDateFormat("yyyy/MM/dd");
        long unixtime = 0;
        dfm.setTimeZone(TimeZone.getTimeZone("GMT-5"));//Specify your timezone
        try
        {
            unixtime = dfm.parse(date).getTime();
            unixtime=unixtime/1000;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return unixtime;
    }

    public static void showAlertDialogForForceLogout(final Activity activity)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle("Authentication Problem!");

        // set dialog message
        alertDialogBuilder
                .setMessage("You have allready login with another device.If not,then change your Pin!")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout(activity);
                        dialog.cancel();
                    }
                });
               /* .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });*/
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }




    /**
     * Decodes an encoded path string into a sequence of LatLngs.
     */
    public static List<LatLng> decode(final String encodedPath) {
        int len = encodedPath.length();

        // For speed we preallocate to an upper bound on the final length, then
        // truncate the array before returning.
        final List<LatLng> path = new ArrayList<LatLng>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
    }
    public static ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }


    public static BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    public static void setLocale(Context context, String lang) {

        Locale myLocale;
        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            conf.setLocale(myLocale);
        }
        else{
            conf.locale = myLocale;
        }


        res.updateConfiguration(conf, dm);


    }

    public static String convertFormateDateAndTime(String s) {


        String date_s = "2011-01-18 00:00:00.0";

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dt.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // *** same for the format String below
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy MMM dd, HH:mm");
        System.out.println(dt1.format(date));
        //2017-07-28 14:13:36


        return dt1.format(date);
    }


   public static String getCurrentDefaultLocaleStr(Context context) {
        Locale locale = null;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
       {

           locale = context.getResources().getConfiguration().getLocales().get(0);
       }
       else{
           locale = context.getResources().getConfiguration().locale;
       }
        return locale.getDefault().toString();
    }


    public static void PhotoPick(Context context, Fragment fragment, int n) {
        PhotoPicker.builder()
                .setPhotoCount(n)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(context, fragment);

    }
}
