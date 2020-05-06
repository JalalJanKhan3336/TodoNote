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
        generateList();
    }

    private void initRef() {
        mList = new ArrayList<>();
        mDatabase = Database.getInstance();
    }

    private void generateList() {
        mList.clear();

        for(int i = 0; i < 20; i++){
            String title = "Note Title "+i;
            String detail = "Note Detail "+i;

            TodoNote note = new TodoNote(null,title, detail);
            mList.add(note);
        }

        setUpRecyclerView();
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