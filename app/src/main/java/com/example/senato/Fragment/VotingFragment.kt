package com.example.senato.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.senato.R

class VotingFragment : Fragment() {

    private var docID = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_voting, container, false)

        val text: TextView = view.findViewById(R.id.textVotingLOL)
        text.text = docID

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("REPLACE WITH A STRING CONSTANT")?.let {
            docID = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(docID: String) = VotingFragment().apply {
            arguments = Bundle().apply {
                putString("REPLACE WITH A STRING CONSTANT", docID)
            }
        }
    }
}