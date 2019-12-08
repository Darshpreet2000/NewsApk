package com.example.newsapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.newsapk.ui.Browser;
import com.example.newsapk.ui.Sports;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import net.time4j.android.ApplicationStarter;
public class MainActivity extends AppCompatActivity {
    public String Country = "in";
    private AppBarConfiguration mAppBarConfiguration;
    public SharedPreferences mPreferences;
    public String sharedPrefFile =
            "com.example.android.newsapkprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationStarter.initialize(this, true); // with prefetch on background thread
        Toolbar toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.Nav_View);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.home,
                R.id.Sports, R.id.Entertainment, R.id.Technology, R.id.browser)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController);

    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("COUNTRY_KEY", Country);
        preferencesEditor.apply();
        super.onPause();
    }
    @Override
    public void onBackPressed() {

        Fragment fragment = (Fragment)
                getSupportFragmentManager().findFragmentById(R.id.browser);
       if(fragment instanceof Browser) {
           if (((Browser) fragment).canGoBack()) {
               ((Browser) fragment).goBack();
           }
       }
        else {
            super.onBackPressed();
        }
    }
}