package com.example.unlist;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private ArrayList<String> todoList;
    private SharedPreferences sharedPreferences;

    public TodoAdapter(ArrayList<String> todoList, SharedPreferences sharedPreferences) {
        this.todoList = todoList;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        String todoItem = todoList.get(position);
        holder.todoTextView.setText(todoItem);

        holder.doneButton.setOnClickListener(v -> {
            todoList.set(position, "Done: " + todoItem);
            notifyItemChanged(position);
            saveTodoList();
        });

        holder.deleteButton.setOnClickListener(v -> {
            todoList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, todoList.size());
            saveTodoList();
            Toast.makeText(holder.itemView.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    private void saveTodoList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(todoList);
        editor.putStringSet("todoList", set);
        editor.apply();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView todoTextView;
        Button doneButton, deleteButton;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTextView = itemView.findViewById(R.id.todoTextView);
            doneButton = itemView.findViewById(R.id.doneButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
