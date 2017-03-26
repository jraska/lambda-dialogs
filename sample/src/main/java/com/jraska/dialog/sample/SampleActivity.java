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
import com.jraska.dialog.FieldsDialog;
import com.jraska.dialog.LambdaDialogs;

import java.util.Random;

public class SampleActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    LambdaDialogs.validateEagerly(BuildConfig.DEBUG);
  }

  @OnClick(R.id.fab) void onFabClick() {
    setupBuilder()
        .show();
  }

  private FieldsDialog.Builder<SampleActivity> setupBuilder() {
    return LambdaDialogs.builder(this)
        .icon(android.R.drawable.ic_dialog_alert)
        .message("Test message")
        .title(R.string.app_name)
        .positiveText(android.R.string.ok)
        .positiveMethod(SampleActivity::onDialogPositiveClicked)
        .negativeText(android.R.string.cancel)
        .negativeMethod(SampleActivity::onDialogNegativeClicked)
        .neutralText("Neutral")
        .cancelable(new Random().nextBoolean())
        .neutralMethod(SampleActivity::onDialogNeutralClicked)
        .cancelMethod(SampleActivity::onDialogCancel)
        .dismissMethod(SampleActivity::onDialogDismiss);
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
        .dismissMethod(SampleActivity::onDialogDismiss)
        .method(SampleActivity::createDialogInCorrectActivity) // method called for correct Activity instance
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
    setupBuilder()
        .dialogFactory(new MaterialDialogFactory<>())
        .show();
  }

  void showSnackbar(String message) {
    Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show();
  }
}
