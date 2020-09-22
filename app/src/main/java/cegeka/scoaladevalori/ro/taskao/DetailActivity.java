package cegeka.scoaladevalori.ro.taskao;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {


    TextView titleShow,dateShow, descShow;
    Button btnDelete;
    FirebaseHelper helper;
    DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);

        titleShow = (TextView) findViewById(R.id.etTitleDetail);
        dateShow= (TextView) findViewById(R.id.etDateDetail);
        descShow = (TextView) findViewById(R.id.etDescriptionDetail);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent i=this.getIntent();

        String name=i.getExtras().getString("NAME_KEY");
        String date=i.getExtras().getString("DATE_KEY");
        String desc=i.getExtras().getString("DESC_KEY");
        final String idDelete=i.getExtras().getString("DELETE_KEY");

        titleShow.setText(name);
        dateShow.setText(date);
        descShow.setText(desc);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DetailActivity.this,"Close the app to apply changes!",Toast.LENGTH_LONG).show();
                helper.remove(idDelete);
                startActivity(new Intent(DetailActivity.this, ListViewActivity.class));
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go to the Activities Viewer to add new activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



}