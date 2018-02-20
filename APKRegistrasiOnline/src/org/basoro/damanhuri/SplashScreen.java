package org.basoro.damanhuri;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
 
public class SplashScreen extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                   Intent mainMenu= new Intent(SplashScreen.this,MainActivity.class);
                   SplashScreen.this.startActivity(mainMenu);
                   SplashScreen.this.finish();
                   overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            }
    }, 3000);
    }
}
