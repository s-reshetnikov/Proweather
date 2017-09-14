package by.reshetnikov.proweather.presentation.location.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import by.reshetnikov.proweather.presentation.location.adapter.LocationsViewAdapterContract;
import by.reshetnikov.proweather.presentation.location.viewholder.LocationViewHolderContract;
import timber.log.Timber;


public class LocationItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

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
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder instanceof LocationViewHolderContract) {
            ((LocationViewHolderContract) viewHolder).onSelectedChanged();
        }
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
        if (dragFromPosition == 0 && dragToPosition == 1) {
            LocationViewHolderContract viewHolderToMark = (LocationViewHolderContract) recyclerView.findViewHolderForAdapterPosition(dragToPosition);
            viewHolderToMark.markAsCurrent(true);
            ((LocationViewHolderContract) viewHolder).markAsCurrent(false);
        }

        if (dragToPosition == 0) {
            LocationViewHolderContract viewHolderToMark = (LocationViewHolderContract) recyclerView.findViewHolderForAdapterPosition(0);
            viewHolderToMark.markAsCurrent(false);
            ((LocationViewHolderContract) viewHolder).markAsCurrent(true);
        }
        boolean saveChanges = false;
        adapter.moveLocationItem(from, to, saveChanges);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        viewHolder.setIsRecyclable(false);
        adapter.removeLocation(position);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Timber.d("clearView()");
        if (dragFromPosition != -1 && dragToPosition != -1 && dragFromPosition != dragToPosition) {
            int fromPosition = dragFromPosition;
            int toPosition = dragToPosition;
            dragFromPosition = dragToPosition = -1;
            boolean saveChanges = true;
            adapter.moveLocationItem(fromPosition, toPosition, saveChanges);
        }
        if (viewHolder instanceof LocationViewHolderContract) {
            ((LocationViewHolderContract) viewHolder).onItemClear();
        }
    }

}
