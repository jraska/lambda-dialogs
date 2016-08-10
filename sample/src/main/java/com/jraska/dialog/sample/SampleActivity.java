package com.jraska.dialog.sample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jraska.dialog.LambdaDialogFragment;

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
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.fab) void onFabClick() {
    LambdaDialogFragment.builder(this)
        .validateEagerly(BuildConfig.DEBUG)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setMessage("Test message")
        .setTitle(R.string.app_name)
        .setPositiveText(android.R.string.ok)
        .setPositiveMethod(SampleActivity::onDialogOkClicked)
        .setNegativeText(android.R.string.cancel)
        .setNegativeMethod(SampleActivity::onDialogCancelClicked)
        .setNeutralText("Neutral")
        .setNeutralMethod(SampleActivity::onDialogNeutralClicked)
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
}