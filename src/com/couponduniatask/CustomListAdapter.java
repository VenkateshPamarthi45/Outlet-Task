package com.couponduniatask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Outlet> outletItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Outlet> outletItems) {
		this.activity = activity;
		this.outletItems = outletItems;
	}

	@Override
	public int getCount() {
		return outletItems.size();
	}

	@Override
	public Object getItem(int location) {
		return outletItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.imageurlid);
		TextView title = (TextView) convertView.findViewById(R.id.nameid);
		TextView offers = (TextView) convertView.findViewById(R.id.offersid);
		TextView categories = (TextView) convertView.findViewById(R.id.categoriesid1);
		TextView location = (TextView) convertView.findViewById(R.id.NeighbourhoodNameid);

		// getting outlet data for the row
		Outlet m = outletItems.get(position);

				// thumbnail image
				thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

				// title
				title.setText(m.getTitle());

				// offers
				int offersnum = m.getOffers();
				if(offersnum > 1)
				{
					offers.setText(offersnum + " offers");        	
				}
				else if(offersnum == 1)
				{
					offers.setText(offersnum + " offer"); 
				}


				// Categories
				String genreStr = "";
				for (String str : m.getCategories()) {
					genreStr += str + ", ";
				}
				genreStr = genreStr.length() > 0 ? genreStr.substring(0,
						genreStr.length() - 2) : genreStr;
				categories.setText(genreStr);

				double dist = m.getDistance();
				// location
				location.setText(((int)dist < 1000 ? (String.valueOf((int)dist) + " m ")  :  (String.valueOf(round((((double)((int)dist))/1000.0), 1)) + "  Km ")) +"  " +String.valueOf(m.getLocation()));

				return convertView;
	}
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
