package com.p9j7.pcbuilder.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import com.p9j7.pcbuilder.Adapter.BuilderAdapter;
import com.p9j7.pcbuilder.Adapter.SchemeAdapter;
import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.SaveDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static android.view.View.VISIBLE;


public class BuildFragment extends Fragment {
    private RecyclerView recyclerView;
    private SchemeViewModel schemeViewModel;
    private BuilderAdapter builderAdapter;
    private List<Object> unsureList;
    private TextView totalNumber;
    private List<Part> toSaveParts;
    private AtomicReference<Double> totalNumberLive;

    public BuildFragment() {
        // Required empty public constructor
        this.toSaveParts = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_build, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        totalNumber = view.findViewById(R.id.totalNumber);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerBuilder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        schemeViewModel = new ViewModelProvider(getActivity()).get(SchemeViewModel.class);

        getActivity().findViewById(R.id.toolbar).setVisibility(VISIBLE);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (!schemeViewModel.getEdit()) {
            actionBar.setTitle(R.string.new_build);
        } else {
            actionBar.setTitle(R.string.edit);
        }

        builderAdapter = new BuilderAdapter(schemeViewModel, getContext());
        builderAdapter.setOnItemClickListener(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                //todo 做新的详情页
                schemeViewModel.setQuickVisible(View.GONE);
                NavHostFragment.findNavController(BuildFragment.this)
                        .navigate(R.id.action_buildFragment_to_pickFragment);
//                .navigateUp();
            }
        });
        //todo 在这里实现更改List内容，用反射判断item的类型改变 viewType
        schemeViewModel.getMixList().observe(getViewLifecycleOwner(), objects -> {
//            builderAdapter.setUnsureList(objects);
            // toSaveParts在内存中暂存数据，等待save按钮响应
            //todo 实现监听back按键提示是否保存功能
            toSaveParts.clear();
            totalNumberLive = new AtomicReference<>(0.0);
            objects.forEach(item -> {
                if (item instanceof Part) {
                    totalNumberLive.updateAndGet(v -> new Double((double) (v + ((Part) item).getPrice())));
                    toSaveParts.add((Part) item);
                }
            });
//            double totalNumberLive = objects.stream().filter(item -> item instanceof Part).mapToDouble(item -> ((Part) item).getPrice()).sum();
            totalNumber.setText("￥" + totalNumberLive);
            builderAdapter.setUnsureList(objects, schemeViewModel.getAdapterPosition());

        });
        recyclerView.setAdapter(builderAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_build, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (toSaveParts.size() == 0) {
                        schemeViewModel.setEdit(false);
                        NavHostFragment.findNavController(BuildFragment.this).navigateUp();
                        return true;
                    } else if (schemeViewModel.getEdit() && (schemeViewModel.getSelected().getValue().getParts().stream().mapToLong(Part::getPartId).sum()
                            == schemeViewModel.getMixList().getValue().stream().filter(item -> item instanceof Part).mapToLong(item -> ((Part) item).getPartId()).sum())) {
                        // 是编辑模式且配件的id相加和一致即表示没有改变配置
                        schemeViewModel.setEdit(false);
                        NavHostFragment.findNavController(BuildFragment.this).navigateUp();
                        schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                        return true;
                    } else {
                        // 进入这个循环，说明配置单改变了或者是正在新建配置，这样在对话框Fragment里面判断是哪一种状态
                        SaveDialogFragment saveDialogFragment = new SaveDialogFragment(schemeViewModel, toSaveParts, totalNumberLive.get());
                        saveDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                        return true;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (toSaveParts.size() == 0) {
                    schemeViewModel.setEdit(false);
                    NavHostFragment.findNavController(BuildFragment.this).navigateUp();
                    return true;
                } else if (schemeViewModel.getEdit() && (schemeViewModel.getSelected().getValue().getParts().stream().mapToLong(Part::getPartId).sum()
                        == schemeViewModel.getMixList().getValue().stream().filter(item1 -> item1 instanceof Part).mapToLong(item1 -> ((Part) item1).getPartId()).sum())) {
                    // 是编辑模式且配件的id相加和一致即表示没有改变配置
                    schemeViewModel.setEdit(false);
                    NavHostFragment.findNavController(BuildFragment.this).navigateUp();
                    schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                    return true;
                } else {
                    // 进入这个循环，说明配置单改变了或者是正在新建配置，这样在对话框Fragment里面判断是哪一种状态
                    SaveDialogFragment saveDialogFragment = new SaveDialogFragment(schemeViewModel, toSaveParts, totalNumberLive.get());
                    saveDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                    return true;
                }
            case R.id.saveSchemeBtn:
                if (toSaveParts.size() == 0) {
                    Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                    return true;
//                } else if (schemeViewModel.getEdit() && (schemeViewModel.getSelected().getValue().getParts().stream().mapToLong(Part::getPartId).sum()
//                        == schemeViewModel.getMixList().getValue().stream().filter(item1 -> item1 instanceof Part).mapToLong(item1 -> ((Part) item1).getPartId()).sum())) {
//                    // 是编辑模式且配件的id相加和一致即表示没有改变配置
//                    schemeViewModel.setEdit(false);
//                    NavHostFragment.findNavController(BuildFragment.this).navigateUp();
//                    schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
//                    return true;
                } else {
                    // 进入这个循环，说明配置单改变了或者是正在新建配置，这样在对话框Fragment里面判断是哪一种状态
                    SaveDialogFragment saveDialogFragment = new SaveDialogFragment(schemeViewModel, toSaveParts, totalNumberLive.get());
                    saveDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
