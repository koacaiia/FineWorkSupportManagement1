package fine.koaca.fineworksupportmanagement;

import android.view.View;

public interface AdapterClickListener {
    void onItemClick(InOutStockListAdapter.ListViewHolder listViewHolder, View view, int pos);
}
