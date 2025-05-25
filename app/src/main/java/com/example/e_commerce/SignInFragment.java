package com.example.e_commerce;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
//import
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Object Tag;
    private TextView dontHaveAccount;
    private FrameLayout parentframeLayout;
    private TextView forgetPassword;
    private EditText email;
    private EditText password;
    private Button loginbtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_signin, container, false);

         dontHaveAccount = view.findViewById(R.id.dontHaveAnAccount);
         email = view.findViewById(R.id.sign_in_EmailAddress);
         password = view.findViewById(R.id.sign_in_Password);
         loginbtn = view.findViewById(R.id.loginbtn);
         firebaseAuth = FirebaseAuth.getInstance();
         forgetPassword = view.findViewById(R.id.sign_in_forget);
         parentframeLayout =getActivity().findViewById(R.id.register_framLayout);
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUPFragment());
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new forgetPasswordFragment());
            }
        });

        //text box text cheak

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cheakInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cheakInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheakEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }



    private  void cheakInput(){
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                loginbtn.setEnabled(true);
            }else{
                loginbtn.setEnabled(false);
            }
        }else{
            loginbtn.setEnabled(false);
        }
    }
/*

    private void  cheakEmailAndPassword(){
        if (email.getText().toString().matches(emailPattern)){
            if (password.length() >=8){
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()){
                            Toast.makeText(getActivity(),"Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent mainIntent= new Intent(getActivity(),MainActivity.class);
                            startActivity(mainIntent);
                            getActivity().finish();

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                        }}
                });
            }

                else {

                Toast.makeText(getActivity(),"Email or password was incrrect",Toast.LENGTH_SHORT).show();;
            }
        }else{
            Toast.makeText(getActivity(),"Email or password was incrrect",Toast.LENGTH_SHORT).show();;
        }
    }

 */


    private void cheakEmailAndPassword() {
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Login Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                            Intent mainIntent= new Intent(getActivity(),MainActivity.class);
                            startActivity(mainIntent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(),"Email or Password was Wrong", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
    }
}
