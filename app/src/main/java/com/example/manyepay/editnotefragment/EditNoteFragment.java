package com.example.manyepay.editnotefragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.manyepay.R;
import com.example.manyepay.listnotesfragment.ListNotesFragment;
import com.example.manyepay.listnotesfragment.OnBackPressed;
import com.example.manyepay.note.Note;
import com.example.manyepay.notificationhelper.AlarmNotifyReciever;
import com.example.manyepay.reqestcodes.RequestCode;
import com.example.manyepay.utils.UtilAlarmSet;
import com.example.manyepay.viewmodel.MainViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.manyepay.R.string.date_format;
import static com.example.manyepay.R.string.item_view_role_description;

public class EditNoteFragment extends Fragment implements OnBackPressed{
    private EditText datetext;
    private EditText summText;
    private EditText nameText;
    private EditText textRepeatdat;
    private Button addNote;
    private MainViewModel viewModel;
    private Spinner spinner;
    private LinearLayout ll;
    private ViewGroup.LayoutParams params;
    private Spinner otherDate;

    private String[] otherDateItem;
    private String[] valuteItem;
    private Spinner repeatSpin;
    private String[] repeatDate;
    private int positionElem;
    private int positionOtherDate;
    private long timeRepeat;
    private String dateSt;
    private UtilAlarmSet alarmSet;

    private Calendar dateAndTime= Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        View v = inflater.inflate(R.layout.content_main, container, false);



        datetext = (EditText) v.findViewById(R.id.datePay);
        summText = (EditText) v.findViewById(R.id.price);
        nameText = (EditText) v.findViewById(R.id.nameProduct);

        alarmSet = new UtilAlarmSet();

        addNote = (Button) v.findViewById(R.id.add_note);

        spinner = (Spinner) v.findViewById(R.id.valute);
        repeatSpin = (Spinner) v.findViewById(R.id.repeatSpenner);
        otherDate = (Spinner) v.findViewById(R.id.repeatSpinnerOther);

        ll = (LinearLayout) v.findViewById(R.id.sublinear);
        textRepeatdat = (EditText) v.findViewById(R.id.otherdateD);

        params = ll.getLayoutParams();
        
        positionElem = 0;
        positionOtherDate =0;
        timeRepeat = 0;

        valuteItem = getActivity().getResources().getStringArray(R.array.valute_item);
        repeatDate = getActivity().getResources().getStringArray(R.array.repeat);
        otherDateItem = getActivity().getResources().getStringArray(R.array.repeatother);

        ArrayAdapter<String> adapter = new ArrayAdapter(v.getContext(),android.R.layout.simple_list_item_1, valuteItem);
        ArrayAdapter<String> adapterForRepeat = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, repeatDate);
        ArrayAdapter<String> adapterOtherRepeat = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, otherDateItem);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listenerSprnner);
        
        repeatSpin.setAdapter(adapterForRepeat);
        repeatSpin.setOnItemSelectedListener(listenerSpinRepeat);

        otherDate.setAdapter(adapterOtherRepeat);
        otherDate.setOnItemSelectedListener(listenerOtherDate);
        
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        addNote.setOnClickListener(addNoteListener);
        datetext.setOnClickListener(listener);
        setInitialDate(v);

        System.out.println(KeyEvent.isGamepadButton(KeyEvent.KEYCODE_BACK));


        return v;
    }

    private AdapterView.OnItemSelectedListener listenerOtherDate = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            positionOtherDate = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private AdapterView.OnItemSelectedListener listenerSpinRepeat = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int height = 0;
            switch (position){
                case 0:
                    timeRepeat = UtilAlarmSet.getMilessecWeek();
                    break;
                case 1:
                    timeRepeat = UtilAlarmSet.getMilessecMonth();
                    break;
                case 2:
                    timeRepeat = UtilAlarmSet.getMilessecYear();
                    break;
                case 3:
                   height = ViewGroup.LayoutParams.MATCH_PARENT;
                    break;
            }

            params.height = height;
            ll.setLayoutParams(params);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //for change period time
    private long setTimeInterval(int positionElem){

        dateSt = textRepeatdat.getText().toString();

        if(!dateSt.equals("")){
            int repeat = Integer.parseInt(dateSt);
            switch (positionElem){
                case 0:
                    return repeat*UtilAlarmSet.getMilessecMin();

                case 1:
                    return repeat*UtilAlarmSet.getMilessecHour();

                case 2:
                    return repeat*UtilAlarmSet.getMilessecWeek();

                case 3:
                    return repeat*UtilAlarmSet.getMilessecMonth();

                case 5:
                    return repeat*UtilAlarmSet.getMilessecYear();

                default:
                    return repeat*UtilAlarmSet.getMilessecMin();
            }
        }

        return timeRepeat;

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
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            if(!summText.getText().equals("")){
            String name = nameText.getText().toString().trim();
            int sum = Integer.parseInt(summText.getText().toString().trim());
            long date = dateAndTime.getTimeInMillis();

            //for request code notification
            int systemCurrentTime =(int) System.currentTimeMillis();
            String key = name+sum;

                if(!name.isEmpty()&&sum!=0){
                    Note note = new Note(name, sum, date, valuteItem[positionElem]);
                    viewModel.insertNote(note);
                    ListNotesFragment listRecyclerFragment = new ListNotesFragment();

                    //start alarm
                    Intent intent = new Intent(v.getContext(), AlarmNotifyReciever.class);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    intent.putExtra("requestCode", systemCurrentTime);
                    intent.putExtra("timeRepeat",setTimeInterval(positionOtherDate));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(),systemCurrentTime, intent, 0);
                    RequestCode requestCode = new RequestCode(systemCurrentTime, key);

                    viewModel.insertCode(requestCode);
                    //sendMessage(name, date);
                    alarmSet.setAlarmMenedger(v.getContext(),pendingIntent, setTimeInterval(positionOtherDate), dateAndTime.getTimeInMillis());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, listRecyclerFragment).commit();
                }else{
                    Toast.makeText(v.getContext(), "Field is not be empty", Toast.LENGTH_SHORT).show();
                }
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
        datetext.setText(DateUtils.formatDateTime(v.getContext(), dateAndTime.getTimeInMillis(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
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


    @Override
    public boolean onBackPress() {
        if (KeyEvent.isGamepadButton(KeyEvent.KEYCODE_BACK)) {
            return true;
        } else {
            return false;
        }
    }
}
