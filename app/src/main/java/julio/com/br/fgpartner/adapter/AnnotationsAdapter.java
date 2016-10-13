package julio.com.br.fgpartner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.MainActivity;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.fragment.EditAnnotationDialog;
import julio.com.br.fgpartner.fragment.GameDetailFragment;
import julio.com.br.fgpartner.model.Annotation;

/**
 * Created by Shido on 19/01/2016.
 */
public class AnnotationsAdapter extends RecyclerView.Adapter<AnnotationsAdapter.AnnotationsViewHolder> {

    private List<Annotation> annotationList;

    private Context context;
    
    private Bundle bundle;

    public AnnotationsAdapter(List<Annotation> annotationList, Context context) {
        super();
        this.annotationList = annotationList;
        this.context = context;
    }


    @Override
    public AnnotationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.annotation_row, parent, false);
        return new AnnotationsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(AnnotationsViewHolder holder,final   int position) {

        holder.anotRowTitle.setText(annotationList.get(position).getTitle());
        holder.anotRowDesc.setText(annotationList.get(position).getDescription());

        if(annotationList.get(position).getGame() != null){
            holder.anotRowGameName.setText(annotationList.get(position).getGame().getName());
        }

        holder.anotRowTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(annotationList.get(position).getId());

            }
        });


    }


    private void fragmentJump(int idAnnotation) {
        EditAnnotationDialog edit = new EditAnnotationDialog();
        bundle = new Bundle();
        bundle.putInt("selectedAnot", idAnnotation);
        edit.setArguments(bundle);
        showDialog(R.id.mainFrameLayout, edit, bundle);
    }



    public void showDialog(int id, android.app.Fragment fragment, Bundle b) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            android.app.Fragment frag = fragment;
            mainActivity.showEditAnnotationsDialog(b);
        }

    }


    
    


    @Override
    public int getItemCount() {
        return annotationList.size();
    }


























    //View holder é a classe que segurará aquele objeto carro.
    public static class AnnotationsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.anotRowTitle)
        TextView anotRowTitle;

        @Bind(R.id.anotRowDesc)
        TextView anotRowDesc;

        @Bind(R.id.anotRowGameName)
        TextView anotRowGameName;


        //No construtor é feita é associação dos ids com os atributos
        public AnnotationsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }






    }






}
