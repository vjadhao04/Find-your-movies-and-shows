package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.viewmodels.SharedViewModel


class WebFragment : Fragment() {
    private lateinit var url:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_web, container, false)
        var webview=view.findViewById<WebView>(R.id.webView)
        var pbar=view.findViewById<ProgressBar>(R.id.progressBar)

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.ytTitleLiveData.observe(viewLifecycleOwner, Observer {

            url=it
            Log.d("URL",url)


            if(url!=null) {
                webview.settings.javaScriptEnabled = true
                webview.loadUrl("https://www.youtube.com/results?search_query=" + url)
                webview.getSettings().setSupportZoom(true);
                webview.getSettings().setUseWideViewPort(true);
                webview.settings.setMediaPlaybackRequiresUserGesture(false)

                webview.settings.setAppCacheEnabled(true)
                webview.settings.loadWithOverviewMode=true
                webview.webViewClient = object : WebViewClient() {

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        pbar.visibility = View.GONE
                        webview.visibility = View.VISIBLE
                   }
                }
            }
        })
        model.urlLivedata.observe(viewLifecycleOwner, Observer {
            url=it
            Log.d("URL",url)
            if(url!=null) {
                webview.settings.javaScriptEnabled = true
                webview.loadUrl("https://en.wikipedia.org/wiki/" + url)
                webview.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        pbar.visibility = View.GONE
                        webview.visibility = View.VISIBLE


                    }
                }
            }

        })




        return view
    }


}