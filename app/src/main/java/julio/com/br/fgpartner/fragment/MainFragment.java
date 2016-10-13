package julio.com.br.fgpartner.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import julio.com.br.fgpartner.R;

/**
 * Created by Shido on 18/01/2016.
 */
public class MainFragment extends Fragment {


    @Bind(R.id.ivFragMainGuilty)
    ImageView ivGuilty;

    @Bind(R.id.ivFragMainStreet5)
    ImageView ivStreet;

    @Bind(R.id.ivFragMainKiller)
    ImageView ivKiller;


    @Bind(R.id.ivFragMainKof2002)
    ImageView ivKof2002;


    @Bind(R.id.ivFragMainKof98)
    ImageView ivKof98;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);


        try {

            InputStream is = getActivity().getAssets().open("guiltylogo.png");
            Drawable logoGame = Drawable.createFromStream(is, null);
            ivGuilty.setImageDrawable(logoGame);

            is = getActivity().getAssets().open("Killerlogo.png");
            logoGame = Drawable.createFromStream(is, null);
            ivKiller.setImageDrawable(logoGame);

            is = getActivity().getAssets().open("street5logo.png");
            logoGame = Drawable.createFromStream(is, null);
            ivStreet.setImageDrawable(logoGame);

            is = getActivity().getAssets().open("kof98logo.png");
            logoGame = Drawable.createFromStream(is, null);
            ivKof98.setImageDrawable(logoGame);

            is = getActivity().getAssets().open("kof2002logo.png");
            logoGame = Drawable.createFromStream(is, null);
            ivKof2002.setImageDrawable(logoGame);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return rootView;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
