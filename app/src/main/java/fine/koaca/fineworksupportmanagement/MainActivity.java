

package fine.koaca.fineworksupportmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Calendar calendar;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    String[] itemList;
    ArrayList<String>  arrItemName;
    String itemName;
    Button txtItemName;
    RecyclerView recyclerview;

    ArrayList<InOutStockList> stockList=new ArrayList<>();
    ArrayList<InOutStockList> stockListCount;
    Button btnSpinner;
    InOutStockListAdapter adapter=new InOutStockListAdapter(stockList);

    int inStock,outStock;
    String in_out_Item="Out";
    int iMonth;
    FloatingActionButton fltBtnPutItem;

    static private final String SHARE_NAME="SHARE_DEPOT";
    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    String depotName;
    String nickName;

    String startDate;
    String endDate;
    String yearDate;
    String pickDate;
    String dateSelect;

    TextView dialogStartDate;
    TextView dialogEndDate;

    Context context;

    String datePickerName;

    int inDate;
    int outDate;
    String usingName="";
    String time="";
    String etc="";

    String activitySelectName;
    String   txtAdd="(짧은클릭=관리항목 선택,긴클릭=관리항목(월) 선택)";
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
        }
    };

    public MainActivity(ArrayList<InOutStockList> stockList) {
        this.stockList=stockList;
    }
    public MainActivity(){

    }

    public MainActivity(Context context) {
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         context=this;
        txtItemName=findViewById(R.id.txtItemName);
        calendar=Calendar.getInstance();

        sharedPref=getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        depotName=sharedPref.getString("depotName",null);
        if(depotName==null){
//            depotName="2물류(02010027)";
//            nickName="Guest";

            AlertDialog.Builder depotNameSelect=new AlertDialog.Builder(context);
            depotNameSelect.setTitle("DepotName,UserName 등록 후 진행 바랍니다.")
                    .setMessage("최소 부서명은 선택 되어야 데이터베이스 접속 가능 합니다.!")
                    .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          putDepotName();
                        }
                    });
            depotNameSelect.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context,"등록후 진행 바랍니다.!!",Toast.LENGTH_SHORT).show();
                }
            });
            depotNameSelect.show();

        }else{
            depotName=sharedPref.getString("depotName",null);
            nickName=sharedPref.getString("nickName","Fine");


            if(getIntent().getStringExtra("month")==null){
                iMonth=calendar.get(Calendar.MONTH)+1;
            }else{
                iMonth= Integer.parseInt(getIntent().getStringExtra("month"));
                itemName=getIntent().getStringExtra("itemName");
            }

            itemSpinner();
            database=FirebaseDatabase.getInstance();
            recyclerview=findViewById(R.id.recyclerView);
            LinearLayoutManager manager=new LinearLayoutManager(this);
            recyclerview.setLayoutManager(manager);

            recyclerview.setAdapter(adapter);

            adapter.setAdapterClickListener(new AdapterClickListener() {
                @Override
                public void onItemClick(InOutStockListAdapter.ListViewHolder listViewHolder, View view, int pos) {
                    int in,out;
                    String date,remark;
                    date=stockList.get(pos).getDate();
                    remark=stockList.get(pos).getRemark();
                    in=stockList.get(pos).getIn();
                    out=stockList.get(pos).getOut();
                    depotName=stockList.get(pos).getDepot();
                    time=stockList.get(pos).getTime();
                    usingName=stockList.get(pos).getUsingName();
                    etc=stockList.get(pos).getEtc();
                    adapterClickedDialog(date,in,out,remark,depotName,usingName,time,etc);
                }
            });
            adapter.setAdapterLongClickListener(new AdapterLongClickListener() {
                @Override
                public void onLongItemClick(InOutStockListAdapter.ListViewHolder listViewHolder, View view, int pos) {
                    String date=stockList.get(pos).getDate();
                    AlertDialog.Builder deleteBuilder=new AlertDialog.Builder(context);
                    deleteBuilder.setTitle("자료 초기화")
                            .setMessage("입,출고 자료를 0으로 초기화 진행 합니다."+"\n"+"조회항목에 대한 세부 사용 내역을 조회 합니다.")

                            .setPositiveButton("초기화 진행", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    putInOutData(date,0,0,"초기화", depotName, "In", time, "");
                                }
                            })
                            .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("사용량 세부조회", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(MainActivity.this,UsingData.class);
                                    intent.putExtra("ItemName",itemName);
                                    startActivity(intent);

                                }
                            })
                            .show();

                }
            });

            txtItemName.setOnClickListener(v->{

                selectItemName();

            });
            txtItemName.setOnLongClickListener(v->{
                MyYearMonthPickerDialog pd=new MyYearMonthPickerDialog(itemName);
                pd.setListener(d);
                pd.show(getSupportFragmentManager(),"YearMonthPicker");
                return true;
            });

            fltBtnPutItem=findViewById(R.id.fltBtnPutItem);
            fltBtnPutItem.setOnClickListener(v->{
                dialogPutItemName();
            });

        }


        }

    private void selectItemName() {

        String[] selectItemList=arrItemName.toArray(new String[arrItemName.size()]);
        List selectedUsingName=new ArrayList();
        int defaultUsingName=0;
        selectedUsingName.add(defaultUsingName);
        AlertDialog.Builder selectItemDialog=new AlertDialog.Builder(this);
        selectItemDialog.setTitle("관리항목 선택")
                .setSingleChoiceItems(selectItemList, defaultUsingName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       itemName=selectItemList[which];
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtItemName.setText(itemName+"("+iMonth+"월)"+"  입출고 등록");
                        getStockData(itemName,iMonth);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();


    }

    private void adapterClickedDialog(String date, int in, int out, String remark, String depotName, String usingName, String time, String etc){
        EditText editText=new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("입,출고 수량을 기입 하세요");

        AlertDialog.Builder inoutBuilder=new AlertDialog.Builder(context);
        inoutBuilder.setView(editText)
                .setTitle("입,출고 수량 등록")
                .setMessage(date+"\n" +"입고수량 :"+in+"\n"+"출고수량 :"+out+"  에 대한 내역을 업데이트 합니다.")
                .setPositiveButton("입고 수량 등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editCount=editText.getText().toString();

                        if(editCount.equals("")){
                            inDate=0;
                            }else{
                            inDate=Integer.parseInt(editCount);}
                        putInOutData(date,in+inDate,out,"비고", depotName, "In", timeExtract(),"");
                        EditText inEditText=new EditText(context);
                        inEditText.setHint("거래명세서상의 입고처 기입 바랍니다.");
                        AlertDialog.Builder inBuilder=new AlertDialog.Builder(context);
                        inBuilder.setView(inEditText)
                               .setTitle("입고 비고란 항목 기재사항")
                                .setPositiveButton("비고등록", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        putInOutData(date,in+inDate,out,inEditText.getText().toString(), depotName,"In",
                                                timeExtract(),"");

                                    }
                                })
                               .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })
                               .show();
                    }
                })
                .setNegativeButton("출고 수량 등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String editCount = editText.getText().toString();
                        if(editCount.equals("")){
                            outDate=0;
                        }else{
                            outDate=Integer.parseInt(editCount);
                        }

                        DatabaseReference databaseRefOut=database.getReference(itemName+"/"+date);
                        InOutStockList list=new InOutStockList(date,in,out+outDate,iMonth,remark,depotName,usingName,time,etc);

                        Log.i("koacaiia","OutData____+++:"+outDate);
                        databaseRefOut.setValue(list);
                        putUsingName(date,out+outDate);
                    }
                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
        }

    private void putUsingName(String date, int out) {
        ArrayList<String> arrUsingName=new ArrayList<>();
        arrUsingName.add(depotName+"소속 직원");
        arrUsingName.add(depotName+"소속 주부사원");
        arrUsingName.add("남자 아웃소싱 인원");
        arrUsingName.add("여자 아웃소싱 인원");
        arrUsingName.add("외부 업체 인원");
        arrUsingName.add("인원외 소모품 사용");

        String[] usingList=arrUsingName.toArray(new String[arrUsingName.size()]);
        List selectedUsingName=new ArrayList();
        int defaultUsingName=0;
        selectedUsingName.add(defaultUsingName);

        AlertDialog.Builder usingNamebuilder=new AlertDialog.Builder(context);
        usingNamebuilder.setTitle("사용처를 등록 바랍니다.")
                .setSingleChoiceItems(usingList, defaultUsingName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        usingName= usingList[which];
                        String timeStamp=String.valueOf(System.currentTimeMillis());
                        String timeDate=new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초").format(new Date());
                        DatabaseReference databaseRef=
                                database.getReference(itemName+"/"+"OutData"+"/"+timeDate+"_"+timeStamp);
                        OutDataList dataList=new OutDataList(date,timeDate,usingName,depotName,out);
                        databaseRef.setValue(dataList);
                        getStockData(itemName,iMonth);

                    }
                })
                .setPositiveButton("사용처 등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    private void putInOutData(String date, int in, int out, String remark, String depotName, String usingName, String time,
                              String etc) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dataRef=database.getReference(itemName+"/"+date);
        InOutStockList list;
        if(remark.equals("초기화")){
            list=new InOutStockList(date,0,0,iMonth,"비고", depotName,usingName,time,etc);

        }else{
            list=new InOutStockList(date,in,out,iMonth,remark,depotName,usingName,time,etc);
        }

        dataRef.setValue(list);
        getStockData(itemName,iMonth);
    }

    public void getStockData(String itemName,int month) {
        stockList.clear();
        stockListCount=new ArrayList<>();
        stockListCount.clear();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference(itemName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    InOutStockList list=data.getValue(InOutStockList.class);
                    String date=list.getDate();
                    int in=list.getIn();
                    int out=list.getOut();
                    int stock=list.getStock();
                    String remark=list.getRemark();
                    time=list.getTime();
                    usingName=list.getUsingName();
                    etc=list.getEtc();
                    if(list.getTime()==null){
                        InOutStockList updateList=new InOutStockList(date,in,out,stock,remark,depotName,"usingName","time","etc");

                       DatabaseReference ref=database.getReference(itemName+"/"+date);
                       ref.setValue(updateList);

                        stockListCount.add(updateList);
                    }else{
                        if(list.getDepot().equals(depotName)){
                            stockListCount.add(list);
                        }
                    }


                }

                sortStockList(stockListCount, month);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void sortStockList(ArrayList<InOutStockList> stockListCount, int month) {
        int listSize=stockListCount.size();
            String date;
            String remark;

        int in,out,stock,stock1;
        InOutStockList list1;
        int totalIn=stockListCount.get(0).getIn();
        int totalOut=stockListCount.get(0).getOut();
        int totalStock=0;

        int subTotalIn = 0;
        int subTotalOut=0;
        int subTotalStock=0;

        for(int i=0;i<listSize;i++){
            date=stockListCount.get(i).getDate();
            remark=stockListCount.get(i).getRemark();
            in=stockListCount.get(i).getIn();
            stock1=stockListCount.get(i).getStock();
            out=stockListCount.get(i).getOut();
            usingName=stockListCount.get(i).getUsingName();
            time=stockListCount.get(i).getTime();
            etc=stockListCount.get(i).getEtc();
            if(i>0){
                stock=(stockListCount.get(i-1).getIn()-stockListCount.get(i-1).getOut())+(in-out);
            }else{
                stock=in-out;
            }
            totalIn=totalIn+in;
            totalOut=totalOut+out;
            totalStock=totalIn-totalOut;

            list1=new InOutStockList(date,in,out,totalStock,remark,depotName,usingName,time,etc);
//            month=iMonth;


            if(month==stock1){
                stockList.add(list1);
            }

        }

        for(int i=0;i<stockList.size();i++){
            subTotalIn=subTotalIn+stockList.get(i).getIn();
            subTotalOut=subTotalOut+stockList.get(i).getOut();

        }

        adapter.notifyDataSetChanged();
    }

    private void itemSpinner() {
      arrItemName =new ArrayList<String>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("ItemName");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    ItemName list=data.getValue(ItemName.class);
                    String itemName=list.getItemName();
                    arrItemName.add(itemName);

                }
                itemList=arrItemName.toArray(new String[0]);
                if(getIntent().getStringExtra("itemName")==null){
                    itemName=itemList[0];
                }

                txtItemName.setText(itemName+"("+iMonth+"월)"+"  입출고 등록"+"\n"+txtAdd);
                if(itemList.length!=0){
                    getStockData(itemList[0],iMonth);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void dialogPutItemName(){
        AlertDialog.Builder itemDialog=new AlertDialog.Builder(this);
        EditText editText=new EditText(this);

        itemDialog.setTitle("관리항목 신규 입력창")
                .setMessage("관리항목 입력후 등록 버튼으로 저장 바랍니다.!")
                .setView(editText);

        itemDialog.setPositiveButton("관리항목등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemName=editText.getText().toString();
                PutItemName(itemName);
                itemSpinner();
            }
        });
        itemDialog.show();

    }
    public void PutItemName(String itemName){
        for(int i=0;i<365;i++){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
            Date dateG = null;
            try {
                dateG=sdf.parse("2021년01월01일");
            } catch (ParseException e) {
                e.printStackTrace();
            }
             Calendar calendar=Calendar.getInstance();
            calendar.setTime(dateG);
            calendar.add(Calendar.DAY_OF_MONTH,i);
            String date=sdf.format(calendar.getTime());
            String sMonth=date.substring(5,7);

            InOutStockList data=new InOutStockList(date,0,0,Integer.parseInt(sMonth),"",depotName,"","","");

            dataRef=database.getReference(itemName+"/"+date );
            dataRef.setValue(data);

        }
        ItemName itemData=new ItemName(itemName);
        DatabaseReference itemRef=database.getReference("ItemName"+"/"+itemName);
        itemRef.setValue(itemData);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint({"NonConstantResourceId", "CommitPrefEdits"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        sharedPref=getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPref.edit();
        switch(item.getItemId()){
            case R.id.action_account_search:
                ArrayList<String> sortSelectItem=new ArrayList<>();
                sortSelectItem.add("전 항목 기간별 사용 내역 조회");
                sortSelectItem.add(itemName+" 사용처별 사용내역 조회");
                ArrayList selectedItems=new ArrayList();
                int defaultItem=0;
                selectedItems.add(defaultItem);


                String[] sortSelectItemList=sortSelectItem.toArray(new String[sortSelectItem.size()]);
                AlertDialog.Builder sortSelectBuilder=new AlertDialog.Builder(this);
                sortSelectBuilder.setTitle("사용내역 검색")
                        .setSingleChoiceItems(sortSelectItemList, defaultItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch(which){
                                   case 0:
                                       activitySelectName="Date";
                                       break;
                                    case 1:
                                        activitySelectName="User";
                                        break;

                                }
                                Toast.makeText(context,sortSelectItemList[which]+activitySelectName,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(activitySelectName==null){
                                    activitySelectName="Date";
                                }
                                switch(activitySelectName){
                                    case "Date":
                                        sortResultData();
                                        break;
                                    case "User":
                                        sortUserNameData();
                                        break;

                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                break;
            case R.id.action_account:

                putDepotName();
                break;


        }
       return true;
    }


    public void processDatePickerResult(int year, int month, int dayOfMonth) {
        pickDate="";
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

        yearDate=String.valueOf(year);
        Log.i("kocaiia","y++:"+yearDate+"Month++:"+month+"day++;"+dayOfMonth);

        pickDate=year+"년"+startDate+"월"+endDate+"일";
        switch(dateSelect){
            case "start":
                dialogStartDate.setText(pickDate);
                break;
            case "end":
                dialogEndDate.setText(pickDate);
                break;

        }
    }
    private void sortResultData(){
        Intent intent=new Intent(MainActivity.this,ResultData.class);
        intent.putExtra("depotName",depotName);
        startActivity(intent);

    }

    private void sortUserNameData(){
        Intent intent=new Intent(MainActivity.this,UsingData.class);
        intent.putExtra("depotName",depotName);
        intent.putExtra("ItemName",itemName);

        startActivity(intent);
    }
    private void putDepotName(){
        sharedPref=getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPref.edit();
        ArrayList<String> depotSort=new ArrayList<>();
        depotSort.add("1물류(02010810)");
        depotSort.add("2물류(02010027)");
        depotSort.add("(주)화인통상창고사업부");

        ArrayList selectedItems=new ArrayList();
        int defaultItem=0;
        selectedItems.add(defaultItem);

        String[] depotSortList=depotSort.toArray(new String[depotSort.size()]);
        AlertDialog.Builder sortBuilder=new AlertDialog.Builder(context);
        View view=getLayoutInflater().inflate(R.layout.user_reg,null);
        EditText reg_edit=view.findViewById(R.id.user_reg_Edit);

        Button reg_button=view.findViewById(R.id.user_reg_button);
        TextView reg_depot=view.findViewById(R.id.user_reg_depot);

        reg_button.setOnClickListener(v->{
            nickName=reg_edit.getText().toString();
            reg_depot.setText(depotName+"_"+nickName+"으로 사용자 등록을"+"\n"
                             +"진행 할려면 하단 confirm 버튼을 클릭 바랍니다.");
        });

        sortBuilder.setView(view);
        sortBuilder.setSingleChoiceItems(depotSortList,defaultItem, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                depotName=depotSortList[which];
                reg_depot.setText("DepotName_"+depotName+" 로 확인");
            }
        });

        sortBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("depotName",depotName);
                editor.putString("nickName",nickName);
                editor.apply();
                Toast.makeText(MainActivity.this,depotName+"__"+nickName+"로사용자등록성공하였습니다.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        sortBuilder.show();

    }

    private String timeExtract(){
        time =new SimpleDateFormat("HH시mm분ss초").format(new Date());

        return time;
    }
}