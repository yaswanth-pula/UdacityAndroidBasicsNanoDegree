package com.example.yaswanth.inventorystage1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;
import com.example.yaswanth.inventorystage1.data.StoreDbHelper;

public class ProductEditActivity extends AppCompatActivity {
    /*Reference EditText Field's for entered data*/
    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private EditText mProductQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        //find all views
        mProductNameEditText = findViewById(R.id.in_product_name);
        mProductPriceEditText = findViewById(R.id.in_product_price);
        mProductQuantityEditText = findViewById(R.id.in_quantity);
        mSupplierNameEditText = findViewById(R.id.in_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.in_supplier_number);
    }

    private void insertData() {
        String productName = mProductNameEditText.getText().toString().trim();
        int productPrice = Integer.parseInt(mProductPriceEditText.getText().toString().trim());
        int productQuantity = Integer.parseInt(mProductQuantityEditText.getText().toString().trim());
        String supplierName = mSupplierNameEditText.getText().toString().trim();
        long supplierNumber = Long.parseLong(mSupplierPhoneNumberEditText.getText().toString().trim());
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, supplierNumber);
        StoreDbHelper mDbHelper = new StoreDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long result = db.insert(ProductEntry.TABLE_NAME, null, contentValues);
        if (result == -1)
            Toast.makeText(this, "ERROR IN INSERTING DATA ", Toast.LENGTH_LONG).show();

        else
            Toast.makeText(this, "DATA INSERTED : " + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {
            // insert into database
            insertData();
            finish();
            return true;
        } else if (item.getItemId() == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
