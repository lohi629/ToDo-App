package com.example.todo.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AddNewTask;
import com.example.todo.MainActivity;
import com.example.todo.Model.ToDoModel;
import com.example.todo.R;
import com.example.todo.Utils.DataBaseHelper;

import java.util.Calendar;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private static List<ToDoModel> mList;
    public static MainActivity activity;
    private DataBaseHelper myDB;
    private int status;

    public ToDoAdapter(DataBaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = mList.get(position);


        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.textView2.setText("Priority"+":"+item.getPriority());
        holder.textView3.setText(item.getDesc());
        holder.textView4.setText("Due Date"+":"+item.getDue());
        holder.textView5.setText("Category: "+item.getCategory());

        holder.textView.setText("Status : Ongoing");
        if (item.getPriority().equalsIgnoreCase("HIGH")) {
            holder.itemView.setBackgroundResource(R.drawable.task_high_priority_bg);
        } else if (item.getPriority().equalsIgnoreCase("MEDIUM")) {
            holder.itemView.setBackgroundResource(R.drawable.task_medium_priority_bg);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.task_low_priority_bg);
        }



        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                    holder.textView.setText("Status : Completed");



                }else
                    myDB.updateStatus(item.getId() , 0);
                holder.textView.setText("Status : Ongoing");


            }

        });
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    myDB.updateStatus(item.getId() , 0);
                    holder.textView.setText("Status : Ongoing");
                }
                else{
                    myDB.updateStatus(item.getId() , 1);
                    holder.textView.setText("Status : Completed");
                }
            }
        });


    }

    public boolean toBoolean(int num){
        return num!=0;
    }


    public Context getContext(){
        return activity;
    }
    public void setTasks(List<ToDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }




    public void deletTask(int position){
        ToDoModel item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());
        bundle.putString("priority", item.getPriority());
        bundle.putString("des", item.getDesc());



        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView editText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
            textView=itemView.findViewById(R.id.textview);
            textView2=itemView.findViewById(R.id.textview2);
            textView3=itemView.findViewById(R.id.textview3);
            textView4=itemView.findViewById(R.id.textview4);
            textView5=itemView.findViewById(R.id.textviewCategory);
            editText=itemView.findViewById(R.id.editDueDate);

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDatePicker(getAdapterPosition());
                }
            });

        }
        private  void openDatePicker(final int position){
            ToDoModel item = mList.get(position);
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog=new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String selectedDate = i + "-" + (i1 + 1) + "-" + i2;
                    mList.get(position).setDue(selectedDate);
                    myDB.updateDueDate(item.getId(), selectedDate);
                    notifyItemChanged(position);
                }
            },year,month,day);
            datePickerDialog.show();
        }
    }
}
