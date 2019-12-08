package com.example.newsapk;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapk.ui.Browser;
import com.squareup.picasso.Picasso;

import net.time4j.Moment;
import net.time4j.android.ApplicationStarter;
import net.time4j.format.expert.Iso8601Format;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.NewsViewHolder> {
    //this context we will use to inflate the layout
    private Context mCtx;
    //we are storing all the products in a list
    private List<NewsModel> NewsList;

    //getting the context and product list with constructor
    public News_Adapter(Context mCtx, List<NewsModel> NewsList) {
        this.mCtx = mCtx;
        this.NewsList = NewsList;
    }



    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.news_list_items, null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        //getting the product of the specified position
        final NewsModel newsModel = NewsList.get(position);
        //binding the data with the viewholder views
        holder.textview.setText(newsModel.getHeadline());
        Picasso.get().load(newsModel.getImage()).into(holder.image);
        holder.publisher.setText(newsModel.getPublisher());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=newsModel.getLink();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Browser optionsFrag = new Browser (link);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, optionsFrag,"Browser").addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView textview,publisher,date;
        ImageView image;
        public NewsViewHolder(View view) {
            super(view);
            textview=(TextView) view.findViewById(R.id.headline);
            image=(ImageView) view.findViewById(R.id.image);
            publisher=(TextView) view.findViewById(R.id.publisher);
        }
    }
}

