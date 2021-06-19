package com.josecmj.rommexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.josecmj.rommexample.databinding.ActivityMainBinding
import com.josecmj.rommexample.db.ListItem


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val itemListViewModel by viewModels<ListItemViewModel>{
        ListItemViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemAdapter = ListAdapter ( { item -> adapterOnClick(item) },
            {item -> adapterOnClickDelete(item)} )


        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = itemAdapter


        itemListViewModel.itemsLiveData.observe(this, {
            it?.let {
                Log.d("ZECA ", "list updated $it")
                itemAdapter.submitList(it)
            }

        })

        binding.btnStart.setOnClickListener {
            itemListViewModel.addItem(binding.txtAddItemName.text.toString(),
                                    binding.addItemPrice.text.toString())
        }
    }

    private fun adapterOnClick(item: ListItem) {
       // val intent = Intent(this, FileDetailActivity()::class.java)
       // intent.putExtra(FILE_ID, file)
       // startActivity(intent)
    }

    private fun adapterOnClickDelete(item: ListItem) {
        itemListViewModel.deleteItem(item)
    }

}