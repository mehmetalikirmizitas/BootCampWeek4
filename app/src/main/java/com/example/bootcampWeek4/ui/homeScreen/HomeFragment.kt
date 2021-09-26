package com.example.bootcampWeek4.ui.homeScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampWeek4.R
import com.example.bootcampWeek4.base.BaseCallBack
import com.example.bootcampWeek4.databinding.FragmentHomeBinding
import com.example.bootcampWeek4.model.CompletedTaskRequest
import com.example.bootcampWeek4.model.Task
import com.example.bootcampWeek4.response.TaskResponse
import com.example.bootcampWeek4.service.ServiceConnector
import com.example.bootcampWeek4.ui.AddTaskFragment
import com.example.bootcampWeek4.utils.gone
import com.example.bootcampWeek4.utils.toast
import com.example.bootcampWeek4.utils.visible

class HomeFragment : Fragment(), ITaskOnClickDelete, ITaskOnClickComplete {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val limit = 10
    var skip = 0

    private var homeAdapter: HomeAdapter? = null
    private lateinit var taskList: ArrayList<Task>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    //binding arguments
    private fun initViews() {
        taskList = arrayListOf()
        binding.taskRecyclerView.addItemDecoration(DividerItemDecoration(context, 0))
        binding.taskRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getAllTask(limit, skip)
        fabOnClickListener()
        binding.taskRecyclerView.gone()
        recyclerViewOnScrollListener()
    }

    //Scroll Listener for pagination
    private fun recyclerViewOnScrollListener() {

        binding.taskRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.taskRecyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE && taskList.size > 0
                ) {
                    getAllTask(limit, skip)
                }
            }
        })
    }

    //adding task with floating action button
    private fun fabOnClickListener() {

        val addTaskFragment = AddTaskFragment()

        addTaskFragment.setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.ThemeOverlay_Demo_BottomSheetDialog
        )

        binding.fabHomeFragment.setOnClickListener {
            addTaskFragment.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
        }
    }

    //getAllTask with pagination
    private fun getAllTask(limit: Int, skip: Int) {

        ServiceConnector.restInterface.getTaskByPagination(limit, skip)
            .enqueue(object : BaseCallBack<TaskResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(data: TaskResponse) {
                    super.onSuccess(data)

                    if (data.count == 0 && homeAdapter != null)
                        toast("Can't scroll anymore you are in end of list")

                    if (data.count == 0 && homeAdapter == null) {
                        binding.noTaskString.visible()
                        binding.taskRecyclerView.gone()
                    }
                    taskList.addAll(data.task)

                    if (homeAdapter == null && data.count != 0) {
                        homeAdapter = HomeAdapter()
                        homeAdapter!!.setData(taskList)
                        binding.taskRecyclerView.adapter = homeAdapter

                    } else {
                        binding.taskRecyclerView.adapter?.notifyDataSetChanged()
                    }
                    this@HomeFragment.skip = taskList.size
                    homeAdapter?.addListener(this@HomeFragment, this@HomeFragment)
                    binding.taskRecyclerView.visible()
                    binding.progressCircularHomeFragment.gone()
                }

                override fun onFailure() {
                    super.onFailure()
                    toast("GetAllTask is not running")
                }
            })
    }

    private fun deleteTask(position: Int) {
        homeAdapter = HomeAdapter()
        taskList.size
        ServiceConnector.restInterface.deleteTaskById(taskList[position]._id)
            .enqueue(object : BaseCallBack<Task>() {
                override fun onSuccess(data: Task) {
                    super.onSuccess(data)
                    taskList.remove(taskList[position])
                    homeAdapter?.setData(taskList)
                    homeAdapter?.notifyItemRemoved(position)
                }
            })
        getAllTask(0, skip)
    }

    private fun completeTask(position: Int) {

        homeAdapter = HomeAdapter()

        val isCompleted = !taskList[position].completed


        taskList[position].completed = !taskList[position].completed
        ServiceConnector.restInterface.updateTaskById(
            taskList[position]._id,
            CompletedTaskRequest(isCompleted)
        ).enqueue(object : BaseCallBack<Task>() {
            override fun onSuccess(data: Task) {
                super.onSuccess(data)
                homeAdapter?.setData(taskList)
            }

            override fun onFailure() {
                super.onFailure()
                Log.e("updated Failed", " ")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickDelete(position: Int) {
        deleteTask(position)
    }

    override fun onClickComplete(position: Int) {
        completeTask(position)
    }

}