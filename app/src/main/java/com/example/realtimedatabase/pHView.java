package com.example.realtimedatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.realtimedatabase.Database.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pHView extends AppCompatActivity {

    DatabaseReference databaseReference;
    GraphView graphView;
    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phgraph);

        //initializing graphs
        graphView = (GraphView) findViewById(R.id.pHgraphView);
        series = new LineGraphSeries();
        graphView.addSeries(series);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(40);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        series.setDrawDataPoints(true);
        series.setColor(Color.BLUE);
        series.setDataPointsRadius(5f);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("pH Level");
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);


        // Write a message to the database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UsersData").child("FQb13XTqedVLoFGwNXaheAvZSO62").child("readings");

        //loadSensorValues();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Firebase", "Before addListenerForSingleValueEvent");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Firebase", "Inside onDataChange");

                if (dataSnapshot.exists()) {
                    DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];    //defining size of array
                    int index = 0;


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        long timestamp = Long.parseLong(snapshot.getKey());
                        double temperatureValue = snapshot.child("temperatureValue").getValue(Double.class);

                        dp[index] = new DataPoint(index, temperatureValue);
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
}