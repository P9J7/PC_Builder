package com.p9j7.pcbuilder.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.p9j7.pcbuilder.Data.SchemeViewModel;
import com.p9j7.pcbuilder.R;


public class DeleteDialogFragment extends DialogFragment {
    private SchemeViewModel schemeViewModel;

    public DeleteDialogFragment(SchemeViewModel schemeViewModel) {
        this.schemeViewModel = schemeViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_delete, null);
        builder.setTitle(R.string.fire)
                .setView(view)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        schemeViewModel.delete(schemeViewModel.getSelected().getValue().getScheme(),
                                schemeViewModel.getSelected().getValue().getParts());
                        NavHostFragment.findNavController(DeleteDialogFragment.this).navigateUp();
                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
