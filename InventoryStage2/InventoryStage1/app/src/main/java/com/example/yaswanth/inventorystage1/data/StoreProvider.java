package com.example.yaswanth.inventorystage1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.yaswanth.inventorystage1.data.StoreContract.ProductEntry;

public class StoreProvider extends ContentProvider {

    private static final String LOG_TAG = StoreProvider.class.getSimpleName();
    private static final String INSERT_LOG_MSG = "Failed to insert row for ";
    private StoreDbHelper mStoreDbHelper;
    private static final int PRODUCT_TABLE = 899;
    private static final int PRODUCT_ROW = 900;

    @Override
    public boolean onCreate() {
        mStoreDbHelper = new StoreDbHelper(getContext());
        return true;
    }

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(StoreContract.CONTENT_AUTHORITY, "products", PRODUCT_TABLE);
        sUriMatcher.addURI(StoreContract.CONTENT_AUTHORITY, "products" + "/#", PRODUCT_ROW);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mStoreDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {

            case PRODUCT_TABLE:
                cursor = database.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ROW:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT_TABLE:
                return ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ROW:
                return ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI" + uri + "with match" + match);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PRODUCT_TABLE:
                return insertNewProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion not possible with this URI " + uri);
        }

    }

    private Uri insertNewProduct(Uri uri, ContentValues contentValues) {

        //checking for the valid product
        String name = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
        int price = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantity = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        String sname = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
        if (TextUtils.isEmpty(name))
            throw new IllegalArgumentException("PRODUCT NAME must be specified");
        if (TextUtils.isEmpty(sname))
            throw new IllegalArgumentException("SUPPLIER NAME must be specified");
        if (price < 0 || quantity < 0)
            throw new IllegalArgumentException("NEGATIVE VALUES ARE NOT ALLOWED");

        SQLiteDatabase database = mStoreDbHelper.getWritableDatabase();
        long result = database.insert(ProductEntry.TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.e(LOG_TAG, INSERT_LOG_MSG + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, result);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mStoreDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case PRODUCT_TABLE:
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ROW:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not possible with this " + uri);
        }
        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT_TABLE:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ROW:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not possible with this " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_NAME)) {
            String pname = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
            if (TextUtils.isEmpty(pname))
                throw new IllegalArgumentException("PRODUCT NAME must be specified");
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_PRICE)) {
            int price = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
            if (price < 0)
                throw new IllegalArgumentException("NEGATIVE VALUES ARE NOT ALLOWED");
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_QUANTITY)) {
            int quantity = contentValues.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            if (quantity < 0)
                throw new IllegalArgumentException("NEGATIVE VALUES NOT ALLOWED");
        }

        if (contentValues.containsKey(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME)) {
            String sname = contentValues.getAsString(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            if (TextUtils.isEmpty(sname))
                throw new IllegalArgumentException("SUPPLIER NAME must be specified");
        }
        if (contentValues.size() == 0)
            return 0;
        SQLiteDatabase database = mStoreDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(ProductEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
