package info.stefancojocaru.wikisearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.stefancojocaru.wikisearch.R
import info.stefancojocaru.wikisearch.model.Model
import kotlinx.android.synthetic.main.card_item.view.*

class ArticleAdapter(val items : ArrayList<Model.Article>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTitle?.text = items.get(position).title
        holder.cardText?.text = items.get(position).snippet
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val cardTitle = view.card_title
    val cardText = view.card_text
}