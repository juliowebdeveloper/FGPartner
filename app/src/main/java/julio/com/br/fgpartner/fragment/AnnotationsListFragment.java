package julio.com.br.fgpartner.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.adapter.AnnotationsAdapter;
import julio.com.br.fgpartner.adapter.GameAdapter;
import julio.com.br.fgpartner.dao.AnnotationsDAO;
import julio.com.br.fgpartner.model.Annotation;

/**
 * Created by Shido on 19/01/2016.
 */
public class AnnotationsListFragment extends Fragment {


    private List<Annotation> annotations;

    private LinearLayoutManager layoutManager;

    private GridLayoutManager gridLayoutManager;


    private AnnotationsDAO adao;

    public int currentPage = 1;


    @Bind(R.id.rvListAnnotations)
    RecyclerView rvAnnotations;

    @Bind(R.id.searchView)
    SearchView searchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listannotations, container, false);
        ButterKnife.bind(this, rootView);

        //Gerenciador de linear layout dentro do recycler view
        layoutManager = new LinearLayoutManager(container.getContext());
        gridLayoutManager = new GridLayoutManager(container.getContext(),2, GridLayoutManager.HORIZONTAL, false);


        //rvAnnotations.setLayoutManager(layoutManager);
        rvAnnotations.setLayoutManager(gridLayoutManager);

        rvAnnotations.setAdapter(new AnnotationsAdapter(annotations, container.getContext()));



        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

               /* Toast.makeText(getActivity(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();*/
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), query,
                        Toast.LENGTH_SHORT).show();

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

               /* Toast.makeText(getActivity(), newText,
                        Toast.LENGTH_SHORT).show();*/

                getAnnotationsByGameName(newText);

                return false;
            }
            });

        return rootView;



    }



    public void getAnnotationsByGameName(String gameName){
        adao = new AnnotationsDAO(getActivity());
        annotations = adao.getAnnotationsByGame(gameName);

        rvAnnotations.setAdapter(new AnnotationsAdapter(annotations, getActivity()));

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        adao = new AnnotationsDAO(getActivity());
        annotations = adao.getAllAnnotationsByUser();
        super.onCreate(savedInstanceState);

    }
}


