package com.grucis.web.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.grucis.dev.service.RawModelService;
import com.grucis.web.mapper.AdrnViewMapper;
import com.grucis.web.view.AdrnView;
import com.grucis.web.view.BufferedViewCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/bitmap")
@Produces("application/json")
public final class BitmapGalleryController {
  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private AdrnViewMapper adrnViewMapper;

  @GET
  @Path("/adrns")
  public Response getAdrns(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<AdrnView> views = adrnViewMapper.map(rawModelService.getAllAdrns());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<AdrnView>(total, views.subList(start, to))).build();
  }
}
