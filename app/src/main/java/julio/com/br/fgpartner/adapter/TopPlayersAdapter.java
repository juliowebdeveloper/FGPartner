package julio.com.br.fgpartner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.model.TopPlayer;

/**
 * Created by Shido on 25/01/2016.
 */
public class TopPlayersAdapter extends BaseAdapter{


    private Context context;

    private List<TopPlayer> topPlayers;

    public TopPlayersAdapter(Context c, List<TopPlayer> topPlayers){
        this.context = c;
        this.topPlayers = topPlayers;
    }


    @Override
    public int getCount() {
        return topPlayers.size();
    }

    @Override
    public Object getItem(int position) {
        return topPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        //Se nao utilizar o viewholder, toda vez que entrar nesse metodo getView ele vai refazer a instacia, com o view holder a informação é reaproveitada
        if(convertView == null){
            //Significa que não tinha sido criado
            //Inflando a partir do contexto
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            //View que será inflada
            convertView = inflater.inflate(R.layout.top_players_row, parent, false);

            holder = new ViewHolder(convertView);

            //Essa tag que informará se o precisa passar por esse trecho
            convertView.setTag(holder);
        }else{
            //Setando a tag do holder caso a view já exista
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomePlayer.setText(topPlayers.get(position).getName());

        try {
            InputStream is = context.getAssets().open(topPlayers.get(position).getCharacter().getPortrait());
            Drawable charPortrait = Drawable.createFromStream(is, null);
               holder.ivTopPlayerCharacter.setImageDrawable(charPortrait);

        }catch (IOException e){
            e.printStackTrace();
        }

        return convertView;
    }



    //Segura a instancia da view que ja foi criada , os atributos dessa classe sao a partir do layout item_livro
    public class ViewHolder{

        @Bind(R.id.nomePlayer)
         TextView nomePlayer;

        @Bind(R.id.ivTopPlayerCharacter)
         ImageView ivTopPlayerCharacter;

        public ViewHolder(View v){
            ButterKnife.bind(this, v);
        }

    }






}
