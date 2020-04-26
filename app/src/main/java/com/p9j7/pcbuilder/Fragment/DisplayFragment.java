package com.p9j7.pcbuilder.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeViewModel schemeViewModel;
    private SchemeAdapter schemeAdapter;
    private List<Scheme> schemeList;


    public static DisplayFragment newInstance() {
        return new DisplayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        schemeAdapter = new SchemeAdapter(schemeViewModel, getContext());
        schemeList = new ArrayList<>();
        schemeAdapter.setSchemes(schemeList);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        schemeViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<Scheme>() {
            @Override
            public void onChanged(Scheme scheme) {
                //TODO 更新UI
//                List<Part> partList = scheme.getPartList();


            }
        });
        schemeViewModel.getAllScheme().observe(getViewLifecycleOwner(), new Observer<List<Scheme>>() {
            @Override
            public void onChanged(List<Scheme> schemes) {
                schemeAdapter.setSchemes(schemes);
            }
        });
        recyclerView.setAdapter(schemeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_display, menu);
    }
}