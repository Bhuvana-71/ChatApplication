package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.ActivitySignInBinding;
import com.example.whatsapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

binding= ActivitySignInBinding.inflate(getLayoutInflater());
getSupportActionBar().hide();

auth=FirebaseAuth.getInstance();

database=FirebaseDatabase.getInstance();

        setContentView(binding.getRoot());



 GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         .requestIdToken(getString(R.string.default_web_client_id))//new version no need of string
         .requestEmail()
         .build();



mGoogleSignInClient= GoogleSignIn.getClient(this,gso);










        progressDialog=new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");




        binding.btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 if(binding.etEmail.getText().toString().isEmpty())
 {
     binding.etEmail.setError("Enter your email");
     return;
 }
                if(binding.etPassword.getText().toString().isEmpty())
                {
                    binding.etPassword.setError("Enter your Passward");
                    return;
                }

               progressDialog.show();


               auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).addOnCompleteListener(
                       new OnCompleteListener<AuthResult>() {


                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressDialog.dismiss();

                               if(task.isSuccessful())
                               {
                                        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                               }
                               else
                               {
                                   Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                               }


                           }
                       });
            }
        });



        binding.tvclickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();


            }
        });



        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });









  if(auth.getCurrentUser()!=null)
  {
        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

  }









    }




    int RC_SIGN_IN=40;

    private void signIn()
    {
        Intent intent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==RC_SIGN_IN)
        {

            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());


            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }









    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful())
                        {
                            FirebaseUser user=auth.getCurrentUser();


                            Users users=new Users();
                            users.setUserId(user.getUid());
                            users.setUserName(user.getDisplayName());
                            users.setProfilepic(user.getPhotoUrl().toString());

                            database.getReference().child("Users").child(user.getUid()).setValue(users);



     Intent intent=new Intent(SignInActivity.this,MainActivity.class);
     startActivity(intent);
     finish();
     Toast.makeText(SignInActivity.this,"Sign In With Google",Toast.LENGTH_SHORT);


                        }
                        else {


                            Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT);

                        }









                    }
                });


    }
}