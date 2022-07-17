package com.example.taskmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.UUID;

public class DetailDialogFragment extends DialogFragment {

    public static final String DATE_PICKER = "Date_picker";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String TIME_PICKER = "Time_picker";
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    private Task mTask;

    private EditText editText_title;
    private EditText editText_description;
    private CheckBox checkBoxDone;
    private CheckBox checkBoxInProgress;
    private Button buttonDate;
    private Button buttonTime;
    private TextView dateAndTime;
    private boolean isDone;
    private boolean isInProgress;

    private Calendar calendar = Calendar.getInstance();

    public static DetailDialogFragment newInstance(UUID taskUUID) {

        Bundle args = new Bundle();

        DetailDialogFragment fragment = new DetailDialogFragment();
        args.putSerializable("taskId",taskUUID);
        fragment.setArguments(args);

        return fragment;
    }

    public DetailDialogFragment() {
        // Required empty public constructor
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_dialog, null);
        iniUi(view);

        UUID taskId = (UUID) getArguments().getSerializable("taskId");
        mTask = Repository.getInstance().getTask(taskId);
        isDone = mTask.isDone();
        if (isDone) {
            checkBoxInProgress.setEnabled(false);
        }
        isInProgress = mTask.isInProgress();
        if (isInProgress) {
            checkBoxDone.setEnabled(false);
        }

        setTask();

        setListener();

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (mTask.isDone() != isDone || mTask.isInProgress() != isInProgress) {
                                if (isDone) {
                                    try {
                                        Repository.getInstance().deleteTask("done", mTask);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else if (isInProgress) {
                                    try {
                                        Repository.getInstance().deleteTask("inProgress", mTask);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    try {
                                        Repository.getInstance().deleteTask("toBeDone", mTask);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                Repository.getInstance().addTask(mTask);
                            }else {
                                Repository.getInstance().updateTask(mTask);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mTask.isDone()) {
                            try {
                                Repository.getInstance().deleteTask("done", mTask);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (mTask.isInProgress()) {
                            try {
                                Repository.getInstance().deleteTask("inProgress", mTask);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {
                                Repository.getInstance().deleteTask("toBeDone", mTask);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setView(view)
                .show();
    }

    private void setListener() {
        editText_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(calendar.getTime());
                fragment.setTargetFragment(DetailDialogFragment.this,REQUEST_CODE_DATE_PICKER);

                fragment.show(getFragmentManager(), DATE_PICKER);
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment fragment = TimePickerFragment.newInstance(calendar.getTime());
                fragment.setTargetFragment(DetailDialogFragment.this,REQUEST_CODE_TIME_PICKER);

                fragment.show(getFragmentManager(), TIME_PICKER);
            }
        });

        checkBoxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    checkBoxInProgress.setEnabled(false);
                    mTask.setDone(true);
                }else {
                    checkBoxInProgress.setEnabled(true);
                    mTask.setDone(false);
                }
            }
        });

        checkBoxInProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    checkBoxDone.setEnabled(false);
                    mTask.setInProgress(true);
                }else {
                    checkBoxDone.setEnabled(true);
                    mTask.setInProgress(false);
                }
            }
        });
    }

    private void setTask() {
        editText_title.setText(mTask.getTitle());
        editText_description.setText(mTask.getDescription());
        checkBoxDone.setChecked(mTask.isDone());
        checkBoxInProgress.setChecked(mTask.isInProgress());
        dateAndTime.setText(mTask.getDate().toString());
    }

    private void iniUi(View view) {
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
            mTask.setDate(date);

            dateAndTime.setText(date.toString());
        }
        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Calendar cal = (Calendar) data.getSerializableExtra(TimePickerFragment.getExtraCrimeTime());
            calendar.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE,cal.get(Calendar.MINUTE));
            Date date = calendar.getTime();
            mTask.setDate(date);

            dateAndTime.setText(date.toString());
        }
    }
}
