package julio.com.br.fgpartner.adapter;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.MainActivity;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.fragment.GameDetailFragment;
import julio.com.br.fgpartner.model.Game;

/**
 * Created by Shido on 19/01/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private Context context;
    private List<Game> games;
   private boolean userGames;

    private GameDetailFragment gameDetailFragment;

    private Bundle bundle;

    //Construtor para criar o contexto
    public GameAdapter (Context context, List<Game> games, boolean userGames){
        super();
        this.context = context;
        this.games = games;
        this.userGames = userGames;

    }

    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.game_row, parent, false);
        return new GameViewHolder(view);
    }


    @Override
    //faz as associações junto com o holder e a lista de carros.
    //position sao os valores a serem colocados
    public void onBindViewHolder(final GameViewHolder holder, final int position) {
       // holder..setText(games.get(position).getName());

        //passando as imagens  da asset para drawable atraves de um inputstream que irá ler o arquivo baseado no nome da foto  do position da lista
        try {
            InputStream is = context.getAssets().open(games.get(position).getLogo());
            Drawable logoGame = Drawable.createFromStream(is, null);

            holder.gamePortrait.setImageDrawable(logoGame);


            //Irá para os detalhes do jogo mostrando seus personagens
            holder.gamePortrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("JOGO IMAGE", getItemName(position));

                    fragmentJump(games.get(position).getName());
                   // FragmentTransaction transaction = /* Here is the change.*/context.getFragmentManager().beginTransaction();
                    //transaction.replace(R.id.viewFragments, newFragment);


                }
            });


            //Se nao forem os jogos do usuario, permite adicionar
        if(!userGames) {
            // holder.gameText.setText(games.get(position).getName());
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Passa o jogo que foi escolhido e adiciona ao usuário.
                    Log.i("JOGO ESCOLHIDO", games.get(position).getName());
                    relacionarGameToUser(games.get(position).getName(), MainActivity.USERNAME);
                    Toast.makeText(context, "Jogo Adicionado à sua lista", Toast.LENGTH_LONG).show();
                    holder.add.setClickable(false);
                    holder.add.setVisibility(View.INVISIBLE);
                }
            });

        }else{
            holder.add.setVisibility(View.INVISIBLE);
            holder.add.setClickable(false);
        }



        } catch (IOException e) {
            e.printStackTrace();
        }



    }




    public void switchContent(int id, android.app.Fragment fragment, Bundle b) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            android.app.Fragment frag = fragment;
            mainActivity.switchContent(id, frag, b);
        }

    }


    private void fragmentJump(String mGameSelected) {
       gameDetailFragment = new GameDetailFragment();
        bundle = new Bundle();
         bundle.putString("selectedGame", mGameSelected);
        gameDetailFragment.setArguments(bundle);
        switchContent(R.id.mainFrameLayout, gameDetailFragment, bundle);
    }



    @Override
    public int getItemCount() {
        return games.size();
    }



    public String getItemName(int position) {
        return games.get(position).getName();
    }


    public void relacionarGameToUser(String gameName, String username){
            GameDAO dao = new GameDAO(context);
        dao.relacionarGameUser(gameName, username);
    }



    //View holder é a classe que segurará aquele objeto carro.
    public static class GameViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gamePortrait)
        ImageView gamePortrait;

        @Bind(R.id.ivAdd)
        ImageView add;



        //No construtor é feita é associação dos ids com os atributos
        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }




    }






}
