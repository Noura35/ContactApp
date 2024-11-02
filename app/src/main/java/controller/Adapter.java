package controller;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import views.AfficheContacts;

import com.example.contactapp.R;

import java.util.ArrayList;
import java.util.List;

import model.Contact;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

   private Context con;
   private List<Contact> list;
   private DatabaseHandler db;
    List<Contact> filteredList;    // Liste filtrée




    public Adapter(Context con, List<Contact> list, DatabaseHandler db) {
        this.con = con;
        this.list = list;
        this.db = db;
        this.filteredList = new ArrayList<>(list); // Initialiser avec la liste complète
    }



    //creer viewholder (viewholder : instance fih les references lel view eli te7eb te3rethom fel fi kol item fi recycleview)
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.element,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {

            Contact contact=list.get(position);
            holder.name.setText(contact.getNom());
            holder.pseudo.setText(contact.getPseudo());
            holder.tel.setText(contact.getTel());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition(); // Récupérer la position actuelle
                if (currentPosition != RecyclerView.NO_POSITION) { // Vérifie si la position est valide
                    deleteContact(currentPosition);
                }
            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // Créer un AlertDialog
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                alert.setTitle("Edit");

                // Inflater le layout du dialog
                LayoutInflater inf = LayoutInflater.from(con);
                View vd = inf.inflate(R.layout.edit_contact, null);
                alert.setView(vd);

                // Obtenir le contact actuel
                Contact contact = list.get(holder.getAdapterPosition());

                // Références aux champs EditText dans le dialog
                EditText editName = vd.findViewById(R.id.ednom_ed);
                EditText editPseudo = vd.findViewById(R.id.edpseudo_ed);
                EditText editTel = vd.findViewById(R.id.edtel_ed);

                // Remplir les champs avec les données existantes
                editName.setText(contact.getNom());
                editPseudo.setText(contact.getPseudo());
                editTel.setText(contact.getTel());

                // Ajouter les boutons pour "Enregistrer" et "Annuler"
                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Mettre à jour le contact avec les nouvelles informations
                        contact.setNom(editName.getText().toString());
                        contact.setPseudo(editPseudo.getText().toString());
                        contact.setTel(editTel.getText().toString());

                        int x =db.updateContact(contact);

                        // Rafraîchir la liste pour afficher les changements
                        notifyItemChanged(holder.getAdapterPosition());
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Fermer le dialog sans rien changer
                    }
                });


                // Afficher le AlertDialog
                alert.show();




            }
        });



/*
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition(); // Récupérer la position actuelle
                Contact c = list.get(currentPosition);
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(android.net.Uri.parse("tel:"+c.getTel()));
                con.startActivity(i);
            }
        });
*/

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition(); // Récupérer la position actuelle
                Contact c = list.get(currentPosition);
                String phoneNumber = c.getTel();

                // Vérifier si la permission est accordée (uniquement pour ACTION_CALL)
                if (ContextCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission
                    ActivityCompat.requestPermissions((Activity) con, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    // Permission accordée, effectuer l'appel direct
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(android.net.Uri.parse("tel:" + phoneNumber));
                    con.startActivity(callIntent);
                }
            }
        });




    }











    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,pseudo,tel;
        public ImageView delete,edit,call;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.ednomcont);
            pseudo=itemView.findViewById(R.id.edpseudocont);
            tel=itemView.findViewById(R.id.edtelcont);
            delete=itemView.findViewById(R.id.imgdel);
            edit=itemView.findViewById(R.id.imgedit);
            call=itemView.findViewById(R.id.imgcall);

        }
    }


private void deleteContact(int position){
        db.deleteContact(list.get(position));
        list.remove(position);
        AfficheContacts.notifyAdapter();
}



    public void setfilteredList(List<Contact> filteredList) {
       list=filteredList;
       notifyDataSetChanged();
    }



}
