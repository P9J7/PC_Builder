package com.p9j7.pcbuilder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeAdapter schemeAdapter;
    private SchemeViewModel schemeViewModel;
    private List<Scheme> schemeList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(IndexFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        schemeAdapter = new SchemeAdapter(schemeViewModel, getContext());
        schemeList = new ArrayList<>();
        schemeAdapter.setSchemes(schemeList);
        //todo 接下来要怎么做？
//        Scheme scheme = new Scheme("AMD游戏平台", 36.2, "r7-3800x");
//        schemeViewModel.insertSchemes(scheme);
        schemeViewModel.getAllScheme().observe(getViewLifecycleOwner(), new Observer<List<Scheme>>() {
            @Override
            public void onChanged(List<Scheme> schemes) {
                schemeAdapter.setSchemes(schemes);
            }
        });
        recyclerView.setAdapter(schemeAdapter);
    }
}
