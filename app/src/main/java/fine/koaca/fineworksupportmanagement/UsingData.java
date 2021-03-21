package fine.koaca.fineworksupportmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.NameList;

import java.util.ArrayList;

public class UsingData extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView recyclerViewUserName;
    UsingDataListAdapter adapter;
    UserNameDataListAdapter uAdapter;
    ArrayList<UsingDataList> list;
    ArrayList<UserNameDataList> userNameList;
    ArrayList<UserNameDataList> sortArrUserNameList;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String itemName;
    TextView uTitle;
    TextView usingTitle;

    String userName_arr;
    int out_arr;
    String outDay_arr;
    String outPercent_arr;

    int out_total=0;

    Double untilDateInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_data);

        recyclerView=findViewById(R.id.u_recycleriview_date);
        recyclerViewUserName=findViewById(R.id.u_recyclerview_username);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        LinearLayoutManager uManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerViewUserName.setLayoutManager(uManager);
        list=new ArrayList<>();
        userNameList=new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        itemName= getIntent().getStringExtra("ItemName");
        uTitle=findViewById(R.id.Utitle);
        uTitle.setText(itemName+"일별 세부자료");
        usingTitle=findViewById(R.id.usingTitle);

        ResultData untilDate=new ResultData();
        untilDateInt= Double.valueOf(untilDate.untilDate());
        usingTitle.setText(itemName+"사용처별 세부자료 ("+untilDate.untilDate()+"Day)");

        databaseReference=database.getReference(itemName+"/"+"OutData");
        getfirebaseData();
        getuserNameData();
        adapter=new UsingDataListAdapter(list);
        uAdapter=new UserNameDataListAdapter(userNameList);
        recyclerView.setAdapter(adapter);
        recyclerViewUserName.setAdapter(uAdapter);
        adapter.notifyDataSetChanged();
        uAdapter.notifyDataSetChanged();
    }

    private void getuserNameData() {

        FirebaseDatabase uDatabase=FirebaseDatabase.getInstance();
        DatabaseReference uRef=uDatabase.getReference(itemName+"/"+"OutData");

        ArrayList<String> userNameDataList=new ArrayList<>();
        sortArrUserNameList=new ArrayList<UserNameDataList>();
        ValueEventListener listener=new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    UserNameDataList list=data.getValue(UserNameDataList.class);
                    String sortUserName=list.getUsingName();
                    sortArrUserNameList.add(list);
                    userNameDataList.add(sortUserName);
                }
                String[] sortUserNameList=userNameDataList.toArray(new String[userNameDataList.size()]);
                userNameDataList.clear();
                for(String item:sortUserNameList){
                    if(! userNameDataList.contains(item)){
                        userNameDataList.add(item);
                    }
                }

                for(int i=0;i<userNameDataList.size();i++){
                    out_arr=0;
                    out_total=0;
                    String userName=userNameDataList.get(i);
                    for(int k=0;k<sortArrUserNameList.size();k++){
                        if(userName.equals(sortArrUserNameList.get(k).getUsingName())){
                            out_arr=out_arr+sortArrUserNameList.get(k).getOut();
                        }
                        out_total=out_total+sortArrUserNameList.get(k).getOut();
                    }

                    Double dOut= Double.valueOf(out_arr);
                    Double outAver= (double) (dOut / untilDateInt);
                    String sOutAver=String.format("%.5f",outAver);

                    Double dout_total=Double.valueOf(out_total);
                    Double out_percent=(double)(dOut/dout_total)*100;
                    String string_outPercent=String.format("%.1f",out_percent);
                        UserNameDataList userNameDatalist=new UserNameDataList(userName,out_arr,sOutAver,string_outPercent+"%");
                    userNameList.add(userNameDatalist);

                }
                uAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        uRef.addListenerForSingleValueEvent(listener);
    }

    private void getfirebaseData() {
        ValueEventListener postlistener=new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    UsingDataList mList=data.getValue(UsingDataList.class);

                    list.add(mList);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query sortItem=databaseReference.orderByChild("date");
        sortItem.addListenerForSingleValueEvent(postlistener);
    }
}