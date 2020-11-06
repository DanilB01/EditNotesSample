package ru.tsu.sampleproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_note.view.*


private const val TYPE_CATEGORY : Int = 0
private const val TYPE_NOTE : Int = 1

class RecyclerViewAdapter(var dates: Array<Dates>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categoryModel: Dates){
            itemView.categoryTextView.text = categoryModel.category?.theme
        }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(noteModel: Dates){
            itemView.titleText.text = noteModel.note?.title
            itemView.descriptionText.text = noteModel.note?.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_CATEGORY){
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent,
                false
            )
            return CategoryViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
            return NoteViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(dates[position].type == 0) {
            TYPE_CATEGORY
        } else{
            TYPE_NOTE
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == TYPE_CATEGORY){
            (holder as CategoryViewHolder).bind(dates[position])
        } else {
            (holder as NoteViewHolder).bind(dates[position])
        }
    }
}