package com.arush.android.hellowebview;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText editText;
    GridView gridView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.editText);
        gridView = (GridView) findViewById(R.id.gridView);
        webView = (WebView) findViewById(R.id.webView);

        String[] gridNames = {"Web Browser", "WebView - String", "WebView - File", "WebView - Uri",
                "Enable Javascript", "Android Calls JavaScript"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gridNames);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:
                String urlString = editText.getText().toString();
                //Confirm user input
                if (urlString.length() == 0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Please enter URL");
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.setPositiveButton(android.R.string.ok, null);
                    alert.show();
                    return;
                }
                Uri uri = Uri.parse(urlString);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 1:
                String html = "<hr><h1>Hello HTML5</h1><hr>";
                webView.loadData(html, "text/html", "utf-8");
                break;
            case 2:
                String stringUrl = "file:///android_asset/one.html";
                webView.loadUrl(stringUrl);
                break;
            case 3:
                String stringURL = "https://google.com";
                webView.loadUrl(stringURL);
                break;
            case 4:
                TextView textView = (TextView) view;
                if (webView.getSettings().getJavaScriptEnabled()) {
                    webView.getSettings().setJavaScriptEnabled(false);
                    textView.setText("Enable JavaScript");
                }
                else {
                    webView.getSettings().setJavaScriptEnabled(true);
                    textView.setText("Disable JavaScript");
                }
                webView.addJavascriptInterface(this, "Android");
                break;
            case 5:
                webView.loadUrl("javascript:doCount('it works')");
                break;
        }
    }

    @JavascriptInterface
    public void sayHello() {
        new AlertDialog.Builder(this).setTitle("Called from JavaScript!").setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.ok, null).show();
    }
}
