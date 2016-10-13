package julio.com.br.fgpartner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.model.*;
import julio.com.br.fgpartner.model.Character;

/**
 * Created by Shido on 25/01/2016.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private Context context;
    private List<Character> characters;


    //Construtor para criar o contexto
    public CharacterAdapter (Context context, List<julio.com.br.fgpartner.model.Character> characters){
        this.context = context;
        this.characters = characters;

    }



    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_row, parent, false);
        return new CharacterViewHolder(view);
    }





    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        //passando as imagens  da asset para drawable atraves de um inputstream que irá ler o arquivo baseado no nome da foto  do position da lista
        try {
            InputStream is = context.getAssets().open(characters.get(position).getPortrait());
            Drawable charPortrait = Drawable.createFromStream(is, null);

            holder.characterPortrait.setImageDrawable(charPortrait);
            holder.characterListName.setText(characters.get(position).getName());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    @Override
    public int getItemCount() {
        return characters.size();
    }


    //View holder é a classe que segurará aquele objeto carro.
    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivCharacterList)
        ImageView characterPortrait;


        @Bind(R.id.tvCharaterListName)
        TextView characterListName;

        //No construtor é feita é associação dos ids com os atributos
        public CharacterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }


    }





    }
