package com.oxilo.shopsity.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.oxilo.shopsity.ApplicationController;
import com.oxilo.shopsity.R;
import com.oxilo.shopsity.activity.MapsActivity;
import com.oxilo.shopsity.logger.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ericbasendra on 15/11/15.
 */
public class ActivityUtils {

    /**
     * Finish the Activity After showing the Toast
     */
    public static void finishMyCurrentActivityAfterShowingToast(final Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppCompatActivity)mContext).finish();
            }
        }, ApplicationController.SHORT_DELAY);
    }

    public static void launcFragement(Fragment fragment,Context context){
        FragmentManager fragmentManager = ((AppCompatActivity)context).getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        MapsActivity.fragmentStack.lastElement().onPause();
        ft.hide(MapsActivity.fragmentStack.lastElement());
        MapsActivity.fragmentStack.push(fragment);
        ft.commit();


    }

    public static void launchFragementWithAnimation(Fragment secondFragment,Context context){
        Fragment currentFragment = ((AppCompatActivity)context).getFragmentManager().findFragmentById(R.id.main_content);
        FragmentManager fm =  ((AppCompatActivity)context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(context, fragmentTransaction, currentFragment, secondFragment, R.id.main_content);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.GLIDE);
        fragmentTransactionExtended.commit();
    }


    public static String GetDayFromDate(String date) {
        try {
            //Use "SimpleDateFormat" to format dates and times into a human-readable string.
            //Use "EEE" for days SHORT_FORM  && "EEEE" for FULL_FORM
            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            SimpleDateFormat sd = new SimpleDateFormat("EEEEE");

            //Pass String Date Format To Set UserDefined Date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Parse given STRING date to DATE format through df
            Date d1 = df.parse(date);

            //now format this 'd1' to string
            // for getting "DAY of week" in short form use 'sdf' to format date object 'd1' in string
            String dayOfTheWeek = sdf.format(d1);
            //For FullName of Day
            String dayOfTheWeekfull = sdf.format(d1);

            Log.d("DAY FROM DATE FORMATE:", "" + dayOfTheWeek);
            Log.d("DAY FROM DATE FORMATE:", "" + dayOfTheWeekfull);

            return dayOfTheWeek;

            //needs try/catch to handle parse Exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetDate(String date) {
        try {
            //Pass String Date Format To Set UserDefined Date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Parse given STRING date to DATE format through df
            Date d1 = df.parse(date);
            String day = (String) android.text.format.DateFormat.format("dd", d1); //20

            return day;

            //needs try/catch to handle parse Exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetYear(String date) {
        try {
            //Pass String Date Format To Set UserDefined Date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Parse given STRING date to DATE format through df
            Date d1 = df.parse(date);
            String year = (String) android.text.format.DateFormat.format("yyyy", d1); //2013

            return year;

            //needs try/catch to handle parse Exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetMonth(String date) {
        try {
            //Pass String Date Format To Set UserDefined Date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Parse given STRING date to DATE format through df
            Date d1 = df.parse(date);
            String stringMonth = (String) android.text.format.DateFormat.format("MMM", d1); //Jun

            return stringMonth;

            //needs try/catch to handle parse Exception
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetDateTime(long millisecond) {
        //Pass String Date Format To Set UserDefined Date
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a", Locale.ENGLISH);
        //Parse given STRING date to DATE format through df
        Date d1 = new Date(millisecond * 1000);
        String dateTime = df.format(d1.getTime());

        return dateTime;

    }

    public static String GetStartDate(long millisecond) {
        //Pass String Date Format To Set UserDefined Date
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        //Parse given STRING date to DATE format through df
        Date d1 = new Date(millisecond * 1000);
        String dateTime = df.format(d1.getTime());

        return dateTime;
    }

    public static String Convert24to12(String time)
    {
        String convertedTime ="";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime=displayFormat.format(date);
            System.out.println("convertedTime : "+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }

    public static void showSettingsAlert(final Context mContext){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("Location Setting");

        // Setting Dialog Message
        alertDialog.setMessage("Mobikyte would like to access the location. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void setupUI(View view,final Context mContext) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(v,mContext);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView,mContext);
            }
        }
    }

    public static void hideSoftKeyboard(View view, Context mConext) {
        InputMethodManager inputMethodManager =(InputMethodManager)mConext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
