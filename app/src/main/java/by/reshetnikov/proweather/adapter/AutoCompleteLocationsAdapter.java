package by.reshetnikov.proweather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import io.reactivex.disposables.CompositeDisposable;

public class AutoCompleteLocationsAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = AutoCompleteLocationsAdapter.class.getSimpleName();

    @BindView(R.id.tv_dropdown_location)
    TextView tvLocation;
    @BindView(R.id.tv_dropdown_country_code)
    TextView tvCountyCode;
    CompositeDisposable compositeDisposable;
    LocationManagerContract.Presenter presenter;
    private WeakReference<Context> contextRef;
    private List<LocationAdapterModel> results = new ArrayList<>();

    public AutoCompleteLocationsAdapter(Context context, LocationManagerContract.Presenter presenter) {
        this.contextRef = new WeakReference<>(context);
        this.presenter = presenter;
        compositeDisposable = new CompositeDisposable();
    }

//    private void loadLocations(String locationName) {
//        results.clear();
//        results.addAll(locations);
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_autocomplete_location, parent, false);
        }
        ButterKnife.bind(this, convertView);
        LocationAdapterModel location = (LocationAdapterModel) getItem(position);
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
                if (constraint != null) {
                    Log.d(TAG, "performFiltering() in getFilter(): " + constraint.toString());
                    AutoCompleteLocationsAdapter.this.loadLocations(constraint.toString());
                    filterResults.values = results;
                    filterResults.count = results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0 && constraint != null) {
                    Log.d(TAG, "performFiltering() in getFilter(): " + constraint.toString());
                    AutoCompleteLocationsAdapter.this.results = (List<LocationAdapterModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private void loadLocations(String searchText) {
        results = presenter.getLocationsByName(searchText);

    }

    private Context getContext() {
        if (contextRef != null) {
            return contextRef.get();
        }
        throw new NullPointerException("Context is null!!!");
    }
}




