package com.example.tmdb.adapters.trending

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.views.TrendingMovieCrewDetailsActivity
import com.example.tmdb.databinding.ItemListTrendingMovieCrewLayoutBinding
import com.example.tmdb.models.moviecredits.MovieCrew
import com.example.tmdb.utils.Credentials
import kotlin.collections.ArrayList

class RecyclerViewListTrendingMovieCrewAdapter(private var context: Context,
                                               private var arrayListTrendingMovieCrew: ArrayList<MovieCrew>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListTrendingMovieCrewVH(ItemListTrendingMovieCrewLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ListTrendingMovieCrewVH){
            var movieCrew:MovieCrew = arrayListTrendingMovieCrew!![position]
            holder.itemListTrendingMovieCrewLayoutBinding.textViewListCrewNameTM.text = movieCrew.name
            if(movieCrew.profilePath?.isNotEmpty() == true){
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CREW + movieCrew.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemListTrendingMovieCrewLayoutBinding.imageViewListCrewTM)
            }else{
                if(movieCrew.gender == 2){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCrewLayoutBinding.imageViewListCrewTM)
                }
                if(movieCrew.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCrewLayoutBinding.imageViewListCrewTM)
                }
                if (movieCrew.gender == 0 || movieCrew.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCrewLayoutBinding.imageViewListCrewTM)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, TrendingMovieCrewDetailsActivity::class.java)
                intent.putExtra("Trending movie list crew name",movieCrew.name)
                intent.putExtra("Trending movie list crew gender",movieCrew.gender)
                intent.putExtra("Trending movie list crew job",movieCrew.job)
                intent.putExtra("Trending movie list crew known for department",movieCrew.knownForDepartment)
                intent.putExtra("Trending movie list crew profile path",movieCrew.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListTrendingMovieCrew?.size!!
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class ListTrendingMovieCrewVH(var itemListTrendingMovieCrewLayoutBinding: ItemListTrendingMovieCrewLayoutBinding):
    RecyclerView.ViewHolder(itemListTrendingMovieCrewLayoutBinding.root)




//    override fun getFilter(): Filter {
//        return object : Filter(){
//            override fun performFiltering(p0: CharSequence?): FilterResults {
//                var filterResults = FilterResults()
//                if(p0?.isEmpty() == true){
//                    filterResults.count = filterArrayListTrendingMovieCrew!!.size
//                    filterResults.values = filterArrayListTrendingMovieCrew
//                }else {
//                    var keyWord = p0.toString().toLowerCase(Locale.getDefault())
//                    var resultData :ArrayList<MovieCrew> = arrayListOf()
//                    filterArrayListTrendingMovieCrew?.forEach(){
//                        if(it.name.toString().toLowerCase(Locale.getDefault()).contains(keyWord)){
//                            resultData.add(it)
//                        }
//                    }
//                    filterResults.count=resultData.size
//                    filterResults.values = resultData
//                }
//                return filterResults
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//                filterArrayListTrendingMovieCrew = p1?.values as ArrayList<MovieCrew>?
//                notifyDataSetChanged()
//            }
//
//        }
//    }
}