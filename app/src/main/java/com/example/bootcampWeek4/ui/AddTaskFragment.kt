package com.example.bootcampWeek4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bootcampWeek4.R
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.databinding.BottomSheetBinding
import com.example.bootcampWeek4.model.Task
import com.example.bootcampWeek4.model.TaskRequest
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.ui.homeScreen.HomeFragment
import com.example.bootcampWeek4.utils.getString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.bootcampWeek4.utils.hideKeyboard

class AddTaskFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

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

    //Add new Item function
    private fun addTask() {
        ServiceConnector.restInterface.addNewTask(TaskRequest(binding.taskEditText.getString()))
            .enqueue(object : BaseCallBack<Task>(){
                override fun onSuccess(data: Task) {
                    super.onSuccess(data)
                    binding.taskEditText.text?.clear()
                    hideKeyboard()
                    dismiss()
                    findNavController().navigate(R.id.action_homeFragment_self)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}