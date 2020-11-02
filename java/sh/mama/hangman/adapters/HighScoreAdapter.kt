package sh.mama.hangman.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_highscore.view.*
import sh.mama.hangman.R
import sh.mama.hangman.models.HighScore

class HighScoreAdapter(
    private var scores: List<HighScore>,
    private val overall: Boolean,
) : RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {
    inner class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_highscore, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.itemView.apply {
            title.text = scores[position].player
            score.text = scores[position].getScore().toString()
            if (overall)
                word.text = scores[position].word.word
        }
    }

    override fun getItemCount(): Int {
        return scores.size
    }
}