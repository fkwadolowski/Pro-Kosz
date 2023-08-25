package com.example.first_mgr

import android.view.LayoutInflater
import android.view.View
import twitter4j
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import twitter4j.Status

class TweetAdapter(private val tweets: List<Status>) : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetTextView: TextView = itemView.findViewById(R.id.tweetTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tweet, parent, false)
        return TweetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.tweetTextView.text = tweet.text
    }

    override fun getItemCount(): Int {
        return tweets.size
    }
}
