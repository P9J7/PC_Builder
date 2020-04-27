package com.p9j7.pcbuilder.Fragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p9j7.pcbuilder.Adapter.DisplayAdapter;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragment extends Fragment{
    private RecyclerView recyclerView;
    private SchemeViewModel schemeViewModel;
    private DisplayAdapter displayAdapter;
    private List<Part> partList;


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
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        return inflater.inflate(R.layout.display_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        displayAdapter = new DisplayAdapter(getContext());
        partList = new ArrayList<>();
        displayAdapter.setParts(partList);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        schemeViewModel.getSelected().observe(getViewLifecycleOwner(), scheme -> {
            TextView schemeName = getActivity().findViewById(R.id.schemeName);
            schemeName.setText(scheme.getScheme().getName());
            TextView schemePrice = getActivity().findViewById(R.id.schemePrice);
            schemePrice.setText("￥" + scheme.getScheme().getPrice());
            displayAdapter.setParts(scheme.getParts());
            //TODO 更新UI
//            schemeViewModel.getAllPartBySchemeId(scheme.getScheme().getSchemeId()).observe(getViewLifecycleOwner(), parts -> displayAdapter.setParts(parts));
        });
//        schemeViewModel.getAllScheme().observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
//            @Override
//            public void onChanged(List<Part> parts) {
//                displayAdapter.setParts(parts);
//            }
//        });
        recyclerView.setAdapter(displayAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_display, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle("PC Builder");
                NavHostFragment.findNavController(DisplayFragment.this).navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
