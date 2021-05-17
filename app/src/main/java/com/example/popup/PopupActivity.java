package com.example.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupActivity extends Activity {

    EditText Name;
    EditText age;
    EditText NickName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Name=findViewById(R.id.Name);
        age=findViewById(R.id.age);
        NickName=findViewById(R.id.NickName);

        //UI 객체생성

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Name.setText(data);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        String data = Name.getText().toString();

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        DatabaseReference rootRef= firebaseDatabase.getReference();

        String name = Name.getText().toString();
        String Age = age.getText().toString();
        String Nickname = NickName.getText().toString();

        Person person = new Person(name, Age, Nickname);

        DatabaseReference personRef = rootRef.child("persons");
        personRef.push().setValue(person);

       /* personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                StringBuffer buffer = new StringBuffer();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Person person = snapshot.getValue(Person.class);
                    String name = person.getName();
                    String Age = person.getAge();
                    String Nickname = person.getNickName();

                    buffer.append(name+","+Age+","+Nickname+"\n");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}






