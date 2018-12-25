package com.example.yaswanth.inventorystage1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;

public class ProductDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView mProductNameTextView;
    private TextView mProductPriceTextView;
    private TextView mProductQuantityTextView;
    private TextView mSupplierNameTextView;
    private TextView mSupplierPhoneNumberTextView;
    private Uri currentProductUri;
    private int PRODUCT_LOADER = 0;
    private Button mQuantityIncreaseButton;
    private Button mQuantityDecreaseButton;
    private Button mOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        currentProductUri = intent.getData();

        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        //find all views
        mProductNameTextView = findViewById(R.id.out_product_name);
        mProductPriceTextView = findViewById(R.id.out_product_price);
        mProductQuantityTextView = findViewById(R.id.out_quantity);
        mSupplierNameTextView = findViewById(R.id.out_supplier_name);
        mSupplierPhoneNumberTextView = findViewById(R.id.out_supplier_number);
        mQuantityIncreaseButton = findViewById(R.id.quantity_increase_button);
        mQuantityDecreaseButton = findViewById(R.id.quantity_decrease_button);
        mOrderButton = findViewById(R.id.productOrderButton);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
        };
        return new CursorLoader(this, currentProductUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        final int newQuantity;
        final String supplier_number;
        if (cursor.moveToFirst()) {
            //read index
            int productNameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int productPriceIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int productQuantityIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierNumberIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);
            //read data
            String name = cursor.getString(productNameIndex);
            int price = cursor.getInt(productPriceIndex);
            int quantity = cursor.getInt(productQuantityIndex);
            String supplierName = cursor.getString(supplierNameIndex);
            String supplierNumber = cursor.getString(supplierNumberIndex);
            String priceDetail = getString(R.string.detail_price_tag) + String.valueOf(price);
            //append data
            mProductNameTextView.setText(name);
            mProductPriceTextView.setText(priceDetail);
            mProductQuantityTextView.setText(String.valueOf(quantity));
            mSupplierNameTextView.setText(supplierName);
            mSupplierPhoneNumberTextView.setText(supplierNumber);
            newQuantity = quantity;
            supplier_number = supplierNumber;
        } else {
            newQuantity = -1;
            supplier_number = "";
        }

        mQuantityIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newOrderQuantity = newQuantity + 1;
                ContentValues values = new ContentValues();
                values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newOrderQuantity);
                getContentResolver().update(currentProductUri, values, null, null);
            }
        });


        mQuantityDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newOrderQuantity = newQuantity - 1;
                if (newOrderQuantity < 0) {
                    mQuantityDecreaseButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Cannot order less than 1 product", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newOrderQuantity);
                    getContentResolver().update(currentProductUri, values, null, null);

                }
            }

        });
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:" + supplier_number));
                if (in.resolveActivity(getPackageManager()) != null)
                    startActivity(in);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete)
            showDeleteConfirmationDialog();

        return super.onOptionsItemSelected(item);
    }


    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {

        if (currentProductUri != null) {

            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);


            if (rowsDeleted == 0) {

                Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }


        // Close the activity
        finish();
    }


}
