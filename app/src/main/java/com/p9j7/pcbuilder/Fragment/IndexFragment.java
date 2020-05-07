package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class IndexFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeAdapter schemeAdapter;
    private SchemeViewModel schemeViewModel;
    private List<SchemeWithParts> schemeWithPartsList;

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
        schemeViewModel.getSchemesAndParts().observe(getViewLifecycleOwner(), schemeWithParts -> {
//            Log.e(TAG, "onActivityCreated: 创建成功");
            //todo 图片顺序显示怎么实现？
            schemeWithParts.forEach(item -> schemeWithPartsList.add(item));
            schemeAdapter.setSchemeWithPartsList(schemeWithPartsList);
        });
        recyclerView.setAdapter(schemeAdapter);
    }
}
