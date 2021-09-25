package com.example.bootcampWeek4.ui.homeScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampWeek4.databinding.RecyclerViewItemBinding
import com.example.bootcampWeek4.model.Task
import kotlin.coroutines.coroutineContext

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
        holder.binding.isCompleted.text = taskList[position].completed.toString()

        holder.binding.deleteButton.setOnClickListener {
            holder.binding.swipeLayout.animateReset()
            Log.e("Tıklandı delete","$task")
            deleteListener.let {
                deleteListener?.onClickDelete(position)
            }
            notifyItemRemoved(position)
        }
        holder.binding.completeButton.setOnClickListener {
            Log.e("Tıklandı update","$task")
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