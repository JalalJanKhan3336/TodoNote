package com.jkstudio.todonote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jkstudio.todonote.R;
import com.jkstudio.todonote.callback.ItemActionCallback;
import com.jkstudio.todonote.model.TodoNote;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context mContext;
    private List<TodoNote> mTodoNoteList;
    private ItemActionCallback mItemActionCallback;

    public NoteAdapter(Context mContext, List<TodoNote> mTodoNoteList, ItemActionCallback mItemActionCallback) {
        this.mContext = mContext;
        this.mTodoNoteList = mTodoNoteList;
        this.mItemActionCallback = mItemActionCallback;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        final TodoNote note = mTodoNoteList.get(position);

        holder.titleTV.setText(note.getTitle());
        holder.detailTV.setText(note.getDetail());
        holder.dateTV.setText(note.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemActionCallback.onNoteView(note, position);
            }
        });

        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemActionCallback.onNoteEdit(note, position);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemActionCallback.onNoteDelete(note,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTodoNoteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private ImageView editIV, deleteIV;
        private TextView titleTV, detailTV, dateTV;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.title_tv);
            detailTV = itemView.findViewById(R.id.detail_tv);
            dateTV = itemView.findViewById(R.id.date_tv);
            editIV = itemView.findViewById(R.id.edit_iv);
            deleteIV = itemView.findViewById(R.id.delete_iv);
        }
    }

}
