package sh.mama.hangman.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import sh.mama.hangman.activities.EditCategoryActivity
import sh.mama.hangman.activities.PlayGameActivity
import sh.mama.hangman.R
import sh.mama.hangman.models.Category

class CategoryAdapter(
    private var categories: List<Category>,
    private val edit: Boolean
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
            catTitle.text = categories[position].title
            catCount.text = categories[position].words.size.toString()
            content_box.setOnClickListener {
                content_box.startAnimation(buttonClick)
                if (edit) {
                    val editContext = Intent(catTitle.context, EditCategoryActivity::class.java)
                    editContext.putExtra("category", categories[position])
                    catTitle.context.startActivity(editContext)
                } else {
                    val game = Intent(catTitle.context, PlayGameActivity::class.java)
                    game.putExtra("word", categories[position].getOne())
                    catTitle.context.startActivity(game)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}