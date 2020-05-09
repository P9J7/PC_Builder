package com.p9j7.pcbuilder.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.p9j7.pcbuilder.Model.SchemeWithParts;
import com.p9j7.pcbuilder.R;
import com.p9j7.pcbuilder.Util.CopyDialogFragment;
import com.p9j7.pcbuilder.Util.DeleteDialogFragment;
import com.p9j7.pcbuilder.Util.ScreenShootHelper;

import java.util.ArrayList;
import java.util.List;

public class DisplayFragment extends Fragment {
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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
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
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle("PC Builder");
                NavHostFragment.findNavController(DisplayFragment.this).navigateUp();
                return true;
            case R.id.delete:
                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(schemeViewModel);
                deleteDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                return true;
            case R.id.edit:
                schemeViewModel.getSelected().getValue().getParts().forEach(part -> {
                    if (part.getCategory().equals("cpu")) {
                        schemeViewModel.setMixListByIndex(0, part);
                    } else if (part.getCategory().equals("motherboard")) {
                        schemeViewModel.setMixListByIndex(1, part);
                    } else if (part.getCategory().equals("dcard")) {
                        schemeViewModel.setMixListByIndex(2, part);
                    } else if (part.getCategory().equals("ram")) {
                        schemeViewModel.setMixListByIndex(3, part);
                    } else if (part.getCategory().equals("storage")) {
                        schemeViewModel.setMixListByIndex(4, part);
                    } else if (part.getCategory().equals("psu")) {
                        schemeViewModel.setMixListByIndex(5, part);
                    } else if (part.getCategory().equals("casing")) {
                        schemeViewModel.setMixListByIndex(6, part);
                    } else if (part.getCategory().equals("cooler")) {
                        schemeViewModel.setMixListByIndex(7, part);
                    }
                });
                schemeViewModel.setEdit(true);
                NavHostFragment.findNavController(DisplayFragment.this).navigate(R.id.action_displayFragment_to_buildFragment);
                return true;
            case R.id.copy:
                CopyDialogFragment copyDialogFragment = new CopyDialogFragment(schemeViewModel);
                copyDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                return true;
            case R.id.textShare:
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                StringBuilder textShare = new StringBuilder();
                SchemeWithParts schemeWithParts = schemeViewModel.getSelected().getValue();
                textShare.append(schemeWithParts.getScheme().getName() + "\n");
                textShare.append(getResources().getString(R.string.total) + "：￥" + schemeWithParts.getScheme().getPrice() + "\n\n");
                List<Part> parts = schemeWithParts.getParts();
                parts.forEach(p -> {
                    textShare.append(p.getTitle() + "\n");
                    textShare.append("￥" + p.getPrice() + "\n\n");
                });
                textShare.append(getResources().getString(R.string.PCBuilderShare));
                ClipData clipData = ClipData.newPlainText(null, textShare);
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(getActivity(), R.string.havacopyed, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.picShare:
                //todo 截长图、打开系统分享。
//                ShareUtil.shotScreen(getActivity());
                LinearLayout nameAndPriceLine = getActivity().findViewById(R.id.namaAndPriceLine);
                Bitmap target = Bitmap.createBitmap(nameAndPriceLine.getWidth(), nameAndPriceLine.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(target);
                nameAndPriceLine.draw(canvas);
                ScreenShootHelper.addPictureToAlbum(getContext(), ScreenShootHelper.getScreenshotFromRecyclerView(recyclerView, target), String.valueOf(System.currentTimeMillis()));
                Toast.makeText(getActivity(), R.string.havesaved, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
