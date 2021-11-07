package com.example.warden;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HistoryFragment extends Fragment {

    EditText datepicker;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerviewinhis;
    ImageButton search;
    ProgressBar proinhis;
    String date;
    Adapter1 adapter;
    IP i=new IP();
    List<ModelClass1> userList1;
    public HistoryFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_history, container, false);
       datepicker=view.findViewById(R.id.datepicker);
       search=view.findViewById(R.id.search);
       proinhis=view.findViewById(R.id.proinhis);
       userList1=new ArrayList<>();
       recyclerviewinhis=view.findViewById(R.id.recyclerviewinhis);

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               date=String.valueOf(datepicker.getText());
               if(!date.equals(""))
               {

                   new fetchData().execute();
               }
               else
               {
                   Toast.makeText(getActivity(),"Enter DATE to search",Toast.LENGTH_SHORT).show();

               }

           }
       });





       datepicker.setInputType(InputType.TYPE_NULL);

       datepicker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final Calendar calendar=Calendar.getInstance();
               DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       calendar.set(Calendar.YEAR,year);
                       calendar.set(Calendar.MONTH,month);
                       calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                       SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                       datepicker.setText(simpleDateFormat.format(calendar.getTime()));

                   }
               };
               new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


           }
       });



        return view;
    }
    class fetchData extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userList1.clear();
            proinhis.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String url = "http://" + i.getIp() + "/wardenReport.php?date="+date;
            FetchData fetchData = new FetchData(url);
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    String result1 = fetchData.getResult();
                    String[] str = result1.split("/");
                    for (int i = 0; i < str.length; i++) {
                        String[] sp = str[i].split(";");
                        userList1.add(new ModelClass1(sp[0], sp[1], sp[2], sp[3]));

                    }

                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            layoutManager=new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerviewinhis.setLayoutManager(layoutManager);
            adapter=new Adapter1(userList1);
            recyclerviewinhis.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            proinhis.setVisibility(View.GONE);
        }
    }
}