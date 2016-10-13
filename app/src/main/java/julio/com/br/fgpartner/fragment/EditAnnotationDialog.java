package julio.com.br.fgpartner.fragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import julio.com.br.fgpartner.R;
import julio.com.br.fgpartner.dao.AnnotationsDAO;
import julio.com.br.fgpartner.dao.GameDAO;
import julio.com.br.fgpartner.model.Annotation;

/**
 * Created by Shido on 25/01/2016.
 */
public class EditAnnotationDialog extends DialogFragment {

    @Bind(R.id.editAnnotationDesc)
    EditText annotDesc;

    @Bind(R.id.editAnotConfirmButton)
    Button buttonConfirm;

    @Bind(R.id.editAnnotationTitle)
    EditText annotTitle;



    private AnnotationsDAO anotdao;

    private Annotation selectedAnnotation;


    public EditAnnotationDialog() {
        // Empty constructor required for DialogFragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_annotation, container);

        ButterKnife.bind(this, view);
        getDialog().setTitle("Edição de Anotação");
        annotDesc.setText(selectedAnnotation.getDescription());
        annotTitle.setText(selectedAnnotation.getTitle());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int selectedAnnotationId = (int) getArguments().getSerializable("selectedAnot");

        anotdao = new AnnotationsDAO(getActivity());
        selectedAnnotation = anotdao.getSingleAnnotation(selectedAnnotationId);


    }

    @OnClick(R.id.editAnotConfirmButton)
    public void editAnnotation(View v) {
        anotdao = new AnnotationsDAO(getActivity());
        Annotation anot2 = new Annotation();
        anot2.setId(selectedAnnotation.getId());
        anot2.setTitle(annotTitle.getText().toString());
        anot2.setDescription(annotDesc.getText().toString());
        anot2.setGame(selectedAnnotation.getGame());
        anotdao.editAnnotation(anot2);
        Snackbar.make(getView(), "Anotação Editada", Snackbar.LENGTH_LONG).show();
        this.dismiss();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);



    }



}
