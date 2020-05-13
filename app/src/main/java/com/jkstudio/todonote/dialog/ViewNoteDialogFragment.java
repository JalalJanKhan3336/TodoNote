package com.jkstudio.todonote.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkstudio.todonote.R;
import com.jkstudio.todonote.model.TodoNote;

public class ViewNoteDialogFragment extends DialogFragment {

    private TextView mTitle_TV, mDetail_TV;

    public ViewNoteDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog dialog = null;

        if(getArguments() != null){

            TodoNote note = (TodoNote) getArguments().getSerializable("note_key");

            if(note != null){

                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_view_note_dialog, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view);

                dialog = builder.create();

                mTitle_TV = view.findViewById(R.id.title_tv);
                mDetail_TV = view.findViewById(R.id.detail_tv);

                mTitle_TV.setText(note.getTitle());
                mDetail_TV.setText(note.getDetail());
            }
        }

        return dialog;
    }

}