package com.p9j7.pcbuilder.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.Model.Part;
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SaveDialogFragment extends DialogFragment {
    private SchemeViewModel schemeViewModel;
    private List<Part> toSaveParts;
    private Double totalPrice;
    private EditText editText;
    private List<Part> toSavePartsCopy;

    public SaveDialogFragment(SchemeViewModel schemeViewModel, List<Part> toSaveParts, Double totalPrice) {
        this.schemeViewModel = schemeViewModel;
        this.toSaveParts = toSaveParts;
        this.totalPrice = totalPrice;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        toSavePartsCopy = new ArrayList<>(toSaveParts);
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_save, null);
        editText = view.findViewById(R.id.buildName);
        if (schemeViewModel.getEdit()) {
            editText.setText(schemeViewModel.getSelected().getValue().getScheme().getName());
        }
        builder.setTitle(R.string.tosave)
                .setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //如果是修改配置模式的话就更新
                        String schemeName = editText.getText().toString();
                        String schemeDetail = toSaveParts.stream().map(Part::getTitle).collect(Collectors.joining(" + "));
                        schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                        if (schemeViewModel.getEdit()) {
                            schemeViewModel.updateSchemeBySchemeId(schemeViewModel.getSelected().getValue().getScheme().getSchemeId(),
                                    schemeName, schemeDetail, totalPrice, toSavePartsCopy, schemeViewModel.getSelected().getValue().getParts());
                            schemeViewModel.setEdit(false);
//                            NavHostFragment.findNavController(SaveDialogFragment.this).navigateUp();
                            NavHostFragment.findNavController(SaveDialogFragment.this).popBackStack(R.id.FirstFragment, false);
                        } else {
                            // 进入此说明是新建
                            Scheme scheme = new Scheme();
                            scheme.setPrice(totalPrice);
                            scheme.setDetail(schemeDetail);
                            scheme.setName(schemeName);
                            schemeViewModel.insertSchemeAndParts(scheme, toSavePartsCopy);
//                        Log.e(TAG, "schemeDetail" + schemeDetail);
//                        Log.e(TAG, "schemePrice" + totalPrice);
//                        Log.e(TAG, "schemeName" + scheme.getName());
//                        scheme.setName(s);
//                        schemeViewModel.insertSchemeAndParts();
                            // 保存后返回主页
                            NavHostFragment.findNavController(SaveDialogFragment.this).popBackStack(R.id.FirstFragment, false);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo 需要对ViewModel中的数据进行重置
                        NavHostFragment.findNavController(SaveDialogFragment.this).navigateUp();
                        schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
