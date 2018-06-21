package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import hu.ait.android.shoppinglist.AddItemDialog;
import hu.ait.android.shoppinglist.data.AppDatabase;
import hu.ait.android.shoppinglist.data.Item;
import hu.ait.android.shoppinglist.data.ItemRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements AddItemDialog.ItemHandler {

    private ItemRecyclerAdapter itemRecyclerAdapter;
    public static final String KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

        setUpDeleteAllButton();
        setUpDeletePurchasedButton();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initItems(recyclerView);

        saveThatItWasStarted();
    }

    private void setUpDeleteAllButton() {
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        final List<Item> items = AppDatabase.getAppDatabase(MainActivity.this).itemDao().getAll();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0; i < items.size(); i++) {
                                    itemRecyclerAdapter.removeItem(0);
                                }
                            }
                        });
                    }
                }.start();
            }

        });
    }

    private void setUpDeletePurchasedButton() {
        Button btnDeletePurchased = findViewById(R.id.btnDeletePurchased);
        btnDeletePurchased.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        final List<Item> items = AppDatabase.getAppDatabase(MainActivity.this).itemDao().getAll();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int i = 0;
                                while(i < items.size()) {
                                    if(items.get(i).isPurchased()) {
                                        itemRecyclerAdapter.removeItem(i);
                                    } else {
                                        i++;
                                    }
                                }
                            }
                        });
                    }
                }.start();
            }

        });
    }

    public void saveThatItWasStarted() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("KEY_FIRST", false);
        editor.commit();
    }

    private void showAddItemDialog() {
        new AddItemDialog().show(getSupportFragmentManager(), "AddItemDialog");
    }

    public void initItems(final RecyclerView recyclerView) {
        new Thread() {
            @Override
            public void run() {
                //query items from the database
                final List<Item> items = AppDatabase.getAppDatabase(MainActivity.this).itemDao().getAll();

                //use items by giving to adapter class
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemRecyclerAdapter = new ItemRecyclerAdapter(items, MainActivity.this);
                        recyclerView.setAdapter(itemRecyclerAdapter);

                    }
                });

            }
        }.start();

    }

    @Override
    public void onNewItemCreated(final Item newItem) {
        new Thread() {
            @Override
            public void run() {

                long id = AppDatabase.getAppDatabase(MainActivity.this).itemDao().insertItem(newItem);

                newItem.setItemId(id);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //and add it to the adapter so that it can be shown
                        itemRecyclerAdapter.addItem(newItem);
                    }
                });


            }
        }.start();
    }

    @Override
    public void onItemUpdated(final Item item) {
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(MainActivity.this).itemDao().update(item);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemRecyclerAdapter.updateItem(item);
                    }
                });
            }
        }.start();
    }

    public void editItem(Item item) {
        AddItemDialog editDialog = new AddItemDialog();

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ITEM_TO_EDIT, item);

        editDialog.setArguments(bundle);

        editDialog.show(getSupportFragmentManager(), "EditDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
