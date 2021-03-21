package fine.koaca.fineworksupportmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MyYearMonthPickerDialog extends DialogFragment {
    private static final int MAX_YEAR=2025;
    private static final int MIN_YEAR=2020;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal=Calendar.getInstance();
    private ArrayList<InOutStockList> stockListCount;
    private ArrayList<InOutStockList> stockList;
    String itemName;

    public MyYearMonthPickerDialog() {

    }
    public MyYearMonthPickerDialog(ArrayList<InOutStockList> stockListCount, ArrayList<InOutStockList> stockList){
        this.stockListCount=stockListCount;
        this.stockList=stockList;
    }

    public MyYearMonthPickerDialog(String itemName) {
        this.itemName=itemName;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener=listener;
    }

    Button btnConfirm;
    Button btnCancel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View dialog=inflater.inflate(R.layout.year_month_picker,null);
        btnConfirm=dialog.findViewById(R.id.btn_confirm);
        btnCancel=dialog.findViewById(R.id.btn_cancel);

        final NumberPicker monthPicker=(NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker=(NumberPicker) dialog.findViewById(R.id.picker_year);
        btnCancel.setOnClickListener(v->{
            MyYearMonthPickerDialog.this.getDialog().cancel();

        });
        btnConfirm.setOnClickListener(v->{
            listener.onDateSet(null,yearPicker.getValue(),monthPicker.getValue(),0);
//            Log.i("koacaiia","NullListChecked+++++:"+stockListCount.size()+"/momth:"+monthPicker.getValue());
            MyYearMonthPickerDialog.this.getDialog().cancel();
            MainActivity mainActivity=new MainActivity(stockList);
//            mainActivity.sortStockLists(stockListCount,monthPicker.getValue());
//            mainActivity.getStockData("A4",monthPicker.getValue());

//            Log.i("koacaiia","NullListChecked_______:"+stockListCount.size()+"/momth:"+monthPicker.getValue());

            int intentMonth = monthPicker.getValue();
            Intent intent=new Intent(getContext(),MainActivity.class);
            intent.putExtra("itemName",itemName);
            intent.putExtra("month",String.valueOf(intentMonth));
            startActivity(intent);

        });

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH)+1);

        int year=cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2020);
        yearPicker.setMaxValue(2023);
        yearPicker.setValue(year);

        builder.setView(dialog);
        return builder.create();
    }
}
