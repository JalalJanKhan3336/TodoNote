package com.jkstudio.todonote.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jkstudio.todonote.activity.MainActivity;
import com.jkstudio.todonote.callback.DatabaseAdditionCallback;
import com.jkstudio.todonote.callback.DatabaseAllNotesFetchCallback;
import com.jkstudio.todonote.model.TodoNote;

public class Database {

    private static final String NOTES_COLLECTION = "Notes";
    private static final String USER_NOTES_SUB_COLLECTION = "UserNotes";

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

    public void addNote(String userId, TodoNote note, final DatabaseAdditionCallback listener){
        mRootRef
                .collection(NOTES_COLLECTION)
                .document(userId)
                .collection(USER_NOTES_SUB_COLLECTION)
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

    // Fetches all Notes from Database
    public void fetchAllNotes(String userId, final DatabaseAllNotesFetchCallback listener){
        mRootRef
                .collection(NOTES_COLLECTION)
                .document(userId)
                .collection(USER_NOTES_SUB_COLLECTION)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "_on_fetchAllNotes_Success: "+queryDocumentSnapshots.getDocuments());
                        listener.onAllNotesFetchedSuccess(queryDocumentSnapshots.getDocuments(), "All Notes Retrieved");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "_on_fetchAllNotes_Failure: "+e.getMessage());
                        listener.onAllNotesFetchedFailure(e.getMessage());
                    }
                });
    }

}
