package com.example.manyepay.listnotesfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manyepay.R;
import com.example.manyepay.editnotefragment.EditNoteFragment;
import com.example.manyepay.note.Note;
import com.example.manyepay.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ListNotesFragment extends Fragment {


    private RecyclerView recyclerView;
    private AdapterFragment adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton actionBtn;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_notes, container, false);recyclerView = (RecyclerView) v.findViewById(R.id.recyclerNotes);

        actionBtn = (FloatingActionButton) v.findViewById(R.id.floatingActionButtonAddNote);
        actionBtn.setOnClickListener(listener);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        adapter = new AdapterFragment();

        getData();

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.setListener(new AdapterFragment.OnNoteClickListener() {
            @Override
            public void onLongCLick(int position) {
                onRemove(position);
            }

            @Override
            public void onNoteClick(int position) {

            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                onRemove(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(recyclerView);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        return v;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        getData();
//    }

    private void onRemove(int position) {
        Note note = adapter.getNotes().get(position);
        viewModel.DeleteNote(note);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditNoteFragment fragment = new EditNoteFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragment).commit();
        }
    };

    private void getData(){
        LiveData<List<Note>> notesFromDB = viewModel.getNotes();

        System.out.println(notesFromDB.getValue()==null);
        notesFromDB.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                System.out.println(notes.size());
                adapter.setNotes(notes);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}