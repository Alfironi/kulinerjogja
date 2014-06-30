package com.kulinerjogja.utils;

import java.util.ArrayList;
import java.util.List;

import com.kulinerjogja.R;
import com.kulinerjogja.model.MakananModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class PencarianAdapter extends BaseAdapter implements Filterable {

	private List<MakananModel> filteredMakanan= null;
	private List<MakananModel> originalMakanan = null;
	private Context mContext;
	private ViewHolder holder;
	private ArrayFilter filter;
	private final Object lock = new Object();

	public PencarianAdapter(Context context, List<MakananModel> makananModels) {
		this.filteredMakanan = makananModels;
		this.originalMakanan = makananModels;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return filteredMakanan.size();
	}


	public List<MakananModel> getfilteredPerusahaan() {
		return filteredMakanan;
	}
	
	@Override
	public Object getItem(int position) {
		return filteredMakanan.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_makanan, null);
			holder.makanan = (TextView) convertView
					.findViewById(R.id.makanan_item_search);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.makanan.setText(((MakananModel) getItem(position))
				.getNamaMakanan());
		return convertView;
	}
	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new ArrayFilter();
		}
		return filter;
	}


	private class ArrayFilter extends Filter {

		ArrayList<MakananModel> newValue;

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults result = new FilterResults();

			if (originalMakanan == null) {
				synchronized (lock) {
					originalMakanan = new ArrayList<MakananModel>(
							filteredMakanan);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (lock) {

					ArrayList<MakananModel> list = new ArrayList<MakananModel>(
							originalMakanan);
					result.values = list;
					result.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();
				final List<MakananModel> value = originalMakanan;
				final int count = value.size();

				newValue = new ArrayList<MakananModel>(count);
				for (int i = 0; i < count; i++) {
					final MakananModel values = value.get(i);
					final String valueText = values.getNamaMakanan()
							.toLowerCase();

					if (valueText.startsWith(prefixString)) {
						newValue.add(values);
					} else {
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;

						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValue.add(values);
								break;
							}
						}
					}
				}
				result.values = newValue;
				result.count = newValue.size();
			}

			
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override

		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filteredMakanan = (List<MakananModel>) results.values;
			notifyDataSetChanged();
		}


	}
	private class ViewHolder {
		TextView makanan;
	}

	
}
