/*
 * Copyright 2018 Google LLC. All Rights Reserved.
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
package com.google.ar.sceneform.samples.bestbuyar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import android.view.View;


/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class ProductViewer extends AppCompatActivity {
  private static final String TAG = ProductViewer.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private final int TV1 = 0;
  private final int TV2 = 1;


  private ArFragment arFragment;
  private ModelRenderable tvRenderable;


    @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  Intent intent = getIntent();
  int DIMENSIONS = intent.getIntExtra("com.google.ar.sceneform.samples.bestbuyar",0);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

    setContentView(R.layout.activity_ux);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.flattv)
        .build()
        .thenAccept(renderable -> tvRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load tv renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (tvRenderable == null) {
            return;
          }

//            Camera camera = arFragment.getArSceneView().getScene().getCamera();
//            camera.setLocalRotation(Quaternion.axisAngle(Vector3.right(), -30.0f));

          // Create the Anchor.
          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());

          // Create the transformable tv and add it to the anchor.
          TransformableNode tv = new TransformableNode(arFragment.getTransformationSystem());
            float scaling = 0.0038f * DIMENSIONS;
//            tv.setLocalScale(new Vector3(0.0038f * 1, 0.0038f * 1, 0.0038f * 1));
//            tv.getScaleController().setMaxScale(scaling);
//            tv.getScaleController().setMinScale(scaling);
//
//            if (plane.getType() == Plane.Type.VERTICAL) {
//
//                Vector3 anchor2 = anchorNode.getLeft();
//                tv.setLookDirection(Vector3.left(), anchor2);
//
//                Vector3 anchor1 = anchorNode.getDown();
//                tv.setLookDirection(Vector3.down(), anchor1);
//            }

          tv.setParent(anchorNode);
          tv.setRenderable(tvRenderable);

          tv.getScaleController().setMinScale(scaling);
          tv.getScaleController().setMaxScale(scaling+0.0001f);
          tv.select();
        });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }
}
