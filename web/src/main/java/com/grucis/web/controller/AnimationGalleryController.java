package com.grucis.web.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.grucis.dev.service.RawModelService;
import com.grucis.dev.service.SpriteSheetService;
import com.grucis.dev.utils.image.ImageUtils;
import com.grucis.web.mapper.SprAdrnViewMapper;
import com.grucis.web.mapper.SpriteSheetViewMapper;
import com.grucis.web.view.BufferedViewCollection;
import com.grucis.web.view.SprAdrnView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/animation")
@Produces("application/json")
public final class AnimationGalleryController {
  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private SpriteSheetService spriteSheetService;
  @Autowired
  private SprAdrnViewMapper sprAdrnViewMapper;
  @Autowired
  private SpriteSheetViewMapper spriteSheetViewMapper;

  @GET
  @Path("/spradrn")
  public Response getAdrns(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<SprAdrnView> views = sprAdrnViewMapper.map(rawModelService.getAllSprAdrns());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<SprAdrnView>(total, views.subList(start, to))).build();
  }

  @GET
  @Path("/sprite/{id}.png")
  @Produces("image/png")
  public Response getSpriteImage(@PathParam("id") int id) {
    return Response.ok(ImageUtils.toBytes(spriteSheetService.getSpriteSheet(id, true).getImage())).build();
  }

  @GET
  @Path("/index/{id}.json")
  public Response getAnimationReference(@PathParam("id") int id) {
    return Response.ok(spriteSheetViewMapper.map(spriteSheetService.getSpriteSheet(id, true))).build();
  }
}
