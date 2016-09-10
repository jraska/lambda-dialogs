package com.jraska.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import lombok.EqualsAndHashCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DialogFieldsBundleAdapterTest {
  @Test
  public void putsIntoBundleCorrectly() {
    DialogFieldsBundleAdapter bundleAdapter = new DialogFieldsBundleAdapter();
    DialogFields originalFields = createRandomFields();
    Bundle bundle = new Bundle();

    bundleAdapter.putTo(bundle, originalFields);
    DialogFields fieldsFromBundle = bundleAdapter.get(bundle);

    assertThat(fieldsFromBundle).isEqualTo(originalFields);
  }

  static DialogFields createRandomFields() {
    EnhancedRandom random = EnhancedRandomBuilder
        .aNewEnhancedRandomBuilder()
        .randomize(CharSequence.class, new StringRandomizer())
        .randomize(ActivityMethod.class, (Randomizer) TestMethod::new).build();
    return random.nextObject(DialogFields.class);
  }

  @EqualsAndHashCode
  static class TestMethod implements ActivityMethod {
    @Override public void call(FragmentActivity activity) {
      throw new UnsupportedOperationException();
    }
  }
}