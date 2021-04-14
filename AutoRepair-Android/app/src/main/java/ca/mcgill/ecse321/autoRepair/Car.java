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

public class Car extends Fragment  implements AdapterView.OnItemSelectedListener{

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.car, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner carTransmissionSpinner = view.findViewById(R.id.carTransmission);
        Spinner plateNumbers = view.findViewById(R.id.plateNumberRemove);

        ArrayAdapter<CharSequence> transmissionAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.carTransmission, android.R.layout.simple_spinner_item);
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTransmissionSpinner.setAdapter(transmissionAdapter);
        carTransmissionSpinner.setOnItemSelectedListener(this);

        ((MainActivity)getActivity()).getCars(view);



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