package julio.com.br.fgpartner.dao;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import julio.com.br.fgpartner.model.*;
import julio.com.br.fgpartner.model.Character;

/**
 * Created by Shido on 18/01/2016.
 */
public class GameDAO {


        private Context context;

        private Realm realm;

        public GameDAO(Context c) {
            context = c;
            realm = getRealmInstance();
        }

        public Realm getRealmInstance() {
            realm = Realm.getInstance(context);
            return realm;
        }



        //Metodo teste : Só será inserido "na hora da isntalação"
        public void insertGamesAndCharacters() {
            realm.beginTransaction();
            // TopPlayer ogawa = realm.createObject(TopPlayer.class);
            // ogawa.setName("Ogawa");
            //ogawa.setCharacter();

            Game g = realm.createObject(Game.class);
            g.setName("Guilty Gear XRD");

            g.setLogo("guiltylogo.png");

            List<Character> personagens = createGuiltyCharacters();
            g.getCharacters().addAll(personagens);


            realm.commitTransaction();

        }

        public void insertAllGames(){
            realm.beginTransaction();
            Game g = realm.createObject(Game.class);
            g.setName("KOF 2002 UM");
            g.setLogo("kof2002logo.png");

            Game g2 = realm.createObject(Game.class);
            g2.setName("Killer Instinct");
            g2.setLogo("Killerlogo.png");

            Game g3 = realm.createObject(Game.class);
            g3.setName("KOF 98 UM");
            g3.setLogo("kof98logo.png");

            Game g4 = realm.createObject(Game.class);
            g4.setName("Street Fighter 5");
            g4.setLogo("street5logo.png");


            realm.commitTransaction();
        }




        public List<Game> getAllGames() {
            RealmQuery<Game> query = realm.where(Game.class);
            RealmResults<Game> result1 = query.findAll();
            for (Game g:
                 result1) {
                Log.i("Result Game", g.getName());
                for (Character c:
                     g.getCharacters()) {
                    Log.i("Result char", c.getName());
                }
            }



            return result1;

        }






        public List<Character> createGuiltyCharacters(){

            List<Character> list = new ArrayList<>();
            Character axl = new Character();
            axl.setName("Axl Low");
            axl.setPortrait("axlportrait.png");
            list.add(axl);

            Character bedman  = new Character();
            bedman.setName("Bedman");
            bedman.setPortrait("bedmanportrait.png");
            list.add(bedman);

            Character chipp  = new Character();
            chipp.setName("Chipp");
            chipp.setPortrait("chippportrait.png");
            list.add(chipp);

            Character elphelt  = new Character();
            elphelt.setName("Elphelt");
            elphelt.setPortrait("elpheltportrait.png");
            list.add(elphelt);


            Character faust  = new Character();
            faust.setName("Faust");
            faust.setPortrait("faustportrait.png");
            list.add(faust);

            Character ino  = new Character();
            ino.setName("I-no");
            ino.setPortrait("inoportrait.png");
            list.add(ino);

            Character jacko  = new Character();
            jacko.setName("Jack-o");
            jacko.setPortrait("jackoportrait.png");
            list.add(jacko);

            Character johnny  = new Character();
            johnny.setName("Johnny");
            johnny.setPortrait("johnnyportrait.png");
            list.add(johnny);

            Character jam  = new Character();
            jam.setName("Jam");
            jam.setPortrait("jamportrait.png");
            list.add(jam);


            Character ky = new Character();
            ky.setName("Ky Kiske");
            ky.setPortrait("kyportrait.png");
            list.add(ky);

            Character leo  = new Character();
            leo.setName("Leo Whitefang");
            leo.setPortrait("leoportrait.png");
            list.add(leo);

            Character may  = new Character();
            may.setName("May");
            may.setPortrait("mayportrait.png");
            list.add(may);


            Character millia = new Character();
            millia.setName("Millia Rage");
            millia.setPortrait("milliaportrait.png");
            list.add(millia);

            Character pot  = new Character();
            pot.setName("Potemkin");
            pot.setPortrait("potemkinportrait.png");
            list.add(pot);

            Character ram  = new Character();
            ram.setName("Ramlethal");
            ram.setPortrait("ramlethalportrait.png");
            list.add(ram);

            Character sin  = new Character();
            sin.setName("Sin Kiske");
            sin.setPortrait("sinportrait.png");
            list.add(sin);

            Character slayer = new Character();
            slayer.setName("Slayer");
            slayer.setPortrait("slayerportrait.png");
            list.add(slayer);

            Character sol = new Character();
            sol.setName("Sol Badguy");
            sol.setPortrait("solportrait.png");
            list.add(sol);

            Character venom  = new Character();
            venom.setName("Venom");
            venom.setPortrait("venomportrait.png");
            list.add(venom);

            Character zato = new Character();
            zato.setName("Zato-one");
            zato.setPortrait("zatoportrait.png");
            list.add(zato);


            return list;

        }




            //Metodo que irá dizer que o usuário joga aquele jogo
            public void relacionarGameUser(String gameName, String username){
                RealmQuery<Game> query = realm.where(Game.class);
                query.equalTo("name", gameName);
                Game game = query.findFirst();
                RealmQuery<User> query2 = realm.where(User.class);
                query2.equalTo("username", username);
                User user = query2.findFirst();
                realm.beginTransaction();
                RealmList<Game> games = new RealmList<>();
                games.add(game);
                user.setGames(games);
                realm.copyToRealm(user);
                realm.commitTransaction();



            }


            public Game getGame(String selectedGameName){

                RealmQuery<Game> query = realm.where(Game.class);
                query.equalTo("name", selectedGameName);
                Game game = query.findFirst();


                return game;
            }


            public Character getCharacter(String characterName){
                RealmQuery<Character> query = realm.where(Character.class);
                query.equalTo("name", characterName);
                Character charcter= query.findFirst();


                return charcter;
            }




            public void insertTopPlayers(){
                realm.beginTransaction();
                TopPlayer t = realm.createObject(TopPlayer.class);
                t.setName("Ogawa");
                t.setGame(getGame("Guilty Gear XRD"));
                t.setCharacter(getCharacter("Zato-one"));

                realm.commitTransaction();

                realm.beginTransaction();

                TopPlayer t2 = realm.createObject(TopPlayer.class);
                t2.setName("Nage");
                t2.setGame(getGame("Guilty Gear XRD"));
                t2.setCharacter(getCharacter("Millia Rage"));
                realm.commitTransaction();

                realm.beginTransaction();

                TopPlayer t3 = realm.createObject(TopPlayer.class);
                t3.setName("Kazuki");
                t3.setGame(getGame("Guilty Gear XRD"));
                t3.setCharacter(getCharacter("Sol Badguy"));


                realm.commitTransaction();

                realm.beginTransaction();

                TopPlayer t4 = realm.createObject(TopPlayer.class);
                t4.setName("Sanma");
                t4.setGame(getGame("Guilty Gear XRD"));
                t4.setCharacter(getCharacter("Slayer"));


                realm.commitTransaction();


            }


        public RealmResults<TopPlayer> getTopPlayerByGame(String gameName){
            RealmResults<TopPlayer> query = realm.where(TopPlayer.class).equalTo("game.name", gameName).findAll();
           return query;


        }


}
