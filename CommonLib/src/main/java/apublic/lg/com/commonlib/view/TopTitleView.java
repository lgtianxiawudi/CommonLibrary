package apublic.lg.com.commonlib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apublic.lg.com.commonlib.R;


/**
 *@author:郁俊耀
 *2015-8-25 上午10:25:54
 */
public class TopTitleView extends RelativeLayout {
	private Context mContext;
	private TextView rootTitleTv = null;
	private RelativeLayout rootLeftRl = null;
	private RelativeLayout rootRightRl = null;
	private ImageView leftImgIv = null;
	private TextView leftTextTv = null;
	private ImageView rightImgIv = null;
	private TextView rightTextTv = null;

	public TopTitleView(Context context) {
		super(context);
		initView(context);
	}
	
	public TopTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	public TopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}
	
	/**初始化View*/
	@SuppressLint("NewApi")
	private void initView(Context context){
		mContext = context;
		if(isInEditMode()){
			TextView textView = new TextView(context);
			textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			textView.setText("Common Top Title View");
//			textView.setTextColor(getResources().getColor(R.color.color_e7343a));
			addView(textView);
		}else{
			View layout = LayoutInflater.from(mContext).inflate(R.layout.activity_root_title, this, true);
			rootTitleTv = (TextView) layout.findViewById(R.id.root_title);
			rootLeftRl = (RelativeLayout) layout.findViewById(R.id.root_left_rl);
			rootRightRl = (RelativeLayout) layout.findViewById(R.id.root_right_rl);
			leftImgIv = (ImageView) layout.findViewById(R.id.left_img);
			leftTextTv=(TextView)layout.findViewById(R.id.left_text);
			rightImgIv = (ImageView) layout.findViewById(R.id.right_img);
			rightTextTv = (TextView) layout.findViewById(R.id.right_text);
		}
	}	
	


	/**设置标题内容*/
	public void setTitle(String title){
		rootTitleTv.setText(title);
	}
	public void setTitle(int resourseID){
		rootTitleTv.setText(mContext.getResources().getString(resourseID));
	}
	
	/**设置左边图片*/
	public void setLeftImageResource(int resourseID){
		leftImgIv.setImageResource(resourseID);
		leftImgIv.setVisibility(View.VISIBLE);
		leftTextTv.setVisibility(View.GONE);
		rootLeftRl.setVisibility(View.VISIBLE);
	}
	public void setLeftImageDrawable(Drawable drawable) {
		leftImgIv.setImageDrawable(drawable);
		leftImgIv.setVisibility(View.VISIBLE);
		leftTextTv.setVisibility(View.GONE);
		rootLeftRl.setVisibility(View.VISIBLE);
	}

	/**设置左边文字*/
	public void setLeftTextResource(int resourseID){
		leftTextTv.setText(mContext.getResources().getString(resourseID));
		leftImgIv.setVisibility(View.GONE);
		leftTextTv.setVisibility(View.VISIBLE);
		rootLeftRl.setVisibility(View.VISIBLE);
	}
	public void setLeftTextDrawable(String leftContent) {
		leftTextTv.setText(leftContent);
		leftImgIv.setVisibility(View.GONE);
		leftTextTv.setVisibility(View.VISIBLE);
		rootLeftRl.setVisibility(View.VISIBLE);
	}
	
	/**设置右边文字*/
	public void setRightText(int resourseID){
		rightTextTv.setText(mContext.getResources().getString(resourseID));
		rightTextTv.setVisibility(View.VISIBLE);
		rightImgIv.setVisibility(View.GONE);
		rootRightRl.setVisibility(View.VISIBLE);
	}
	public void setRightText(String rightContent){
		rightTextTv.setText(rightContent);
		rightTextTv.setVisibility(View.VISIBLE);
		rightImgIv.setVisibility(View.GONE);
		rootRightRl.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置右边text颜色
	 * @param color
	 */
	public void setRightTextColor(int color){
		rightTextTv.setTextColor(color);
		rightTextTv.setVisibility(View.VISIBLE);
		rightImgIv.setVisibility(View.GONE);
		rootRightRl.setVisibility(View.VISIBLE);
	}
	/**设置右边图片*/
	public void setRightImageResource(int resourseID){
		rightImgIv.setImageResource(resourseID);
		rightImgIv.setVisibility(View.VISIBLE);
		rightTextTv.setVisibility(View.GONE);
		rootRightRl.setVisibility(View.VISIBLE);
	}
	public void setRightImageDrawable(Drawable drawable){
		rightImgIv.setImageDrawable(drawable);
		rightImgIv.setVisibility(View.VISIBLE);
		rightTextTv.setVisibility(View.GONE);
		rootRightRl.setVisibility(View.VISIBLE);
	}

	public void setLeftOnClikcListener(OnClickListener listener){
		rootLeftRl.setOnClickListener(listener);
	}

	public void setRightOnClickLister(OnClickListener listener){
		rootRightRl.setOnClickListener(listener);
	}

	public void showLeftMenu(boolean flag){
		rootLeftRl.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void showRightMenu(boolean flag){
		rootRightRl.setVisibility(flag?View.VISIBLE:View.GONE);
	}

}
