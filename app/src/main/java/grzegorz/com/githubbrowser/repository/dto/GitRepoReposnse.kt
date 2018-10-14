package grzegorz.com.githubbrowser.repository.dto

import grzegorz.com.githubbrowser.model.Repo

data class GitRepoReposnse(
        val  total_count : Int,
        val  incomplete_results : Boolean,
val items : List<Repo>)