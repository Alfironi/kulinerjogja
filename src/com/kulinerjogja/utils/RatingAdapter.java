
package com.kulinerjogja.utils;

import com.kulinerjogja.R;
import com.kulinerjogja.model.RatingModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class RatingAdapter extends BaseAdapter {
    Context mContext;
    List<RatingModel> mItems;

    public RatingAdapter(Context mContext, List<RatingModel> mItems) {
        super();
        this.mContext = mContext;
        this.mItems = mItems;
    }

    private class ViewHolder {
        TextView txtNomor;
        TextView txtNama;
        RatingBar rating;
        TextView txtRest;
    }
    
    public void updateProduct(List<RatingModel> ratingModels) {
        // TODO Auto-generated method stub
        this.mItems = ratingModels;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return mItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_rating, null);
            holder = new ViewHolder();
            holder.txtNomor = (TextView) convertView.findViewById(R.id.no_rating);
            holder.txtNama = (TextView) convertView.findViewById(R.id.name_rating);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            holder.txtRest = (TextView) convertView.findViewById(R.id.restoran_rating);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RatingModel model = (RatingModel) getItem(position);
        holder.txtNomor.setText(model.getmNomor());
        holder.txtNama.setText(model.getmNama());
        holder.rating.setRating(model.getmRating());
        holder.txtRest.setText(model.getmRest());
        return convertView;
    }

}
