package com.example.first_mgr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewHomeTeam: TextView = itemView.findViewById(R.id.textViewHomeTeam)
        val textViewHomeScore: TextView = itemView.findViewById(R.id.textViewHomeScore)
        val textViewVisitorTeam: TextView = itemView.findViewById(R.id.textViewVisitorTeam)
        val textViewVisitorScore: TextView = itemView.findViewById(R.id.textViewVisitorScore)
        val imageViewHomeLogo: ImageView = itemView.findViewById(R.id.imageViewHomeTeamLogo)
        val imageViewVisitorLogo: ImageView = itemView.findViewById(R.id.imageViewVisitorTeamLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        val year = game.date.substring(0, 10)
        holder.textViewDate.text = year
        holder.textViewHomeTeam.text = game.home_team.full_name
        holder.textViewHomeScore.text = game.home_team_score.toString()
        holder.textViewVisitorTeam.text = game.visitor_team.full_name
        holder.textViewVisitorScore.text = game.visitor_team_score.toString()

        // Get the drawable resource ID based on the team ID using the TeamLogoMapping
        val homeTeamLogoId = TeamLogoMapping.getLogoResourceID(game.home_team.id)
        val visitorTeamLogoId = TeamLogoMapping.getLogoResourceID(game.visitor_team.id)

        // Set the team logos
        holder.imageViewHomeLogo.setImageResource(homeTeamLogoId)
        holder.imageViewVisitorLogo.setImageResource(visitorTeamLogoId)
    }

    override fun getItemCount(): Int {
        return games.size
    }
}
