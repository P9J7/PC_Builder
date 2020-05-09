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
import com.p9j7.pcbuilder.Model.Scheme;
import com.p9j7.pcbuilder.Model.SchemeWithParts;
import com.p9j7.pcbuilder.R;


public class CopyDialogFragment extends DialogFragment {
    private SchemeViewModel schemeViewModel;
    private EditText editText;

    public CopyDialogFragment(SchemeViewModel schemeViewModel) {
        this.schemeViewModel = schemeViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_save, null);
        editText = view.findViewById(R.id.buildName);
        editText.setText(schemeViewModel.getSelected().getValue().getScheme().getName() + " " + getString(R.string.copy));
        builder.setTitle(R.string.tocopy)
                .setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //如果确认复制就复制
                        String schemeName = editText.getText().toString();
                        SchemeWithParts schemeWithParts = schemeViewModel.getSelected().getValue();
                        // 进入此说明是新建
                        Scheme scheme = new Scheme();
                        scheme.setPrice(schemeWithParts.getScheme().getPrice());
                        scheme.setDetail(schemeWithParts.getScheme().getDetail());
                        scheme.setName(schemeName);
                        schemeViewModel.insertSchemeAndParts(scheme, schemeWithParts.getParts());
//                        Log.e(TAG, "schemeDetail" + schemeDetail);
//                        Log.e(TAG, "schemePrice" + totalPrice);
//                        Log.e(TAG, "schemeName" + scheme.getName());
//                        scheme.setName(s);
//                        schemeViewModel.insertSchemeAndParts();
                        // 保存后返回主页
                        NavHostFragment.findNavController(CopyDialogFragment.this).popBackStack(R.id.FirstFragment, false);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
