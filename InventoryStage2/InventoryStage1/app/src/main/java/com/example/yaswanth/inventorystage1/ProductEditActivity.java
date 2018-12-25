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
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;

public class ProductEditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /*Reference EditText Field's for entered data*/
    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private EditText mProductQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneNumberEditText;
    private static final int EXISTING_PRODUCT_LOADER = 0;
    private Uri currentProductUri;
    private boolean mProductChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        if (currentProductUri == null) {
            setTitle(getString(R.string.edit_activity_add_Label));
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.edit_activity_edit_label));

            getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
        }

        //find all views
        mProductNameEditText = findViewById(R.id.in_product_name);
        mProductPriceEditText = findViewById(R.id.in_product_price);
        mProductQuantityEditText = findViewById(R.id.in_quantity);
        mSupplierNameEditText = findViewById(R.id.in_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.in_supplier_number);
        //add touch listeners
        mProductNameEditText.setOnTouchListener(mTouchListener);
        mProductPriceEditText.setOnTouchListener(mTouchListener);
        mProductQuantityEditText.setOnTouchListener(mTouchListener);
        mSupplierNameEditText.setOnTouchListener(mTouchListener);
        mSupplierPhoneNumberEditText.setOnTouchListener(mTouchListener);
    }

    private void saveProduct() {
        int productPrice, productQuantity;
        String productName, supplierName, supplierNumber;
        try {
            productName = mProductNameEditText.getText().toString().trim();
            productPrice = Integer.parseInt(mProductPriceEditText.getText().toString().trim());
            productQuantity = Integer.parseInt(mProductQuantityEditText.getText().toString().trim());
            supplierName = mSupplierNameEditText.getText().toString().trim();
            supplierNumber = mSupplierPhoneNumberEditText.getText().toString().trim();
        } catch (Exception e) {
            Toast.makeText(this, R.string.edit_empty_error, Toast.LENGTH_SHORT).show();
            return;
        }


        if (productPrice < 0 || productQuantity == 0) {
            Toast.makeText(this, R.string.invalid_price_quantity, Toast.LENGTH_SHORT).show();
            return;
        }
        if (supplierNumber.length() != 10) {
            Toast.makeText(this, R.string.invalid_mobile_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (productName.length() == 0 || supplierName.length() == 0) {
            Toast.makeText(this, R.string.invalid_names, Toast.LENGTH_SHORT).show();
            return;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, supplierNumber);

        if (currentProductUri == null) {
            //insertion of new row in data base
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);
            if (newUri == null) {
                Toast.makeText(this, R.string.insert_error_toast, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, R.string.insert_success_toast, Toast.LENGTH_LONG).show();
                finish();
            }

        } else {
            int rowsUpdated = getContentResolver().update(currentProductUri, contentValues, null, null);
            if (rowsUpdated == 0) {
                Toast.makeText(this, getString(R.string.update_fail_msg), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, getString(R.string.update_success_msg), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // insert into database
                saveProduct();
                return true;
            case android.R.id.home:
                if (!mProductChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(ProductEditActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!mProductChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
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
            //append data
            mProductNameEditText.setText(name);
            mProductPriceEditText.setText(String.valueOf(price));
            mProductQuantityEditText.setText(String.valueOf(quantity));
            mSupplierNameEditText.setText(supplierName);
            mSupplierPhoneNumberEditText.setText(supplierNumber);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductNameEditText.setText("");
        mProductPriceEditText.setText("");
        mProductQuantityEditText.setText("");
        mSupplierNameEditText.setText("");
        mSupplierPhoneNumberEditText.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
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


}




