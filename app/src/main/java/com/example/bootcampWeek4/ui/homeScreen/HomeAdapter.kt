package com.example.bootcampWeek4.ui.homeScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampWeek4.R
import com.example.bootcampWeek4.databinding.RecyclerViewItemBinding
import com.example.bootcampWeek4.model.Task

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var taskList: ArrayList<Task>
    private var deleteListener: ITaskOnClickDelete? = null
    private var updateListener: ITaskOnClickComplete? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task: Task = taskList[position]
        holder.binding.taskDescription.text = task.description

        if (task.completed)
            holder.binding.isCompleted.setImageResource(R.drawable.ic_completed)
        else
            holder.binding.isCompleted.setImageResource(R.drawable.ic_not_completed)

        //Delete task
        holder.binding.deleteButton.setOnClickListener {
            holder.binding.swipeLayout.animateReset()
            deleteListener.let {
                deleteListener?.onClickDelete(position)
            }
            notifyItemRemoved(position)
        }
        //mark task as complete
        holder.binding.completeButton.setOnClickListener {
            updateListener.let {
                updateListener?.onClickComplete(position)
            }
            notifyItemChanged(position)
            holder.binding.swipeLayout.animateReset()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<Task>) {
        this.taskList = list
        notifyDataSetChanged()
    }

    fun addListener(deleteListener: ITaskOnClickDelete, completeListener: ITaskOnClickComplete) {
        this.deleteListener = deleteListener
        this.updateListener = completeListener
    }

    override fun getItemCount(): Int = taskList.size

    inner class ViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}