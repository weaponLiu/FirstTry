package com.example.lwp.blue.DeviceActivity;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SaveDeviceNameService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.lwp.blue.action.FOO";
    private static final String ACTION_BAZ = "com.example.lwp.blue.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.lwp.blue.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.lwp.blue.extra.PARAM2";

    public SaveDeviceNameService() {
        super("SaveDeviceNameService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SaveDeviceNameService.class);
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
        Intent intent = new Intent(context, SaveDeviceNameService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final Intent intent1 = intent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> allDeviceName = intent1.getStringArrayListExtra("allDeviceNameList");
                SharedPreferences.Editor editor = getSharedPreferences(
                        "data",MODE_PRIVATE
                ).edit();
                Set<String> nameSet = new TreeSet<String>();
                Iterator<String> iterator = allDeviceName.iterator();
                while (iterator.hasNext()){
                    nameSet.add(iterator.next());
                }
                editor.putStringSet("NameListOnService",nameSet);
                editor.apply();
                stopSelf();
            }
        }).start();
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
