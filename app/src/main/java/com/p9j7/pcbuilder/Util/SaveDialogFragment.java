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

import java.util.List;
import java.util.stream.Collectors;


public class SaveDialogFragment extends DialogFragment {
    private SchemeViewModel schemeViewModel;
    private List<Part> toSaveParts;
    private Double totalPrice;
    private EditText editText;

    public SaveDialogFragment(SchemeViewModel schemeViewModel, List<Part> toSaveParts, Double totalPrice) {
        this.schemeViewModel = schemeViewModel;
        this.toSaveParts = toSaveParts;
        this.totalPrice = totalPrice;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_save, null);
        editText = view.findViewById(R.id.buildName);
        builder.setTitle(R.string.tosave)
                .setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Scheme scheme = new Scheme();
                        String schemeDetail = toSaveParts.stream().map(Part::getTitle).collect(Collectors.joining(" + "));
                        String schemeName = editText.getText().toString();
                        scheme.setDetail(schemeDetail);
                        scheme.setPrice(totalPrice);
                        scheme.setName(schemeName);
//                        Log.e(TAG, "schemeDetail" + schemeDetail);
//                        Log.e(TAG, "schemePrice" + totalPrice);
//                        Log.e(TAG, "schemeName" + scheme.getName());
//                        scheme.setName(s);
//                        schemeViewModel.insertSchemeAndParts();
                        // 保存后返回主页
                        List<Object> objects = schemeViewModel.getDefaultPickTexts();
                        schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                        NavHostFragment.findNavController(SaveDialogFragment.this).navigateUp();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //todo 需要对ViewModel中的数据进行重置
                        schemeViewModel.setMixList(schemeViewModel.getDefaultPickTexts());
                        NavHostFragment.findNavController(SaveDialogFragment.this).navigateUp();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
