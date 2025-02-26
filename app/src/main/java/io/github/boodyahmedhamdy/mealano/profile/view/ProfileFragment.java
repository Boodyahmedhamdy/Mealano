package io.github.boodyahmedhamdy.mealano.profile.view;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.R;
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
    NavController navController;

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
        navController = Navigation.findNavController(binding.getRoot());

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

        binding.btnAbout.setOnClickListener(v -> {
            navController.navigate(
                    ProfileFragmentDirections.actionProfileFragmentToAboutFragment()
            );
        });

        binding.btnLoginProfile.setOnClickListener(v -> {
            navController.navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment());
        });


    }

    @Override
    public void onSuccess() {
        navController.navigate(
                ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        );
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

            if(user.getDisplayName() != null) {
                binding.tvUserName.setText(user.getDisplayName());
            } else {
                binding.tvUserName.setText(user.getEmail().split("@")[0]);
            }

            if(user.getEmail() != null) {
                binding.tvUserEmail.setText(user.getEmail());
            }
            Glide.with(binding.getRoot())
                    .load(user.getPhotoUrl())
                    .error(R.drawable.baseline_face_24)
                    .into(binding.ivUser);

            binding.lockLayoutProfile.getRoot().setVisibility(INVISIBLE);
            binding.btnLoginProfile.setVisibility(GONE);

            binding.ivUser.setVisibility(VISIBLE);
            binding.tvUserName.setVisibility(VISIBLE);
            binding.tvUserEmail.setVisibility(VISIBLE);
            binding.btnSignout.setVisibility(VISIBLE);
        } else { // logged out

            binding.lockLayoutProfile.getRoot().setVisibility(VISIBLE);
            binding.btnLoginProfile.setVisibility(VISIBLE);

            binding.ivUser.setVisibility(INVISIBLE);
            binding.tvUserName.setVisibility(INVISIBLE);
            binding.tvUserEmail.setVisibility(INVISIBLE);
            binding.btnSignout.setVisibility(INVISIBLE);
        }

    }
}