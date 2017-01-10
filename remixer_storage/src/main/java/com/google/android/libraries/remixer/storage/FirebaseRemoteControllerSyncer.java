/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.libraries.remixer.storage;

import android.content.Context;
import com.google.android.libraries.remixer.Remixer;
import com.google.android.libraries.remixer.Variable;
import com.google.android.libraries.remixer.sync.LocalValueSyncing;
import com.google.android.libraries.remixer.sync.SynchronizationMechanism;
import java.lang.ref.WeakReference;

/**
 * A {@link SynchronizationMechanism} that syncs up to a firebase Remote Controller.
 *
 * <p>This synchronization mechanism assumes that the local host is the source of truth, so it does
 * very little in terms of conflict resolution.
 */
public class FirebaseRemoteControllerSyncer extends LocalValueSyncing {

  WeakReference<Object> context;

  public FirebaseRemoteControllerSyncer(Context context) {
    super();
  }

  /**
   * Syncs a variable up to the remote controller.
   */
  private void syncVariableToRemoteController (Variable variable) {
    // TODO(miguely): write controller sync logic
  }

  /**
   * Clears all the data in a remote
   */
  private void clearVariablesInRemoteController() {
    // TODO(miguely): write controller sync logic
  }

  @Override
  public void onAddingVariable(Variable variable) {
    super.onAddingVariable(variable);
    syncVariableToRemoteController(variable);
  }

  @Override
  public void onValueChanged(Variable variable) {
    super.onValueChanged(variable);
    syncVariableToRemoteController(variable);
  }

  @Override
  public void onContextChanged(Object currentContext) {
    super.onContextChanged(currentContext);
    if ((context == null && currentContext != null) ||
        (context != null && currentContext != context.get())) {
      context = new WeakReference<Object>(context);
      clearVariablesInRemoteController();
      for (Variable<?> var : Remixer.getInstance().getVariablesWithContext(currentContext)) {
        syncVariableToRemoteController(var);
      }
    }
  }

  @Override
  public void onContextRemoved(Object currentContext) {
    super.onContextRemoved(currentContext);
    if (context != null && context.get() == currentContext) {
      clearVariablesInRemoteController();
    }
  }
}
