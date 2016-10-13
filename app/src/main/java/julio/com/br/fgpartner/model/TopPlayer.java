package julio.com.br.fgpartner.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Shido on 18/01/2016.
 */
public class TopPlayer extends RealmObject {


    @PrimaryKey
    private String name;


    private Game game;


    private Character character;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
