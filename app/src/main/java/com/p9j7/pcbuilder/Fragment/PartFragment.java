package com.p9j7.pcbuilder.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.LoadImage;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PartFragment extends Fragment {
    private ImageView partPicItem;
    private TextView partName;
    private TextView partPrice;
    private TextView partCommentNumber;
    private TextView partDetail;
    private SchemeViewModel schemeViewModel;
    private LinearLayout removeLine;
    private LinearLayout buyLine;
    private LinearLayout addLine;
    private LinearLayout switchLine;
    private String partId;

    public PartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_part, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        removeLine = view.findViewById(R.id.removeLine);
        buyLine = view.findViewById(R.id.buyLine);
        switchLine = view.findViewById(R.id.switchLine);
        addLine = view.findViewById(R.id.addLine);
        // todo:动态改变toolbar的状态
        if (getArguments() != null && getArguments().getString("toolbarType") != null) {
            switch (getArguments().getString("toolbarType")) {
                case "pickToolbar":
                    removeLine.setVisibility(View.GONE);
                    switchLine.setVisibility(View.GONE);
                    buyLine.setVisibility(View.VISIBLE);
                    addLine.setVisibility(View.VISIBLE);
                    break;
                case "partToolbar":
                    removeLine.setVisibility(View.GONE);
                    switchLine.setVisibility(View.GONE);
                    addLine.setVisibility(View.GONE);
                    buyLine.setVisibility(View.VISIBLE);
                    break;
                case "editToolbar":
                    addLine.setVisibility(View.GONE);
                    buyLine.setVisibility(View.VISIBLE);
                    removeLine.setVisibility(View.VISIBLE);
                    switchLine.setVisibility(View.VISIBLE);
                    break;
            }
        }
        partPicItem = getActivity().findViewById(R.id.partPicItem1);
        partName = getActivity().findViewById(R.id.partName2);
        partPrice = getActivity().findViewById(R.id.partPrice2);
        partCommentNumber = getActivity().findViewById(R.id.partNumber);
        partDetail = getActivity().findViewById(R.id.partItemDetail);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.backFloat).setOnClickListener(v -> NavHostFragment.findNavController(PartFragment.this)
                .navigateUp());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        schemeViewModel.getPartSelected().observe(getViewLifecycleOwner(), part -> {
            Log.e(TAG, "onActivityCreated: " + part.getTitle());
            partName.setText(part.getTitle());
            partPrice.setText("￥" + part.getPrice());
            partCommentNumber.setText(part.getCommentNumber().toString());
            partDetail.setText(part.getDetail());
            partId = String.valueOf(part.getPartId());
            LoadImage.glideClrcle(getContext(), part.getImgPath(), partPicItem);
        });
        // todo 改变viewmodel里面的混合list
        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 问题出在这里
                schemeViewModel.setMixListByIndex(schemeViewModel.getAdapterPosition(), schemeViewModel.getPartSelected().getValue());
                // popBackStack跳过一个返回栈，直接跳到BuildFragment
//                NavHostFragment.findNavController(PartFragment.this).popBackStack();
                NavHostFragment.findNavController(PartFragment.this).popBackStack(R.id.buildFragment, false);
//                NavHostFragment.findNavController(PartFragment.this).navigateUp();
            }
        });
        buyLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://item.jd.com/" + partId + ".html");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                getContext().startActivity(intent);
            }
        });
    }
}
