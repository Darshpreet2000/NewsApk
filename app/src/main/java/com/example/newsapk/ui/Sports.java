package com.example.newsapk.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapk.NewsModel;
import com.example.newsapk.News_Adapter;
import com.example.newsapk.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sports extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    SwipeRefreshLayout swipe;
    final String NEWS_BASE_URL =
            "https://newsapi.org/v2/top-headlines?";
    final String COUNTRY_PARAM="country";
    String  url;
    String country="in";

    public Sports() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    //getting the recyclerview from xml
    ///  ProgressBar progressBar;
    List<NewsModel> NewsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mainfragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SharedPreferences preferences = getActivity().getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE);
        country=preferences.getString("COUNTRY_KEY","in");

        Uri builtUri = Uri.parse(NEWS_BASE_URL)
                .buildUpon()
                .appendQueryParameter(COUNTRY_PARAM, country)
                .appendQueryParameter("category","sports")
                .appendQueryParameter("apiKey","3e66337b0e4344979f9574c68bc0de5c")
                .build();
        url = builtUri.toString();
        //initializing the productlist
        NewsList = new ArrayList<>();
        swipe=view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        // Inflate the layout for this fragment
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);

                getData();

            }
        });
//        progressBar=(ProgressBar) view.findViewById(R.id.progressbar);
        //      progressBar.setVisibility(View.VISIBLE)

        return view;
    }
    private void getData() {  //getting the progressbar

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //                    progressBar.setVisibility(View.INVISIBLE);
                        swipe.setRefreshing(false);
                        try {
                            JSONArray array = response.getJSONArray("articles");

                            //////////////
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject jo = array.getJSONObject(i);
                                JSONObject source = jo.getJSONObject("source");
                                String name = source.getString("name");
                                String title = jo.getString("title");
                                String image = jo.getString("urlToImage");

                                String link=jo.getString("url");
                                String date=jo.getString("publishedAt");
                                NewsList.add(new NewsModel(image, title, name,link,date));
                            }
                            News_Adapter adapter = new News_Adapter(getContext(), NewsList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            //////////
                        } catch (JSONException e) {
                            Log.v("Problem in url", "Problem");
                        }
                    }
                    // notifying list adapter about data changes so that it renders the list view with updated data

                    //      News_Adapter.notifyDataSetChanged();
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null; // error message, show it in toast or dialog, whatever you want
                        if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                            message = "Cannot connect to Internet";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again later";
                        }  else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again later";
                        }
                        if(message!=null){
                            Snackbar snackbar=Snackbar.make(getActivity().findViewById(R.id.coordinatorL),"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getData();
                                        }
                                    });
                            snackbar.show();
                        }

                    }
                });
        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
