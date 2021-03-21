package fine.koaca.fineworksupportmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsingDataListAdapter extends RecyclerView.Adapter<UsingDataListAdapter.ListViewHolder>{
    ArrayList<UsingDataList> list=new ArrayList<>();

    public UsingDataListAdapter(ArrayList<UsingDataList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.using_data_list,parent,false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.uDate.setText(list.get(position).getTimeStamp());
        holder.uOut.setText(String.valueOf(list.get(position).getOut()));
        holder.uDepot.setText(list.get(position).getEtc());
        holder.uUsingName.setText(list.get(position).getUsingName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView uDate;
        TextView uOut;
        TextView uDepot;
        TextView uUsingName;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.uDate=itemView.findViewById(R.id.Udate);
            this.uOut=itemView.findViewById(R.id.Uout);
            this.uDepot=itemView.findViewById(R.id.Udepot);
            this.uUsingName=itemView.findViewById(R.id.UusingName);
            
        }
    }
}
