package com.example.senato.Fragment

data class Voting(val creator: String?= null, val members: MutableList<String>? = null, val options: MutableList<String>? = null, val title: String? = null,
val type: String? = null, val usersvoted:MutableList<String>? = null, val votes: MutableList<Int>? = null)
