package fine.koaca.fineworksupportmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResultData extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;
    RecyclerView recyclerViewDate;
    RecyclerView recyclerViewYear;
    Button btnResult;
    TextView txtResultYear;
    InOutStockListAdapter adapter;
    InOutStockListAdapter adapterDate;
    ArrayList<InOutStockList> list;
    ArrayList<InOutStockList> listDate;
    ArrayList<InOutStockList> arrList;
    ArrayList<InOutStockList> arrListDate;

    InOutStockList listData;


    TextView dateResultStartDate;
    TextView dateResultEndDate;

    TextView yearResultStartDate;
    TextView yearResultEndDate;
//    ArrayList<String> list;

    int sortIn=0;
    int sortOut,sortStock;

   String sortDateSelected;
   String datePickerName;
   String depotName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_data);

        depotName= getIntent().getStringExtra("depotName");
        Log.i("koacaiia","depotName+++:"+depotName);
        recyclerViewDate=findViewById(R.id.recyclerResultDate);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        LinearLayoutManager manager1=new LinearLayoutManager(this);
        recyclerViewDate.setLayoutManager(manager);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
        Calendar calendar= Calendar.getInstance();
        String date=sdf.format(calendar.getTime());
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month;
        if((calendar.get(Calendar.MONTH)+1)<10){
            month="0"+String.valueOf(calendar.get(Calendar.MONTH)+1);
        }else{
            month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        }



        dateResultStartDate=findViewById(R.id.dateResultDateStart);
        dateResultStartDate.setText(year+"년"+month+"월01일");
        dateResultEndDate=findViewById(R.id.dateResultDateEnd);

        dateResultEndDate.setText(date);

        yearResultStartDate=findViewById(R.id.yearResultDateStart);
        yearResultStartDate.setText("2021년01월01일");
        yearResultEndDate=findViewById(R.id.yearResultDateEnd);
        yearResultEndDate.setText(date);

        recyclerViewYear=findViewById(R.id.recyclerResultYear);
        recyclerViewYear.setLayoutManager(manager1);
        btnResult=findViewById(R.id.txtResultDate);
        btnResult.setOnClickListener(v->{
            sortDateSelected="검색 시작일";
            sortDate();

        });

        btnResult.setOnLongClickListener(v->{
            sortDateSelected="검색 종료일";
            sortDate();
            return true;
        });
        txtResultYear=findViewById(R.id.txtResultYearDate);
        list=new ArrayList<>();
        listDate=new ArrayList<>();

        getFirebaseDate();
        getFirebaseData();
//        getFirebaseDate2();

        adapter=new InOutStockListAdapter(list);
        adapterDate=new InOutStockListAdapter(listDate);
        recyclerViewDate.setAdapter(adapterDate);
        recyclerViewYear.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        adapterDate.notifyDataSetChanged();
        txtResultYear.setText("누적 재고상황"+"\n"+"누적 경과일:"+untilDate()+"일");
    }

    private void getFirebaseDate2(String itemName) {
                arrListDate=new ArrayList<>();
                Log.i("koacaiia","ItemName Array++++"+itemName);
                FirebaseDatabase databaseDate=FirebaseDatabase.getInstance();
                DatabaseReference refDate=databaseDate.getReference(itemName);
                ValueEventListener postListener= new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data:snapshot.getChildren()){
                            InOutStockList list=data.getValue(InOutStockList.class);
                            Log.i("koacaiia","ListDate++++:"+list.getDate());
                            arrListDate.add(list);
                        }
                        int totalIn=0;
                        int totalOut=0;

                        untilDate();
                        for(int i=0;i< arrListDate.size();i++){
                            totalIn=totalIn+arrListDate.get(i).getIn();
                            totalOut=totalOut+arrListDate.get(i).getOut();
                        }
                        Double dOut= Double.valueOf(totalOut);
                        Double outAver= (double) (dOut / untilDate());

                        String sOutAver=String.format("%.5f",outAver);

                        InOutStockList listDataDate= new InOutStockList(itemName,totalIn,totalOut,totalIn-totalOut,
                                sOutAver,depotName,"","","");
//                                arrListDate.clear();
                        listDate.add(listDataDate);
                        arrListDate.clear();
                        adapterDate.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                    Query sortByDate=
                            refDate.orderByChild("date").startAt(dateResultStartDate.getText().toString()).endAt(dateResultEndDate.getText().toString());
                    sortByDate.addListenerForSingleValueEvent(postListener);
                    Log.i("koacaiia",
                            "StartDate++"+dateResultStartDate.getText().toString()+"///EndDate++"+dateResultEndDate.getText().toString() );


    }

    private void getFirebaseDate() {
            arrList=new ArrayList<>();
            ArrayList<String> consigneeList=new ArrayList<>();
        FirebaseDatabase databaseDate=FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceDate=databaseDate.getReference("ItemName");
        Log.i("koacaiia","liatDateSize+++1:"+databaseReferenceDate);
        databaseReferenceDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("koacaiia","onDataChanged Checked True");
                for(DataSnapshot data:snapshot.getChildren()){

                    ResultList mDate=data.getValue(ResultList.class);
                    String itemName=mDate.getItemName();



             getFirebaseDate2(itemName);

                }
                Log.i("koacaiia","consigneeListData++++:"+consigneeList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFirebaseData() {
       
        arrList=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataRef=firebaseDatabase.getReference("ItemName");
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren()){

                    ResultList mList=data.getValue(ResultList.class);
                    String itemName=mList.getItemName();
                    DatabaseReference dataReference=firebaseDatabase.getReference(itemName);
                    dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data:snapshot.getChildren()){
                                InOutStockList mList=data.getValue(InOutStockList.class);
                                arrList.add(mList);
                            }
                            int totalIn=0;
                            int totalOut=0;

                            untilDate();
                            for(int i=0;i< arrList.size();i++){
                                totalIn=totalIn+arrList.get(i).getIn();
                                totalOut=totalOut+arrList.get(i).getOut();
                            }
                            Double dOut= Double.valueOf(totalOut);
                            Double outAver= (double) (dOut / untilDate());
                            String sOutAver=String.format("%.5f",outAver);
                            listData= new InOutStockList(itemName,totalIn,totalOut,totalIn-totalOut,sOutAver,depotName,"","","");

                            arrList.clear();
                            list.add(listData);
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void sortDate(){
        datePickerName=this.getClass().getSimpleName();
        DatePickerFragment datePicker=new DatePickerFragment(datePickerName);
        datePicker.show(getSupportFragmentManager(),"datePicker");
        Toast.makeText(this,sortDateSelected+" 을 설정 합니다.",Toast.LENGTH_SHORT).show();

    }


    public void processDatePickerResult(int year, int month, int dayOfMonth) {

         String pickDate;
         String startDate,endDate;
        if(month<10){
            startDate="0"+String.valueOf(month+1);
        }else{
            startDate=String.valueOf(month+1);
        }

        if(dayOfMonth<10){
            endDate="0"+(dayOfMonth);
        }else{
            endDate=Integer.toString(dayOfMonth);
        }


        pickDate=year+"년"+startDate+"월"+endDate+"일";
        Toast.makeText(this,pickDate+" 을 "+sortDateSelected+" 로 설정",Toast.LENGTH_SHORT).show();
        switch(sortDateSelected){
            case "검색 시작일":
                dateResultStartDate.setText(pickDate);
                Toast.makeText(this,pickDate+" 을 "+sortDateSelected+" 로 설정",Toast.LENGTH_SHORT).show();
                break;
            case "검색 종료일":
               dateResultEndDate.setText(pickDate);
                break;

        }
//        String sUntilDate=String.valueOf(untilDate());


    }

    public int untilDate(){
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        Calendar cal2=Calendar.getInstance();
        cal2.set(2021,00,01);
        int count=0;
        while(!cal2.after(cal)){
            count++;
            cal2.add(Calendar.DATE,1);
        }

        return count;
    }
    }
