package hu.ait.android.shoppinglist.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by mayavarghese on 4/5/18.
 */

@Entity
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long itemId;

    @ColumnInfo(name = "item_name")
    private String itemTitle;
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "purchased")
    private boolean purchased;

    @ColumnInfo(name = "category")
    private int category;

    public Item(String itemTitle, String price, boolean purchased, int category) {
        this.itemTitle = itemTitle;
        this.price = price;
        this.purchased = purchased;
        this.category = category;
    }


    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public int getCategory() {
        return category;
    }

    public int setCategory(int category) {
        return category;
    }

}