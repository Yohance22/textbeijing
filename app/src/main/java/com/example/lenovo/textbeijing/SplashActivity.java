package com.example.lenovo.textbeijing;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.textbeijing.activity.GuideActivity;
import com.example.lenovo.textbeijing.activity.MainActivity;
import com.example.lenovo.textbeijing.util.CacheUtils;

public class SplashActivity extends Activity {


    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splash_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_splash_root =(RelativeLayout) findViewById(R.id.rl_splash_root);


        AlphaAnimation aa = new AlphaAnimation(0,1);
//        aa.setDuration(500);
        aa.setFillAfter(true);
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
//        sa.setDuration(500);
        sa.setFillAfter(true);
        RotateAnimation  ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5F);
//        ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(ra);
        set.addAnimation(sa);
        set.addAnimation(aa);
        set.setDuration(2000);
        rl_splash_root.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());






    }

    private class MyAnimationListener implements Animation.AnimationListener {




        @Override
        public void onAnimationStart(Animation animation) {


            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            Intent intent;

            if(isStartMain) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            else{
                 intent = new Intent(SplashActivity.this,GuideActivity.class);

            }
            startActivity(intent);
            finish();

//            Toast.makeText(SplashActivity.this,"动画播放完成了",Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
