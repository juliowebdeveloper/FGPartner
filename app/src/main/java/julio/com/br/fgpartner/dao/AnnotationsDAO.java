package julio.com.br.fgpartner.dao;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import julio.com.br.fgpartner.model.Annotation;
import julio.com.br.fgpartner.model.Game;
import julio.com.br.fgpartner.model.User;

/**
 * Created by Shido on 19/01/2016.
 */
public class AnnotationsDAO {




    private Context context;

    private Realm realm;

    public AnnotationsDAO(Context c){
        context = c;
        realm = getRealmInstance();
    }

    public Realm getRealmInstance(){
        realm = Realm.getInstance(context);
        return realm;
    }



    public int returnLastId(){
        RealmResults<Annotation> query = realm.where(Annotation.class).findAll();
        query.sort("id", Sort.DESCENDING);

        if(query.isEmpty()) {
           return 1;

        }else{
            int lastid = query.get(0).getId();
            return lastid +1;
        }

    }



    public void registerAnnotation(Annotation a){

        a.setId(returnLastId());
// Copy the object to Realm. Any further changes must happen on realmUser
        realm.beginTransaction();
        Annotation realmAnot = realm.copyToRealm(a);
        realm.commitTransaction();
    }



public RealmResults<Annotation> getAllAnnotationsByUser(){
    RealmResults<Annotation> query = realm.where(Annotation.class).findAll();

        return query;
}


    public Annotation getSingleAnnotation(int anotId){
        RealmQuery<Annotation> query = realm.where(Annotation.class).equalTo("id", anotId);
        Annotation anot = query.findFirst();
        return anot;
    }


    public void editAnnotation(Annotation a){
        realm.beginTransaction();
        Annotation realmAnot = realm.copyToRealmOrUpdate(a);
        realm.commitTransaction();
    }


    public RealmResults<Annotation> getAnnotationsByGame(String gameName){
        RealmResults<Annotation> query = realm.where(Annotation.class).contains("game.name", gameName).findAll();
        return query;


    }




}
