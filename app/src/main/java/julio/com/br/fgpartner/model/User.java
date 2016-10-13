package julio.com.br.fgpartner.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Shido on 18/01/2016.
 */
public class User extends RealmObject {



        @Required
        @PrimaryKey
        private String username;

        @Required
        private String password;


        private RealmList<Game> games;





    public RealmList<Game> getGames() {
        return games;
    }

    public void setGames(RealmList<Game> games) {
        this.games = games;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
