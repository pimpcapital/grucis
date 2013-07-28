package com.grucis.dev.mapper;

import com.grucis.dev.model.Model;

public abstract class ModelMapper<SM extends Model, TM extends Model> {
  public abstract TM map(SM source);
}
