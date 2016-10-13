package julio.com.br.fgpartner.fragment;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.adapter.CharacterAdapter;
import julio.com.br.fgpartner.adapter.TopPlayersAdapter;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.model.*;
import julio.com.br.fgpartner.model.Character;

/**
 * Created by Shido on 25/01/2016.
 */
public class GameDetailFragment extends Fragment {


    @Bind(R.id.gameDetailsGameImage)
    ImageView gameDetailsGameImage;

    private GridLayoutManager gridLayoutManager;

    @Bind(R.id.gameDetailsName)
    TextView gameDetailsName;


    @Bind(R.id.lvTopPlayers)
    ListView lvTopPlayers;

    private List<TopPlayer> topPlayersByGame;


    private GameDAO gameDao;

    private Game game;

    private List<Character> characters;

    private String selectedGameName;

    @Bind(R.id.rvCharacters)
    RecyclerView rvCharacters;

    private CharacterAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_details, container, false);

        ButterKnife.bind(this, rootView);
        gridLayoutManager = new GridLayoutManager(container.getContext(),2, GridLayoutManager.VERTICAL, false);
        rvCharacters.setLayoutManager(gridLayoutManager);
      //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());
        //rvCharacters.setLayoutManager(linearLayoutManager);

        rvCharacters.setAdapter(new CharacterAdapter(container.getContext(), characters));

        lvTopPlayers.setAdapter(new TopPlayersAdapter(container.getContext(), topPlayersByGame));


        try {
            InputStream is = container.getContext().getAssets().open(game.getLogo());
            Drawable logoGame = Drawable.createFromStream(is, null);

            gameDetailsGameImage.setImageDrawable(logoGame);

        }catch (IOException e){
            e.printStackTrace();
        }
            gameDetailsName.setText(game.getName());


        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedGameName = (String) getArguments().getSerializable("selectedGame");
        gameDao = new GameDAO(getActivity());
        game = gameDao.getGame(selectedGameName);
        characters = game.getCharacters();
        topPlayersByGame = gameDao.getTopPlayerByGame(game.getName());
        Log.i("GAME NAME", game.getName());
        for (julio.com.br.fgpartner.model.Character c:
                game.getCharacters()) {
            Log.i("Character name", c.getName());
        }




    }



}
