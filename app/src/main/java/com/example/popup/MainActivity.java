package com.example.popup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    TextView tv;
    EditText Name;
    EditText age;
    EditText NickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        Name=findViewById(R.id.Name);
        age=findViewById(R.id.age);
        NickName=findViewById(R.id.NickName);

    }

    public void clickSave(View view) {

        String data = Name.getText().toString();

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        DatabaseReference rootRef= firebaseDatabase.getReference();

        String name = Name.getText().toString();
        String Age = age.getText().toString();
        String Nickname = NickName.getText().toString();

        Person person = new Person(name, Age, Nickname);

        DatabaseReference personRef = rootRef.child("persons");
        personRef.push().setValue(person);

        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                StringBuffer buffer = new StringBuffer();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Person person = snapshot.getValue(Person.class);
                    String name = person.getName();
                    String Age = person.getAge();
                    String Nickname = person.getNickName();

                    buffer.append("이름 : " + name+"\n"+"나이 : " + Age+"\n"+ "별명 : " + Nickname+"\n\n");
                }
                tv.setText(buffer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
