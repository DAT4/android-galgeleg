package sh.mama.hangman.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import sh.mama.hangman.activities.EditContextActivity
import sh.mama.hangman.activities.GameActivity
import sh.mama.hangman.R
import sh.mama.hangman.models.Category

class CategoryAdapter(
    var categories: List<Category>,
    val edit: Boolean
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHoler>() {
    inner class CategoryViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHoler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHoler(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHoler, position: Int) {
        holder.itemView.apply {
            catTitle.text = categories[position].title
            catCount.text = categories[position].words.size.toString()
            catTitle.setOnClickListener {
                if (edit) {
                    val editContext = Intent(catTitle.context, EditContextActivity::class.java)
                    editContext.putExtra("category", categories[position])
                    catTitle.context.startActivity(editContext)
                } else {
                    val game = Intent(catTitle.context, GameActivity::class.java)
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