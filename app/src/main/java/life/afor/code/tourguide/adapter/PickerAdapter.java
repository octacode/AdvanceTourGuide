package life.afor.code.tourguide.adapter;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import life.afor.code.tourguide.R;
import life.afor.code.tourguide.activity.PickerActivity;
import life.afor.code.tourguide.app.model.FoursquareResults;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickerViewHolder>{

    private List<FoursquareResults> foursquareResults;
    private Context mContext;

    public PickerAdapter(List<FoursquareResults> foursquareResults, Context context) {
        this.foursquareResults = foursquareResults;
        mContext = context;
    }

    public class PickerViewHolder extends RecyclerView.ViewHolder{
        private TextView title, rating, address, pos;
        public PickerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            address = (TextView) itemView.findViewById(R.id.address);
            rating = (TextView) itemView.findViewById(R.id.rating);
            pos = (TextView) itemView.findViewById(R.id.pos);
        }
    }

    @Override
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PickerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.picker_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PickerViewHolder holder, int position) {
        holder.title.setText(foursquareResults.get(position).getVenue().getName());

        if(foursquareResults.get(position).getVenue().getRating() >= 8)
            holder.rating.setBackgroundColor(Color.parseColor("#6ECE34"));
        else if(foursquareResults.get(position).getVenue().getRating() < 8 && foursquareResults.get(position).getVenue().getRating() >= 6)
            holder.rating.setBackgroundColor(Color.parseColor("#F2F740"));
        else
            holder.rating.setBackgroundColor(Color.RED);

        holder.rating.setText(String.valueOf(foursquareResults.get(position).getVenue().getRating()));
        holder.address.setText(foursquareResults.get(position).getVenue().getLocation().address);
        holder.pos.setText(String.valueOf("#"+(position+1)));
    }

    @Override
    public int getItemCount() {
        return foursquareResults.size();
    }
}
