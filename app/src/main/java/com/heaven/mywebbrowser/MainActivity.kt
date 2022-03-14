package com.heaven.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import com.heaven.mywebbrowser.databinding.ActivityMainBinding
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://www.google.com")
        }

        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            } else {
                false
            }
        }

        registerForContextMenu(binding.webView)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_google, R.id.action_home -> {
                binding.webView.loadUrl("https://www.google.com")
                return true;
            }
            R.id.action_naver -> {
                binding.webView.loadUrl("https://www.naver.com")
                return true;
            }
            R.id.action_daum -> {
                binding.webView.loadUrl("https://www.daum.net")
                return true;
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:02-1234-1234")
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                sendSMS("010-7777-7777", binding.webView.url.toString())
                return true
            }
            R.id.action_email -> {
                email("aaaa@gmail.com", "Good Page", binding.webView.url.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share -> {
                share(binding.webView.url.toString())
                return true
            }
            R.id.action_browser -> {
                browse(binding.webView.url.toString())
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}