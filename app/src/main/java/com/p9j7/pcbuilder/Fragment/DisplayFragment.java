package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Adapter.DisplayAdapter;
import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.DeleteDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragment extends Fragment{
    private RecyclerView recyclerView;
    private SchemeViewModel schemeViewModel;
    private DisplayAdapter displayAdapter;
    private List<Part> partList;
    private Bundle toolbarType;

    public DisplayFragment() {
        // Required empty public constructor
        toolbarType = new Bundle();
        toolbarType.putString("toolbarType", "partToolbar");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
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
        partList = new ArrayList<>();

        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        displayAdapter = new DisplayAdapter(schemeViewModel, getContext());
        displayAdapter.setParts(partList);
        displayAdapter.setOnItemClickListener(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                //todo 做新的详情页
                NavHostFragment.findNavController(DisplayFragment.this)
                        .navigate(R.id.action_displayFragment_to_partFragment, toolbarType);
//                .navigateUp();
            }
        });
        schemeViewModel.getSelected().observe(getViewLifecycleOwner(), scheme -> {
            TextView schemeName = getActivity().findViewById(R.id.schemeName);
            schemeName.setText(scheme.getScheme().getName());
            TextView schemePrice = getActivity().findViewById(R.id.schemePrice);
            schemePrice.setText("￥" + scheme.getScheme().getPrice());
            displayAdapter.setParts(scheme.getParts());
        });
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
            case R.id.delete:
                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(schemeViewModel);
                deleteDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                ActionBar actionBar1 = ((AppCompatActivity) getActivity()).getSupportActionBar();
                actionBar1.setDisplayHomeAsUpEnabled(false);
                actionBar1.setTitle("PC Builder");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
