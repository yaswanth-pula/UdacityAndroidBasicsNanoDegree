package com.example.yaswanth.inventorystage1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;

public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_store_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {


        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID));
        int cPrice = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
        final int cQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));

        Button saleButton = view.findViewById(R.id.saleButton);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri currentUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                if (cQuantity > 0) {
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, cQuantity - 1);
                    context.getContentResolver().update(currentUri, values, null, null);
                } else {

                    Toast.makeText(context, R.string.out_of_stock_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button editButton = view.findViewById(R.id.showButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductEditActivity.class);
                Uri currentProduct = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                intent.setData(currentProduct);
                context.startActivity(intent);
            }
        });

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        String price = context.getString(R.string.list_price_tag) + String.valueOf(cPrice);
        String quantity = context.getString(R.string.list_quantity_tag) + String.valueOf(cQuantity);
        tvName.setText(name);
        tvPrice.setText(price);
        tvQuantity.setText(quantity);
    }
}
