package me.iasb.myspace.feature;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import me.iasb.myspace.feature.Fragments.DatePickerFragment;
import me.iasb.myspace.feature.Utils.DateTimeUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static TextView m_dateTextView, m_daysTextView;
    private static int m_year, m_month, m_day, m_hour, m_minute;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private Button m_datePickerButton;

    public static void setDateTextView(String dateTextView) {
        MainActivity.m_dateTextView.setText(dateTextView);
    }

    public static void setYear(int year) {
        MainActivity.m_year = year;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static void setMonth(int m_month) {
        MainActivity.m_month = m_month;
    }

    public static void setDay(int m_day) {
        MainActivity.m_day = m_day;
    }

    public static void setHour(int m_hour) {
        MainActivity.m_hour = m_hour;
    }

    public static void setMinute(int m_minute) {
        MainActivity.m_minute = m_minute;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_datePickerButton = findViewById(R.id.btn_date);
        m_dateTextView = findViewById(R.id.in_date);
        m_daysTextView = findViewById(R.id.no_of_days);

        sharedPreferences = getSharedPreferences(getString(R.string.app_pref_file), MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.contains(getString(R.string.pref_yob))) {
            // Assume all other values are also saved!

            String daysString = DateTimeUtils.DateString(m_day, m_month, m_year, DateTimeUtils.FORWARD_SLASH);
            m_dateTextView.setText(daysString);
            m_daysTextView.setText(Integer.toString(DateTimeUtils.getDaysDiff(daysString)));
        }

        m_datePickerButton.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO : Create Alarm entry
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.MINUTE, 10);
                calendar.set(Calendar.SECOND, 0);
                Intent intent = new Intent(getApplicationContext(), NotificationsReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), NotificationsReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Snackbar.make(view, "Alarm set at 9AM, repeating every day!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == m_datePickerButton) {
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(this.getFragmentManager(), "Date Picker");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            m_daysTextView.setText("");
            m_dateTextView.setText("");
            editor.clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void saveData() {
        editor.putInt(getString(R.string.pref_yob), m_year);
        editor.putInt(getString(R.string.pref_mob), m_month);
        editor.putInt(getString(R.string.pref_dob), m_day);
        editor.apply();
    }

    @Override
    protected void onStop() {
/*        saveData();
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        }*/
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // Ideally it not required to save data from onDestroy as onPause will be called before it.
        // This is to avoid some rare exceptions when
        saveData();
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        saveData();
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        }
        super.onPause();
    }

    private void populateData() {
        if (sharedPreferences.contains(getString(R.string.pref_yob))) {
            // Assume all other values are also saved!
            m_year = sharedPreferences.getInt(getString(R.string.pref_yob), 2000);
            m_month = sharedPreferences.getInt(getString(R.string.pref_mob), 12);
            m_day = sharedPreferences.getInt(getString(R.string.pref_dob), 12);

            String daysString = DateTimeUtils.DateString(m_day, m_month, m_year, DateTimeUtils.FORWARD_SLASH);
            m_dateTextView.setText(daysString);
            m_daysTextView.setText(Integer.toString(DateTimeUtils.getDaysDiff(daysString)));
        }
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        populateData();
        if (BuildConfig.DEBUG) {
            // do something for a debug build
            Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
