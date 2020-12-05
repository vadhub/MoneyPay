package com.example.manyepay.editnotefragment;

import android.app.Activity;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.manyepay.R;
import com.example.manyepay.listnotesfragment.ListNotesFragment;
import com.example.manyepay.note.Note;
import com.example.manyepay.notificationhelper.AlarmNotifyReciever;
import com.example.manyepay.reqestcodes.RequestCode;
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
    private Spinner spinner;
    private String[] valuteItem;
    private int positionElem;

//    private SharedPreferences sPref;

    Calendar dateAndTime= Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);
        datetext = (EditText) v.findViewById(R.id.datePay);
        summText = (EditText) v.findViewById(R.id.price);
        nameText = (EditText) v.findViewById(R.id.nameProduct);
        addNote = (Button) v.findViewById(R.id.add_note);
        spinner = (Spinner) v.findViewById(R.id.valute);
        positionElem = 0;

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        valuteItem = getActivity().getResources().getStringArray(R.array.valute_item);

        ArrayAdapter<String> adapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1, valuteItem);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listenerSprnner);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        addNote.setOnClickListener(addNoteListener);
        datetext.setOnClickListener(listener);
        setInitialDate(v);

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new ListNotesFragment()).commit();
                getActivity().onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private AdapterView.OnItemSelectedListener listenerSprnner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            positionElem = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener addNoteListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString().trim();
            int sum = Integer.parseInt(summText.getText().toString().trim());
            String date = datetext.getText().toString();
            //for request code
            int systemCurrentTime =(int) System.currentTimeMillis();
            String key = name+sum;

            if(!name.isEmpty()&&sum!=0){
                Note note = new Note(name, sum, date, valuteItem[positionElem]);
                viewModel.insertNote(note);
                ListNotesFragment listRecyclerFragment = new ListNotesFragment();

                Intent intent = new Intent(v.getContext(), AlarmNotifyReciever.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(),systemCurrentTime, intent, 0);
                RequestCode requestCode = new RequestCode(systemCurrentTime, key);

                System.out.println(key+" KEY1");
                viewModel.insertCode(requestCode);
                //sendMessage(name, date);
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

        long timeWakeUp = convertDate(dateWakeUp);

        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeWakeUp, pendingIntent);
    }

//    private void sendMessage(int requestCode){
//        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sPref.edit();
//        editor.putInt(dateMsg, requestCode);
//        editor.commit();
//
//    }

    private long convertDate(String dateText){
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("MMMM dd, yyyy, hh:mm a").parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeWakeUp = dateFormat.getTime();

        return timeWakeUp;

    }
}
