package com.example.lwp.blue.baseActivity;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.lwp.blue.R;
import com.example.lwp.blue.locaActivity.LocaFragment;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocaHistoryService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.lwp.blue.baseActivity.action.FOO";
    private static final String ACTION_BAZ = "com.example.lwp.blue.baseActivity.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.lwp.blue.baseActivity.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.lwp.blue.baseActivity.extra.PARAM2";

    public LocaHistoryService() {
        super("LocaHistoryService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocaHistoryService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, LocaHistoryService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    public LocationClient mLocationClient;
    @Override
    protected void onHandleIntent(Intent intent) {
      /*  if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }*/
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
       mLocationClient.start();
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   StringBuilder currentPosition = new StringBuilder();
                   currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("  ")
                           .append("经线：").append(bdLocation.getLongitude()).append("\n");
                   currentPosition.append(bdLocation.getCountry()).append(bdLocation.getProvince())
                           .append(bdLocation.getCity()).append(bdLocation.getDistrict())
                           .append(bdLocation.getStreet()).append("\n");
                   SharedPreferences.Editor editor = getSharedPreferences(
                           "locaHistoryData",MODE_PRIVATE
                   ).edit();
                   editor.putString("historyData",currentPosition.toString());
                   editor.apply();
                 //  stopSelf();
               }
           }).start();
        }
    }






    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
