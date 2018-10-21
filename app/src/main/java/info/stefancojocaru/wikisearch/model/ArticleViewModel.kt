package info.stefancojocaru.wikisearch.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import info.stefancojocaru.wikisearch.repository.ArticleRepository

class ArticleViewModel: ViewModel() {

    lateinit var articleRepository: ArticleRepository
    lateinit var allArticles: LiveData<List<Model.Article>>
}