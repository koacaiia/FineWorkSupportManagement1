package fine.koaca.fineworksupportmanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class A4Fragment extends Fragment {
    ArrayList<InOutStockList> list;
    InOutStockListAdapter adapter;
    RecyclerView recyclerView;
    Context context;
    ArrayCollection arr;


    public A4Fragment(Context context){
        this.context=context;
    }

    public A4Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_a4,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerA4);
        context=view.getContext();
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        arr=new ArrayCollection();
//        list=arr.arrayExtract();
        adapter=new InOutStockListAdapter(list);
        recyclerView.setAdapter(adapter);


        return view;
    }
}