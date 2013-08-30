package com.grucis.web.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.grucis.dev.service.ThumbnailService;
import com.grucis.dev.utils.image.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/experiment")
@Produces("application/json")
public final class ExperimentSpaceController {

  @Autowired
  private ThumbnailService thumbnailService;

  @GET
  @Path("/thumbnail/animation/{id}.png")
  public Response getAnimationThumbnail(@PathParam("id") int id) {
    return Response.ok(ImageUtils.toBytes(thumbnailService.getAnimationThumbnail(id, false))).build();
  }
}
