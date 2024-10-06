package com.example.newsnow

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import coil.compose.AsyncImage


@Composable
fun HomePage (newsViewModel: NewsViewModel){

    val articles by newsViewModel.articles.observeAsState(emptyList())

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        CategoriesBar(newsViewModel)

        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(articles){article ->
                ArticleItem(article)
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article){
    Card (
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = article.urlToImage?:"https://static.thenounproject.com/png/3421-200.png",
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ){
                Text(text = article.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                    )
                Text(
                    text = article.source.name,
                    maxLines = 1,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel){
    val categoriesList = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORTS",
        "TECHNOLOGY"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categoriesList.forEach{ category ->
            Button(onClick = {
                newsViewModel.fetchNewsTopHeadlines(category)
            },
                modifier = Modifier.padding(4.dp)
                ) {
                Text(text = category)
            }

        }
    }
}