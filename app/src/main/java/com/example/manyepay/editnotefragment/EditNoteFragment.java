package com.example.manyepay.editnotefragment;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.manyepay.R.string.date_format;

public class EditNoteFragment extends Fragment {
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
    private long timeRepeat;
    String dateSt;

    private static final long MILESSEC_MIN = 60*1000;
    private static final long MILESSEC_HOUR = 60*60*1000;
    private static final long MILESSEC_WEEK = 604800000;
    private static final long MILESSEC_MONTH = 2628002880L;
    private static final long MILESSEC_YEAR = 31536000000L;


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
        repeatSpin = (Spinner) v.findViewById(R.id.repeatSpenner);
        otherDate = (Spinner) v.findViewById(R.id.repeatSpinnerOther);

        ll = (LinearLayout) v.findViewById(R.id.sublinear);
        textRepeatdat = (EditText) v.findViewById(R.id.otherdateD);

        params = ll.getLayoutParams();
        
        positionElem = 0;
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


        return v;
    }

    private AdapterView.OnItemSelectedListener listenerOtherDate = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            timeRepeat=0;

            dateSt = textRepeatdat.getText().toString();
            System.out.println("timerep1: "+timeRepeat+" dst: "+ textRepeatdat.getText());
            if(!dateSt.equals("")){
                int repeat = Integer.parseInt(dateSt);
                switch (position){
                    case 0:
                        timeRepeat = repeat*MILESSEC_MIN;
                        System.out.println("timerep2: "+timeRepeat+" rep: "+repeat+" ");
                        break;
                    case 1:
                        timeRepeat = repeat*MILESSEC_HOUR;
                        break;
                    case 2:
                        timeRepeat = repeat*MILESSEC_WEEK;
                        break;
                    case 3:
                        timeRepeat = repeat*MILESSEC_MONTH;
                        break;
                    case 5:
                        timeRepeat = repeat*MILESSEC_YEAR;
                        break;
                    default:
                        timeRepeat = repeat*MILESSEC_MIN;
                }
            }

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
                    timeRepeat = MILESSEC_WEEK;
                    break;
                case 1:
                    timeRepeat = MILESSEC_MONTH;
                    break;
                case 2:
                    timeRepeat = MILESSEC_YEAR;
                    break;
                case 3:
                   height = ViewGroup.LayoutParams.MATCH_PARENT;
                   timeRepeat=0;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + position);
            }

            params.height = height;
            ll.setLayoutParams(params);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
            //for request code
            int systemCurrentTime =(int) System.currentTimeMillis();
            String key = name+sum;

            if(!name.isEmpty()&&sum!=0){
                Note note = new Note(name, sum, date, valuteItem[positionElem]);
                viewModel.insertNote(note);
                ListNotesFragment listRecyclerFragment = new ListNotesFragment();

                Intent intent = new Intent(v.getContext(), AlarmNotifyReciever.class);
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(),systemCurrentTime, intent, 0);
                RequestCode requestCode = new RequestCode(systemCurrentTime, key);

                viewModel.insertCode(requestCode);
                //sendMessage(name, date);
                setAlarmMenedger(pendingIntent);

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setAlarmMenedger(PendingIntent pendingIntent){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        long timeWakeUp = dateAndTime.getTimeInMillis();

        assert alarmManager != null;
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeWakeUp, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeRepeat, pendingIntent);
        System.out.println(timeRepeat);

    }

}
