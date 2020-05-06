package com.p9j7.pcbuilder.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p9j7.pcbuilder.Adapter.DisplayAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PickFragment extends Fragment {
    private RecyclerView recyclerView;
    private DisplayAdapter displayAdapter;
    private SchemeViewModel schemeViewModel;
    private List<Part> partList;
    private Bundle toolbarType;
    public PickFragment() {
        // Required empty public constructor
        toolbarType = new Bundle();
        toolbarType.putString("toolbarType", "pickToolbar");
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
        return inflater.inflate(R.layout.fragment_pick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        super.onActivityCreated(savedInstanceState);
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);
        schemeViewModel.getPickTitle().observe(getViewLifecycleOwner(), actionBar::setTitle);
        displayAdapter = new DisplayAdapter(schemeViewModel, getContext());
        recyclerView = getActivity().findViewById(R.id.recyclerPick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        partList = new ArrayList<>();

        List<Part> parts = null;
        try {
            parts = schemeViewModel.getPartsByCategory(schemeViewModel.getAdapterPosition());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        partList.addAll(parts);

        displayAdapter.setParts(partList);
        displayAdapter.setOnItemClickListener(() -> {
            //todo 跳转到详情页，通过改变linelayout组件的可见性来复用！！！
            NavHostFragment.findNavController(PickFragment.this)
                    .navigate(R.id.action_pickFragment_to_partFragment, toolbarType);
//                .navigateUp();
        });
        recyclerView.setAdapter(displayAdapter);
    }

    //    @SuppressLint("ResourceType")
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_pick, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(650);
        searchView.setQueryHint(getString(R.string.search_tooltip));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                String pattern = s.trim();
//                filteredWords.removeObservers(getViewLifecycleOwner());
//                filteredWords = wordViewModel.findWordsWithPattern(pattern);
//                filteredWords.observe(getViewLifecycleOwner(), new Observer<List<Word>>() {
//                    @Override
//                    public void onChanged(List<Word> words) {
//                        int temp = myAdapter1.getItemCount();
//                        allWords = words;
//                        if (temp != words.size()) {
//                            myAdapter1.submitList(words);
//                            myAdapter2.submitList(words);
//                        }
//                    }
//                });
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        switch (item.getItemId()) {
            case android.R.id.home:
                NavHostFragment.findNavController(PickFragment.this).navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
