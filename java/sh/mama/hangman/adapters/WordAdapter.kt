package sh.mama.hangman.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import sh.mama.hangman.R
import sh.mama.hangman.activities.EditWordActivity
import sh.mama.hangman.models.Word

class WordAdapter(
    private var words: List<Word>,
) : RecyclerView.Adapter<WordAdapter.WordViewHoler>() {
    inner class WordViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView)
    private val buttonClick = AlphaAnimation(1F, 0.8F)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHoler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return WordViewHoler(view)
    }

    override fun onBindViewHolder(holder: WordViewHoler, position: Int) {
        holder.itemView.apply {
            title.text = words[position].word
            content_box.setOnClickListener {
                content_box.startAnimation(buttonClick)
                val editWord = Intent(content_box.context, EditWordActivity::class.java)
                editWord.putExtra("word", words[position])
                editWord.putExtra("create", false)
                content_box.context.startActivity(editWord)
            }
        }
    }

    override fun getItemCount(): Int {
        return words.size
    }
}