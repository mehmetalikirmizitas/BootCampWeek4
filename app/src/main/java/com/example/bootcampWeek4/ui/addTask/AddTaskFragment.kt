package com.example.bootcampWeek4.ui.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.databinding.BottomSheetBinding
import com.example.bootcampWeek4.model.Task
import com.example.bootcampWeek4.model.TaskRequest
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.utils.getString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.app.Activity
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager
import com.example.bootcampWeek4.utils.hideKeyboard


class AddTaskFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!
    private var updateListener : IAddTask? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {
        binding.addTaskButton.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        ServiceConnector.restInterface.addNewTask(TaskRequest(binding.taskEditText.getString()))
            .enqueue(object : BaseCallBack<Task>(){
                override fun onSuccess(data: Task) {
                    super.onSuccess(data)
                    binding.taskEditText.text?.clear()
                    updateListener.let {
                        updateListener?.updateItemList(data)
                    }
                    hideKeyboard()
                    dismiss()
                }
            })
    }
    fun addListener(listener: IAddTask){
        this.updateListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
