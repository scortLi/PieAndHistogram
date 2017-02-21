package com.example.pieandhistogram;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * 自定义横向单柱状图
 * @author LIHUI
 *
 */
public class CustomHistogram extends View {

	private Context mContext;
	private Paint   paint;
	private Paint   font_Paint;

	// 柱状图的宽度
	private int viewWidth = 0;
	// 柱状图的总长度
	private int viewAllLength = 0;
	// 文字距离左侧的距离
	private int marginLeft = 0;
	// 文字离柱状顶端的高度
	private int textMarginTop = 0;
	// 框图的实际长度 
	private int maxSize = 0;
	private int indexSize = 0;
	private float allLength=0;
	// 线程控制
	private boolean display = true;
	// 是否开启动画效果
	private boolean animMode = true;
	// 传递的色值
	private int numColor;
	private int value;

	public CustomHistogram(Context context, int maxSize, boolean animMode,int numColor,float allLength ,int value ) {
		super(context);
		this.mContext = context;
		this.maxSize = maxSize;
		this.animMode = animMode;
		this.value = value;
		this.numColor = numColor;
		this.allLength = allLength;
		init();
	}
   
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		float length = (float) ((indexSize / 100.0) * viewAllLength); 
		float length = (float) ((indexSize / allLength) * viewAllLength); 
		canvas.drawRect(0, 0, length, viewWidth, paint);
		if(!display){
			//画右文字
			if(value == 1){
			canvas.drawText(maxSize + "辆", length+marginLeft, textMarginTop, font_Paint);
			}else if(value == 2){
				canvas.drawText(maxSize + "万公里", length+marginLeft, textMarginTop, font_Paint);
			}else if(value == 3){
				canvas.drawText(maxSize + "万元", length+marginLeft, textMarginTop, font_Paint);
			}else if(value == 4){
				canvas.drawText(maxSize + "小时", length+marginLeft, textMarginTop, font_Paint);
			}else{
				canvas.drawText(maxSize + "", length+marginLeft, textMarginTop, font_Paint);
			}
		}
	}

	// 初始化
	private void init() {
		paint = new Paint();
		paint.setColor(mContext.getResources().getColor(numColor));
		
		font_Paint = new Paint();
		font_Paint.setTextSize(mContext.getResources().getDimension(R.dimen.vm_home_all_carinfo_text_size));
		font_Paint.setColor(mContext.getResources().getColor(R.color.all_text_color));
		font_Paint.setAntiAlias(true);
		//初始化图形尺寸
		viewWidth = mContext.getResources().getDimensionPixelSize(R.dimen.more_head_texttop);
		viewAllLength = mContext.getResources().getDimensionPixelSize(R.dimen.control_alarm_pie_h);
		marginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.vm_home_car_item_margin);
		textMarginTop = mContext.getResources().getDimensionPixelSize(R.dimen.more_account_textsize);
		
		if (animMode) {
			// 启动一个线程来实现柱状图缓慢增高
			thread.start();
		} else {
			display = false;
			indexSize = maxSize;
			invalidate();
		}
	}

	@SuppressLint("HandlerLeak") 
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1 && indexSize < maxSize ) {
				indexSize += 1;
			} else {
				display = false;
			}
			invalidate();
		}
	};

	private Thread thread = new Thread() {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted() && display) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					System.err.println("InterruptedException！线程关闭");
					this.interrupt();
				}
			}
		}
	};

	// 刷新View
	public void toInvalidate() {
		this.invalidate();
	}
}