package com.example.warden;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ModelClass> userList;

    public Adapter(List<ModelClass>userList)
    {
        this.userList=userList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String t1=userList.get(position).getSid();
        String t2=userList.get(position).getReason();
        String t3=userList.get(position).getEintime();
        String t4=userList.get(position).getEouttime();
        String t5=userList.get(position).getCorrect();
        String t6=userList.get(position).getWrong();
        String t7=userList.get(position).getCall();

        holder.setDate(t1,t2,t3,t4,t5,t6,t7);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sid;
        private TextView reason;
        private TextView expintime;
        private TextView expouttime;
        private Button correct;
        private Button wrong;
        private Button call;
        private ProgressBar progressBar;
        Bundle b=new Bundle();
        IP i=new IP();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sid=itemView.findViewById(R.id.sid);
            reason=itemView.findViewById(R.id.reason);
            expintime=itemView.findViewById(R.id.eintime);
            expouttime=itemView.findViewById(R.id.eouttime);
            correct=itemView.findViewById(R.id.correct);
            wrong=itemView.findViewById(R.id.wrong);
            call=itemView.findViewById(R.id.call);
            progressBar=itemView.findViewById(R.id.proitem);


        }

        public void setDate(String t1, String t2, String t3, String t4,String t5,String t6,String t7) {
        sid.setText("ID : "+t1);
        reason.setText("Reason : "+t2);
        expintime.setText("ExpInTime : "+t3);
        expouttime.setText("ExpOutTime : "+t4);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "tid";
                        field[1] = "stat";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = t5;
                        data[1] = "1";
                        String url="http://"+i.getIp()+"/wardenpush.php";
                        PutData putData = new PutData(url, "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("success")) {
                                    Toast.makeText(itemView.getContext(), "Reload the Page", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(itemView.getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "tid";
                        field[1] = "stat";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = t5;
                        data[1] = "2";
                        String url="http://"+i.getIp()+"/wardenpush.php";
                        PutData putData = new PutData(url, "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("success")) {
                                    Toast.makeText(itemView.getContext(), "Reload the Page", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(itemView.getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(t7);


            }
        });





        }
    }
}
