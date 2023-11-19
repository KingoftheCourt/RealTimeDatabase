package com.example.realtimedatabase;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity

{
    protected Button viewGraphButton;

    FirebaseAuth mAuth; //creating Firebase authentication
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.d(TAG,"User Logged in");
        }
        }
 */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();

        viewGraphButton=findViewById(R.id.ViewGraphButton);


        viewGraphButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToGraphView();
            }
        });


    }

    protected void goToGraphView()
    {

        Intent intent= new Intent(this, pHView.class); //switching towards data activity
        startActivity(intent);

    }


}


/*
public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    Map<String, Object> firebaseSensorValues = new HashMap<>();
    GraphView graphView;
    LineGraphSeries series;

    SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteAll();

        //initializing graphs
        graphView=(GraphView) findViewById(R.id.graphView) ;
        series=new LineGraphSeries();
        graphView.addSeries(series);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(14);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        series.setDrawDataPoints(true);
        series.setColor(Color.BLUE);
        series.setDataPointsRadius(5f);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("pH Level");
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);


        float pH = 7;
        float temperature = 25;
        float ppm = 130;

        dbHelper.insertSensorValues(new SensorValues(pH, temperature, ppm));

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        loadSensorValues();

    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];    //defining size of array
                    int index = 0;


                    for (DataSnapshot myDataSnapshot : snapshot.getChildren())
                    {

                        HashMap<String, Object> map = (HashMap<String, Object>) myDataSnapshot.getValue();
                        Object rawValue= map.get("pH");
                        Float value;

                        value=((Long) rawValue).floatValue();
                        dp[index] = new DataPoint( index,  value);
                        index++;
                    }
                    series.resetData(dp);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadSensorValues() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);   //creating DatabaseHelper object
        List<SensorValues> sensorValues = dbHelper.getAllSensorValues();  // retrieving all items from the database in the form of Sensor Reading objects in a list

        ArrayList<String> sensorsListValues = new ArrayList<>();

        for (int i = 0; i < sensorValues.size(); i++) {


            firebaseSensorValues.put("pH", sensorValues.get(i).getpH());
            firebaseSensorValues.put("PPM", sensorValues.get(i).getPpm());
            firebaseSensorValues.put("temperature", sensorValues.get(i).getTemperature());
            String id=myRef.push().getKey();

            myRef.child(id).setValue(firebaseSensorValues);

        }


    }

 */