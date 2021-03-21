package fine.koaca.fineworksupportmanagement;

import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArrayCollection {
    ArrayList<InOutStockList> list=new ArrayList<InOutStockList>();
    Calendar calendar;
    String year;
    String month;
    String day;
    String date;

    public void callCalendar(){
        calendar=Calendar.getInstance();
        year=String.valueOf(calendar.get(Calendar.YEAR));

        if(calendar.get(Calendar.MONTH)+1<10){
            month="0"+String.valueOf(calendar.get(Calendar.MONTH)+1);
        }else{
            month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        }

        if(calendar.get(Calendar.DAY_OF_MONTH)<10){
            day="0"+(calendar.get(Calendar.DAY_OF_MONTH));
        }else{
            day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }
        date=year+"년"+month+"월"+day+"일";

        Date date=new Date();
        Log.i("koacaiia",date+"koacadata"   );

    }
//    public ArrayList arrayExtract(){
//        for(int i=0;i<365;i++){
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
//            Date dateG = null;
//            try {
//               dateG=sdf.parse("2021년01월01일");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            calendar=Calendar.getInstance();
//            calendar.setTime(dateG);
//            calendar.add(Calendar.DAY_OF_MONTH,i);
//            date=sdf.format(calendar.getTime());
//            InOutStockList data=new InOutStockList(date,0,0,0,"",depotName);
//            list.add(data);
//            Log.i("koacaiia", String.valueOf(list));
//        }
//return list;
//    }




}
