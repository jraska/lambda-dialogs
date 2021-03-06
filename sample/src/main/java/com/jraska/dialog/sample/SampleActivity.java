package com.jraska.dialog.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.dialog.LambdaDialogs;

public final class SampleActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
  }

  @OnClick(R.id.fab) void onFabClick() {
    LambdaDialogs.delegate(this)
        .parameter(setupBuilder().build())
        .method(new AlertDialogFactory<>())
        .dismissMethod(SampleActivity::onDialogDismiss)
        .cancelMethod(SampleActivity::onDialogCancel)
        .show();
  }

  private DialogFields.Builder setupBuilder() {
    return DialogFields.builder(this)
        .iconRes(android.R.drawable.ic_dialog_alert)
        .message("Test message")
        .title(getString(R.string.app_name))
        .positiveText("OK")
        .positiveMethod(SampleActivity::onDialogPositiveClicked)
        .negativeText("Cancel")
        .negativeMethod(SampleActivity::onDialogNegativeClicked)
        .neutralText("Neutral")
        .neutralMethod(SampleActivity::onDialogNeutralClicked);
  }

  void onDialogPositiveClicked() {
    showSnackbar("Positive clicked");
  }

  void onDialogNeutralClicked() {
    showSnackbar("Neutral clicked");
  }

  void onDialogNegativeClicked() {
    showSnackbar("Negative clicked");
  }

  void onDialogDismiss() {
    Toast.makeText(this, "Dismissed", Toast.LENGTH_SHORT).show();
  }

  void onDialogCancel() {
    showSnackbar("Canceled");
  }

  @OnClick(R.id.delegate_dialog)
  void showDelegateDialog() {
    LambdaDialogs.delegate(this)
        .dismissMethod(SampleActivity::onDialogDismiss) // method called for correct Activity instance
        .method(SampleActivity::createDialogInCorrectActivity) // method called for correct Activity instance
        .cancelMethod(SampleActivity::onDialogCancel) // method called for correct Activity instance
        .show();
  }

  Dialog createDialogInCorrectActivity() {
    return new AlertDialog.Builder(this)
        .setTitle("static title")
        .setMessage("Dialog message")

        // We can set listeners directly when we have delegate dialog
        .setNegativeButton("Cancel", (dialog, which) -> onDialogNegativeClicked())
        .show();
  }

  @OnClick(R.id.delegate_dialog_with_parameter)
  void showDelegateDialogWithParameter() {
    LambdaDialogs.delegate(this)
        .parameter("Parameter title")
        .cancelable(true)
        .cancelMethod(SampleActivity::onDialogCancel) // methdd referecne, method will be caled on proper Activity
        .method(SampleActivity::createDialogWithStringParameter) // method called for correct Activity instance
        .show();
  }

  Dialog createDialogWithStringParameter(String title) {
    return new AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage("Dialog message")
        .setIcon(android.R.drawable.ic_dialog_info)

        // We can set listeners directly when we have delegate dialog
        .setPositiveButton("Ok", (dialog, which) -> onDialogPositiveClicked())
        .show();
  }

  @OnClick(R.id.material_dialog)
  void showMaterialDialog() {
    LambdaDialogs.delegate(this)
        .parameter(setupBuilder().build())
        .method(new MaterialDialogFactory<>())
        .dismissMethod(SampleActivity::onDialogDismiss)
        .cancelMethod(SampleActivity::onDialogCancel)
        .show();
  }

  void showSnackbar(String message) {
    Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show();
  }
}
