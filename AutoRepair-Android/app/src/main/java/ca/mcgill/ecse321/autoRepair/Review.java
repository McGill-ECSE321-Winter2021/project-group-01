package ca.mcgill.ecse321.autoRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Review extends Fragment implements AdapterView.OnItemSelectedListener {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.review, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner ratingSpinner = view.findViewById(R.id.rating);

        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.serviceRating, android.R.layout.simple_spinner_item);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ratingAdapter);
        ratingSpinner.setOnItemSelectedListener(this);

        ((MainActivity)getActivity()).getPreviousAppointments(view);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}