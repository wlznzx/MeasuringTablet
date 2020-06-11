package alauncher.cn.measuringtablet.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import alauncher.cn.measuringtablet.R;

public class Indicator extends View {
    //实心圆的画笔;
    private Paint mForePaint;
    //空心圆的画笔;
    private Paint mBgPaint;

    //规定圆的数量,默认是4个,如果有XML指定的数量,使用指定的
    private int mNumber = 2;
    //圆的半径,规定默认值为10,如果有XML指定的数量,使用指定的
    private int mRadius = 3;
    //定义圆(空心圆)的背景颜色,默认红色,如果有XML指定的数量,使用指定的
    private int mBgColor = Color.RED;
    //定义圆(实心圆)的背景颜色,默认蓝色,如果有XML指定的数量,使用指定的
    private int mkForeColor = Color.BLACK;


    private float moffest;

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔对象
        initPaint();
        //引用atts文件下,给自己定义控件设置属性,得到TypdeArray对象.
        //参数1:attrs固定    参数2:在values文件下XML里R.styleable.下写的name名字
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        //使用typedArray对象,把在自定义控件设置的属性和XML文件里的属性进行关联,才算完成
        //参数1:R.styleable.Indicator 你在attrs定义的名字   参数2:你要管理的成员变量名(最后=也是成员变量名)
        //注意你在XML文件里设置的类型属性获取时也要是对应的类型.(最后同步Gradle文件,否则在XML布局文件里依然没有办法引用)
        mNumber = typedArray.getInteger(R.styleable.Indicator_setNumber, mNumber);
        mRadius = typedArray.getInteger(R.styleable.Indicator_setRadius, mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPaint();
    }

    //初始化画笔对象
    private void initPaint() {
        //创建画笔的对象
        mForePaint = new Paint();
        //设置抗锯齿(如果不设置抗锯齿画出来的图会模糊)
        mForePaint.setAntiAlias(true);
        //设置画笔的样式,为实心
        mForePaint.setStyle(Paint.Style.FILL);
        //设置画笔的颜色
        mForePaint.setColor(mkForeColor);
        //设置画笔的宽度
        mForePaint.setStrokeWidth(2);

        //创建画笔的对象,用于画空心圆
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(R.color.colorPrimary);
        mBgPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画多个空心圆,为了使圆不重叠,所以对X轴坐标进行动态的修改
        for (int i = 0; i < mNumber; i++) {
            canvas.drawCircle(100 + i * mRadius * 3, 20, mRadius, mBgPaint);
        }
        //画实心圆,为使实心圆能够进行X轴移动   参数1加上了偏移量
        canvas.drawCircle(100 + moffest, 20, mRadius, mForePaint);
    }

    public void setoffest(int position, float positionOffset) {
        invalidate();
        //为了防止角标越界,取余数
        position %= mNumber;
        //给成员变量设置偏移量具体数据
        //因为从一个圆到另一个圆,要经过3个半径+偏移量*3个半径,也可以看出点的移动过程
        moffest = position * 3 * mRadius + positionOffset * 3 * mRadius;
        if (position == mNumber - 1) {
            moffest = position * 3 * mRadius;
        }
        //关键的一点,从新绘制自定义View的方法,十分常用.(不绘制看不成自定义控件的动态效果)
        invalidate();
    }

}
