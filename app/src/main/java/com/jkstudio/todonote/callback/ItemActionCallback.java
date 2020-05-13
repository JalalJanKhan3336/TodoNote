package com.jkstudio.todonote.callback;

import com.jkstudio.todonote.model.TodoNote;

public interface ItemActionCallback {
    void onNoteView(TodoNote note, int position);
    void onNoteEdit(TodoNote note, int position);
    void onNoteDelete(TodoNote note, int position);
}
