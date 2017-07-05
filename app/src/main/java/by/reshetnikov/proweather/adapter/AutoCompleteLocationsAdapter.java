package by.reshetnikov.proweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.greenrobot.greendao.annotation.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.DataRepository;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;

public class AutoCompleteLocationsAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = AutoCompleteLocationsAdapter.class.getSimpleName();
    private static final int MAX_RESULTS = 7;

    @Inject
    DataRepository dataRepository;

    @BindView(R.id.tv_dropdown_location)
    TextView tvLocation;
    @BindView(R.id.tv_dropdown_country_code)
    TextView tvCountyCode;

    private int threshold;
    private WeakReference<Context> contextRef;
    private List<LocationAppModel> resultList;

    public AutoCompleteLocationsAdapter(@NotNull Context context, int threshold) {
        this.contextRef = new WeakReference<>(context);
        this.threshold = threshold;
        ((ProWeatherApp) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null && contextRef != null) {
            LayoutInflater inflater = (LayoutInflater) contextRef.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.location_dropdown_item, parent, false);
        }
        ButterKnife.bind(this, convertView);
        LocationAppModel location = (LocationAppModel) getItem(position);
        tvLocation.setText(location.getLocationName());
        tvCountyCode.setText(location.getCountryCode());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() >= threshold) {
                    List<LocationAppModel> locations = findLocations(constraint.toString());
                    filterResults.values = locations;
                    if (locations == null)
                        filterResults.count = 0;
                    else
                        filterResults.count = locations.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<LocationAppModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<LocationAppModel> findLocations(String search) {
        return dataRepository.getAllLocationsByName(search, MAX_RESULTS).blockingGet();
    }
}




