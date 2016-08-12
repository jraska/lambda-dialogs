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
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      showDelegateDialog();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void showDelegateDialog() {
    LambdaDialogs.delegate(this)
        .method(SampleActivity::createDialog)
        .show();
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

  void showSnackbar(String message) {
    Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show();
  }

  Dialog createDialog() {
    // TODO: 12/08/16 add material dialogs example
    return new AlertDialog.Builder(this)
        .setTitle("Example title")
        .show();
  }
}
