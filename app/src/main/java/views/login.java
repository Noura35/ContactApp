package views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.health.ServiceHealthStats;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contactapp.MainActivity;
import com.example.contactapp.R;

public class login extends AppCompatActivity {


    Button btnLogin ,btnQuit;
    EditText ednom,edmp;

     SharedPreferences sharedPreferences;
     static final String SHARED_PREF_NAME="mypref";
     static final String NAME="name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        ednom=findViewById(R.id.ed_username);
        edmp=findViewById(R.id.ed_pass);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,0);


        //when open activity fist check Shared prefernces data available or not
        String name=sharedPreferences.getString(NAME,null);

        if (name !=null){
            //if data is available call afficheContact
            Intent i=new Intent(login.this,AfficheContacts.class);
            startActivity(i);
        }





        //btn btnLogin
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when click a button put data on sharedpreferences

                String nom=ednom.getText().toString();
                String password=edmp.getText().toString();


                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(NAME,nom);
                editor.apply();




                if(nom.equalsIgnoreCase("noura") && password.equalsIgnoreCase("123")){

                    Intent i=new Intent(login.this,AfficheContacts.class);

                   // i.putExtra("user",nom);

                    startActivity(i);

                    Toast.makeText(login.this,"Login Success",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(login.this,"Invalid values ",Toast.LENGTH_SHORT).show();
                }


            }
      });




        btnQuit=findViewById(R.id.btn_qt);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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