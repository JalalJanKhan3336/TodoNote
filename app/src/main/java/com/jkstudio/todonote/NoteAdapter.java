package com.jkstudio.todonote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context mContext;
    private List<TodoNote> mTodoNoteList;

    public NoteAdapter(Context mContext, List<TodoNote> mTodoNoteList) {
        this.mContext = mContext;
        this.mTodoNoteList = mTodoNoteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        TodoNote note = mTodoNoteList.get(position);

        holder.titleTV.setText(note.getTitle());
        holder.detailTV.setText(note.getDetail());
        holder.dateTV.setText(note.getDate());
    }

    @Override
    public int getItemCount() {
        return mTodoNoteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV, detailTV, dateTV;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.title_tv);
            detailTV = itemView.findViewById(R.id.detail_tv);
            dateTV = itemView.findViewById(R.id.date_tv);
        }
    }

}
