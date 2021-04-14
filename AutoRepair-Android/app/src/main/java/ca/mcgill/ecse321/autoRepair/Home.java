package ca.mcgill.ecse321.autoRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        ((MainActivity)getActivity()).getServices(view);
        //((MainActivity)getActivity()).getReminders(view);
        //super.onViewCreated(view, savedInstanceState);

    }
}