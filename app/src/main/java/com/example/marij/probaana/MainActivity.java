package com.example.marij.probaana;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBAdapter db = new DBAdapter(this);


        //---add a contact---
        db.open();
       long id = db.insertContact("Wei-Meng Lee", "weimenglee@learn2develop.net");
        id = db.insertContact("Mary Jackson", "mary@jackson.com");
       id = db.insertContact("Anci nikic", "ann@wee.com");
        db.close();

        db.open();
       id = db.insertAddress(1, "ulica1", 9, "Tokio");
        id = db.insertAddress(2, "ulica2", 9, "Tokijjjo");
       id = db.insertAddress(3, "ulica3", 80, "Zagreb");
        db.close();



        //--get all contacts---
//        db.open();
//        Cursor c = db.getAllContacts();
//        if (c.moveToFirst())
//        {
//            do {
//                DisplayContact(c);
//            } while (c.moveToNext());
//        }
//        db.close();
//
//
//        //--get all addresses--
//        db.open();
//        Cursor ca = db.getAllAddresses();
//        if (ca.moveToFirst())
//        {
//            do {
//                DisplayAddress(ca);
//            } while (c.moveToNext());
//        }
//        db.close();



        //---get a contact---
        db.open();
        Cursor cu = db.getContact(2);
        if (cu.moveToFirst())
            DisplayContact(cu);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        db.close();


        //--get an address--
        db.open();
        Cursor cua = db.getAddress(2);
        if (cua.moveToFirst())
            DisplayAddress(cua);
        else
            Toast.makeText(this, "No address found", Toast.LENGTH_LONG).show();
        db.close();

        db.open();
        Cursor cua1 = db.getAddress(1);
        if (cua1.moveToFirst())
            DisplayAddress(cua1);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();
        db.close();



        //---update contact---
        db.open();
        if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();


        //--update address--
        db.open();
        if (db.updateAddress(1, 1,"novaUlica", 6, "Tokio"))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
        db.close();


        //---delete a contact---
//        db.open();
//        for (int i = 15; i< 22; i++) {
//            if (db.deleteContact(i))
//                Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
//        }
//        db.close();


        //--delete address--
//        db.open();
//        for (int i=3; i<15; i++) {
//            if (db.deleteAddress(i))
//                Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
//        }
//        db.close();


        //--get all contacts and addresses--- again!
        db.open();
        Cursor c2 = db.getAllContacts();
        if (c2.moveToFirst())
        {
            do {
                DisplayContact(c2);
            } while (c2.moveToNext());
        }
        db.close();

        db.open();
        Cursor c3 = db.getAllAddresses();
        if (c3.moveToFirst())
        {
            do {
                DisplayAddress(c3);
            } while (c3.moveToNext());
        }
        db.close();
    }

    //funkcija za ispis
    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "Email:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }

    public void DisplayAddress(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "ContactId: " + c.getString(1) + "\n" +
                        "Street:  " + c.getString(2) + "\n" +
                        "House number:  " + c.getString(3) + "\n" +
                        "City:  " + c.getString(4),
                Toast.LENGTH_LONG).show();
    }


}
