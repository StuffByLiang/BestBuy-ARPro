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
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class MultipleProductViewer extends AppCompatActivity {
  private static final String TAG = MultipleProductViewer.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private final int TV1 = 0;
  private final int TV2 = 1;


  private ArFragment arFragment;
  private ModelRenderable tvRenderable;
    private ModelRenderable tv2Renderable;

  private int tvType = 0;

    private Button button1;
    private Button button2;

    public void toggleTv1(View view) {
        tvType = TV1;
        button1.setBackgroundColor(Color.YELLOW);
        button2.setBackgroundColor(Color.BLUE);
    }

    public void toggleTv2(View view) {
        tvType = TV2;
        button2.setBackgroundColor(Color.YELLOW);
        button1.setBackgroundColor(Color.BLUE);
    }

    private void createTv(HitResult hitResult) {
        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable tv and add it to the anchor.
        TransformableNode tv = new TransformableNode(arFragment.getTransformationSystem());
        tv.setParent(anchorNode);
        float scaling=1;
        switch (tvType) {
            case TV1:
                scaling = 0.0038f * 90;
                tv.setRenderable(tvRenderable);
                break;
            case TV2:
                scaling = 0.0022f * 32;
                tv.setRenderable(tv2Renderable);
        }

        tv.getScaleController().setMinScale(scaling);
        tv.getScaleController().setMaxScale(scaling+0.0001f);
        tv.select();
    }

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

    setContentView(R.layout.activity_multiple);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
    button1 = findViewById(R.id.tv1);
    button2 = findViewById(R.id.tv2);

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

    ModelRenderable.builder()
        .setSource(this, R.raw.tv2)
        .build()
        .thenAccept(renderable -> tv2Renderable = renderable)
        .exceptionally(
                throwable -> {
                    Toast toast =
                            Toast.makeText(this, "Unable to load tv2 renderable", Toast.LENGTH_LONG);
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

            createTv(hitResult);
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
