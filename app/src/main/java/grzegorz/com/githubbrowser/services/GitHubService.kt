package grzegorz.com.githubbrowser.services

import grzegorz.com.githubbrowser.repository.dto.GitRepoReposnse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): Flowable<GitRepoReposnse>
}