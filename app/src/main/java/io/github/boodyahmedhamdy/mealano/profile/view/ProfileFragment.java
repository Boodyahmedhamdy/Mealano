package io.github.boodyahmedhamdy.mealano.profile.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentProfileBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;
import io.github.boodyahmedhamdy.mealano.profile.contract.ProfileView;
import io.github.boodyahmedhamdy.mealano.profile.presenter.ProfilePresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class ProfileFragment extends Fragment implements ProfileView, OnSignOutCallback {
    private static final String TAG = "ProfileFragment";
    FragmentProfileBinding binding;
    ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(
                inflater, container, false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.showToolbar(requireActivity());
        UiUtils.showBottomBar(requireActivity());

        presenter = new ProfilePresenter(
                this,
                UsersRepository.getInstance(
                        UsersLocalDataSource.getInstance(SharedPreferencesManager.getInstance(getContext()), FirebaseAuth.getInstance()),
                        UsersRemoteDataSource.getInstance(FirebaseAuth.getInstance())
                )
        );

        presenter.getCurrentUser();

        binding.btnSignout.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Sign out")
                    .setMessage("are you sure to Sign out?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        Log.i(TAG, "clicked on positive button. will signout");
                        presenter.signOut(this);
                    })
                    .setNegativeButton("No", (dialog1, which) -> {
                        Log.i(TAG, "clicked on negative button");
                    })
                    .create();
            dialog.show();
        });

    }

    @Override
    public void onSuccess() {
        Navigation.findNavController(binding.getRoot())
                .navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment());
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.e(TAG, "onFailure: error message: " + errorMessage);

    }

    @Override
    public void setCurrentUser(FirebaseUser user) {
        if(user != null) {
            String email = user.getEmail();
            binding.tvUserEmail.setText(email);
        } else {
            binding.tvUserEmail.setText("you arn't Logged in");
        }

    }
}