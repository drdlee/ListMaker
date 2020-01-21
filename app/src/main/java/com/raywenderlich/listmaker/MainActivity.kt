package com.raywenderlich.listmaker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ListSelectionFragment.OnFragmentInteractionListener {

    private var listSelectionFragment: ListSelectionFragment = ListSelectionFragment.newInstance()
    private var fragmentContainer: FrameLayout? = null
    private var largeScreen = false
    private var listFragment: ListDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        listSelectionFragment = supportFragmentManager.findFragmentById(R.id.list_selection_fragment) as ListSelectionFragment
        fragmentContainer = findViewById(R.id.fragment_container)
        largeScreen = fragmentContainer != null

        fab.setOnClickListener {
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType =
            InputType.TYPE_CLASS_TEXT // this is to tell android to show a text-based keyboard (instead of numpad keyboard)

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString()) // Create new lists
            listSelectionFragment.addList(list)

            dialog.dismiss()
            showListDetail(list)
        }

        builder.create().show()
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                val task = taskEditText.text.toString()
                listFragment?.addTask(task)
                dialog.dismiss()
            }
            .create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showListDetail(list: TaskList) {
        if (!largeScreen) {
            val listDetailintent = Intent(this, ListDetailActivity::class.java)
            listDetailintent.putExtra(INTENT_LIST_KEY, list)
            startActivityForResult(listDetailintent, LIST_DETAIL_REQUEST_CODE)
        }
        else {
            title = list.name
            listFragment = ListDetailFragment.newInstance(list)
            listFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it, getString(R.string.list_fragment_tag))
                    .addToBackStack(null)
                    .commit()
            }
            fab.setOnClickListener{
                showCreateTaskDialog()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                listSelectionFragment.saveList(data.getParcelableExtra(INTENT_LIST_KEY) as TaskList)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title = resources.getString(R.string.app_name)
        listFragment?.list?.let {
            listSelectionFragment.listDataManager.saveList(it)
        }
        listFragment?.let {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
            listFragment = null
        }
        fab.setOnClickListener {
            showCreateListDialog()
        }
    }

    override fun onFragmentInteraction(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }
}
