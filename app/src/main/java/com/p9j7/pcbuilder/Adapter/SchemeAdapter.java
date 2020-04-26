package com.p9j7.pcbuilder.Adapter;

import android.content.Context;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Data.SchemeViewModel;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SchemeAdapter extends ListAdapter<Scheme, SchemeAdapter.SchemeViewHolder> {
    private SchemeViewModel schemeViewModel;
    private List<Scheme> schemes;
    private Context context;

    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public SchemeAdapter(SchemeViewModel viewModel, Context context) {
        super(new DiffUtil.ItemCallback<Scheme>() {
            @Override
            public boolean areItemsTheSame(@NonNull Scheme oldItem, @NonNull Scheme newItem) {
                return oldItem.getSchemeId() == newItem.getSchemeId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Scheme oldItem, @NonNull Scheme newItem) {
                return (oldItem.getDetail().equals(newItem.getDetail()) &&
                        oldItem.getPrice() == newItem.getPrice() &&
                        oldItem.getName().equals(newItem.getName()));
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
                schemeViewModel.select(schemes.get(holder.getAdapterPosition()));
                mOnItemClickListener.onItemClick();
                //todo ?????
                Log.i(TAG, "onClick: ");
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
        Glide.with(context).load("https://img10.360buyimg.com/n1/s450x450_jfs/t3082/185/5978515992/258515/dc6beac1/589a7190N62b6ee82.jpg")
                .into(holder.imageView);
        Glide.with(context).load("http://img13.360buyimg.com/n1/s450x450_jfs/t1/114117/14/1515/307612/5e994b5eE24c45abf/684be3b9b37b4f33.jpg")
                .into(holder.imageView1);
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
        private LinearLayout gridPhoto;
        private ImageView imageView;
        private ImageView imageView1;

        public SchemeViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.scheme_name);
            schemePrice = itemView.findViewById(R.id.scheme_price);
            schemeDetail = itemView.findViewById(R.id.scheme_dtl);
            gridPhoto = itemView.findViewById(R.id.linearLayout);
            imageView = itemView.findViewById(R.id.imageView8);
            imageView1 = itemView.findViewById(R.id.imageView7);
        }
    }
}
