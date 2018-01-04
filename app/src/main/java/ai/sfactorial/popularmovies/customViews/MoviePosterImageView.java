package ai.sfactorial.popularmovies.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dillan on 2018/01/04.
 */

public class MoviePosterImageView extends ImageView {

    public MoviePosterImageView(Context context) {
        super(context);
    }

    public MoviePosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoviePosterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = (int)(measuredWidth * 1.5);

        setMeasuredDimension(measuredWidth, measuredHeight); //Snap to width
    }
}
