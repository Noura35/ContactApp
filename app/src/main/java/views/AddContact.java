package views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactapp.R;

import controller.DatabaseHandler;
import model.Contact;

public class AddContact extends AppCompatActivity {



     EditText edname,edpseudo,edtel;
     Button savebtn,qtbtn;
     DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        edname=findViewById(R.id.ed_name_add);
        edpseudo=findViewById(R.id.ed_name_add);
        edtel=findViewById(R.id.ed_name_add);

        databaseHandler = new DatabaseHandler(this); // Initialisation de DatabaseHandler

        savebtn=findViewById(R.id.btn_save_add);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String nom =edname.getText().toString();
                 String pseudo=edpseudo.getText().toString();
                 String tel=edtel.getText().toString();

                 databaseHandler.addContact(new Contact(nom,pseudo,tel));
                Intent intent=new Intent(AddContact.this,AfficheContacts.class);
                startActivity(intent);
                finish();

            }
        });



        qtbtn=findViewById(R.id.btn_cancel_add);
        qtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(AddContact.this,AfficheContacts.class);
                    startActivity(intent);
                    finish();
            }
        });















        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}