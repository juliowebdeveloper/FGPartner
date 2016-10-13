package julio.com.br.fgpartner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import julio.com.br.fgpartner.dao.UserDAO;
import julio.com.br.fgpartner.model.User;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    //Nome no SharedPreferences
    private final String PREF_NAME ="FGPARTNER";

    private final String MANTERCONECTADO = "MANTERCONECTADO";


   // public final String CADASTRADOTAG = "FOICADASTRADO";

    // UI references.
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.loginbutton)
    Button loginbutton;


    @Bind(R.id.login_form)
    View mLoginFormView;

    @Bind(R.id.fbloginButton)
    LoginButton fbLoginButton;

    @Bind(R.id.imgFGLogo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        if(isLogado()){
            iniciarApp(false);
        }

        setImgLogo();


        fbLoginButton.setReadPermissions("user_friends");




        /*************************LOGIN FACEBOOK**************************************************************************/





        CallbackManager callbackManager;
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




    }




    public void setImgLogo(){
        try {
            // get input stream
            InputStream ims = getAssets().open("fgpartnerlogo.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imgLogo.setImageDrawable(d);
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }
    }




    @OnClick(R.id.loginbutton)
    public void cadastroOrLogin(View v) {
        //Nao há a necessidade de pegar os Edit Texts que estiverem dentro de um TxtInputLayout

        String usuario = username.getText().toString();
        String senha = password.getText().toString();

        //cadastrarUsuario();
        UserDAO udao = new UserDAO(this);

        int result = udao.loginManager(usuario, senha);
        if (result == UserDAO.LOGIN_NOTOK){
            //Login efetuado
            Snackbar.make(findViewById(android.R.id.content), "Senha Incorreta", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }


        if(result == UserDAO.CADASTRAR){
            iniciarApp(true);
        }

        if(result == UserDAO.LOGIN_OK){
            iniciarApp(false);
        }


    }







    private void iniciarApp(boolean foiCadastrado){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("FOICADASTRADO", foiCadastrado);
        i.putExtra("Username", username.getText().toString());
        startActivity(i);
        finish();

    }






    //Sempre manterá o usuario conectado a não ser que ele deliberadamente remova-o
    private void manterConectado(){
        //Trabalhando com o SharedPreferences xml que guarda as informações numa espécie de cookie do android
        SharedPreferences settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //PREF_NAME = nome da preferencia que será buscada e pode ser usada em outros pontos, caso nao criada, o android cria
        //Somente a aplicação tem o acesso com o MODE_PRIVATE
        SharedPreferences.Editor editor = settings.edit();
        //Opção vinda do checkbox
        editor.putBoolean(MANTERCONECTADO, true);

        editor.commit();

    }



    private boolean isLogado(){
        //Pegando se o usuario ja estava logado
        SharedPreferences settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //Buscando o boolean no sharedpreferences, retornando um false como default
        return settings.getBoolean(MANTERCONECTADO, false);

    }


}

