package by.reshetnikov.proweather.presentation.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SimpleDividerItemDecoration extends DividerItemDecoration {

    private final Rect bounds = new Rect();
    private final Drawable divider;
    private final int leftPaddingPx;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}for vertical orientation.
     *
     * @param context Current context, it will be used to access resources.
     */
    public SimpleDividerItemDecoration(Context context, Drawable divider, int leftPaddingPx) {
        super(context, DividerItemDecoration.VERTICAL);
        this.divider = divider;
        this.leftPaddingPx = leftPaddingPx;

    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();
        final int leftWithMargin = leftPaddingPx;
        final int right = parent.getWidth();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            int left = (adapterPosition == childCount) ? 0 : leftWithMargin;
            parent.getDecoratedBoundsWithMargins(child, bounds);
            final int bottom = bounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
        canvas.restore();
    }
}
