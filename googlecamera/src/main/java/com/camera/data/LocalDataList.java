/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camera.data;

import android.net.Uri;


import com.camera.debug.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Fast access data structure for an ordered LocalData list.
 */
public class LocalDataList {
    /**
     * We use this as a way to compare a Uri to LocalData instances inside a
     * LinkedList. A linked list in indexOf does a other.equals(get(i)).
     */
    private static class UriWrapper {
        private final Uri mUri;

        public UriWrapper(Uri uri) {
            mUri = uri;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof LocalData)) {
                return false;
            }
            return mUri.equals(((LocalData) o).getUri());
        }
    }

    private static final Log.Tag TAG = new Log.Tag("LocalDataList");
    private final LinkedList<LocalData> mList = new LinkedList<LocalData>();
    private final HashMap<Uri, LocalData> mUriMap = new HashMap<Uri, LocalData>();

    public LocalData get(int index) {
        return mList.get(index);
    }

    /**
     * Removes the item at the given index.
     *
     * @param index the item to delete
     * @return If the item was found and deleted, it is returned. If the item
     *         was not found, null is returned.
     */
    public synchronized LocalData remove(int index) {
        try {
            LocalData removedItem = mList.remove(index);
            mUriMap.remove(removedItem);
            return removedItem;
        } catch (IndexOutOfBoundsException ex) {
            Log.w(TAG, "Could not remove item. Not found: " + index, ex);
            return null;
        }
    }

    public LocalData get(Uri uri) {
        return mUriMap.get(uri);
    }

    public void set(int pos, LocalData data) {
        mList.set(pos, data);
        mUriMap.put(data.getUri(), data);
    }

    public void add(LocalData data) {
        mList.add(data);
        mUriMap.put(data.getUri(), data);
    }

    public void add(int pos, LocalData data) {
        mList.add(pos, data);
        mUriMap.put(data.getUri(), data);
    }

    public void addAll(List<LocalData> localDataList) {
        for (LocalData localData : localDataList) {
            add(localData);
        }
    }

    public int size() {
        return mList.size();
    }

    public void sort(Comparator<LocalData> comparator) {
        Collections.sort(mList, comparator);
    }

    /**
     * This implementation routes through to LinkedList.indexOf, so performs in
     * O(n) but has a fast exit path for when the uri is not contained in the
     * list, and immediately returns -1;
     */
    public int indexOf(Uri uri) {
        if (!mUriMap.containsKey(uri)) {
            return -1;
        }
        return mList.indexOf(new UriWrapper(uri));
    }
}
