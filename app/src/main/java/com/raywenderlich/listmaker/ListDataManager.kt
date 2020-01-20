package com.raywenderlich.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context : Context) {
    fun saveList(list: TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet()) // why the list use HashSet because sharedPreference require it, and it holds unique values
        sharedPreferences.apply()
    }

    fun readLists() : ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferencesContents) {
            val itemHashSet = ArrayList(taskList.value as HashSet<String>) // convert back the HashSet back to ArrayList<String>
            val list = TaskList(taskList.key, itemHashSet)
            taskLists.add(list)
        }

        return taskLists

    }
}