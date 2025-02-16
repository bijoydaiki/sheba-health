// Generated by view binder compiler. Do not edit!
package com.riad.shebahealthcheck.databinding;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.riad.shebahealthcheck.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDiabetesProcessBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ProgressBar O2PB;

  @NonNull
  public final SurfaceView preview;

  private ActivityDiabetesProcessBinding(@NonNull LinearLayout rootView, @NonNull ProgressBar O2PB,
      @NonNull SurfaceView preview) {
    this.rootView = rootView;
    this.O2PB = O2PB;
    this.preview = preview;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDiabetesProcessBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDiabetesProcessBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_diabetes_process, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDiabetesProcessBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.O2PB;
      ProgressBar O2PB = ViewBindings.findChildViewById(rootView, id);
      if (O2PB == null) {
        break missingId;
      }

      id = R.id.preview;
      SurfaceView preview = ViewBindings.findChildViewById(rootView, id);
      if (preview == null) {
        break missingId;
      }

      return new ActivityDiabetesProcessBinding((LinearLayout) rootView, O2PB, preview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
