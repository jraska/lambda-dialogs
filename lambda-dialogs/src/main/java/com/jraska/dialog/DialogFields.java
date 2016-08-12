package com.jraska.dialog;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder(builderClassName = "Builder")
@EqualsAndHashCode
public final class DialogFields {
  public final CharSequence title;
  public final CharSequence message;
  public final int iconRes;
  public final ActivityMethod positiveAction;
  public final CharSequence positiveText;
  public final ActivityMethod neutralAction;
  public final CharSequence neutralText;
  public final ActivityMethod negativeAction;
  public final CharSequence negativeText;
  public final boolean cancelable;
}
