package android.sigaamobile.sigaamobile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button sigaa;
    Button provas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sigaa = (Button) findViewById(R.id.sigaa);
        sigaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openInCustomTab("file:///android_asset/sigaa.html");
                openInCustomTab("https://lua.creactive.com.br/");
            }
        });

        provas = (Button) findViewById(R.id.provas);
        provas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, ProvasActivity.class);
                startActivity(it);

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_sigaa) {

             openInCustomTab("https://lua.creactive.com.br/");

         } else if (id == R.id.nav_provas) {

             startActivity( new Intent(this,ProvasActivity.class));

        } else if (id == R.id.nav_sobre) {

             startActivity( new Intent(this,SobreActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Código para que as customtabs funcionem. Vídeo: https://www.youtube.com/watch?v=YeTfOTUxOv4
    private void openInCustomTab(String url){



        Uri websiteUri;
        websiteUri = Uri.parse(url);


        CustomTabsIntent.Builder customtabintent = new CustomTabsIntent.Builder();
        customtabintent.setToolbarColor(Color.parseColor("#ffa000"));
        customtabintent.setShowTitle(true);


        if(chromeInstalled()){
            customtabintent.build().intent.setPackage("com.android.chrome");

        }

        customtabintent.build().launchUrl(MainActivity.this, websiteUri);


    }


    private boolean chromeInstalled() {
        try {

            getPackageManager().getPackageInfo("com.android.chrome", 0);
            return true;

        } catch (Exception e) {
            //Toast.makeText(this, "O APP Funciona melhor com o Google Chrome intalado", Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
