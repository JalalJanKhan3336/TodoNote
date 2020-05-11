package com.jkstudio.todonote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton mAddNewNoteButton;
    private RecyclerView mRecyclerView;

    private List<TodoNote> mList;
    private NoteAdapter mAdapter;
    private Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initRef();
        click();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchAllNotes();
    }

    private void initRef() {
        mList = new ArrayList<>();
        mDatabase = Database.getInstance();
    }

    private void fetchAllNotes() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){ // User is logged in
            String userId = user.getUid();

            mDatabase.fetchAllNotes(userId, new DatabaseAllNotesFetchCallback() {
                @Override
                public void onAllNotesFetchedSuccess(List<DocumentSnapshot> notesDocumentsList, String msg) {
                    if(notesDocumentsList != null){

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        mList.clear();

                        for(int i = 0; i < notesDocumentsList.size(); i++){
                            DocumentSnapshot ds = notesDocumentsList.get(i);
                            TodoNote note = ds.toObject(TodoNote.class);

                            if(note != null){
                                mList.add(note);
                            }

                        }// for loop ends here
                    }

                    setUpRecyclerView();
                }

                @Override
                public void onAllNotesFetchedFailure(String error) {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }else {  // User is not logged in
            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setUpRecyclerView() {

        mAdapter = new NoteAdapter(this, mList);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddNewNoteButton = findViewById(R.id.add_new_note_btn);
    }

    private void click() {
        mAddNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}