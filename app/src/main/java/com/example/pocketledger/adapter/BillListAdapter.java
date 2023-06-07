package com.example.pocketledger.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketledger.R;
import com.example.pocketledger.activity.BillDetailsActivity;
import com.example.pocketledger.databaseclass.BillDataManager;
import com.example.pocketledger.databaseclass.dataclass.Bill;
import com.example.pocketledger.in.OnBillDeleteListener;

import java.util.ArrayList;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.ViewHolder> {
    private ArrayList<Bill> billList;
    private Context context;
    private OnBillDeleteListener onBillDeleteListener;

    public void setOnBillDeleteListener(OnBillDeleteListener listener) {
        this.onBillDeleteListener = listener;
    }


    public BillListAdapter(Context context, ArrayList<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill currentBill = billList.get(position);

        // 填充账单类型和日期
        if (currentBill.getCategory().equals("1")) {
            holder.typeTextView.setText("支出");
        } else if (currentBill.getCategory().equals("0")) {
            holder.typeTextView.setText("收入");
        }
        holder.dateTextView.setText(currentBill.getEntryTime());

        // 填充账单金额和备注
        double amount = currentBill.getAmount();
        String amountText = String.valueOf(amount);
        holder.amountTextView.setText(amountText);
        holder.noteTextView.setText(currentBill.getProjectName());
        holder.btnDelete.setText("删除");

        if (!currentBill.isFavorite()) {
            holder.favoriteColor.setBackgroundColor(Color.parseColor("#eccc68"));
        } else {
            holder.favoriteColor.setBackgroundColor(Color.parseColor("#2153d6"));

        }

        BillDataManager billDataManager = new BillDataManager(context);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billDataManager.deleteBillById(currentBill.getId());
                if (onBillDeleteListener != null) {
                    onBillDeleteListener.onBillDeleted();
                }

            }
        });

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDataManager.toggleBillFavoriteStatus(currentBill.getId());
                if (onBillDeleteListener != null) {
                    onBillDeleteListener.onBillDeleted();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView btnFavorite;
        TextView typeTextView;
        TextView dateTextView;
        TextView amountTextView;
        TextView noteTextView;

        TextView btnDelete;

        ImageView favoriteColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.tvType);
            dateTextView = itemView.findViewById(R.id.tvDate);
            amountTextView = itemView.findViewById(R.id.tvAmount);
            noteTextView = itemView.findViewById(R.id.tvNote);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            favoriteColor = itemView.findViewById(R.id.imageView12);
            btnFavorite = itemView.findViewById(R.id.btn_collect);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Bill currentBill = billList.get(position);
                BillDetailsActivity.id = currentBill.getId();
                context.startActivity(new Intent(context, BillDetailsActivity.class));
            }
        }
    }
}
