package com.raywenderlich.listmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListDetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var listItemsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
        title = list.name

        listItemsRecyclerView = findViewById(R.id.list_items_recyclerview)
        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list)
        listItemsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
