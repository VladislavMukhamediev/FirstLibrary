package com.reactlibrary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.Manifest;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.CalendarContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.database.Cursor;
import android.accounts.Account;
import android.accounts.AccountManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Dynamic;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import android.util.Log;

public class FirstLibraryModule extends ReactContextBaseJavaModule {

    private final ReactContext reactContext;

      public FirstLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
      }

      @Override
      public String getName() {
        return "RNFirstLibrary";
      }

    @ReactMethod
    public void saveEvent(final String title, final ReadableMap details, final Promise promise) {

        if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.WRITE_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {
                ContentResolver cr = reactContext.getContentResolver();
                ContentValues values = new ContentValues();

                values.put(CalendarContract.Events.DTSTART, (long)details.getDouble("startDate"));
                values.put(CalendarContract.Events.DTEND, (long)details.getDouble("endDate"));
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, "");

                TimeZone timeZone = TimeZone.getDefault();
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

                values.put(CalendarContract.Events.CALENDAR_ID, 1);

                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                promise.resolve("done");
        }
    }

    @ReactMethod
        public void deleteEvent(String entryID, final Promise promise) {
            if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.WRITE_CALENDAR)
                == PackageManager.PERMISSION_GRANTED) {

                    ContentResolver cr = reactContext.getContentResolver();
                    int iNumRowsDeleted = 0;

                    Uri eventsUri = CalendarContract.Events.CONTENT_URI;
                    Uri eventUri = ContentUris.withAppendedId(eventsUri, Long.parseLong(entryID));
                    iNumRowsDeleted = cr.delete(eventUri, null, null);

                    promise.resolve(iNumRowsDeleted);
            }
        }

    public static final String[] FIELDS = {
        "calendar_id",
        "title",
        "description",
        "dtstart",
        "dtend",
    };

    private WritableNativeArray serializeEvents(Cursor cursor) {
        WritableNativeArray results = new WritableNativeArray();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                results.pushMap(serializeEvent(cursor));
            }

            cursor.close();
        }

        return results;
    }

    private WritableNativeMap serializeEvent(Cursor cursor) {
        WritableNativeMap event = new WritableNativeMap();

        event.putString("id", cursor.getString(0));
        event.putString("title", cursor.getString(cursor.getColumnIndex("title")));
        event.putString("description", cursor.getString(2));

        return event;
    }

    @ReactMethod
    public void getEvents(final ReadableMap details, final Promise promise) {
        if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.READ_CALENDAR)
            == PackageManager.PERMISSION_GRANTED) {

                Cursor cursor;
                        ContentResolver cr = reactContext.getContentResolver();

                        Uri.Builder uriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();
                        ContentUris.appendId(uriBuilder, (long)details.getDouble("startDate"));
                        ContentUris.appendId(uriBuilder, (long)details.getDouble("endDate"));

                        Uri uri = uriBuilder.build();

                        cursor = cr.query(uri, new String[]{
                                CalendarContract.Instances.EVENT_ID,
                                CalendarContract.Instances.TITLE,
                                CalendarContract.Instances.DESCRIPTION,
                                CalendarContract.Instances.BEGIN,
                                CalendarContract.Instances.END,
                                CalendarContract.Instances.CALENDAR_ID,
                                CalendarContract.Instances.ORIGINAL_ID,
                        }, null, null, null);


                promise.resolve(serializeEvents(cursor));
        }
    }
}
