package grzegorz.com.githubbrowser

import grzegorz.com.githubbrowser.repoSearch.RepoSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeExpenseManagementFragment(): RepoSearchFragment


}
