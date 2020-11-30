package com.example.manyepay.editnotefragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.manyepay.R;
import com.example.manyepay.listnotesfragment.ListNotesFragment;
import com.example.manyepay.note.Note;
import com.example.manyepay.notificationhelper.AlarmNotifyReciever;
import com.example.manyepay.viewmodel.MainViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNoteFragment extends Fragment {
    private EditText datetext;
    private EditText summText;
    private EditText nameText;
    private Button addNote;
    private MainViewModel viewModel;
    Calendar dateAndTime= Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        datetext = (EditText) v.findViewById(R.id.datePay);
        summText = (EditText) v.findViewById(R.id.price);
        nameText = (EditText) v.findViewById(R.id.nameProduct);
        addNote = (Button) v.findViewById(R.id.add_note);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        addNote.setOnClickListener(addNoteListener);
        datetext.setOnClickListener(listener);
        setInitialDate(v);

        return v;
    }

    View.OnClickListener addNoteListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString().trim();
            int sum = Integer.parseInt(summText.getText().toString().trim());
            String date = datetext.getText().toString();
            if(!name.isEmpty()&&sum!=0){
                Note note = new Note(name, sum, date);
                viewModel.insertNote(note);
                ListNotesFragment listRecyclerFragment = new ListNotesFragment();

                Intent intent = new Intent(v.getContext(), AlarmNotifyReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent, 0);

                setAlarmMenedger(date, pendingIntent);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, listRecyclerFragment).commit();
            }else{
                Toast.makeText(v.getContext(), "Field is not be empty", Toast.LENGTH_SHORT).show();
            }

        }
    };

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(v.getContext(), timeListener, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true).show();
            new DatePickerDialog(v.getContext(), dateListener, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
        }
    };


    private void setInitialDate(View v){
        datetext.setText(DateUtils.formatDateTime(v.getContext(), dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDate(view);
        }
    };

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate(view);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarmMenedger(String dateWakeUp, PendingIntent pendingIntent){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("MMMM dd, yyyy, hh:mm a").parse(dateWakeUp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeWakeUp = dateFormat.getTime();

        System.out.println(timeWakeUp);

        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeWakeUp, pendingIntent);
    }
}
