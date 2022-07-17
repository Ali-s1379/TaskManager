package com.example.taskmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.models.Repository;
import com.example.taskmanager.models.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddDialogFragment extends DialogFragment {

    public static final String DATE_PICKER = "Date_picker";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String TIME_PICKER = "Time_picker";
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    private EditText editText_title;
    private EditText editText_description;
    private CheckBox checkBoxDone;
    private CheckBox checkBoxInProgress;
    private Button buttonDate;
    private Button buttonTime;
    private TextView dateAndTime;
    private Date mDate = new GregorianCalendar().getTime();
    private Calendar calendar = Calendar.getInstance();

    public static AddDialogFragment newInstance() {

        Bundle args = new Bundle();

        AddDialogFragment fragment = new AddDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddDialogFragment() {
        // Required empty public constructor
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_dialog, null);
        initUi(view);

        setListener();


        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Repository repository = Repository.getInstance();
                        Task task = new Task(editText_title.getText().toString(),
                                editText_description.getText().toString(),
                                mDate,
                                checkBoxDone.isChecked(),
                                checkBoxInProgress.isChecked());
                        repository.addTask(task);
                    }
                })
                .setView(view)
                .show();
    }

    private void setListener() {
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(calendar.getTime());
                fragment.setTargetFragment(AddDialogFragment.this,REQUEST_CODE_DATE_PICKER);

                fragment.show(getFragmentManager(), DATE_PICKER);
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment fragment = TimePickerFragment.newInstance(calendar.getTime());
                fragment.setTargetFragment(AddDialogFragment.this,REQUEST_CODE_TIME_PICKER);

                fragment.show(getFragmentManager(), TIME_PICKER);
            }
        });

        checkBoxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    checkBoxInProgress.setEnabled(false);
                }else checkBoxInProgress.setEnabled(true);
            }
        });

        checkBoxInProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    checkBoxDone.setEnabled(false);
                }else checkBoxDone.setEnabled(true);
            }
        });
    }

    private void initUi(View view) {
        editText_title = view.findViewById(R.id.edit_text_title);
        editText_description = view.findViewById(R.id.edit_text_description);
        checkBoxDone = view.findViewById(R.id.checkBox_done);
        checkBoxInProgress = view.findViewById(R.id.checkBox_inProgress);
        buttonDate = view.findViewById(R.id.button_date);
        buttonTime = view.findViewById(R.id.button_time);
        dateAndTime = view.findViewById(R.id.textView_date_time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Calendar cal = (Calendar) data.getSerializableExtra(DatePickerFragment.getExtraCrimeDate());
            calendar.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
            Date date = calendar.getTime();
            mDate = date;

            dateAndTime.setText(date.toString());
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Calendar cal = (Calendar) data.getSerializableExtra(TimePickerFragment.getExtraCrimeTime());
            calendar.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,cal.get(Calendar.MINUTE));
            Date date = calendar.getTime();
            mDate = date;

            dateAndTime.setText(date.toString());
        }
    }
}

