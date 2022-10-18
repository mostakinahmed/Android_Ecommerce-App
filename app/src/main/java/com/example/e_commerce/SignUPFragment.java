package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class SignUPFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUPFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignUPFragment newInstance(String param1, String param2) {
        SignUPFragment fragment = new SignUPFragment();
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

    private TextView alreadyHaveAccount;
    private FrameLayout parentframelayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText email;
    private EditText name;
    private EditText password;
    private  EditText editTextPhone;
    private EditText confirm_password;
    private Button signUp;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAccount= view.findViewById(R.id.alreadyHaveAnAccount);
        parentframelayout = getActivity().findViewById(R.id.register_framLayout);
        email = view.findViewById(R.id.sign_up_EmailAddress);
        name = view.findViewById(R.id.sign_up_Name);
        password = view.findViewById(R.id.sign_up_Password);
        confirm_password = view.findViewById(R.id.sign_up_confirmPassword);
        signUp = view.findViewById(R.id.signUpBtn);
      //  editTextPhone = view.findViewById(R.id.editTextPhone);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });


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

        name.addTextChangedListener(new TextWatcher() {
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

        confirm_password.addTextChangedListener(new TextWatcher() {
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

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                cheakInput();;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to firebase
                cheakEmailAndPasssword();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    private  void cheakInput(){
        if (! TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(name.getText())){
                if (!TextUtils.isEmpty(password.getText())){
                    if (!TextUtils.isEmpty(confirm_password.getText())){
                        signUp.setEnabled(true);
                    }else{
                        signUp.setEnabled(false);
                    }
                }else{
                    signUp.setEnabled(false);
                }

            }else{
                signUp.setEnabled(false);
            }

        }else{
            signUp.setEnabled(false);
        }
    }


    private void cheakEmailAndPasssword() {
        if (email.getText().toString().matches(emailPattern)) {
           // if (password.getText().toString().equals(confirm_password.getText().toString())) {
               // if (password.length() >= 8) {
            if (password.length() >= 8) {
                if (password.getText().toString().equals(confirm_password.getText().toString())) {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Map<Object, String> userdata = new HashMap<>();
                                        userdata.put("Full Name", name.getText().toString());
                                        userdata.put("password", password.getText().toString());
                                        userdata.put("Phone",editTextPhone.getText().toString());
                                        firebaseFirestore.collection("USERS")
                                                .add(userdata)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isComplete()) {
                                                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                                            Toast.makeText(getActivity(), "signup completed", Toast.LENGTH_SHORT).show();
                                                            startActivity(mainIntent);
                                                            getActivity().finish();

                                                        } else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else{
                    Toast.makeText(getActivity(), "Don't match the password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(), "Password is too short", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(), "Email Not Valid", Toast.LENGTH_SHORT).show();
        }

    }
}