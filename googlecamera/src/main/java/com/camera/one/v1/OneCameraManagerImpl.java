/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.camera.one.v1;

import android.os.Handler;

import com.camera.one.OneCamera;
import com.camera.one.OneCameraManager;
import com.camera.util.Size;


/**
 * The {@link OneCameraManager} implementation on top of the Camera 1 API
 * portability layer.
 */
public class OneCameraManagerImpl extends OneCameraManager {

    @Override
    public void open(OneCamera.Facing facing, boolean enableHdr, Size pictureSize, OneCamera.OpenCallback callback, Handler handler) {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean hasCameraFacing(OneCamera.Facing facing) {
        throw new RuntimeException("Not implemented yet.");
    }
}
