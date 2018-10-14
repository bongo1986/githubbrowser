package grzegorz.com.githubbrowser.repoSearch

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import grzegorz.com.githubbrowser.R
import grzegorz.com.githubbrowser.binding.FragmentDataBindingComponent
import grzegorz.com.githubbrowser.common.AppExecutors
import grzegorz.com.githubbrowser.common.RepoListAdapter

import grzegorz.com.githubbrowser.databinding.FragmentRepoSearchBinding
import grzegorz.com.githubbrowser.util.autoCleared
import grzegorz.com.githubbrowser.util.extensions.afterTextChanged
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class RepoSearchFragment : Fragment() {


    lateinit var binding : FragmentRepoSearchBinding
    var adapter  by autoCleared<RepoListAdapter>()
    @Inject
    lateinit var appExecutors: AppExecutors
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var vm: RepoSerachViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, viewModelFactory).get(RepoSerachViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_repo_search,
                container,
                false
        )

        binding.setLifecycleOwner(this)
        binding.etSearch.afterTextChanged({
            vm.searchTextChanged(it) })
        initRecyclerView()

        val rvAdapter = RepoListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors,
                showFullName = true
        ) { repo ->

        }
        this.adapter = rvAdapter
        binding.repoList.adapter = adapter
        binding.vm = vm
        return binding.root
    }



    private fun initRecyclerView() {
        vm.repoList.removeObservers(this)
        vm.repoList.observe(this, Observer { result ->
            adapter.submitList(result?.data)
        })
    }

    fun navController() = NavHostFragment.findNavController(this)
}
