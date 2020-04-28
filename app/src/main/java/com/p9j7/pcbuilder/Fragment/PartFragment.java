package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.LoadImage;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PartFragment extends Fragment {
    private ImageView partPicItem;
    private TextView partName;
    private TextView partPrice;
    private TextView partReview;
    private SchemeViewModel schemeViewModel;

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
        partPicItem = getActivity().findViewById(R.id.partPicItem1);
        partName = getActivity().findViewById(R.id.partName2);
        partPrice = getActivity().findViewById(R.id.partPrice2);
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
            LoadImage.glideClrcle(getContext(), part.getImgPath(), partPicItem);
        });
    }
}
