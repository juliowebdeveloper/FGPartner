package julio.com.br.fgpartner.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.adapter.SpinnerGamesAdapter;
import julio.com.br.fgpartner.dao.AnnotationsDAO;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.model.Annotation;
import julio.com.br.fgpartner.model.Game;
import julio.com.br.fgpartner.model.User;

/**
 * Created by Shido on 19/01/2016.
 */
public class AnnotationFragment extends Fragment {
    private SpinnerGamesAdapter adapter;

    private Game[] games;

    @Bind(R.id.spinnerGames)
    Spinner spinner;

    @Bind(R.id.etAnotTitle)
    EditText anotTitle;

    @Bind(R.id.etAnotDescription)
    EditText anotDesc;

    @Bind(R.id.buttonSalvarAnotacao)
    Button salvarAnotacao;

    @Bind(R.id.buttonShowListAnnotations)
    Button showListAnnotations;

    private AnnotationsDAO anotdao;

    private Game selectedGame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_annotation, container, false);

        // Initialize the adapter sending the current context
        // Send the simple_spinner_item layout
        // And finally send the Users array (Your data)
        ButterKnife.bind(this, rootView);



        adapter = new SpinnerGamesAdapter(container.getContext(), android.R.layout.simple_spinner_item, games);

        //Spinner da pagina
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Game game = adapter.getItem(position);
                selectedGame = adapter.getItem(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                Snackbar.make(rootView, "Selecione o jogo correspondente daquela anotação", Snackbar.LENGTH_LONG).show();

            }
        });



        return rootView;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameDAO gdao = new GameDAO(getActivity());
        List<Game> lista = gdao.getAllGames();
        games = new Game[lista.size()];
        lista.toArray(games);

    }




    @OnClick(R.id.buttonSalvarAnotacao)
    public void salvarAnotacao(View v) {
        if (anotTitle.getText().toString().equals("") || anotDesc.getText().equals("")) {
            Snackbar.make(v, "Você precisa preencher o titulo e a descrição ", Snackbar.LENGTH_LONG).show();

        } else {
            registrarAnnotation(getActivity());
            Snackbar.make(v, "Anotação Salva ", Snackbar.LENGTH_LONG).show();
        }

    }







    public void registrarAnnotation(Context c) {
        anotdao = new AnnotationsDAO(c);
        Annotation a = new Annotation();
        a.setTitle(anotTitle.getText().toString());
        a.setDescription(anotDesc.getText().toString());
        a.setGame(selectedGame);
        Log.i("SelectedGame", selectedGame.getName());
        anotdao.registerAnnotation(a);
        anotTitle.setText("");
        anotDesc.setText("");
    }












}
