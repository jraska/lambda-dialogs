package com.jraska.dialog.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.dialog.LambdaDialogs;

public class SampleActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_sample, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_delegate_dialog:
        showDelegateDialog();
        return true;

      case R.id.action_delegate_dialog_with_parameter:
        showDelegateDialogWithParameter();
        return true;

      case R.id.action_delegate_dialog_with_method:
        showDelegateDialogWithMethod();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @OnClick(R.id.fab) void onFabClick() {
    LambdaDialogs.builder(this)
        .validateEagerly(BuildConfig.DEBUG)
        .icon(android.R.drawable.ic_dialog_alert)
        .message("Test message")
        .title(R.string.app_name)
        .positiveText(android.R.string.ok)
        .positiveMethod(SampleActivity::onDialogOkClicked)
        .negativeText(android.R.string.cancel)
        .negativeMethod(SampleActivity::onDialogCancelClicked)
        .neutralText("Neutral")
        .neutralMethod(SampleActivity::onDialogNeutralClicked)
        .show();
  }

  void onDialogOkClicked() {
    showSnackbar("Ok clicked");
  }

  void onDialogNeutralClicked() {
    showSnackbar("Neutral clicked");
  }

  void onDialogCancelClicked() {
    showSnackbar("Cancel clicked");
  }

  private void showDelegateDialog() {
    LambdaDialogs.delegate(this)
        .method(SampleActivity::createDialog)
        .validateEagerly(BuildConfig.DEBUG)
        .show();
  }

  private void showDelegateDialogWithParameter() {
    LambdaDialogs.delegate(this)
        .parameter("Parameter title")
        .method(SampleActivity::createDialog)
        .validateEagerly(BuildConfig.DEBUG)
        .show();
  }

  private void showDelegateDialogWithMethod() {
    LambdaDialogs.delegate(this, SampleActivity::createDialog)
        .validateEagerly(BuildConfig.DEBUG)
        .show();
  }

  Dialog createDialog() {
    return new AlertDialog.Builder(this)
        .setTitle("static title")
        .show();
  }

  Dialog createDialog(String parameterTitle) {
    // TODO: 12/08/16 add material dialogs example
    return new AlertDialog.Builder(this)
        .setTitle(parameterTitle)
        .show();
  }

  void showSnackbar(String message) {
    Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show();
  }
}
