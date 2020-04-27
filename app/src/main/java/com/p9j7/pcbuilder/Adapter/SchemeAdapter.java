package com.p9j7.pcbuilder.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.p9j7.pcbuilder.Model.SchemeWithParts;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Util.LoadImage;

import java.util.Iterator;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SchemeAdapter extends ListAdapter<SchemeWithParts, SchemeAdapter.SchemeViewHolder> {
    private SchemeViewModel schemeViewModel;
    private List<SchemeWithParts> schemeWithPartsList;
    private Context context;

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public SchemeAdapter(SchemeViewModel viewModel, Context context) {
        super(new DiffUtil.ItemCallback<SchemeWithParts>() {
            @Override
            public boolean areItemsTheSame(@NonNull SchemeWithParts oldItem, @NonNull SchemeWithParts newItem) {
                return oldItem.getScheme().getSchemeId() == newItem.getScheme().getSchemeId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull SchemeWithParts oldItem, @NonNull SchemeWithParts newItem) {
                return (oldItem.getScheme().getDetail().equals(newItem.getScheme().getDetail()) &&
                        oldItem.getScheme().getPrice() == newItem.getScheme().getPrice() &&
                        oldItem.getScheme().getName().equals(newItem.getScheme().getName()));
            }
        });
        this.schemeViewModel = viewModel;
        this.context = context;
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
                schemeViewModel.select(schemeWithPartsList.get(holder.getAdapterPosition()));
                mOnItemClickListener.onItemClick();
                //todo ?????
                Log.i(TAG, "onClick: ");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
        final SchemeWithParts scheme = schemeWithPartsList.get(position);
        holder.schemeName.setText(scheme.getScheme().getName());
        holder.schemePrice.setText("￥" + scheme.getScheme().getPrice());
        holder.schemeDetail.setText(scheme.getScheme().getDetail());
        List<Part> partList = scheme.getParts();
        if (partList != null) {
            partList.forEach(item -> {
                if (item.getCategory().equals("cpu")) {
                    LoadImage.glideClrcle(context, item.getImgPath(), holder.cpuPhoto);
                } else if (item.getCategory().equals("dcard")) {
                    LoadImage.glideClrcle(context, item.getImgPath(), holder.dcardPhoto);
                }
            });
        }
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cpu);
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
//        roundedBitmapDrawable.setCircular(true);
//        holder.imageView.setImageDrawable(roundedBitmapDrawable);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.dc);
//        RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap1);
//        roundedBitmapDrawable1.setCircular(true);
//        holder.imageView1.setImageDrawable(roundedBitmapDrawable1);
        // todo 设置图片列表
    }



    public void setSchemeWithPartsList(List<SchemeWithParts> schemeWithPartsList) {
        this.schemeWithPartsList = schemeWithPartsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return schemeWithPartsList.size();
    }

    public class SchemeViewHolder extends RecyclerView.ViewHolder {
        private TextView schemeName;
        private TextView schemePrice;
        private TextView schemeDetail;
        private ImageView cpuPhoto;
        private ImageView dcardPhoto;

        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.scheme_name);
            schemePrice = itemView.findViewById(R.id.scheme_price);
            schemeDetail = itemView.findViewById(R.id.scheme_dtl);
            cpuPhoto = itemView.findViewById(R.id.imageView8);
            dcardPhoto = itemView.findViewById(R.id.imageView7);
        }
    }
}
