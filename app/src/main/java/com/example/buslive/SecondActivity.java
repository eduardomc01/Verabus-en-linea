package com.example.buslive;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        Button btn = findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                servicio(view);
            }
        });

    }



    public void servicio(View v){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void ReturnActivityMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }





}
