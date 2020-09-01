package com.melihakkose.mygalleryfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    //FIREBASE OBJE TANIMLAMASI
    private FirebaseAuth firebaseAuth;

    //Kullanicidan Bilgileri Almak Icin Tanimlamalar
    EditText mailText,passwordText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //INITIALIZE
        firebaseAuth=FirebaseAuth.getInstance();

        mailText=findViewById(R.id.mailText);
        passwordText=findViewById(R.id.passwordText);

        //User kontrol (Daha once giris yapti mi?)
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            Intent intent=new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void  signInClicked(View view){

        //EDIT TEXTLERI DEGISKENLERE ATAYALIM
        String email=mailText.getText().toString();
        String password=passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this,"Sign In Success!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void  signUpClicked(View view){

        //EDIT TEXTLERI DEGISKENLERE ATAYALIM
        String email=mailText.getText().toString();
        String password=passwordText.getText().toString();

        //Listener callback icin gerekli
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this,"User Created!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });




    }
}