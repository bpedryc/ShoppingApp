package com.haxos.shoppingapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.haxos.shoppingapp.data.models.ShoppingList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ShoppingListDaoTest {

    private lateinit var database: ShoppingDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            ShoppingDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertShoppingListAndGetById() = runBlockingTest {
        val shoppingListDao = database.shoppingListDao()

        val inserted = ShoppingList("Test list", archived = true)
        val insertedId = inserted.id
        shoppingListDao.insertShoppingList(inserted)

        val loaded = shoppingListDao.getShoppingListById(insertedId)

        assertThat(loaded, notNullValue())
        assertThat(loaded.id, `is`(inserted.id))
        assertThat(loaded.archived, `is`(inserted.archived))
        assertThat(loaded.createdTime, `is`(inserted.createdTime))
        assertThat(loaded.name, `is`(inserted.name))
    }

    @Test
    fun insertThreeListsAndGetAll() = runBlockingTest {
        insertThreeShoppingLists()

        val shoppingLists = database.shoppingListDao().getShoppingLists()
        assertThat(shoppingLists.count(), `is`(3))
    }

    @Test
    fun insertAndUpdateAsArchived() = runBlockingTest {
        val initiallyUnarchived = ShoppingList("Unarchived", archived = false)

        val dao = database.shoppingListDao()
        dao.insertShoppingList(initiallyUnarchived)

        dao.updateArchived(initiallyUnarchived.id, true)
        val loadedShoppingList = dao.getShoppingListById(initiallyUnarchived.id)

        assertThat(loadedShoppingList, notNullValue())
        assert(loadedShoppingList.archived)
    }

    @Test
    fun insertListsAndDeleteThem() = runBlockingTest {
        insertThreeShoppingLists()

        val dao = database.shoppingListDao()
        dao.deleteShoppingLists()

        val loadedShoppingLists = dao.getShoppingLists()
        assert(loadedShoppingLists.isEmpty())
    }

    @Test
    fun insertTwoListsAndDeleteOne() = runBlockingTest {
        val toBeKept = ShoppingList("To be kept")
        val toBeDeleted = ShoppingList("To be deleted")

        val dao = database.shoppingListDao()
        dao.insertShoppingList(toBeKept)
        dao.insertShoppingList(toBeDeleted)

        dao.deleteShoppingListById(toBeDeleted.id)

        val loadedShoppingLists = dao.getShoppingLists()
        assertThat(loadedShoppingLists.size, `is`(1))
        assertThat(loadedShoppingLists[0].id, `is`(toBeKept.id))
    }


    private suspend fun insertThreeShoppingLists() {
        val shoppingList1 = ShoppingList("Test1")
        val shoppingList2 = ShoppingList("Test2")
        val shoppingList3 = ShoppingList("Test3")

        val dao = database.shoppingListDao()
        dao.insertShoppingList(shoppingList1)
        dao.insertShoppingList(shoppingList2)
        dao.insertShoppingList(shoppingList3)
    }
}