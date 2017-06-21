package MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
//带下划线的EditText
public class LinedEditText extends EditText {
    private Paint linePaint;
    private float margin;
    private int paperColor;
    public LinedEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.linePaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas paramCanvas) {
        paramCanvas.drawColor(this.paperColor);
        int i = getLineCount();// 得到总的行數
        int j = getHeight();// 获得TextView的高度
        int k = getLineHeight();// 获得TextView的行高
        int m = j / k + 1;// 总的线数
        if (i < m)
            i = m;
        int n = getCompoundPaddingTop();
        Log.d("wxl", "n----" + n);
        paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
        for (int i2 = 0;; i2++) {
            if (i2 >= i) {
                setPadding(10 + (int) this.margin, 0, 0, 0);
                super.onDraw(paramCanvas);
                paramCanvas.restore();
                return;
            }
            n += k;
            paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
            paramCanvas.save();
        }
    }
}
//主要工作就是重载onDraw方法，利用从TextView继承下来的getLineCount函数
// 获取文本所占的行数，以及getLineBounds来获取特定行的基准高度值，而且
// 这个函数第二个参数会返回此行的“外包装”值。再利用这些值绘制这一行的
// 线条。为了让界面的View使用自定义的EditText类，必须在配置文件中进行设
// 置
