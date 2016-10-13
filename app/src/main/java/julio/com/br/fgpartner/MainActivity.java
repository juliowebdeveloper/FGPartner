package julio.com.br.fgpartner;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.dao.UserDAO;
import julio.com.br.fgpartner.fragment.AnnotationFragment;
import julio.com.br.fgpartner.fragment.AnnotationsListFragment;
import julio.com.br.fgpartner.fragment.EditAnnotationDialog;
import julio.com.br.fgpartner.fragment.GameListFragment;
import julio.com.br.fgpartner.fragment.MainFragment;
import julio.com.br.fgpartner.fragment.UserGamesFragment;
import julio.com.br.fgpartner.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private User user;

    private UserDAO udao;



    public static final String PREFERENCESTAG = "FGPARTNER";

    public static final String USERTAG = "USERTAG";

    public static final String FIRSTRUN = "FIRSTRUN";

    public static String USERNAME;




    public void switchContent(int id, android.app.Fragment fragment, Bundle b) {

        //Tomar cuidado com o FragmentManager, há diferença entre as versoes appV4 e a android.app.Fragment
        //Log.i("bundle game", b.getString("selectedGame"));
        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(id, fragment).commit();


    }




    public void showEditAnnotationsDialog(Bundle b) {
        android.app.FragmentManager fm = getFragmentManager();
        EditAnnotationDialog editAnotDialog = new EditAnnotationDialog();
        editAnotDialog.setArguments(b);
        editAnotDialog.show(fm, "fragment_edit_annotation");
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ButterKnife.bind(this);


        //Pegando o username para preencher as informações.

        String username = null;
        username = getIntent().getStringExtra("Username");
        SharedPreferences sharedpreferences = getSharedPreferences(PREFERENCESTAG, Context.MODE_PRIVATE);


        udao = new UserDAO(this);
        if(username != null){

            user = udao.retornarInfosUsuario(username);

            setUserOnSession();
            showWelcomeText();
            USERNAME = user.getUsername();

            //Fazendo verificação se essa é a primeira vez que o usuário acessou o app
            checkFirstRun();
        }else{
            user = udao.retornarInfosUsuario(sharedpreferences.getString(USERTAG,""));

        }




        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView usernameInfo = (TextView) hView.findViewById(R.id.usernameInfo);


        usernameInfo.setText(sharedpreferences.getString(USERTAG,""));


        //Trazendo o Fragment Inicial
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.mainFrameLayout, new MainFragment()).commit();


    }


    public void setUserOnSession() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREFERENCESTAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERTAG, user.getUsername());
        editor.commit();
    }




    public void checkFirstRun(){
        SharedPreferences sharedpreferences =  getSharedPreferences(PREFERENCESTAG , MODE_PRIVATE);

       if (sharedpreferences.getBoolean(FIRSTRUN, true)) {
           Log.i("First Run", "first run");
            //Aqui ficarão todos os metodos que preencherão o banco com valores.
            GameDAO gdao = new GameDAO(this);
           gdao.insertGamesAndCharacters();
           gdao.insertAllGames();
           gdao.insertTopPlayers();
            sharedpreferences.edit().putBoolean(FIRSTRUN, false).commit();
        }
    }


    public void showWelcomeText() {
        //Verificando se o usuário foi cadastrado para exibir um texto de boas vindas
        boolean foiCadastrado = getIntent().getBooleanExtra("CADASTRADOTAG", false);
        if (foiCadastrado) {
            Snackbar.make(findViewById(android.R.id.content), "Bem vindo ao FG Partner", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Bem vindo ao FG Partner", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_games) {
            GameListFragment gameListFragment = new GameListFragment();
            Bundle b = new Bundle();
            fm.beginTransaction().replace(R.id.mainFrameLayout, new UserGamesFragment()).commit();


        } else if (id == R.id.nav_annotationcreate) {

            fm.beginTransaction().replace(R.id.mainFrameLayout, new AnnotationFragment()).commit();

        } else if (id == R.id.nav_annotationlist) {
            fm.beginTransaction().replace(R.id.mainFrameLayout, new AnnotationsListFragment()).commit();

        } else if (id == R.id.nav_allgames) {

            fm.beginTransaction().replace(R.id.mainFrameLayout, new GameListFragment()).commit();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
