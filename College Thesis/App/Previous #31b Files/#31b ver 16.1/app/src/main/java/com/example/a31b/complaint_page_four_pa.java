package com.example.a31b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class complaint_page_four_pa extends Activity {

    String _REFCODE, _PLACE, _USERNAME;
    DatabaseReference reference;
    TextView refcode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_page_four_pa);

        final Button done = findViewById(R.id.btnDone_complaint_page_qrCode);
        refcode = findViewById(R.id.refcode_pa);

        Intent intent = getIntent();
        _PLACE = intent.getStringExtra("Place_of_Incident");

        reference = FirebaseDatabase.getInstance().getReference(_PLACE).child("Physical_Abuse");

        showAllUserData();

        //BACK TO DASHBOARD BUTTON
         done.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = getIntent();
                 _USERNAME = intent.getStringExtra("username");
                 //_EMAIL = intent.getStringExtra("email");

                 DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                 Query checkUser = reference.orderByChild("username").equalTo(_USERNAME);

                 checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if (dataSnapshot.exists()){

                             String usernameFromDB = dataSnapshot.child(_USERNAME).child("username").getValue(String.class);
                             //   String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                                                                                // pinapalitan to, kase ito yung destination page
                             Intent intent = new Intent(getApplicationContext(), dashboardPage.class);
                             intent.putExtra("username", usernameFromDB);
                             //   intent.putExtra("email", emailFromDB);
                             startActivity(intent);
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

             }
         });

    }

   private void showAllUserData(){
        Intent intent = getIntent();
        _REFCODE = intent.getStringExtra("Reference_Number");
        refcode.setText(_REFCODE);
    }

}
