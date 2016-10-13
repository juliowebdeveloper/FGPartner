package julio.com.br.fgpartner.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.adapter.GameAdapter;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.dao.UserDAO;
import julio.com.br.fgpartner.model.Game;

/**
 * Created by Shido on 26/01/2016.
 */
public class UserGamesFragment extends Fragment {

    @Bind(R.id.rvUserGames)
    RecyclerView rvUserGames;

    private LinearLayoutManager layoutManager;

    private GridLayoutManager gridLayoutManager;

    private List<Game> userGames;

    public UserGamesFragment(){}

    private GameAdapter gameAdapter;



    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Game g);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_games, container, false);

        ButterKnife.bind(this, rootView);


        //Gerenciador de linear layout dentro do recycler view
        layoutManager = new LinearLayoutManager(container.getContext());
        // gridLayoutManager = new GridLayoutManager(container.getContext(),2, GridLayoutManager.HORIZONTAL, false);

        rvUserGames.setLayoutManager(layoutManager);
        //rvGames.setLayoutManager(gridLayoutManager);

        gameAdapter = new GameAdapter(container.getContext(), userGames, true );
        rvUserGames.setAdapter(gameAdapter);

        rvUserGames.setAdapter(gameAdapter);



        return rootView;

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        UserDAO udao = new UserDAO(getActivity());
        userGames = udao.returnAllUserGames();

        super.onCreate(savedInstanceState);


    }




}
