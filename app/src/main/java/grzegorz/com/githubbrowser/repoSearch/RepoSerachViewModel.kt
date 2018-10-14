package grzegorz.com.githubbrowser.repoSearch

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import grzegorz.com.githubbrowser.repository.GitHubRepository
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoSerachViewModel @Inject constructor(
        private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val searchTextProcessor = PublishProcessor.create<String>()

    val loading = MutableLiveData<Boolean>()
    val query =  MutableLiveData<String>()
    val repoList = LiveDataReactiveStreams.fromPublisher(
            searchTextProcessor
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .doOnNext{
                        query.postValue(it)
                        loading.postValue(true)
                    }
                    .flatMap { gitHubRepository.searchRepositories(it) }
                    .doOnNext {
                        loading.postValue( false)
                    })


    fun searchTextChanged(text: String) {
        searchTextProcessor.onNext(text)
    }


}