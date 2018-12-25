package com.example.yaswanth.inventorystage1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;
import com.example.yaswanth.inventorystage1.data.StoreDbHelper;

public class MainActivity extends AppCompatActivity {

    private StoreDbHelper mDbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for new product
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductEditActivity.class);
                startActivity(intent);
            }
        });

        mDbhelper = new StoreDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase database = mDbhelper.getReadableDatabase();
        String[] projection = {ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };
        //query the database
        Cursor cursor = database.query(ProductEntry.TABLE_NAME, projection, null, null, null, null, null);
        try {

            TextView displayView = findViewById(R.id.store_list);
            displayView.setText("The Product table contains " + cursor.getCount() + " Products.\n\n");
            displayView.append("\tID|\tP.NAME|\t Price|\tQuantity|\tS.name|\tS.No\n\n");
            int productIDIndex = cursor.getColumnIndex(ProductEntry._ID);
            int productNameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int productPriceIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int productQuantityIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierNumberIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);
            while (cursor.moveToNext()) {
                displayView.append("\t" + cursor.getInt(productIDIndex) + "\t\t|"
                        + cursor.getString(productNameIndex) + "|\t\t"
                        + cursor.getInt(productPriceIndex) + "|\t\t"
                        + cursor.getInt(productQuantityIndex) + "|\t\t"
                        + cursor.getString(supplierNameIndex) + "|\t\t"
                        + cursor.getInt(supplierNumberIndex) + "\n\n"
                );
            }

        } finally {
            cursor.close();
        }

    }
}
