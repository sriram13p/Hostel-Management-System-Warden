package com.example.warden;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class HomeFragment extends Fragment {

    int c=0;
    TextView nameholder;
    SwipeRefreshLayout pullToRefresh;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    ProgressBar proinhis;
    IP i=new IP();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userList=new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        nameholder=view.findViewById(R.id.nameholder);

        Bundle bundle = getArguments();
        proinhis = view.findViewById(R.id.proinhis);

        nameholder.setText("Hi "+bundle.getString("name", "Default"));

        proinhis.setVisibility(VISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String url="http://"+i.getIp()+"/warden.php";
                FetchData fetchData = new FetchData(url);
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result1 = fetchData.getResult();
                        String[] str = result1.split("/");
                        for (int i = 0; i < str.length; i++)
                         {
                            System.out.println(str.length);
                            String[] sp = str[i].split(";");
                            userList.add(new ModelClass(sp[0],sp[1], sp[2], sp[3],sp[4],sp[5],sp[6]));

                         }
                        proinhis.setVisibility(View.GONE);


                    }
                }
            }
        });


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userList.clear();
                proinhis.setVisibility(VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/warden.php";
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch()) {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split("/");
                                for (int i = 0; i < str.length; i++)
                                {
                                    System.out.println(str.length);
                                    String[] sp = str[i].split(";");
                                    userList.add(new ModelClass(sp[0],sp[1], sp[2], sp[3],sp[4],sp[5],sp[6]));

                                }
                                proinhis.setVisibility(View.GONE);


                            }
                        }
                    }
                });








                recyclerView=view.findViewById(R.id.recyclerview);
                layoutManager=new LinearLayoutManager(getContext());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                adapter=new Adapter(userList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);

            }
        });
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        return view;
    }
}