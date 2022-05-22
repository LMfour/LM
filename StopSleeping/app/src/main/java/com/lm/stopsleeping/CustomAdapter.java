package com.lm.stopsleeping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<DateItem> mDateItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<DateItem> mDateItems, Context mContext) {
        this.mDateItems = mDateItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.sleep_date.setText(mDateItems.get(position).getSleepDate());
    }

    @Override
    public int getItemCount() {
        return mDateItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sleep_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sleep_date = itemView.findViewById(R.id.chk_day);
        }
    }

    public void addItem(DateItem _item) {
        mDateItems.add(0, _item);
        notifyItemInserted(0);
    }
}
