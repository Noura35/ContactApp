package views;

import static views.login.NAME;
import static views.login.SHARED_PREF_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapp.R;

import java.util.ArrayList;
import java.util.List;

import controller.Adapter;
import controller.DatabaseHandler;
import model.Contact;

public class AfficheContacts extends AppCompatActivity {

    public static Adapter adapter;

    TextView tvuser;
    ImageView btnAdd;
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    SearchView searchView;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_affiche_contacts);

        tvuser=findViewById(R.id.tv_acc);


        //Intent x=this.getIntent();
        //Bundle b=x.getExtras();
        //String u=b.getString("user");
       // tvuser.setText(tvuser.getText().toString()+" "+u);


        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, 0);

        if(preferences.contains(NAME)) {
            String username = preferences.getString(NAME, "Not found");
            tvuser.setText(tvuser.getText().toString() + " " + username);
        }






        recyclerView=findViewById(R.id.rv);
        databaseHandler=new DatabaseHandler(this);

        searchView=findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });




        //btn add
        btnAdd=findViewById(R.id.addImg);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AfficheContacts.this,AddContact.class);
                startActivity(i);
                finish();

            }
        });


        adapter=new Adapter(this,databaseHandler.getAllContact(),databaseHandler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        //logout
        btnLogout=findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(AfficheContacts.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                finish();

            }
        });












        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





    private void filterList(String text) {
        List<Contact> filteredList=new ArrayList<>();
        List<Contact> allList=databaseHandler.getAllContact();
        for (Contact contact : allList) {
            if (contact.getNom().toLowerCase().contains(text.toLowerCase()) ||
                    contact.getPseudo().toLowerCase().contains(text.toLowerCase()) ){
                filteredList.add(contact);

                if (filteredList.isEmpty()) {
                    Toast.makeText(this, "no data found", Toast.LENGTH_LONG).show();
                } else {
                    adapter.setfilteredList(filteredList);
                }
            }
        }
    }


    public static void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }



}