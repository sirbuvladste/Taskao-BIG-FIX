package cegeka.scoaladevalori.ro.taskao;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AdapterListUpdateCallback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
    EditText titleList,dateList,descList;
    String title, desc, date, id;
    SwipeRefreshLayout swipeRefreshLayout;
    Date dateV;


    Handler handler =  new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_down);

        firebaseAuth = FirebaseAuth.getInstance();

        lv = (ListView) findViewById(R.id.listView);

        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);


        adapter= new CustomAdapter(ListViewActivity.this,helper.getUserActivities());
        //lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
                refresh();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


    }



    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        handler.postDelayed(runnable, 5);
    }


    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();

        handler.postDelayed(runnable, 5);
    }

    public Runnable runnable = new Runnable() {

        public void run() {
            refresh();
        }
    };

    public class CustomAdapter extends BaseAdapter {
        Context c;
        DatabaseReference db;
        int s;
        public ArrayList<UserActivities> userActivities;


        public CustomAdapter(Context c, ArrayList<UserActivities> userActivities) {
            this.c = c;
            this.userActivities=userActivities;
        }
        @Override
        public int getCount() {
            return userActivities.size();
        }

        @Override
        public Object getItem(int pos) {
            return userActivities.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView==null)
            {
                convertView= LayoutInflater.from(c).inflate(R.layout.single_item,viewGroup,false);
            }

            TextView titleShow= (TextView) convertView.findViewById(R.id.tvTitleShow);
            TextView dateShow= (TextView) convertView.findViewById(R.id.tvDateShow);
            TextView descShow= (TextView) convertView.findViewById(R.id.tvDescriptionShow);

            final UserActivities s= (UserActivities) this.getItem(position);

            titleShow.setText(s.getUserActivityTitile());
            dateShow.setText(s.getUserActivityDate());
            descShow.setText(s.getUserActivityDescription());


            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDetailActivity(s.getUserActivityTitile(),s.getUserActivityDate(),s.getUserActivityDescription(),s.getUserActivityId());

                }
            });

            return convertView;
        }

        private void openDetailActivity(String...details)
        {

            Intent i=new Intent(c,DetailActivity.class);
            i.putExtra("NAME_KEY",details[0]);
            i.putExtra("DATE_KEY",details[1]);
            i.putExtra("DESC_KEY",details[2]);
            i.putExtra("DELETE_KEY",details[3]);


            c.startActivity(i);

        }
    }
    private void displayInputDialog()
    {
        final Dialog dialogPopUp=new Dialog(ListViewActivity.this);
        dialogPopUp.setTitle("Save To Firebase");
        dialogPopUp.setContentView(R.layout.activity_add);

        titleList= (EditText) dialogPopUp.findViewById(R.id.etTitleActivity);
        dateList= (EditText) dialogPopUp.findViewById(R.id.etDueDate);
        descList= (EditText) dialogPopUp.findViewById(R.id.etDescriptionActivity);
        Button saveBtn= (Button) dialogPopUp.findViewById(R.id.btnAddActivityMain);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title=titleList.getText().toString();
                date=dateList.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateV = format.parse(dateList.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                desc=descList.getText().toString();

                UserActivities s=new UserActivities();
                s.setUserActivityTitile(title);
                s.setUserActivityDate(date);
                s.setUserActivityDescription(desc);

                if(validate())
                {
                    String id_introdus= helper.save(s);

                    if(id_introdus!=null)
                    {
                        id = id_introdus;
                        s.setUserActivityId(id);
                        titleList.setText("");
                        dateList.setText("");
                        descList.setText("");


                        refresh();
                        dialogPopUp.dismiss();
                    }
                }

            }
        });
        dialogPopUp.show();

    }

    public void refresh()
    {
        //lv.setAdapter(null);
        lv.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }


    public void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ListViewActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean validate() {
        Boolean result = false;
        title = titleList.getText().toString();
        desc = descList.getText().toString();
        date = dateList.getText().toString();

        if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
            Toast.makeText(ListViewActivity.this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = format.parse(dateList.getText().toString() + " 23:59");
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(ListViewActivity.this, "Invalid date format", Toast.LENGTH_LONG).show();
                return false;
            }
            Date currentDate = new Date();
            if(date != null && date.compareTo(currentDate) <= 0) {
                Toast.makeText(ListViewActivity.this, "Date should be at least today", Toast.LENGTH_LONG).show();
                return false;
            }else{
            result = true;
            }
        }
        return result;
    }

}
