package com.example.newsnow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel() {
    //Calling the method below
    init {
        fetchNewsTopHeadlines()
    }

    //Method that will fetch the headlines from the API
    fun fetchNewsTopHeadlines(){
        val newsApiClient = NewsApiClient(Constant.apiKey)

        val  request = TopHeadlinesRequest.Builder().language("en").build()

        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.forEach{
                    Log.i("NewsApi Response", it.title)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null){
                    Log.i("NewsApi Response Failed", throwable.localizedMessage)
                }
            }
        })
    }
}