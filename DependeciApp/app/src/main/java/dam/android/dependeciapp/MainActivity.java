package dam.android.dependeciapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dam.android.dependeciapp.AsyncTasks.LanzaLlamada;
import dam.android.dependeciapp.Controladores.Conexion;
import dam.android.dependeciapp.Controladores.SQLite.DependenciaDBManager;
import dam.android.dependeciapp.Fragments.BotonFragment;
import dam.android.dependeciapp.Fragments.MapFragment;
import dam.android.dependeciapp.Fragments.RecordatorioDetalleFragment;
import dam.android.dependeciapp.Fragments.RecordatorioFragment;
import dam.android.dependeciapp.Pojo.Usuario;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton fabGigante;
    private Usuario user;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private FABToolbarLayout fabToolbar;
    private MapFragment mapFragment;
    private RecordatorioFragment recordatorioFragment;
    private TabLayout tabLayout;
    private RelativeLayout fabContiner;
    private FrameLayout recordatoriosFrame;
    private BotonFragment botonFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        if (i != null)
            user = (Usuario) i.getSerializableExtra("user");
        setComunUI();
    }

    private void setComunUI() {
        fabContiner = findViewById(R.id.fabtoolbar_container);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView tvNombre = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNombre);
        tvNombre.setText(user.getNombre() + " " + user.getApellidos());
        fabGigante = (FloatingActionButton) findViewById(R.id.fab_gigante);
        fabGigante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                enviaAviso(view);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaAviso(view);

            }
        });
        fabToolbar = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        fabGigante.hide();
        mViewPager = (ViewPager) findViewById(R.id.tabsContainer);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            //Se ejecuta cada vez que cambiamos de tab
            public void onTabSelected(TabLayout.Tab tab) {
                int posicion = tab.getPosition();
                switch (posicion) {
                    case 1:
                        fab.show();
                        break;
                    case 2:
                        View screenView = findViewById(R.id.drawer_layout);
                        float centrex = screenView.getWidth() / 3;
                        float centreY = screenView.getHeight() / 3;
                        fab.animate().scaleX(4).scaleY(4).translationX(-centrex).translationY(-centreY + 50).setDuration(200);
                        fabGigante.show();
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int posicion = tab.getPosition();
                switch (posicion) {
                    case 0:
                        cierraRecordatorioDetalle(true);
                        appBarLayout.setExpanded(true);
                        break;
                    case 2:
                        fabGigante.hide();
                        fab.show();
                        fab.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(500);
                        fabContiner.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

    }



    private void enviaAviso(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        if (user != null && actNetInfo != null && actNetInfo.isConnected()) {
            LatLng ubicacion = mapFragment.getMyLastLocation();
            double lat = 0;
            double lon = 0;
            if (ubicacion != null) {
                lat = ubicacion.latitude;
                lon = ubicacion.longitude;
            }
            LanzaLlamada llamada = new LanzaLlamada(view, getApplicationContext());
            llamada.execute(user.getIdPersona() + "", lon + "", lat + "");
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_conexion_aviso, Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para cerrar el RecordatorioDetalleFragment que pueda haber abierto
    //El boleano indica si ha de esconder el fab o no
    private void cierraRecordatorioDetalle(boolean escondeFab) {
        List<Fragment> listFragment = getSupportFragmentManager().getFragments();
        //Seleccionamos el fragment que sea RecordatorioDetalleFragment y lo eliminamos
        for (Fragment fragment : listFragment) {
            if (fragment instanceof RecordatorioDetalleFragment) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                fabToolbar.hide();
                if (escondeFab)
                    fab.hide();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            //Si el FAB esta convertido en toolbar cerramos el FrameLayout
        } else if (fabToolbar.isToolbar()) {
            cierraRecordatorioDetalle(false);

        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    //On selected del menu del Navigation View
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_datosPersonales:
                Intent i = new Intent(this, UsuarioActivity.class);
                i.putExtra("Usuario", user);
                startActivity(i);
                break;
            case R.id.nav_refrescar:
                recordatorioFragment.refrescaAvisos();
                break;
            case R.id.nav_cerrarSesion:
                cerrarSesion();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        user = null;
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("CIERRA_SESION", true);
        startActivity(i);
        DependenciaDBManager.UsuarioDBManager db = new DependenciaDBManager.UsuarioDBManager(getApplicationContext());
        db.emptyTable();
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", user);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = (Usuario) savedInstanceState.getSerializable("user");
        fab.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(500);
        recordatorioFragment = RecordatorioFragment.newInstance(user.getIdPersona());
        mapFragment = new MapFragment();
        botonFragment = new BotonFragment();
        int i = tabLayout.getSelectedTabPosition();
        if (i == 2)
            fabContiner.setVisibility(View.INVISIBLE);


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //Le pasamos el menu para poder hacerlo visible al abrir el fragmento de Recordatorio Detalle
                    recordatorioFragment = RecordatorioFragment.newInstance(user.getIdPersona());
                    return recordatorioFragment;
                case 1:
                    mapFragment = new MapFragment();
                    return mapFragment;
                case 2:
                    botonFragment = new BotonFragment();
                    return botonFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}