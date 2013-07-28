package com.grucis.dev.service;

import com.grucis.dev.logic.SpriteSheetPacker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class SpriteSheetService {

  @Autowired
  private SpriteSheetPacker spriteSheetPacker;


}
