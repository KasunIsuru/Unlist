package com.example.unlist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment {
    private ArrayList<String> todoList;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String selectedDate = "No Date Selected";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("todoPrefs", Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        todoList = new ArrayList<>(sharedPreferences.getStringSet("todoList", new HashSet<>()));

        adapter = new TodoAdapter(todoList, sharedPreferences);
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addTodoButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTodoDialog();
            }
        });

        return view;
    }

    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_todo, null);
        builder.setView(dialogView);

        EditText todoEditText = dialogView.findViewById(R.id.todoEditText);
        TextView dateTextView = dialogView.findViewById(R.id.dateTextView);
        Button selectDateButton = dialogView.findViewById(R.id.selectDateButton);
        Button addButton = dialogView.findViewById(R.id.addButton);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                dateTextView.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        AlertDialog dialog = builder.create();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = todoEditText.getText().toString().trim();
                if (!todoText.isEmpty() && !selectedDate.equals("No Date Selected")) {
                    addTodoItem(todoText + " - " + selectedDate);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter a to-do and select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void addTodoItem(String todoText) {
        todoList.add(todoText);
        adapter.notifyItemInserted(todoList.size() - 1);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(todoList);
        editor.putStringSet("todoList", set);
        editor.apply();
    }
}
