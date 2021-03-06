package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.SchemeWithParts;
import com.p9j7.pcbuilder.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IndexFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeAdapter schemeAdapter;
    private SchemeViewModel schemeViewModel;
    private List<SchemeWithParts> schemeWithPartsList;
    private boolean isFirstLoading;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        isFirstLoading = true;
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("PC Builder");
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schemeViewModel.setQuickVisible(View.VISIBLE);
                NavHostFragment.findNavController(IndexFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        schemeAdapter = new SchemeAdapter(schemeViewModel, getContext());
        schemeWithPartsList = new ArrayList<>();
        schemeAdapter.setSchemeWithPartsList(schemeWithPartsList);
        schemeViewModel.getSchemesAndParts().observe(getViewLifecycleOwner(), schemeWithParts -> {
            Log.e(TAG, "onActivityCreated: 进入了一次");
            //这个不clear就会导致数据被重复加载
            schemeWithPartsList.clear();
            schemeWithParts.forEach(item -> schemeWithPartsList.add(item));
            schemeAdapter.setSchemeWithPartsList(schemeWithPartsList);
        });
        // todo 并没有搞明白下面这么做为什么能把 Fragment对象传过去
        schemeAdapter.setOnItemClickListener(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                schemeViewModel.setQuickVisible(View.GONE);
                NavHostFragment.findNavController(IndexFragment.this)
                        .navigate(R.id.action_FirstFragment_to_displayFragment);
            }
        });
        //todo 接下来要怎么做？
        recyclerView.setAdapter(schemeAdapter);
    }
}
