package alauncher.cn.measuringtablet.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;


@SuppressLint("AppCompatCustomView")
public class BorderSpinner extends Spinner {

    public BorderSpinner(Context context) {
        super(context);
    }

    public BorderSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int sroke_width = 1;

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        //  将边框设为黑色
        paint.setColor(android.graphics.Color.BLACK);
        //  画TextView的4个边
        // canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);
        // canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);
        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        super.onDraw(canvas);
    }


}
