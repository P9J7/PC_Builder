package com.p9j7.pcbuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SchemeAdapter extends ListAdapter<Scheme, SchemeAdapter.SchemeViewHolder> {
    private SchemeViewModel schemeViewModel;
    private List<Scheme> schemes;

    SchemeAdapter(SchemeViewModel viewModel) {
        super(new DiffUtil.ItemCallback<Scheme>() {
            @Override
            public boolean areItemsTheSame(@NonNull Scheme oldItem, @NonNull Scheme newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Scheme oldItem, @NonNull Scheme newItem) {
                return (oldItem.getDetail().equals(newItem.getDetail()) &&
                        oldItem.getPrice() == newItem.getPrice() &&
                        oldItem.getName().equals(newItem.getName()));
            }
        });
        this.schemeViewModel = viewModel;
    }

    @NonNull
    @Override
    public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.index_item, parent, false);
        final SchemeViewHolder holder = new SchemeViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到配置详情界面
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
        final Scheme scheme = schemes.get(position);
        holder.schemeName.setText(scheme.getName());
        holder.schemePrice.setText("￥" + scheme.getPrice());
        holder.schemeDetail.setText(scheme.getDetail());
        // todo 设置图片列表

    }

    public void setSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return schemes.size();
    }

    public class SchemeViewHolder extends RecyclerView.ViewHolder {
        private TextView schemeName;
        private TextView schemePrice;
        private TextView schemeDetail;
        private GridView gridPhoto;
        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.scheme_name);
            schemePrice = itemView.findViewById(R.id.scheme_price);
            schemeDetail = itemView.findViewById(R.id.scheme_dtl);
            gridPhoto = itemView.findViewById(R.id.grid_photo);
        }
    }
}
