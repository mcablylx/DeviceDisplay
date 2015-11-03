package mcablylx.mckiera.com.devicedisplay;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * 获取屏幕的宽高方法.
 */
public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getName();
    private RelativeLayout rlRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //布局的根节点
        rlRootView = (RelativeLayout) findViewById(R.id.rlRootView);
        //当在一个视图树中的焦点状态发生改变时，所要调用的回调函数的接口类
        rlRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //此时就可以获取到屏幕的宽高等信息了.
                Display display = getWindowManager().getDefaultDisplay();
                // ↓ 此方法已经废弃.建议用size来代替.
                //display.getHeight();
                //创建一个DisplayMetrics对象用来装载设备宽高.
                DisplayMetrics metrics = new DisplayMetrics();
                //获取屏幕的宽高,装载到 DisplayMetrics对象中
                //注意这个方法要 api 17以后才可以用.
                display.getRealMetrics(metrics);
                //获得到屏幕的宽
                int screenWidth = metrics.widthPixels;
                //获得到屏幕的高
                int screenHeight = metrics.heightPixels;

                Log.i(TAG, "screenWidth == " + screenWidth);
                Log.i(TAG, "screenHeight == " + screenHeight);
                /**
                 * 经过小米4 测试. x = 1080  y = 1920
                 * Display id 0: DisplayInfo{"内置屏幕", app 1080 x 1920, real 1080 x 1920, largest app 1920 x 1860, smallest app 1080 x 1020, 60.0 fps, rotation0, density 480 (449.704 x 447.412) dpi, layerStack 0, type BUILT_IN, FLAG_SECURE, FLAG_SUPPORTS_PROTECTED_BUFFERS}, DisplayMetrics{density=3.0, width=1080, height=1920, scaledDensity=3.0, xdpi=449.704, ydpi=447.412}, isValid=true
                 */
                /**
                 * api 小于17的 可以这么玩.计算出来的点坐标就是屏幕的右下角. 数值可以当做宽高来看.像素为单位.
                 Point point = new Point();
                 display.getSize(point);
                 int pWidth = point.x;
                 int pHeight = point.y;

                 Log.i(TAG, "pWidth == " + pWidth);
                 Log.i(TAG, "pWidth == " + pWidth);
                 */

                /**
                 * 在onCreate的时候,是拿不到rlRootView的宽高的,如果想用到 rlRootView的宽高.此时就可以获取的到
                 */
                int layoutWidth = rlRootView.getWidth();
                int layoutHeight = rlRootView.getHeight();
                Log.i(TAG, "layoutWidth == " + layoutWidth);
                Log.i(TAG, "layoutHeight == " + layoutHeight);
                //判断当前版本是否大于等于api 16
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //因为OnGlobalLayoutListener可能会被调用多次,所以使用后要把这个监听移除掉.
                    //但是,请注意,这个移除方法 在 api 16以后才有.
                    rlRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}
