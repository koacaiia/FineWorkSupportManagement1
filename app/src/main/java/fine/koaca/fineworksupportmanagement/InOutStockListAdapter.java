package fine.koaca.fineworksupportmanagement;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InOutStockListAdapter extends RecyclerView.Adapter<InOutStockListAdapter.ListViewHolder> implements AdapterClickListener,AdapterLongClickListener{
    ArrayList<InOutStockList>  list;
    private AdapterClickListener clickListener=null;
    private AdapterLongClickListener longClickListener=null;

    private final SparseBooleanArray  mSelectedItems=new SparseBooleanArray(0);


    public InOutStockListAdapter(ArrayList<InOutStockList> list){
        this.list=list;

    }
    public void setAdapterClickListener(AdapterClickListener listener){
        this.clickListener=listener;
    }
    public void setAdapterLongClickListener(AdapterLongClickListener longClickListener){
        this.longClickListener=longClickListener;
    }

    @NonNull
    @Override
    public InOutStockListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.in_out_stock_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InOutStockListAdapter.ListViewHolder holder, int position) {
        holder.date.setText(list.get(position).getDate());
        holder.in.setText(String.valueOf(list.get(position).getIn()));
        holder.out.setText(String.valueOf(list.get(position).getOut()));
        holder.remark.setText(list.get(position).getRemark());
        holder.stock.setText(String.valueOf(list.get(position).getStock()));
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public void onItemClick(ListViewHolder listViewHolder, View view, int pos) {
        clickListener.onItemClick(listViewHolder, view, pos);

    }

    @Override
    public void onLongItemClick(ListViewHolder listViewHolder, View view, int pos) {
        longClickListener.onLongItemClick(listViewHolder, view, pos);

    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView in;
        TextView out;
        TextView stock;
        TextView remark;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            this.date=itemView.findViewById(R.id.in_out_stock_date);
            this.in=itemView.findViewById(R.id.in_out_stock_in);
            this.out=itemView.findViewById(R.id.in_out_stock_out);
            this.remark=itemView.findViewById(R.id.in_out_stock_remark);
            this.stock=itemView.findViewById(R.id.in_out_stock_stock);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                        public void onClick(View v){
                    int pos=getAdapterPosition();

                    clickListener.onItemClick(ListViewHolder.this,v,pos);
                }



            });

            itemView.setOnLongClickListener(v->{
                int pos=getAdapterPosition();

                longClickListener.onLongItemClick(ListViewHolder.this,v,pos);
                return true;
            });

        }
    }
}
