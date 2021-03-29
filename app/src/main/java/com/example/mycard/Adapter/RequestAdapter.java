package com.example.mycard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycard.Model.Request;
import com.example.mycard.R;

import java.util.List;

public class RequestAdapter extends BaseAdapter  {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Request> requestList;


    public RequestAdapter(Activity activity, List<Request> requestsItme) {
        this.activity = activity;
        this.requestList = requestsItme;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int location) {
        return requestList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.request_layout, null);

        TextView reqID = convertView.findViewById(R.id.reqID);
        ImageView imageView = convertView.findViewById(R.id.appointment_status);


        switch (requestList.get(position).getStatus()){
            case 1:
                imageView.setImageResource(R.drawable.resech);
                break;
            case 2:
                imageView.setImageResource(R.drawable.waiting);
                break;
            case 3:
                imageView.setImageResource(R.drawable.confirmed);
                break;
            default:
                imageView.setImageResource(R.drawable.resech);
                break;
        }




        return convertView;
    }

}