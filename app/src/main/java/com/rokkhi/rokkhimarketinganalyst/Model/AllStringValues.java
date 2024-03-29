package com.rokkhi.rokkhimarketinganalyst.Model;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rokkhi.rokkhimarketinganalyst.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllStringValues {

   public String[] raos=new String[]{"1","2","3","4"};

   static DatePickerDialog datePickerDialog;


   String A="1",B="2",C="3",D="4",E="5",F="6",G="7",H="8",I="9",J="10",K="11",L="12",M="13",N="14",
           O="15",P="16",Q="17",R="18",S="19",T="20",U="21",W="22",X="23",Y="24",Z="25";

   public String[] area=new String[]{"Gulshan 1", "Gulshan 2","Banani","Baridhara",
           "Baridhara DOHS","Uttara","Dhanmondi","Mirpur","Baily Road","Mirpur","Saydabad",
           "Jatrabari","Mohammodpur","Shyamoli","Baily Road","Malibagh","Mogbazar"};

   public String[] road_no=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14",
           "15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32",
           "33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49",
           "50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67"
           ,"68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85",
           "86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103",
           "104","105","106","107","108","109","110","111", "112","113","114","115","116","117","118","119",
           "120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135",
           "136","137","138","139","140","141","142","143","144","145","146","147","148","149","150",
           "151","152","153", "154","155", "156","157","158","159","160","161","162","163","164","165",
           "166","167","168","169","170","171",
           "172","173","174","175","176","177","178","179","180","181","182","183","184","185","186",
           "187","188","189","190","191","192","193","194","195","196","197","198","199","200"};

   public String[] block_numbers=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z"};

   public String[] gender=new String[]{"Male","Female"};

   public String[] status=new String[]{"Pending","Cancel","Done","Following"};

   public String[] flatformat=new String[]{"1A","A1","101"};

   public String[] district=new String[] {"Dhaka"};

   public String[] designation=new String[]{"Manager","Caretaker","Guard","Owner"};




   public static void showCalendar(Context context, final EditText editText){


      Calendar calendar = Calendar.getInstance();
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH);
      int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

      datePickerDialog = new DatePickerDialog(context,
              new DatePickerDialog.OnDateSetListener() {
                 @Override
                 public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    editText.setText(day + "/" + (month + 1) + "/" + year);
                 }
              }, year, month, dayOfMonth);
      datePickerDialog.show();

   }


   public static void showDialog(Context context, int v){

      AlertDialog.Builder builder=new AlertDialog.Builder(context);
      builder.setView(v);
      builder.show();

   }
}
