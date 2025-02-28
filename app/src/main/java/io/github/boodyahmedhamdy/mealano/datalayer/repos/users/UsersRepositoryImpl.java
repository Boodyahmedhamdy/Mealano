package io.github.boodyahmedhamdy.mealano.datalayer.repos.users;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users.UsersRemoteDataSource;
import io.reactivex.rxjava3.core.Completable;

public class UsersRepositoryImpl implements UsersRepository {

    private static final String TAG = "UsersRepository";
    UsersLocalDataSource localDataSource;
    UsersRemoteDataSource remoteDataSource;

    private static UsersRepository instance;

    public static UsersRepository getInstance(UsersLocalDataSource localDataSource, UsersRemoteDataSource remoteDataSource) {
        if(instance == null) {
            instance = new UsersRepositoryImpl(localDataSource, remoteDataSource);
        }
        return instance;
    }


    private UsersRepositoryImpl(UsersLocalDataSource localDataSource, UsersRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Task<AuthResult> signupWithEmailAndPassword(String email, String password) {

        return remoteDataSource.signupWithEmailAndPassword(email, password);

    }

    @Override
    public Task<AuthResult> loginWithEmailAndPassword(String email, String password) {
        return remoteDataSource.loginWithEmailAndPassword(email, password);

    }

    @Override
    public Boolean isFirstTime() {
        return localDataSource.isFirstTime();
    }

    @Override
    public Boolean isLoggedIn() {
        return localDataSource.isLoggedIn();
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return localDataSource.getCurrentUser();
    }

    @Override
    public Task<AuthResult> signInWithCredential(AuthCredential credential) {
        return remoteDataSource.signupWithCredential(credential);
    }

    @Override
    public Completable signOut(GoogleSignInClient client) {
        return localDataSource.signOut(client);
    }
}
