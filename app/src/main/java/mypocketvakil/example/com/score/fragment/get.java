package mypocketvakil.example.com.score.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.AsyncTask.GetAsyncTask;
import mypocketvakil.example.com.score.R;


public class get extends Fragment {

    private static final String url = "http://192.168.137.248:138/post/";
    ListView listView;
    TextView name;
    ArrayList<HashMap<String, String>> contactlist;

    SwipeRefreshLayout swipeRefreshLayout;
    get g;

    Context cont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactlist = new ArrayList<>();

        cont = getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);

                        fetchRefresh();
                    }
                }, 2000);

            }
        });


        listView = (ListView) view.findViewById(R.id.list);
        GetAsyncTask task = new GetAsyncTask(g, getContext());
        task.execute();
    }

    private void fetchRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        GetAsyncTask task = new GetAsyncTask(g, getContext());
        task.execute();
        swipeRefreshLayout.setRefreshing(false);
    }


}
