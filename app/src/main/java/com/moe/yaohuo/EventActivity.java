package com.moe.yaohuo;
import android.os.Bundle;
import android.view.WindowManager;
import android.support.v7.widget.Toolbar;
import com.moe.utils.PreferenceUtils;
import android.view.MotionEvent;
import android.view.View;
import android.animation.ObjectAnimator;
import android.animation.Animator;

public class EventActivity extends BaseActivity
{
	private boolean isAnime;
	private float oldx,oldy,X;
	private int mode;
	private View view;
	private long time;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_view);
		view=findViewById(R.id.coordinatorlayout);
		getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(0xffffffff);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if(isAnime)return true;
		switch(event.getAction()){
			case event.ACTION_DOWN:
				oldx=event.getRawX();
				if(oldx<30){
				oldy=event.getRawY();
				mode=0;
				time=System.currentTimeMillis();
				//super.dispatchTouchEvent(event);
				return true;
				}else{
					oldy=-1;
				break;
				}
			case event.ACTION_MOVE:
				if(oldy==-1)break;
				switch(mode){
					case -1://横向滚动
						float lx=X+event.getRawX()-oldx;
						view.setX(lx<0?0:lx);
						return true;
					case 0:
						if(Math.abs(oldy-event.getRawY())<5&&event.getRawX()>oldx)
						{mode=-1;
							X=view.getX();
						}else{
							mode=-1;
							}
						dispatchTouchEvent(event);
						break;
					case 1:
						break;
				}
				break;
			case event.ACTION_UP:
				if(oldy==-1)break;
				if(mode==-1){
					if(System.currentTimeMillis()-time<200||view.getX()>view.getWidth()*0.25)
				animeFinish();
				else
				animeResume();
				}
				break;
		}
		
		return super.dispatchTouchEvent(event);
	}

	private void animeResume()
	{
		float[] data=new float[]{view.getX(),0};
		Animator anim=ObjectAnimator.ofFloat(view,"X",data);
		anim.setDuration((int)(data[0]/view.getWidth()*500));
		anim.addListener(new Animator.AnimatorListener(){

				@Override
				public void onAnimationStart(Animator p1)
				{
					isAnime=true;
				}

				@Override
				public void onAnimationEnd(Animator p1)
				{
					isAnime=false;
				}

				@Override
				public void onAnimationCancel(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationRepeat(Animator p1)
				{
					// TODO: Implement this method
				}
			});
		anim.start();
	}
	private void animeFinish(){
		float[] data=new float[]{view.getX(),view.getWidth()};
		Animator anim=ObjectAnimator.ofFloat(view,"X",data);
		anim.addListener(new Animator.AnimatorListener(){

				@Override
				public void onAnimationStart(Animator p1)
				{
					isAnime=true;
				}

				@Override
				public void onAnimationEnd(Animator p1)
				{
					finish();
				}

				@Override
				public void onAnimationCancel(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationRepeat(Animator p1)
				{
					// TODO: Implement this method
				}
			});
			anim.setDuration((int)((data[1]-data[0])/data[1]*500));
			anim.start();
	}
	@Override
	public void finish()
	{
		// TODO: Implement this method
		super.finish();
		overridePendingTransition(0,0);
	}
	
}