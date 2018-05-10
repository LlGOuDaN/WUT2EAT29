package com.example.l8411.wut2eat29.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.l8411.wut2eat29.Fragment.LoginFragment;
import com.example.l8411.wut2eat29.Utils.FirebaseData;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.example.l8411.wut2eat29.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnLoginListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_GOOGLE_LOGIN = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private OnCompleteListener<AuthResult> mOnCompleteListener;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mRef;
    private boolean isNew;
    private OnCompleteListener<AuthResult> mOnRegisterCompleteListener;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        ft.add(R.id.login_container, loginFragment);
        ft.commit();

        initializeListener();
        setupGoogleSignin();
    }

    private void setupGoogleSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initializeListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    switchToMainActivity("users/" + user.getUid());
                }
            }
        };

        mOnCompleteListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showLoginError("Authentication failed");
                }else{
                    isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                    if (isNew) {
                        mRef.child("usernumber").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long numOfuser = (Long) dataSnapshot.getValue();
                                String uid = Long.toString(numOfuser);
                                UserProfile user = new UserProfile(uid);
                                mRef.child("user").child(mAuth.getCurrentUser().getUid()).setValue(user);
                                numOfuser++;
                                mRef.child("usernumber").setValue(numOfuser);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        };

        mOnRegisterCompleteListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    showRegisterError();
                }else{
                    mRef.child("usernumber").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Long numOfuser = (Long) dataSnapshot.getValue();
                            String uid = Long.toString(numOfuser);
                            UserProfile user = new UserProfile(uid);
                            mRef.child("user").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            numOfuser++;
                            mRef.child("usernumber").setValue(numOfuser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    private void switchToMainActivity(String s) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mOnCompleteListener);
    }

    @Override
    public void onRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mOnRegisterCompleteListener);
    }

    @Override
    public void onGoogleLogin() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_GOOGLE_LOGIN);
    }

    private void showLoginError(String message) {
        loginFragment.onLoginError(message);
    }

    private void showRegisterError(){
        loginFragment.onRegisterError();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLoginError("Login Failed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_GOOGLE_LOGIN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential).addOnCompleteListener(mOnCompleteListener);
                } else {
                    showLoginError("Google Auth Failed");
                }
            }
        }
    }

}

