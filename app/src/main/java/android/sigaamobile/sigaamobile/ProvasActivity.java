package android.sigaamobile.sigaamobile;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class ProvasActivity extends AppCompatActivity {

    WebView telaProvas;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        //Código para fazer o botão voltar, na parte superior, aparecer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Provas anteriores");     //Titulo para ser exibido na sua Action Bar em frente à seta

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        telaProvas = (WebView)findViewById(R.id.telaProvas);

        //configurações do WebView
        /*
        telaProvas.getSettings().setBuiltInZoomControls(true);
        telaProvas.getSettings().setSupportMultipleWindows(true);
        telaProvas.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        telaProvas.getSettings().setDomStorageEnabled(true);
        telaProvas.getSettings().setJavaScriptEnabled(true);
        telaProvas.setWebViewClient(new WebViewClient());
        */
        telaProvas.getSettings().setJavaScriptEnabled(true);
        telaProvas.setWebViewClient(new WebViewClient());

        telaProvas.setWebChromeClient(new GoogleClient() {

            @Override
            //Barra de progresso
            public void onProgressChanged(WebView View, int newProgress) {
                progressBar.setProgress(newProgress);

                if (newProgress == 100) {

                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

        });

        telaProvas.loadUrl("https://drive.google.com/drive/folders/1mLtT2NOUWdsRx4oCT8DaKApznaQG0mQ6?usp=sharing");


        //Código para fazer Download
        telaProvas.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));


                request.setMimeType(mimeType);


                String cookies = CookieManager.getInstance().getCookie(url);


                request.addRequestHeader("cookie", cookies);


                request.addRequestHeader("User-Agent", userAgent);


                request.setDescription("Downloading file...");


                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                        mimeType));


                request.allowScanningByMediaScanner();


                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(ProvasActivity.this,
                        Environment.DIRECTORY_DOWNLOADS,".pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File",
                        Toast.LENGTH_LONG).show();
            }});

/*
        telaProvas.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
*/
    }

    @Override
    public void onBackPressed() {
        if (telaProvas.canGoBack())
            telaProvas.goBack();
        else
            super.onBackPressed();

    }

    //Código que faz o botão voltar, na parte superior, funcionar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    class GoogleClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view,int newProgress)
        {
            super.onProgressChanged(view,newProgress);
        }
    }

}
