package com.example.recipebox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipebox.databinding.HealthyItemBinding
import com.example.recipebox.databinding.ItemIngredientBinding
import com.example.recipebox.databinding.ItemIngredientDetailBinding
import com.example.recipebox.databinding.PopularItemBinding
import com.example.recipebox.databinding.QuickItemBinding
import com.example.recipebox.databinding.RecommendedItemBinding
import com.example.recipebox.databinding.RoomItemBinding
import com.example.recipebox.databinding.SearchHomeItemBinding
import com.example.recipebox.databinding.VeganItemBinding
import com.example.recipebox.room.SavedRecipe


class Adapter(
    private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val binding: RecommendedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecommendedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        holder.binding.recipeTitle.text = item.title
        holder.binding.recipeTime.text = "${item.readyInMinutes} minutes"
        holder.itemView.setOnClickListener { onItemClick(item)}

        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}


class SecondAdapter(private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit): RecyclerView.Adapter<SecondAdapter.ViewHolder>() {
    class ViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.recipeTitle.text = item.title
        holder.binding.recipeTime.text = "${item.readyInMinutes} minutes"
        holder.itemView.setOnClickListener { onItemClick(item)}
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)
    }
    override fun getItemCount(): Int {
        return data.size
    }

}


class ThirdAdapter(private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit): RecyclerView.Adapter<ThirdAdapter.ViewHolder>(){
class ViewHolder(val binding: HealthyItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HealthyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = data[position]
    holder.binding.recipeTitle.text = item.title
    holder.binding.recipeTime.text = "${item.readyInMinutes} minutes"
    holder.itemView.setOnClickListener { onItemClick(item)}
    Glide.with(holder.itemView.context)
        .load(item.image)
        .placeholder(R.drawable.salad_png_def)
        .error(R.drawable.salad_png_def)
        .fallback(R.drawable.salad_png_def)
        .into(holder.binding.recipeImage)

}
    override fun getItemCount(): Int {
        return data.size
    }

}


class FourthAdapter(private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit): RecyclerView.Adapter<FourthAdapter.ViewHolder>(){
    class ViewHolder(val binding: QuickItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuickItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.recipeTitle.text = item.title
        holder.binding.recipeTime.text = "${item.readyInMinutes}"
        holder.itemView.setOnClickListener { onItemClick(item)}
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)
    }
    override fun getItemCount(): Int {
        return data.size
    }

}



class FivesAdapter(private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit):
    RecyclerView.Adapter<FivesAdapter.ViewHolder>() {
    class ViewHolder(val binding: VeganItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VeganItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.recipeTitle.text = item.title
        holder.binding.recipeTime.text = "${item.readyInMinutes}"
        holder.itemView.setOnClickListener { onItemClick(item) }
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    }

class HomeSearchAdapter(private var data: List<Recipe>,private val onItemClick: (Recipe) -> Unit):
    RecyclerView.Adapter<HomeSearchAdapter.ViewHolder>(){

        class ViewHolder(val binding: SearchHomeItemBinding) :
                RecyclerView.ViewHolder(binding.root)

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = SearchHomeItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
    return ViewHolder(binding)
}
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.recipeTitle.text = item.title
        holder.itemView.setOnClickListener { onItemClick(item) }
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    }

class SavedRoom(
    private var data: MutableList<SavedRecipe>,
    private val onDeleteClick: (SavedRecipe) -> Unit,
    private val onItemClick: (SavedRecipe) -> Unit
) : RecyclerView.Adapter<SavedRoom.ViewHolder>() {

    class ViewHolder(val binding: RoomItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RoomItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        holder.binding.recipeTitle.text = item.title
        holder.binding.recipeTime.text = "${item.readyInMinutes} min"
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.recipeImage)


        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.binding.btnDelete.setOnClickListener {

            val index = holder.adapterPosition
            if (index != RecyclerView.NO_POSITION) {

                onDeleteClick(item)

                data.removeAt(index)
                notifyItemRemoved(index)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

class VegAdapter(
    private val items: List<Ingredients>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<VegAdapter.VH>() {

    private val selected = mutableSetOf<String>()

    inner class VH(val binding: ItemIngredientBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = items[position]

        holder.binding.tvIngredient.text = item.name
        holder.binding.imgIngredient.setImageResource(item.icon)

        if (selected.contains(item.name)) {
            holder.binding.cardIngredient.strokeWidth = 6
        } else {
            holder.binding.cardIngredient.strokeWidth = 0
        }

        holder.binding.root.setOnClickListener {

            if (selected.contains(item.name)) {
                selected.remove(item.name)
            } else {
                selected.add(item.name)
            }

            onClick(item.name)

            notifyItemChanged(position)
        }
    }
}

class DetailIngredientAdapter(
    private val ingredients: List<Ingredient>
) : RecyclerView.Adapter<DetailIngredientAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemIngredientDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = ingredients[position]

        holder.binding.tvIngredientName.text = item.name
        holder.binding.tvIngredientAmount.text = item.original

        Glide.with(holder.itemView)
            .load("https://img.spoonacular.com/ingredients_100x100/${item.image}")
            .fitCenter()
            .placeholder(R.drawable.salad_png_def)
            .error(R.drawable.salad_png_def)
            .fallback(R.drawable.salad_png_def)
            .into(holder.binding.imgIngredient)
    }

    override fun getItemCount(): Int = ingredients.size
}







