### Sample of concurrent safe access to Android SQLite Database

[Concurrent Database Access article][1]

Code

```java
// initialize Database manager Singleton
DatabaseManager.initializeInstance(new DatabaseHelper(getContext()));

// execute query on current thread
DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
    @Override
    public void run(SQLiteDatabase database) {
        new UserDAO(database, getContext()).deleteAll(); // your class
    }
});

// execute query on separate current thread
DatabaseManager.getInstance().executeQueryTask(new QueryExecutor() {
    @Override
    public void run(SQLiteDatabase database) {
        new UserDAO(database, getContext()).deleteAll(); // your class
    }
});
```

  [1]: https://github.com/dmytrodanylyk/dmytrodanylyk/blob/gh-pages/articles/Concurrent%20Database%20Access.md
