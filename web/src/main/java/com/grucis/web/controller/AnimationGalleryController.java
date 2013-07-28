package com.grucis.web.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.grucis.dev.service.OutputModelService;
import com.grucis.dev.service.RawModelService;
import com.grucis.web.mapper.SprAdrnViewMapper;
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
  private OutputModelService outputModelService;
  @Autowired
  private SprAdrnViewMapper sprAdrnViewMapper;

  @GET
  @Path("/spradrn")
  public Response getAdrns(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<SprAdrnView> views = sprAdrnViewMapper.map(rawModelService.getAllSprAdrns());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<SprAdrnView>(total, views.subList(start, to))).build();
  }
}
