package sh.mama.hangman.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import sh.mama.hangman.R
import sh.mama.hangman.activities.EditCategoryActivity
import sh.mama.hangman.activities.HighScoreActivity
import sh.mama.hangman.activities.StartGameActivity
import sh.mama.hangman.adapters.ActionType.*
import sh.mama.hangman.models.Category

class CategoryAdapter(
    private var categories: List<Category>,
    private val action: Enum<ActionType>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val buttonClick = AlphaAnimation(1F, 0.5F)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.itemView.apply {
            title.text = categories[position].title
            count.text = categories[position].words.size.toString()
            content_box.setOnClickListener {
                content_box.startAnimation(buttonClick)
                when (action) {
                    EDIT -> {
                        val intent = Intent(content_box.context, EditCategoryActivity::class.java)
                        intent.putExtra("category", categories[position])
                        content_box.context.startActivity(intent)
                    }
                    GAME -> {
                        val intent = Intent(content_box.context, StartGameActivity::class.java)
                        intent.putExtra("word", categories[position].getOne())
                        content_box.context.startActivity(intent)
                    }
                    HIGHSCORES -> {
                        val intent = Intent(content_box.context, HighScoreActivity::class.java)
                        intent.putExtra("category", categories[position].title)
                        content_box.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}