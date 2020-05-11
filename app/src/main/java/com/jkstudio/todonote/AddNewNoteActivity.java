package com.jkstudio.todonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class AddNewNoteActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private EditText mTitle_ET, mDetail_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        initView();
        initRef();
    }

    private void initRef() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }

    private void initView() {
        TextInputLayout titleLayout = findViewById(R.id.title_til);
        TextInputLayout detailLayout = findViewById(R.id.detail_til);

        mTitle_ET = titleLayout.getEditText();
        mDetail_ET = detailLayout.getEditText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_save){ // Save Option is Clicked

            String id = UUID.randomUUID().toString();
            String title = mTitle_ET.getText().toString().trim();
            String detail = mDetail_ET.getText().toString().trim();

            if(isEmpty(mTitle_ET, mDetail_ET) == false){
                // Save Note to Database
                mProgressDialog.setMessage("Saving note... please wait.");
                mProgressDialog.show();
                TodoNote note = new TodoNote(id, title, detail);
                saveToDatabase(note);
            }


            return true;
        }else if(item.getItemId() == R.id.action_cancel){ // Cancel Option is Clicked
            onBackPressed();
            return true;
        }

        return false;
    }

    // Saves Given Note to Database
    private void saveToDatabase(TodoNote note) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){ // user is logged in
            String userId = user.getUid();

            Database database = Database.getInstance();
            database.addNote(userId, note, new DatabaseAdditionCallback() {
                @Override
                public void onAdditionSuccess(String msg) {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddNewNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdditionFailure(String error) {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddNewNoteActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }else { // User is not logged in
            Intent intent = new Intent(AddNewNoteActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean isEmpty(EditText et1, EditText et2) {
        String title = mTitle_ET.getText().toString().trim();
        String detail = mDetail_ET.getText().toString().trim();

        if(TextUtils.isEmpty(title)){
            et1.setError("Title is required");
            return true;
        }

        if(TextUtils.isEmpty(detail)){
            et2.setError("Detail is required");
            return true;
        }

        return false; // if not empty
    }
}