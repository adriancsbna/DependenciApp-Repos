package dam.android.dependeciapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import dam.android.dependeciapp.R;

/**
 * Created by adria on 12/02/2018.
 */

@SuppressLint("ValidFragment")
public class BotonFragment extends Fragment {

    TextView textView;
    @SuppressLint("ValidFragment")
    public BotonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_boton, container, false);
       textView = (TextView) rootView.findViewById(R.id.section_label);
        estableceSegunOrientacion();
        return rootView;
    }
    public void estableceSegunOrientacion(){
        final int rotation = getActivity().getResources().getConfiguration().orientation;
        switch (rotation) {
            case Surface.ROTATION_0:
                textView.setText("");

                break;
            case Surface.ROTATION_90:
                textView.setText(getString(R.string.section_format));

                break;
            case Surface.ROTATION_180:
                textView.setText("");

                break;
            default:
                break;
        }

    }


}