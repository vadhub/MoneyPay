package com.example.manyepay.listnotesfragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manyepay.R;
import com.example.manyepay.note.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AdapterFragment extends RecyclerView.Adapter<AdapterFragment.MyViewHolder>{
    private List<Note> notes;

    private static final String DATE_FORMAT = "d MMM yyyy HH:mm";

    private OnNoteClickListener listener;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(notes==null) return 0;
        return notes.size();
    }

    public OnNoteClickListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickListener listener) {
        this.listener = listener;
    }


    interface OnNoteClickListener{
        void onNoteClick(int position);
        void onLongCLick(int position);
    }

    @NonNull
    @Override
    public AdapterFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(notes.get(position).getNameProduct());
        holder.summ.setText(String.valueOf(notes.get(position).getSumm()));
        holder.date.setText(converterDate(notes.get(position).getDate()));

    }

    @SuppressLint("SimpleDateFormat")
    private String converterDate(String date){
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("MMMM dd, yyyy, hh:mm a").parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat(DATE_FORMAT);
            System.out.println(dateFormat.toString());
            return newFormat.format(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            return "date not convert";
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, summ, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_product_text);
            summ = (TextView) itemView.findViewById(R.id.summ_text);
            date = (TextView) itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        listener.onNoteClick(getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener !=null){
                        listener.onLongCLick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
