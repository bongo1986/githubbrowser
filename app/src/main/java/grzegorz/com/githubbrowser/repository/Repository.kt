package grzegorz.com.githubbrowser.repository

import grzegorz.com.githubbrowser.common.Resource
import io.reactivex.Flowable

open class Repository {

    protected fun <T> Flowable<T>.toResource() : Flowable<Resource<T>> {
        return this
                .map {  Resource.success(it) }
                .onErrorReturn { Resource.error<T>(it.message!!, null)  }
    }

}