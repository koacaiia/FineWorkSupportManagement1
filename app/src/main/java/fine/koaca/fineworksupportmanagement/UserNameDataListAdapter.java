package fine.koaca.fineworksupportmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserNameDataListAdapter extends RecyclerView.Adapter<UserNameDataListAdapter.ListViewHolder> {
    ArrayList<UserNameDataList> mList=new ArrayList<UserNameDataList>();
    public UserNameDataListAdapter(ArrayList<UserNameDataList> mList){
        this.mList=mList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.using_data_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.userName.setText(mList.get(position).getUsingName());
        holder.out.setText(String.valueOf(mList.get(position).getOut()));
        holder.outDay.setText(mList.get(position).getDayOut());
        holder.outPercent.setText(mList.get(position).getUsingPercent());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView out;
        TextView outDay;
        TextView outPercent;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userName=itemView.findViewById(R.id.Udate);
            this.out=itemView.findViewById(R.id.Uout);
            this.outDay=itemView.findViewById(R.id.Udepot);
            this.outPercent=itemView.findViewById(R.id.UusingName);
        }

    }
}
