package julio.com.br.fgpartner.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Shido on 18/01/2016.
 */
public class Game extends RealmObject {

    @PrimaryKey
    private String name;


    private RealmList<TopPlayer> topplayers;


    private RealmList<Character> characters;

    private String logo;





    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<TopPlayer> getTopplayers() {
        return topplayers;
    }

    public void setTopplayers(RealmList<TopPlayer> topplayers) {
        this.topplayers = topplayers;
    }

    public RealmList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(RealmList<Character> characters) {
        this.characters = characters;
    }
}
