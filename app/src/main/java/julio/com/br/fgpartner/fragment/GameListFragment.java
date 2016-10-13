package julio.com.br.fgpartner.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.MainActivity;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.adapter.GameAdapter;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.dao.UserDAO;
import julio.com.br.fgpartner.model.Game;

/**
 * Created by Shido on 19/01/2016.
 */
public class GameListFragment extends Fragment  {

    @Bind(R.id.rvGames)
    RecyclerView rvGames;

    @Bind(R.id.rvAllGames)
    RecyclerView rvAllGames;


    private LinearLayoutManager layoutManager;

    private GridLayoutManager gridLayoutManager;

    private List<Game> listGames;

    private List<Game> userGames;

    public GameListFragment(){}

    private GameAdapter gameAdapter;



    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Game g);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gameslist, container, false);

        ButterKnife.bind(this, rootView);


        //Gerenciador de linear layout dentro do recycler view
        layoutManager = new LinearLayoutManager(container.getContext());
       // gridLayoutManager = new GridLayoutManager(container.getContext(),2, GridLayoutManager.HORIZONTAL, false);

        rvGames.setLayoutManager(layoutManager);
        //rvGames.setLayoutManager(gridLayoutManager);

        gameAdapter = new GameAdapter(container.getContext(), listGames, false );
        rvGames.setAdapter(gameAdapter);

        rvAllGames.setAdapter(gameAdapter);



        return rootView;

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        GameDAO gdao = new GameDAO(getActivity());
        listGames = gdao.getAllGames();

        super.onCreate(savedInstanceState);


    }








}
