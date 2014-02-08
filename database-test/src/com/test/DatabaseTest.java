package com.test;

import com.dd.database.DatabaseManager;
import com.dd.database.QueryExecutor;
import com.dd.utils.L;

import junit.framework.Assert;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        L.LOG_TAG = "database";
        DatabaseManager.initializeInstance(new DatabaseHelper(getContext()));

        L.v("Deleting all users");

        DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                new UserDAO(database, getContext()).deleteAll();
            }
        });
    }

    public void testInsertUserList() {
        L.v("testInsertUserList");

        DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                UserDAO dao = new UserDAO(database, getContext());
                dao.insert(generateDummyUserList(10));

                List<User> listFromDB = dao.selectAll();
                Assert.assertTrue("User list is empty", !listFromDB.isEmpty());
                Assert.assertTrue("User list size is wrong", listFromDB.size() == 10);
            }
        });
    }

    public void testInsertUser() {
        L.v("testInsertUser");

        DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                User user = new User();
                user.setAge(100);
                user.setName("Jon Doe");

                UserDAO dao = new UserDAO(database, getContext());
                dao.insert(user);

                List<User> listFromDB = dao.selectAll();
                Assert.assertTrue("User list is empty", !listFromDB.isEmpty());
                Assert.assertTrue("User list size is wrong", listFromDB.size() == 1);

                User userFromDB = listFromDB.get(0);

                Assert.assertTrue("Incorrect data",
                        user.getName().contentEquals(userFromDB.getName()));
                Assert.assertTrue("Incorrect data", user.getAge() == userFromDB.getAge());
            }
        });

    }

    public void testUpdateUser() {
        L.v("testUpdateUser");

        DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                UserDAO dao = new UserDAO(database, getContext());
                User user = new User();
                user.setAge(18);
                user.setName("Jon Doe");

                dao.insert(user);

                dao.updateNameByAge("Will Smith", 18);

                List<User> listFromDB = dao.selectByAge(18);
                Assert.assertTrue("User list is empty", !listFromDB.isEmpty());

                User userFromDB = listFromDB.get(0);

                Assert.assertTrue("User is null", userFromDB != null);
                Assert.assertTrue("User age is wrong", userFromDB.getAge() == 18);
                Assert.assertTrue("User name is wrong", userFromDB.getName().equals("Will Smith"));
            }
        });
    }

    private List<User> generateDummyUserList(int itemsCount) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < itemsCount; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("Jon Doe");
            userList.add(user);
        }
        return userList;
    }

    private int totalTasks = 100;
    private AtomicInteger tasksAlive = new AtomicInteger(totalTasks);

    public void testConcurrentAccess() {
        L.v("testConcurrentAccess");

        CountDownLatch signal = new CountDownLatch(1);

        for (int i = 0; i < totalTasks; i++) {
            spamNewThread(signal);
        }

        try {
            signal.await();
        } catch (InterruptedException e) {
            L.d(e.getMessage(), e);
        }
    }

    private void spamNewThread(final CountDownLatch signal) {
        DatabaseManager.getInstance().executeQueryTask(new QueryExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                UserDAO dao = new UserDAO(database, getContext());
                int usersCount = 10;
                dao.insert(generateDummyUserList(usersCount));

                L.v("Task #" + tasksAlive.get() + " is finished");

                boolean allTasksFinished = tasksAlive.decrementAndGet() == 0;
                if (allTasksFinished) {
                    int totalUsers = usersCount * totalTasks;
                    List<User> listFromDB = dao.selectAll();
                    Assert.assertTrue("User list is empty", !listFromDB.isEmpty());
                    Assert.assertTrue("User list size is wrong", listFromDB.size() == totalUsers);
                    signal.countDown();
                }
            }
        });
    }

}
