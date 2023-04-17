package com.example.mytodoapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mytodoapplication.Model.ToDoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = AddNewTask.class.getName();
    private TextView setDueDate;
    private EditText mTaskEdit;
    private Button mSaveBtn;
//    private FirebaseFirestore firestore;
    private Context context;
    private String dueDate = "";

    public static AddNewTask newInstance(){
        return new AddNewTask();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.set_due_tv);
        mTaskEdit = view.findViewById(R.id.task_edittext);
        mSaveBtn = view.findViewById(R.id.save);

//        firestore = FirebaseFirestore.getInstance();

        mTaskEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().equals("")){
//                    mSaveBtn.setEnabled(false);
//                    mSaveBtn.setBackgroundColor(Color.GRAY);
//                }
//                else{
//                    mSaveBtn.setEnabled(true);
//                    mSaveBtn.setBackgroundColor(getResources().getColor(R.color.green_blue));
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       month = month + 1;
                       setDueDate.setText(dayOfMonth + "/" + month + "/" + year);
                       dueDate = dayOfMonth + "/" + month + "/" + year;

                    }
                },YEAR,MONTH,DAY);
                datePickerDialog.show();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = mTaskEdit.getText().toString();

                if (task.isEmpty()) {
                    Toast.makeText(context, "Empty Task not Allowed !!", Toast.LENGTH_LONG).show();
                }
                else{
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("task",task);
                    taskMap.put("due", dueDate);
                    taskMap.put("status",0);

                    //sending to firebase
                   // video 2 16:28 https://www.youtube.com/watch?v=MoIVCfNwJf8&list=PLhhNsarqV6MRXyyjFRjm4Emj25KV-SSmu&index=3

                }
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListner){
            ((onDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
