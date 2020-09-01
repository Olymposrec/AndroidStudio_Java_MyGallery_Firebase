package com.melihakkose.mygalleryfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore  firebaseFirestore;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> usercommentFromFB;
    ArrayList<String> userimageFromFB;
    FeedRecyclerAdapter feedRecyclerAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gallery_options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);

        }else if(item.getItemId()==R.id.sign_out){
            firebaseAuth.signOut();
            Intent intent =new Intent(FeedActivity.this,SignUpActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        usercommentFromFB =new ArrayList<>();
        userEmailFromFB=new ArrayList<>();
        userimageFromFB=new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getDataFromFirestore();

        //RECYCLERVIEW
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedRecyclerAdapter=new FeedRecyclerAdapter(userEmailFromFB,usercommentFromFB,userimageFromFB);

        recyclerView.setAdapter(feedRecyclerAdapter);

    }

    public void getDataFromFirestore(){

        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error !=null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if(value !=null){
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data= snapshot.getData();

                        //CASTING
                        String comment =(String) data.get("comment");
                        String userEmail=(String) data.get("useremail");
                        String downloadUrl=(String) data.get("downloadurl");

                        //LISTLERE EKLIYORUZ
                        usercommentFromFB.add(comment);
                        userEmailFromFB.add(userEmail);
                        userimageFromFB.add(downloadUrl);
                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}