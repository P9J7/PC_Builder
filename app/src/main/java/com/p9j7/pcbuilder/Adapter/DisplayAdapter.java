package com.p9j7.pcbuilder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.LoadImage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayAdapter extends ListAdapter<Part, DisplayAdapter.DisplayViewHolder> {
    private SchemeViewModel schemeViewModel;
    private Context context;
    private List<Part> parts;
    private Map<String, Integer> cateToInt;

    private SchemeAdapter.OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(SchemeAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public DisplayAdapter(SchemeViewModel schemeViewModel, Context context) {
        super(new DiffUtil.ItemCallback<Part>() {
            @Override
            public boolean areItemsTheSame(@NonNull Part oldItem, @NonNull Part newItem) {
                return oldItem.getPartId() == newItem.getPartId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Part oldItem, @NonNull Part newItem) {
                return (oldItem.getPrice() == newItem.getPrice() &&
                        oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getCategory().equals(newItem.getTitle()));
            }
        });
        this.schemeViewModel = schemeViewModel;
        this.context = context;
        this.cateToInt = new HashMap<>();
        cateToInt.put("cpu", 0);
        cateToInt.put("motherboard", 1);
        cateToInt.put("dcard", 2);
        cateToInt.put("ram", 3);
        cateToInt.put("storage", 4);
        cateToInt.put("psu", 5);
        cateToInt.put("casing", 6);
        cateToInt.put("cooler", 7);
        cateToInt.put("display", 8);
        cateToInt.put("keyboard", 9);
        cateToInt.put("mouse", 10);
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
                schemeViewModel.partSelect(parts.get(holder.getAdapterPosition()));
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
        holder.partCommentNumber.setText(part.getCommentNumber().toString());
        LoadImage.glideClrcle(context, part.getImgPath(), holder.partPic);
        // fix 因为不同的BindViewHolder和不同的视图绑定，共享的只是ViewHolder模型而已，所以可以直接更改可见性而不需要通过ViewModel
//        holder.deleteBtn.setVisibility(View.GONE);
        // todo 通过ViewModel更改两个可见性
        holder.quickBtnLine.setVisibility(schemeViewModel.getQuickVisible());
    }

    public void setParts(List<Part> parts) {
        Collections.sort(parts, (a, b) -> cateToInt.get(a.getCategory()).compareTo(cateToInt.get(b.getCategory())));
        this.parts = parts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    public static class DisplayViewHolder extends RecyclerView.ViewHolder {
        public TextView partName;
        public TextView partPrice;
        public TextView partCommentNumber;
        public ImageView partPic;
        public LinearLayout quickBtnLine;
        public LinearLayout delBtnLine;
        public LinearLayout switchBtnLine;
        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            partName = itemView.findViewById(R.id.partName);
            partPrice = itemView.findViewById(R.id.partPrice);
            partPic = itemView.findViewById(R.id.partPic);
            quickBtnLine = itemView.findViewById(R.id.quickBtnLine);
            partCommentNumber = itemView.findViewById(R.id.partCommentNumber);
            delBtnLine = itemView.findViewById(R.id.delBtnLine);
            switchBtnLine = itemView.findViewById(R.id.swtichBtnLine);
        }
    }
}
