package ca.mcgill.ecse321.autoRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Login extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
<<<<<<<< HEAD:AutoRepair-Android/app/src/main/java/ca/mcgill/ecse321/autoRepair/Login.java
========

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_login_to_Signup);
            }
        });
>>>>>>>> origin/android-eric:AutoRepair-Android/app/src/main/java/ca/mcgill/ecse321/autoRepair/FirstFragment.java
    }
}