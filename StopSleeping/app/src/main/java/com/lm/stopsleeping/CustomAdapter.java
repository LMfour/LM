package com.lm.stopsleeping;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        holder.chc_day.setText(mDateItems.get(position).getSleepDate());
        holder.chk_time.setText(mDateItems.get(position).getSleepTime());
    }

    @Override
    public int getItemCount() {
        return mDateItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chk_time;
        private TextView chc_day;
        private ImageButton btn_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chc_day = itemView.findViewById(R.id.chk_day);
            chk_time = itemView.findViewById(R.id.chk_time);
            btn_del = itemView.findViewById(R.id.chk_trash_img);

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // delete table
                    int curPos = getAdapterPosition();
                    DateItem dateItem = mDateItems.get(curPos);
                    mDBHelper.deleteRecord(dateItem.getId());

                    // delete UI
                    mDateItems.remove(curPos);
                    notifyItemRemoved(curPos);
                }
            });
        }
    }


}
