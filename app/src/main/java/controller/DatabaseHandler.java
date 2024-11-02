package controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.Contact;
import utils.Utils;
public class DatabaseHandler extends SQLiteOpenHelper {

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + " ( " +
                Utils.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Utils.KEY_NAME + " TEXT , " +
                Utils.KEY_PSEUDO + " TEXT , " +
                Utils.KEY_TEL + " TEXT ); ";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);
    }

    // Add contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, contact.getNom());
        contentValues.put(Utils.KEY_PSEUDO, contact.getPseudo());
        contentValues.put(Utils.KEY_TEL, contact.getTel());

        db.insert(Utils.TABLE_NAME, null, contentValues);
        db.close();
    }

    // Get a contact by ID
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Utils.TABLE_NAME,
                new String[]{Utils.KEY_ID, Utils.KEY_NAME, Utils.KEY_PSEUDO, Utils.KEY_TEL},
                Utils.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        cursor.close();  // Close cursor after use
        return contact;
    }

    // Get all contacts
    public List<Contact> getAllContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> list_contact = new ArrayList<>();

        String getAll = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(getAll, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNom(cursor.getString(1));
                contact.setPseudo(cursor.getString(2));
                contact.setTel(cursor.getString(3));
                list_contact.add(contact);
            } while (cursor.moveToNext());  // Use moveToNext() instead of moveToFirst()
        }
        cursor.close();  // Close cursor after use
        return list_contact;
    }


    // update a contact
    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, contact.getNom());
        contentValues.put(Utils.KEY_PSEUDO, contact.getPseudo());
        contentValues.put(Utils.KEY_TEL, contact.getTel());


        int result=db.update(Utils.TABLE_NAME, contentValues ,Utils.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        db.close();

        return result;
    }


    //delete contact:

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(Utils.TABLE_NAME ,Utils.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

        db.close();

    }


public int getNumContact(){

    SQLiteDatabase db = this.getReadableDatabase();
    String getAll = "SELECT * FROM " + Utils.TABLE_NAME;

    Cursor cursor = db.rawQuery(getAll, null);

    //cursor.close();

    return cursor.getCount();
}


}
