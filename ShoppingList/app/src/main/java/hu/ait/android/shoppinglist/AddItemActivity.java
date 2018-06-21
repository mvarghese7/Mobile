//package hu.ait.android.shoppinglist;
//
//import android.arch.persistence.db.SupportSQLiteOpenHelper;
//import android.arch.persistence.room.DatabaseConfiguration;
//import android.arch.persistence.room.InvalidationTracker;
//import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
//import android.support.annotation.NonNull;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//
//import java.util.List;
//
//import hu.ait.android.shoppinglist.data.AppDatabase;
//import hu.ait.android.shoppinglist.data.Item;
//import hu.ait.android.shoppinglist.data.ItemDAO;
//
//public class AddItemActivity extends AppCompatActivity {
//
//    private AppDatabase database;
//    private ItemHandler itemHandler;
//
//    public interface ItemHandler {
//        public void onNewItemCreated(Item item);
//
//        public void onItemUpdated(Item item);
//    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////
////        if (context instanceof ItemHandler) {
////            itemHandler = (ItemHandler) context;
////        } else {
////            throw new RuntimeException(
////                    getString(R.string.error_wrong_interface));
////        }
////    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_item);
//
//        database = AppDatabase.getAppDatabase(this);
//
//        final EditText etItemName = findViewById(R.id.etItemName);
//
//        final EditText etPrice = findViewById(R.id.etPrice);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategories);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.categories_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        Button btnAddItem = findViewById(R.id.btnAddItem);
//        btnAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String itemName = etItemName.getText().toString();
//                String price = etPrice.getText().toString();
//                boolean purchased = false; //NEED TO FIX
//                if(TextUtils.isEmpty(itemName)) {
//                    etItemName.setError("Please enter a name");
//                } else if(TextUtils.isEmpty(price)) {
//                    etPrice.setError("Please enter a price");
//                } else {
////                    if (getArguments() != null &&
////                            getArguments().containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
////                        Item itemToEdit = (Item) getArguments().getSerializable(MainActivity.KEY_ITEM_TO_EDIT);
////                        itemToEdit.setItemTitle(etItemName.getText().toString());
////                        itemToEdit.setPrice(etPrice.getText().toString());
////
////                        itemHandler.onItemUpdated(itemToEdit);
////                    } else {
//                        Item item = new Item(
//                                etItemName.getText().toString(),
//                                etPrice.getText().toString(),
//                                purchased,
//
//                        );
//
//                        itemHandler.onNewItemCreated(item);
////                    }
//                }
//            }
//        });
//    }
//}
