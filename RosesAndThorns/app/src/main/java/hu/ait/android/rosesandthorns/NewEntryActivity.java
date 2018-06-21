package hu.ait.android.rosesandthorns;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.rosesandthorns.data.Entry;

public class NewEntryActivity extends AppCompatActivity {

    @BindView(R.id.etRose)
    EditText etRose;
    @BindView(R.id.etThorn)
    EditText etThorn;
    @BindView(R.id.etBud)
    EditText etBud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnSaveEntry)
    void sendClick() {
        uploadPost();
    }

    private void uploadPost() {
        String key = FirebaseDatabase.getInstance().getReference().child("entries").push().getKey();
        if(TextUtils.isEmpty(etRose.getText().toString())) {
            etRose.setError(getString(R.string.empty_entry_message));
        } else if(TextUtils.isEmpty(etThorn.getText().toString())) {
            etThorn.setError(getString(R.string.empty_entry_message));
        } else if(TextUtils.isEmpty(etBud.getText().toString())) {
            etBud.setError(getString(R.string.empty_entry_message));
        } else {
            Entry newEntry = new Entry(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    etRose.getText().toString(),
                    etThorn.getText().toString(),
                    etBud.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("entries").child(key).setValue(newEntry).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(NewEntryActivity.this, "Entry created", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
    }
}
