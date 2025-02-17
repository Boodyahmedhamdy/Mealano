package io.github.boodyahmedhamdy.mealano.signup;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class SignupFragment extends Fragment {

    TextView tvLogin; // will go to login
    TextInputEditText tietEmail;
    TextInputEditText tietPassword;
    TextInputEditText tietConfirmPassword;
    Button btnSignup;
    ProgressBar pbSignup;
    FirebaseAuth firebaseAuth;
    SharedPreferencesManager sharedPreferencesManager;
    private static final String TAG = "SignupFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        findUI(view);


        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(requireActivity());

        tvLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(
                    SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            );
        });
        btnSignup.setOnClickListener(v -> {
            String email = tietEmail.getText().toString();
            String password = tietPassword.getText().toString();
            String confirmPassword = tietConfirmPassword.getText().toString();

            if(isValid(email, password, confirmPassword)) {
                createAccountUsingEmailAndPassword(email, password);
                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToHomeFragment());
            } else {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void createAccountUsingEmailAndPassword(String email, String password) {
        changeInProgress(true);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), task -> {
            if(task.isSuccessful()) {
                // success
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.i(TAG, "createAccountUsingEmailAndPassword: email: " + user.getEmail() );
                Log.i(TAG, "createAccountUsingEmailAndPassword: display name: " + user.getDisplayName() );
                Log.i(TAG, "createAccountUsingEmailAndPassword: uid: " + user.getUid() );
                Log.i(TAG, "createAccountUsingEmailAndPassword: phone: " + user.getPhoneNumber() );
                Log.i(TAG, "createAccountUsingEmailAndPassword: provider id: " + user.getProviderId() );
                sharedPreferencesManager.updateUserId(user.getUid());
                changeInProgress(false);

            } else {
                // failure
                Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                changeInProgress(false);
            }
        });


    }

    private void changeInProgress(boolean inProgress) {
        if(inProgress) {
            pbSignup.setVisibility(VISIBLE);
            btnSignup.setVisibility(GONE);
        } else {
            pbSignup.setVisibility(GONE);
            btnSignup.setVisibility(VISIBLE);
        }

    }

    private boolean isValid(String email, String password, String confirmPassword) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tietEmail.setError("enter a valid email: " + email + " isn't valid");
            return false;
        }
        if(password.trim().length() < 6) {
            tietPassword.setError("the password must be at least 6 characters");
            return false;
        }
        if(!password.equals(confirmPassword)) {
            tietConfirmPassword.setError("confirm password is not the same as password");
            return false;
        }
        return true;
    }

    private void findUI(View view) {
        tvLogin = view.findViewById(R.id.tvLogin);
        tietEmail = view.findViewById(R.id.tietEmailSignupScreen);
        tietPassword = view.findViewById(R.id.tietPasswordSignupScreen);
        tietConfirmPassword = view.findViewById(R.id.tietConfirmPasswordSignupScreen);
        btnSignup = view.findViewById(R.id.btnSignup);
        pbSignup = view.findViewById(R.id.pbSignup);
    }


}