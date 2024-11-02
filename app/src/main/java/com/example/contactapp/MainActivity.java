package com.example.contactapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import controller.DatabaseHandler;
import model.Contact;
import views.login;


public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    Button btnStarted;
    private static final int SPLASH_DELAY = 3000; // Durée de 3 secondes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db= new DatabaseHandler(this);

            db.addContact(new Contact("noura","yyy","92989950"));
            db.addContact(new Contact("omar","hhhhh","92989950"));
            db.addContact(new Contact("hamza","gfff","92989950"));
            db.addContact(new Contact("med","yyyyy","92989950"));



       /*
        List<Contact> contactList=db.getAllContact();


        Log.d("before","---------------------");

        for (Contact c: contactList){
            String myInfo="ID: "+c.getId()+" Name:"+c.getNom();
            Log.d("contact data : ",myInfo);
        }

        Log.d("all data","---------------------");
        Log.d("person num =",String.valueOf(db.getNumContact()));

        //Contact p=db.getContact(4);
        //db.deleteContact(p);

*/




//        Contact c1=db.getContact(2);
//        String myInfo1="ID: "+c1.getId()+" Name:"+c1.getNom();
//        Log.d("person before update",myInfo1);
//


        //update
//        Contact c=db.getContact(2);
//        c.setNom("abd");
//        c.setPseudo("s");
//        c.setTel("2524898");
//
//        int updateInfo=db.UpdateContact(c);
//        String myInfo="ID: "+c.getId()+" Name:"+c.getNom();
//
//        Log.d("person after update",myInfo);
//

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Après le délai, démarrer l'activité principale
                Intent mainIntent = new Intent(MainActivity.this, login.class);
                startActivity(mainIntent);
                finish(); // Ferme l'activité du splash screen
            }
        }, SPLASH_DELAY);



















        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}