package julio.com.br.fgpartner.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.login.widget.LoginButton;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import julio.com.br.fgpartner.MainActivity;
import julio.com.br.fgpartner.model.Game;
import julio.com.br.fgpartner.model.User;

/**
 * Created by Shido on 18/01/2016.
 */
public class UserDAO {


    private String sessionUsername;

    //Constantes de login
    public static final int LOGIN_OK = 1;
    public static final int LOGIN_NOTOK = 2;
    public static final int CADASTRAR = 3;


    private Context context;

    private Realm realm;

    public UserDAO(Context c){
        context = c;
        realm = getRealmInstance();
        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFERENCESTAG, context.MODE_PRIVATE);
        sessionUsername =  settings.getString(MainActivity.USERTAG, "DEFAULT");

    }

    public Realm getRealmInstance(){
        realm = Realm.getInstance(context);
        return realm;
    }


    public void cadastrarUsuario(String username, String password){
        realm.beginTransaction();
        User u = realm.createObject(User.class);
        u.setUsername(username);
        u.setPassword(password);
        realm.commitTransaction();


    }


    public int loginManager(String username, String password){

        if(retornarLogin(username, password)){
            //Se o retornar Login for true então pode-se checar a senha
            if(checkPassword(username, password)){
                //Se loginok for true quer dizer que errou a senha
                return UserDAO.LOGIN_OK;
            }else{
                //Errou a senha
                return UserDAO.LOGIN_NOTOK;
            }

        }else{
            //Se retornar false então cadastra-se o usuario
            cadastrarUsuario(username, password);
            return UserDAO.CADASTRAR;
        }



    }

    public boolean checkPassword(String username, String password) {
        boolean loginok = false;

            //Checar se o password ta certo
            RealmQuery<User> query = realm.where(User.class);
            User result1 = query.findFirst();

            query.equalTo("username", username);

                if (result1.getPassword().equals(password)) {
                    Log.i("Password", "LOGIN OK");
                    loginok = true;
                } else {
                    Log.i("Password", "LOGIN não OK");
                    loginok = false;
                }

        return loginok;

    }




    public boolean retornarLogin(String username, String password) {
        boolean canCheckPassword = false;
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);

        RealmResults<User> result1 = query.findAll();
        if (result1.size()>0){
            canCheckPassword = true;
        }else{
            //Se não achar nenhum resultado pode cadastrar com aquele username
            Log.i("LOGIN NOT FOUND", "CADASTRAR");
        }
        return canCheckPassword;
    }








    public User retornarInfosUsuario(String username){
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);
        User user = query.findFirst();
        return user;
    }


    public List<Game> returnAllUserGames(){

        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", sessionUsername);
        User u = query.findFirst();
        return u.getGames();

    }


}
