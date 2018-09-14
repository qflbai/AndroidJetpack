// IBookManager.aidl
package com.qflbai.jetpack.testdemo.aidl;
import com.qflbai.jetpack.testdemo.aidl.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     List<Book> getBookList();
     void addBook(in Book book);
}
