package com.p9j7.pcbuilder.Adapter;

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

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.LoadImage;

import java.util.List;

public class DisplayAdapter extends ListAdapter<Part, DisplayAdapter.DisplayViewHolder> {
    private Context context;
    private List<Part> parts;

    private SchemeAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(SchemeAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public DisplayAdapter(Context context) {
        super(new DiffUtil.ItemCallback<Part>() {
            @Override
            public boolean areItemsTheSame(@NonNull Part oldItem, @NonNull Part newItem) {
                return oldItem.getPartId() == newItem.getPartId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Part oldItem, @NonNull Part newItem) {
                return (oldItem.getDetail().equals(newItem.getDetail()) &&
                        oldItem.getPrice() == newItem.getPrice() &&
                        oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getCategory().equals(newItem.getTitle()));
            }
        });
        this.context = context;
    }

    @NonNull
    @Override
    public DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.display_item, parent, false);
        final DisplayViewHolder holder = new DisplayViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到配置详情界面
//                schemeViewModel.select(schemes.get(holder.getAdapterPosition()));
                mOnItemClickListener.onItemClick();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewHolder holder, int position) {
        final Part part = parts.get(position);
        holder.partName.setText(part.getTitle());
        holder.partPrice.setText("￥" + part.getPrice());
        LoadImage.glideClrcle(context, part.getImgPath(), holder.partPic);
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder {
        private TextView partName;
        private TextView partPrice;
        private TextView partDetail;
        private ImageView partPic;
        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            partName = itemView.findViewById(R.id.partName);
            partPrice = itemView.findViewById(R.id.partPrice);
            partPic = itemView.findViewById(R.id.partPic);
        }
    }
}
