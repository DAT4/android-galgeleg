package sh.mama.hangman.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import sh.mama.hangman.activities.PlayGameActivity
import sh.mama.hangman.R
import sh.mama.hangman.activities.EditWordsActivity
import sh.mama.hangman.models.Word

class WordAdapter(
    private var words: List<Word>,
) : RecyclerView.Adapter<WordAdapter.WordViewHoler>() {
    inner class WordViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHoler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return WordViewHoler(view)
    }

    override fun onBindViewHolder(holder: WordViewHoler, position: Int) {
        holder.itemView.apply {
            catTitle.text = words[position].word
            catCount.text = words[position].difficulty.toString()
            catTitle.setOnClickListener {
                val editWord = Intent(catTitle.context, EditWordsActivity::class.java)
                editWord.putExtra("word", words[position])
                editWord.putExtra("create", false)
                catTitle.context.startActivity(editWord)
            }
        }
    }

    override fun getItemCount(): Int {
        return words.size
    }
}