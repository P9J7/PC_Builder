package com.p9j7.pcbuilder.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BuilderAdapter extends ListAdapter<Object, BuilderAdapter.ObjectViewHolder> {
    private SchemeViewModel schemeViewModel;
    private Context context;
    private List<Object> unsureList;
    private SchemeAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(SchemeAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BuilderAdapter(SchemeViewModel schemeViewModel, Context context) {
        super(new DiffUtil.ItemCallback<Object>() {
            @Override
            public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                return oldItem.toString().equals(newItem.toString());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                return (oldItem.toString().equals(newItem.toString()));
            }
        });
        this.context = context;
        this.schemeViewModel = schemeViewModel;
    }

    public void setUnsureList(List<Object> list) {
        this.unsureList = list;
        notifyDataSetChanged();
    }

    public List<Object> getUnsureList() {
        return unsureList;
    }

    @Override
    public int getItemCount() {
        return unsureList.size();
    }

    @NonNull
    @Override
    public ObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.build_default, parent, false);
        final BuilderAdapter.ObjectViewHolder holder = new BuilderAdapter.ObjectViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到配置详情界面
//                schemeViewModel.partSelect(parts.get(holder.getAdapterPosition()));
                schemeViewModel.setPickTitle(holder.tip.getText().toString());
                mOnItemClickListener.onItemClick();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectViewHolder holder, int position) {
        final Object item = unsureList.get(position);
        holder.tip.setText(item.toString());
    }

    public class ObjectViewHolder extends RecyclerView.ViewHolder {
        private TextView tip;
        public ObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tip = itemView.findViewById(R.id.textView6);
        }
    }
}
