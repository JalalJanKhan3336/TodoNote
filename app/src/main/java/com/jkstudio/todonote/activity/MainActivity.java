package com.jkstudio.todonote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jkstudio.todonote.callback.ItemActionCallback;
import com.jkstudio.todonote.callback.RemoveCallback;
import com.jkstudio.todonote.database.Database;
import com.jkstudio.todonote.callback.DatabaseAllNotesFetchCallback;
import com.jkstudio.todonote.adapter.NoteAdapter;
import com.jkstudio.todonote.R;
import com.jkstudio.todonote.dialog.ViewNoteDialogFragment;
import com.jkstudio.todonote.model.TodoNote;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemActionCallback {

    private FloatingActionButton mAddNewNoteButton;
    private RecyclerView mRecyclerView;

    private List<TodoNote> mList;
    private NoteAdapter mAdapter;
    private Database mDatabase;

    private ViewNoteDialogFragment mViewNoteDialogFragment;

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
        mViewNoteDialogFragment = new ViewNoteDialogFragment();
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

        mAdapter = new NoteAdapter(this, mList, this);

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

    @Override
    public void onNoteView(TodoNote note, int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("note_key", note);

        mViewNoteDialogFragment.setArguments(bundle);
        mViewNoteDialogFragment.show(getSupportFragmentManager(), mViewNoteDialogFragment.getTag());
    }

    @Override
    public void onNoteEdit(TodoNote note, int position) {
        Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public void onNoteDelete(TodoNote note, int position) {
        showAlertDialog(note, position);
    }

    private void showAlertDialog(final TodoNote note, final int position) {

        String title = getResources().getString(R.string.confirm);
        String msg = getResources().getString(R.string.delete_confirm_msg);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                if(user != null){
                    String userId = user.getUid();
                    mDatabase.remove("Notes", userId, "UserNotes", note.getId(), new RemoveCallback() {
                        @Override
                        public void onRemovedSuccess(String msg) {
                            mList.remove(note);
                            mAdapter.notifyItemRemoved(position);
                        }

                        @Override
                        public void onRemovedFailure(String error) {

                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}