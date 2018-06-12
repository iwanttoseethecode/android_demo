package com.example.luoling.android_dome.svg;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import com.example.luoling.android_dome.R;

import butterknife.ButterKnife;

public class SVGDemoActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private boolean isTick = true;
    private boolean isFull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svgdemo);
        ButterKnife.bind(this);
    }

    public void startAnim(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void anim1(View view){
        ImageView imageView = (ImageView) view;

        AnimatedVectorDrawable tickToCross = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_path_tick2cross,null);
        AnimatedVectorDrawable crossToTick = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_path_cross2ticke,null);

        AnimatedVectorDrawable animDrawable = isTick ? tickToCross : crossToTick ;
        imageView.setImageDrawable(animDrawable);
        animDrawable.start();
        isTick = !isTick;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void anim2(View view){
        ImageView imageView = (ImageView) view;

        AnimatedVectorDrawable fullToEmpty = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.heart_empty_anim);
        AnimatedVectorDrawable emptyToFull = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.heart_full_anim);

        AnimatedVectorDrawable animDrawable = isFull ? emptyToFull : fullToEmpty;
        imageView.setImageDrawable(animDrawable);
        animDrawable.start();

        isFull = !isFull;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void anim3(View view){
        ImageView imageView = (ImageView) view;
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.fivestar_anim,null);
        imageView.setImageDrawable(drawable);
        drawable.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void anim4(View view){
        ImageView imageView = (ImageView) view;
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.avd_path_paw,null);
        imageView.setImageDrawable(drawable);
        drawable.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void anim(View view){
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }

/*

    svg矢量图转化成xml http://inloop.github.io/svg2android/

* Vector 语法简介
			通过使用它的Path标签，几乎可以实现SVG中的其它所有标签，虽然可能会复杂一点，
			但这些东西都是可以通过工具来完成的，所以，不用担心写起来会很复杂。
			(1)Path指令解析如下所示：
				M = moveto(M X,Y) ：将画笔移动到指定的坐标位置，相当于 android Path 里的moveTo()
				L = lineto(L X,Y) ：画直线到指定的坐标位置，相当于 android Path 里的lineTo()
				H = horizontal lineto(H X)：画水平线到指定的X坐标位置
				V = vertical lineto(V Y)：画垂直线到指定的Y坐标位置
				C = curveto(C X1,Y1,X2,Y2,ENDX,ENDY)：三次贝赛曲线
				S = smooth curveto(S X2,Y2,ENDX,ENDY) 同样三次贝塞尔曲线，更平滑
				Q = quadratic Belzier curve(Q X,Y,ENDX,ENDY)：二次贝赛曲线
				T = smooth quadratic Belzier curveto(T ENDX,ENDY)：映射 同样二次贝塞尔曲线，更平滑
				A = elliptical Arc(A RX,RY,XROTATION,FLAG1,FLAG2,X,Y)：弧线 ，相当于arcTo()
				Z = closepath()：关闭路径（会自动绘制链接起点和终点）

				注意，’M’处理时，只是移动了画笔， 没有画任何东西。

Vector 属性示例

在 drawable 目录中创建一个 triangle.xml 文件，内容如下：

XHTML
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="400dp"
    android:height="400dp"
    android:viewportHeight="400"
    android:viewportWidth="400">
    <path
        android:pathData="M 100 100 L 300 100 L 200 300 z"
        android:strokeColor="#000000"
        android:strokeWidth="5"
        android:fillColor="#FF0000"
        />
</vector>

path 元素里面的 pathData 就是矢量图的路径数据，除此之外还可以设置其他属性。 path 元素一共包含如下属性：

    android:name 定义该 path 的名字，这样在其他地方可以通过名字来引用这个路径
    android:pathData 和 SVG 中 d 元素一样的路径信息。
    android:fillColor 定义填充路径的颜色，如果没有定义则不填充路径
    android:strokeColor 定义如何绘制路径边框，如果没有定义则不显示边框
    android:strokeWidth 定义路径边框的粗细尺寸
    android:strokeAlpha 定义路径边框的透明度
    android:fillAlpha 定义填充路径颜色的透明度
    android:trimPathStart 从路径起始位置截断路径的比率，取值范围从 0 到1
    android:trimPathEnd 从路径结束位置截断路径的比率，取值范围从 0 到1
    android:trimPathOffset 设置路径截取的范围 Shift trim region (allows showed region to include the start and end), in the range from 0 to 1.
    android:strokeLineCap 设置路径线帽的形状，取值为 butt, round, square.
    android:strokeLineJoin 设置路径交界处的连接方式，取值为 miter,round,bevel.
    android:strokeMiterLimit 设置斜角的上限，Sets the Miter limit for a stroked path. 注：当strokeLineJoin设置为 “miter” 的时候， 绘制两条线段以锐角相交的时候，所得的斜面可能相当长。当斜面太长，就会变得不协调。strokeMiterLimit 属性为斜面的长度设置一个上限。这个属性表示斜面长度和线条长度的比值。默认是 10，意味着一个斜面的长度不应该超过线条宽度的 10 倍。如果斜面达到这个长度，它就变成斜角了。当 strokeLineJoin 为 “round” 或 “bevel” 的时候，这个属性无效。

根元素 vector 是用来定义这个矢量图的，该元素包含如下属性：

    android:name 定义该drawable的名字
    android:width 定义该 drawable 的内部(intrinsic)宽度,支持所有 Android 系统支持的尺寸，通常使用 dp
    android:height 定义该 drawable 的内部(intrinsic)高度,支持所有 Android 系统支持的尺寸，通常使用 dp
    android:viewportWidth 定义矢量图视图的宽度，视图就是矢量图 path 路径数据所绘制的虚拟画布
    android:viewportHeight 定义矢量图视图的高度，视图就是矢量图 path 路径数据所绘制的虚拟画布
    android:tint 定义该 drawable 的 tint 颜色。默认是没有 tint 颜色的
    android:tintMode 定义 tint 颜色的 Porter-Duff blending 模式，默认值为 src_in
    android:autoMirrored 设置当系统为 RTL (right-to-left) 布局的时候，是否自动镜像该图片。比如 阿拉伯语。
    android:alpha 该图片的透明度属性

有时候我们需要对几个路径一起处理，这样就可以使用 group 元素来把多个 path 放到一起。 group 支持的属性如下：

    android:name 定义 group 的名字
    android:rotation 定义该 group 的路径旋转多少度
    android:pivotX 定义缩放和旋转该 group 时候的 X 参考点。该值相对于 vector 的 viewport 值来指定的。
    android:pivotY 定义缩放和旋转该 group 时候的 Y 参考点。该值相对于 vector 的 viewport 值来指定的。
    android:scaleX 定义 X 轴的缩放倍数
    android:scaleY 定义 Y 轴的缩放倍数
    android:translateX 定义移动 X 轴的位移。相对于 vector 的 viewport 值来指定的。
    android:translateY 定义移动 Y 轴的位移。相对于 vector 的 viewport 值来指定的。

通过上面的属性可以看出， group 主要是用来设置路径做动画的关键属性的。

最后， vector 还支持 clip-path 元素。定义当前绘制的剪切路径。注意，clip-path 只对当前的 group 和子 group 有效。

    android:name 定义 clip path 的名字
    android:pathData 和 android:pathData 的取值一样。

从上面 vector 支持的属性可以看出，功能还是比较丰富的。
例如 前面提到的三角形，通过 group 可以把其旋转 90度。

XHTML
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="400dp"
    android:height="400dp"
    android:viewportHeight="400"
    android:viewportWidth="400">
    <group
        android:name="name"
        android:pivotX="200"
        android:pivotY="200"
        android:rotation="90">
        <path
            android:fillColor="#FF0000"
            android:pathData="M 100 100 L 300 100 L 200 300 z"
            android:strokeColor="#000000"
            android:strokeWidth="5" />
    </group>
</vector>

* */
}
