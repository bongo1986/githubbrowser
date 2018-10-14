package grzegorz.com.githubbrowser.repository

import grzegorz.com.githubbrowser.common.Resource
import grzegorz.com.githubbrowser.model.Repo
import grzegorz.com.githubbrowser.services.GitHubService
import io.reactivex.Flowable
import javax.inject.Inject

class GitHubRepository @Inject constructor(val gitHubService: GitHubService) : Repository() {
    fun searchRepositories(searchString: String): Flowable<Resource<List<Repo>>> {
        return gitHubService.searchRepos(searchString)
                .map { it.items }
                .toResource()
    }
}