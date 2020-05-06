package com.jkstudio.todonote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {

    private static final String TAG = "Database";

    private static Database instance;

    public static Database getInstance() {

        if(instance == null)
            instance = new Database();

        return instance;
    }

    private FirebaseFirestore mRootRef;

    private Database(){
        mRootRef = FirebaseFirestore.getInstance();
    }

    public void addNote(TodoNote note, final DatabaseAdditionCallback listener){
        mRootRef
                .collection("Notes")
                .document(note.getId())
                .set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){ // Successfully Added to Database
                            Log.d(TAG, "_onComplete_Success: Note added to Database");
                            listener.onAdditionSuccess("Note Saved");
                        }else { // Some Error Occurred
                            Log.d(TAG, "_onComplete_Error: "+task.getException().getMessage());
                            listener.onAdditionFailure(task.getException().getMessage());
                        }
                    }
                });
    }

}
