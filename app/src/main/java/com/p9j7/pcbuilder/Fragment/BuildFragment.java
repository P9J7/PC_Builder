package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.p9j7.pcbuilder.Adapter.BuilderAdapter;
import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.R;

import java.util.List;

import static android.view.View.VISIBLE;


public class BuildFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeViewModel schemeViewModel;
    private BuilderAdapter builderAdapter;
    private List<Object> unsureList;

    public BuildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.toolbar).setVisibility(VISIBLE);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.new_build);
        //todo 后期用得上
//        Drawable blue = getResources().getDrawable(android.R.color.holo_blue_dark);
//        actionBar.setBackgroundDrawable(blue);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_build, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerBuilder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        builderAdapter = new BuilderAdapter(schemeViewModel, getContext());
        unsureList = schemeViewModel.getMixList().getValue();
//        Log.e(TAG, "onActivityCreated: " + unsureList.toString());
        builderAdapter.setUnsureList(unsureList);
        builderAdapter.setOnItemClickListener(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                //todo 做新的详情页
                NavHostFragment.findNavController(BuildFragment.this)
                        .navigate(R.id.action_buildFragment_to_pickFragment);
//                .navigateUp();
            }
        });
        //todo 在这里实现更改List内容，用反射判断item的类型改变 viewType
        schemeViewModel.getMixList().observe(getViewLifecycleOwner(), objects -> builderAdapter.setUnsureList(objects));
        recyclerView.setAdapter(builderAdapter);
    }

//    @Override
//    public void onStop() {
        //todo 后期用得上
//        super.onStop();
//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        Drawable white = getResources().getDrawable(R.color.titlecolor);
//        actionBar.setBackgroundDrawable(white);
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_build, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle("PC Builder");
                NavHostFragment.findNavController(BuildFragment.this).navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
