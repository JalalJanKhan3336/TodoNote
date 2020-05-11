package com.jkstudio.todonote;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface DatabaseAllNotesFetchCallback {
    void onAllNotesFetchedSuccess(List<DocumentSnapshot> notesDocumentsList, String msg);
    void onAllNotesFetchedFailure(String error);
}
