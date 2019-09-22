package com.danghai.dangdhps10201.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookCategoryViewAdapter extends RecyclerView.Adapter<BookCategoryViewAdapter.BookCategoryHolder> {


    @NonNull
    @Override
    public BookCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
        /* View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loai, parent, false);
        ListViewHolder lvh = new ListViewHolder(v, mListener);
        return lvh;

         */
    }

    @Override
    public void onBindViewHolder(@NonNull BookCategoryHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BookCategoryHolder extends RecyclerView.ViewHolder {
        public BookCategoryHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
