package com.ssa.taskapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ssa.taskapp.App;
import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentAuthBinding;

import java.util.Collections;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private NavController controller;
    private String email;
    private String password;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater);
        callbackManager = CallbackManager.Factory.create();
        initController();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        return binding.getRoot();
    }

    private void initController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostController != null;
        controller = navHostController.getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        Eyes();
    }



    private void initListeners() {
        binding.enterReg.setOnClickListener(v -> {
            email = binding.emailEt.getText().toString().trim();
            password = binding.passEt.getText().toString().trim();
            EnterEmail();
        });
        binding.enterReg.setOnLongClickListener(v -> {
            email = binding.emailEt.getText().toString().trim();
            password = binding.passEt.getText().toString().trim();
            registerEmail();
            return true;
        });
        binding.btnGoogle.setOnClickListener(v -> {
            signIn();
            controller.navigate(R.id.navigation_home);
        });

        binding.loginButton.setReadPermissions(Collections.singletonList(email));
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(requireContext(), "User:" +
                                email + "\n successfully created"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(requireContext(),"          User died" +
                                "\n Long press to register",
                        Toast.LENGTH_SHORT).show();
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(requireContext(), "User:" +
                                email + "\n successfully created"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(requireContext(),"          User died" +
                                "\n Long press to register",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void EnterEmail() {
        App.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(requireContext(), "User:" +
                                email + "\n successfully created"
                        , Toast.LENGTH_SHORT).show();
                controller.navigate(R.id.navigation_home);
            }else {
                Toast.makeText(requireContext(),"          User died" +
                                "\n Long press to register",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerEmail() {
        App.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(requireContext(), "User:" +
                                email + "\n successfully logged in"
                        , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(requireContext(), "User died" ,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Eyes() {
        binding.withSlack.setEnabled(false);
        binding.withSlack.setOnClickListener(view -> {
            binding.withSlack.setVisibility(View.INVISIBLE);
            binding.withSlack.setEnabled(false);
            binding.slashEyes.setVisibility(View.VISIBLE);
            binding.slashEyes.setEnabled(true);
            binding.passEt.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });
        binding.slashEyes.setOnClickListener(view -> {
            binding.slashEyes.setVisibility(View.INVISIBLE);
            binding.slashEyes.setEnabled(false);
            binding.withSlack.setVisibility(View.VISIBLE);
            binding.withSlack.setEnabled(true);
            binding.passEt.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        });
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
       App.auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(requireContext(), "User:" +
                                        email + "\n successfully with Google logged in"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "User died" ,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}