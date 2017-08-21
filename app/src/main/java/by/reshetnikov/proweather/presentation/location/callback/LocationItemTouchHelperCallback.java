package by.reshetnikov.proweather.presentation.location.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import by.reshetnikov.proweather.presentation.location.adapter.LocationsViewAdapterContract;


public class LocationItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = LocationItemTouchHelperCallback.class.getSimpleName();
    private final LocationsViewAdapterContract adapter;
    private int dragFromPosition = -1;
    private int dragToPosition = -1;


    public LocationItemTouchHelperCallback(LocationsViewAdapterContract adapter) {
        super(0, ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();

        if (dragFromPosition == -1) {
            dragFromPosition = from;
        }
        dragToPosition = to;

        boolean saveChanges = false;
        adapter.moveLocationItem(from, to, saveChanges);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.removeLocation(position);
        viewHolder.setIsRecyclable(false);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d(TAG, "clearView()");
        if (dragFromPosition != -1 && dragToPosition != -1 && dragFromPosition != dragToPosition) {
            int fromPosition = dragFromPosition;
            int toPosition = dragToPosition;
            dragFromPosition = dragToPosition = -1;
            boolean saveChanges = true;
            adapter.moveLocationItem(fromPosition, toPosition, saveChanges);
        }

    }


}
