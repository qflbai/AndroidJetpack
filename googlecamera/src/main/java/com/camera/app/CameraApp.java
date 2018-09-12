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

package com.camera.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import com.camera.MediaSaverImpl;
import com.camera.processing.ProcessingServiceManager;
import com.camera.remote.RemoteShutterListener;
import com.camera.session.CaptureSessionManager;
import com.camera.session.CaptureSessionManagerImpl;
import com.camera.session.PlaceholderManager;
import com.camera.session.SessionStorageManager;
import com.camera.session.SessionStorageManagerImpl;
import com.camera.settings.SettingsManager;
import com.camera.util.CameraUtil;
import com.camera.util.RemoteShutterHelper;
import com.camera.util.SessionStatsCollector;
import com.camera.util.UsageStatistics;
import com.qfl.camera2.debug.LogHelper;

/**
 * The Camera application class containing important services and functionality
 * to be used across modules.
 */
public class CameraApp extends Application implements CameraServices {
    private MediaSaver mMediaSaver;
    private CaptureSessionManager mSessionManager;
    private SessionStorageManager mSessionStorageManager;
    private MemoryManagerImpl mMemoryManager;
    private PlaceholderManager mPlaceHolderManager;
    private RemoteShutterListener mRemoteShutterListener;
    private MotionManager mMotionManager;
    private SettingsManager mSettingsManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        LogHelper.initialize(context);

        // It is important that this gets called early in execution before the
        // app has had the opportunity to create any shared preferences.
        UsageStatistics.instance().initialize(this);
        SessionStatsCollector.instance().initialize(this);
        CameraUtil.initialize(this);

        ProcessingServiceManager.initSingleton(context);

        mMediaSaver = new MediaSaverImpl();
        mPlaceHolderManager = new PlaceholderManager(context);
        mSessionStorageManager = SessionStorageManagerImpl.create(this);
        mSessionManager = new CaptureSessionManagerImpl(mMediaSaver, getContentResolver(),
                mPlaceHolderManager, mSessionStorageManager);
        mMemoryManager = MemoryManagerImpl.create(getApplicationContext(), mMediaSaver);
        mRemoteShutterListener = RemoteShutterHelper.create(this);
        mSettingsManager = new SettingsManager(this);

        clearNotifications();

        mMotionManager = new MotionManager(context);
    }

    @Override
    public CaptureSessionManager getCaptureSessionManager() {
        return mSessionManager;
    }

    @Override
    public MemoryManager getMemoryManager() {
        return mMemoryManager;
    }

    @Override
    public MotionManager getMotionManager() {
        return mMotionManager;
    }

    @Override
    @Deprecated
    public MediaSaver getMediaSaver() {
        return mMediaSaver;
    }

    @Override
    public RemoteShutterListener getRemoteShutterListener() {
        return mRemoteShutterListener;
    }

    @Override
    public SettingsManager getSettingsManager() {
        return mSettingsManager;
    }

    /**
     * Clears all notifications. This cleans up notifications that we might have
     * created earlier but remained after a crash.
     */
    private void clearNotifications() {
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancelAll();
        }
    }
}
