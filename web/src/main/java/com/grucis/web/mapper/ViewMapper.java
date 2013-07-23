package com.grucis.web.mapper;

import java.util.ArrayList;
import java.util.List;

import com.grucis.dev.model.Model;
import com.grucis.web.view.View;

public abstract class ViewMapper<M extends Model, V extends View> {
  public abstract V map(M model);

  public final List<V> map(Iterable<M> models) {
    List<V> ret = new ArrayList<V>();
    for(M model : models) {
      ret.add(map(model));
    }
    return ret;
  }
}
