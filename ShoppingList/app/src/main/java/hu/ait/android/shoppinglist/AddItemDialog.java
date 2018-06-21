package hu.ait.android.shoppinglist;

import android.support.v4.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import hu.ait.android.shoppinglist.data.Item;

public class AddItemDialog extends DialogFragment {

    public interface ItemHandler {
        public void onNewItemCreated(Item item);

        public void onItemUpdated(Item item);
    }

    private ItemHandler itemHandler;
    private Spinner spinnerItemType;
    private EditText etItemName;
    private EditText etPrice;
    private boolean cbPurchased;
    private Item itemToEdit = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ItemHandler) {
            itemHandler = (ItemHandler)context;
        } else {
            throw new RuntimeException(
                    getString(R.string.error_wrong_interface));
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New item");

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.activity_add_item, null);


        spinnerItemType = (Spinner) rootView.findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItemName = (EditText) rootView.findViewById(R.id.etItemName);
        etPrice = (EditText) rootView.findViewById(R.id.etPrice);

        if (getArguments() != null &&
                getArguments().containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
            Item itemToEdit = (Item) getArguments().getSerializable(MainActivity.KEY_ITEM_TO_EDIT);
            etItemName.setText(itemToEdit.getItemTitle());
            etPrice.setText(itemToEdit.getPrice());
            spinnerItemType.setSelection(itemToEdit.getCategory());
        }

        builder.setView(rootView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }


    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!TextUtils.isEmpty(etItemName.getText())) {
                        if (getArguments() != null &&
                                getArguments().containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
                            Item itemToEdit = (Item) getArguments().getSerializable(MainActivity.KEY_ITEM_TO_EDIT);
                            itemToEdit.setItemTitle(etItemName.getText().toString());
                            itemToEdit.setPrice(etPrice.getText().toString());
                            itemToEdit.setCategory(spinnerItemType.getSelectedItemPosition());

                            itemHandler.onItemUpdated(itemToEdit);
                        } else {
                            Item item = new Item(
                                    etItemName.getText().toString(),
                                    etPrice.getText().toString(),
                                    false,
                                    spinnerItemType.getSelectedItemPosition()
                            );

                            itemHandler.onNewItemCreated(item);
                        }

                        d.dismiss();

                    } else {
                        etItemName.setError("Error: field empty");
                    }

                }
            });
        }
    }
}