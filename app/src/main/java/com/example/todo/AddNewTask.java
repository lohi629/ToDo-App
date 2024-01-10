package com.example.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.Adapter.ToDoAdapter;
import com.example.todo.Model.ToDoModel;
import com.example.todo.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG="AddNewTask";


    //widgets
    private EditText mEditText;
    private EditText mEditText2;
    private EditText mEditText3;
    private Button mSaveButton;
    private DataBaseHelper myDb;
    private TextView textView;
    private ToDoAdapter adapter;
    private Spinner mSpinnerCategory;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_new_task,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText=view.findViewById(R.id.edittext);
        mEditText2=view.findViewById(R.id.edittext2);
        mEditText3=view.findViewById(R.id.edittext3);
        mSaveButton=view.findViewById(R.id.button_save);
        mSpinnerCategory = view.findViewById(R.id.spinnerCategory);
        setupSpinner();

        myDb=new DataBaseHelper(getActivity());


        boolean isUpdate=false;

        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task= bundle.getString("task");
            mEditText.setText(task);
            if(task.length() >0)
            {
               mSaveButton.setEnabled(false);
            }
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().equals("")) {
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }else{
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=mEditText.getText().toString();
                String priority=mEditText2.getText().toString();
                String des=mEditText3.getText().toString();
                String category = mSpinnerCategory.getSelectedItem().toString();


                if(finalIsUpdate){
                    myDb.updateTask(bundle.getInt("id"),text,priority,des,category);

                }else{
                    ToDoModel item=new ToDoModel();


                    item.setTask(text);
                    item.setStatus(0);
                    item.setPriority(priority);
                    item.setDesc(des);
                    item.setCategory(category);
                    myDb.insertTask(item);



                }
                dismiss();
            }
        });


    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);

        }
    }
    private void setupSpinner() {

        String[] categories = {"Home ", "Personal", "Office","Work","Health"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mSpinnerCategory.setAdapter(adapter);
    }

}
