package com.example.e_commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public forgetPasswordFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static forgetPasswordFragment newInstance(String param1, String param2) {
        forgetPasswordFragment fragment = new forgetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText forgetEmail;
    private Button resetPasswordBtn;
    private TextView goBack;
    private FrameLayout parentframeLayout;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        forgetEmail = view.findViewById(R.id.forgetpassEmailAddress);
        resetPasswordBtn = view.findViewById(R.id.forgetPasswordBtn);
        goBack = view.findViewById(R.id.goBack);
        parentframeLayout = view.findViewById(R.id.register_framLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forgetEmail.addTextChangedListener(new TextWatcher() {
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
        
        //goBack Button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(forgetEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Check Your Email", Toast.LENGTH_SHORT).show();
                                    forgetEmail.getText().clear();
                                   // forgetTxt.setVisibility(view.VISIBLE);
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


/*
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(forgetEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Done sent",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"Error Occured",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        */

        /*
        //resetbtn working
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPasswordBtn.setEnabled(false);
                firebaseAuth.sendPasswordResetEmail(forgetEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    Toast.makeText(getActivity(), "Email send done", Toast.LENGTH_SHORT).show();
                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "Email error", Toast.LENGTH_SHORT).show();
                                }resetPasswordBtn.setEnabled(true);
                            }
                        });
            }
        });   */
    }


    //all extra method define section
    private void cheakInput() {
        if (!TextUtils.isEmpty(forgetEmail.getText())) {
            resetPasswordBtn.setEnabled(true);
        }else{
          resetPasswordBtn.setEnabled(false);
        }
    }


    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(R.id.register_framLayout,fragment);
        fragmentTransaction.commit();
    }
}