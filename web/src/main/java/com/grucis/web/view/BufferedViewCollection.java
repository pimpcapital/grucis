package com.grucis.web.view;

import java.util.Collection;

public final class BufferedViewCollection<T extends View> {

  private int total;
  private Collection<T> views;

  public BufferedViewCollection(int total, Collection<T> views) {
    this.total = total;
    this.views = views;
  }

  public int getTotal() {
    return total;
  }

  public Collection<T> getViews() {
    return views;
  }
}
