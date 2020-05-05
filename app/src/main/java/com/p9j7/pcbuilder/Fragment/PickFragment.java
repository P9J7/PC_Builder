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
        //todo 从网络爬取数据封装在一个Part集合里
//        partList.addAll();

        List<Part> parts = null;
        try {
            parts = schemeViewModel.getPartsByCategory(schemeViewModel.getAdapterPosition());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        parts.add(new Part(50, "http://img12.360buyimg.com/n1/s450x450_jfs/t1/46613/28/13064/384165/5da03815E3f14a325/f0c6d7871d68b5f3.jpg",
//                "爱国者（aigo）炫影黑京东特别版", 199));
//        parts.add(new Part(51, "http://img13.360buyimg.com/n1/s450x450_jfs/t1/68742/5/8070/124190/5d60da61Ecf10e671/b7d1381c2cfe2954.jpg",
//                "先马（SAMA）黑洞 黑色 中塔式机箱", 299));
//        parts.add(new Part(52, "http://img14.360buyimg.com/n1/s450x450_jfs/t1/117374/4/1146/184753/5e9523d9E6bc6b4e6/ccd117666037dde5.jpg",
//                "鑫谷（Segotep）LUX重装版全侧透白色机箱", 199));
        partList.addAll(parts);

        displayAdapter.setParts(partList);
        displayAdapter.setOnItemClickListener(() -> {
            //todo 跳转到详情页，通过改变toolbar的可见性来复用！！！
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
