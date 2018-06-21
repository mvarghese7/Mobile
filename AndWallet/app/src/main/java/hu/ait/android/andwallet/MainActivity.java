package hu.ait.android.andwallet;

import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.etLabel)
    EditText etLabel;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.rbIncome)
    RadioButton rbIncome;
    @BindView(R.id.rbExpense)
    RadioButton rbExpense;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.mainLayout)
    LinearLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnSave)
    public void saveButtonClicked() {

        View input = getLayoutInflater().inflate(R.layout.input_layout, null, false);

        TextView tvLabel= input.findViewById(R.id.tvLabel);
        tvLabel.setText(etLabel.getText().toString());

        TextView tvAmount= input.findViewById(R.id.tvAmount);
        tvAmount.setText(etAmount.getText().toString());

        if(!tvLabel.getText().equals("") && !tvAmount.getText().equals("") &&
                android.text.TextUtils.isDigitsOnly(tvAmount.getText())) {
            ImageView ivIcon = input.findViewById(R.id.ivIcon);

            int balance = 0;
            if(rbIncome.isChecked()) {
                ivIcon.setImageResource(R.mipmap.plussign);
                balance = (Integer.parseInt(String.valueOf(tvBalance.getText())) +
                        Integer.parseInt(String.valueOf(tvAmount.getText())));
            } else if(rbExpense.isChecked()){
                ivIcon.setImageResource(R.mipmap.minussign);
                balance = (Integer.parseInt(String.valueOf(tvBalance.getText())) -
                        Integer.parseInt(String.valueOf(tvAmount.getText())));
            }


            tvBalance.setText("");
            tvBalance.setText(String.format("%d", balance));

            layoutContent.addView(input);
            tvLabel.setHintTextColor(Color.GRAY);
            tvAmount.setHintTextColor(Color.GRAY);

        } else {
            if(tvLabel.getText().equals("")) {
//                tvLabel.setHint("Please label your income or expense!");
//                tvLabel.setHintTextColor(Color.RED);
                Toast.makeText(this, "Please label your income or expense!", Toast.LENGTH_SHORT).show();
            }
            if(tvAmount.getText().equals("")) {
//                tvAmount.setHint("Please enter an amount!");
//                tvAmount.setHintTextColor(Color.RED);
                Toast.makeText(this, "Please enter an amount!", Toast.LENGTH_SHORT).show();
            }
            if(!android.text.TextUtils.isDigitsOnly(tvAmount.getText())) {
                Toast.makeText(this, "Please enter amount in digits!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @OnClick(R.id.btnClearAll)
    public void clearAllClicked() {
        layoutContent.removeAllViews();
        tvBalance.setText("0");
    }
}
