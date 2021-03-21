package fine.koaca.fineworksupportmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FirebaseDataTest extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataRef;
    InOutStockList list;
    ArrayList<InOutStockList> calendarList;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_data_test);
        database=FirebaseDatabase.getInstance();
        list=new InOutStockList();
//        calendarArrayList();


    }

//    public ArrayList calendarArrayList(){
//        for(int i=0;i<365;i++){
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
//            Date dateG=null;
//            try {
//                dateG=sdf.parse("2021년01월01일");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            calendar=Calendar.getInstance();
//            calendar.setTime(dateG);
//            calendar.add(Calendar.DAY_OF_MONTH,i);
//            String date=sdf.format(calendar.getTime());
//            InOutStockList data=new InOutStockList(date,0,0,0,"",depotName);
//            dataRef=database.getReference("A4"+"/"+date);
//            dataRef.setValue(data);
////            calendarList.add(data);
//
//        }
//        return calendarList;
//    }
}