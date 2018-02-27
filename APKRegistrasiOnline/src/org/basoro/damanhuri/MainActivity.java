package org.basoro.damanhuri;

import android.app.Activity; 
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class MainActivity extends Activity {
  private WebView mWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    mWebView = new WebView(this);
    mWebView.setClickable(true); 
    mWebView.setFocusableInTouchMode(true);
    mWebView.getSettings().setJavaScriptEnabled(true);
    mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
    mWebView.setVerticalScrollBarEnabled(false); 
    mWebView.setHorizontalScrollBarEnabled(false);
    mWebView.loadUrl("https://pasien.rshdbarabai.com");
    mWebView.setWebViewClient(new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        
    });
 
    this.setContentView(mWebView);
  }
  
  @Override
  public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
        
}