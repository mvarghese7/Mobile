package hu.ait.android.weatherinfo;


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
import android.widget.Button;
import android.widget.EditText;

import hu.ait.android.weatherinfo.data.City;


public class AddCityDialog extends DialogFragment {

    public interface CityHandler {
        public void onNewCityCreated(City city);
    }

    private CityHandler cityHandler;
    private EditText etCityName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CityHandler) {
            cityHandler = (CityHandler)context;
        } else {
            throw new RuntimeException(getString(R.string.error_wrong_interface));
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.new_city);

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.add_city, null);

        etCityName = (EditText) rootView.findViewById(R.id.etCityName);

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
                    if (!TextUtils.isEmpty(etCityName.getText())) {

                            City city = new City(etCityName.getText().toString());
                            cityHandler.onNewCityCreated(city);

                            d.dismiss();

                    } else {
                        etCityName.setError(getString(R.string.field_empty_error));
                    }

                }
            });
        }
    }
}