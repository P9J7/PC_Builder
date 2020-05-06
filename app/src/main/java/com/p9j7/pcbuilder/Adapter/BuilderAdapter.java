package com.p9j7.pcbuilder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.LoadImage;

import java.util.List;

public class BuilderAdapter extends ListAdapter<Object, RecyclerView.ViewHolder> {
    private SchemeViewModel schemeViewModel;
    private Context context;
    private List<Object> unsureList;
    private static final int TYPE_BLANK = 0;
    private static final int TYPE_PART = 1;
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

    public void setUnsureList(List<Object> objects, Integer adapterPosition) {
        this.unsureList = objects;
        notifyItemChanged(adapterPosition);
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (viewType == TYPE_BLANK) {
            itemView = layoutInflater.inflate(R.layout.build_default, parent, false);
            return new ObjectViewHolder(itemView);
        }
        itemView = layoutInflater.inflate(R.layout.display_item, parent, false);
        return new DisplayAdapter.DisplayViewHolder(itemView);
//        View itemView = layoutInflater.inflate(R.layout.build_default, parent, false);
//        final BuilderAdapter.ObjectViewHolder holder = new ObjectViewHolder(itemView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 跳转到配置详情界面
////                schemeViewModel.partSelect(parts.get(holder.getAdapterPosition()));
//                schemeViewModel.setPickTitle(holder.tip.getText().toString());
//                mOnItemClickListener.onItemClick();
//            }
//        });
//        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        final Context context = holder.itemView.getContext();
        Object object = unsureList.get(position);
        if (viewType == TYPE_BLANK) {
            String string = (String) object;
            ObjectViewHolder objectViewHolder = (ObjectViewHolder) holder;
            objectViewHolder.tip.setText(string);
            objectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schemeViewModel.setPickTitle(schemeViewModel.getDefaultPickTexts().get(objectViewHolder.getAdapterPosition()).toString());
                    mOnItemClickListener.onItemClick();
                    schemeViewModel.setPickIndex(objectViewHolder.getAdapterPosition());
                }
            });
        } else {
            Part part = (Part) object;
            DisplayAdapter.DisplayViewHolder displayViewHolder = (DisplayAdapter.DisplayViewHolder) holder;
            displayViewHolder.partName.setText(part.getTitle());
            displayViewHolder.partPrice.setText("￥" + part.getPrice());
            displayViewHolder.delBtnLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schemeViewModel.setMixListByIndex(holder.getAdapterPosition(), schemeViewModel.getDefaultPickTexts().get(holder.getAdapterPosition()));
                }
            });
            displayViewHolder.switchBtnLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    schemeViewModel.setPickTitle(schemeViewModel.getDefaultPickTexts().get(displayViewHolder.getAdapterPosition()).toString());
                    mOnItemClickListener.onItemClick();
                    schemeViewModel.setPickIndex(displayViewHolder.getAdapterPosition());
                }
            });
            LoadImage.glideClrcle(context, part.getImgPath(), displayViewHolder.partPic);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object object = unsureList.get(position);
        if (object instanceof String)
            return TYPE_BLANK;
        else
            return TYPE_PART;
    }

    public static class ObjectViewHolder extends RecyclerView.ViewHolder {
        private TextView tip;

        public ObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tip = itemView.findViewById(R.id.textView6);
        }
    }
}
