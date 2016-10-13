package julio.com.br.fgpartner.dao;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by Shido on 18/01/2016.
 */
public class CharacterDAO {


    private Context context;

    private Realm realm;

    public CharacterDAO(Context c){
        context = c;
        realm = getRealmInstance();
    }

    public Realm getRealmInstance(){
        realm = Realm.getInstance(context);
        return realm;
    }






}
