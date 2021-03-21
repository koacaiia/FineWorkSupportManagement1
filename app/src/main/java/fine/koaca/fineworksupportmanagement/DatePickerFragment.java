package fine.koaca.fineworksupportmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    String datePickerName;


    public DatePickerFragment(String datePickerName) {
        this.datePickerName=datePickerName;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        switch(datePickerName){
            case "MainActivity":
                MainActivity activity=(MainActivity)getActivity();
                activity.processDatePickerResult(year,month,dayOfMonth);
                break;
            case "ResultData":
                ResultData resultData=(ResultData)getActivity();
                resultData.processDatePickerResult(year,month,dayOfMonth);
                break;
        }


    }
}
