package com.grucis.web.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.grucis.dev.service.RawModelService;
import com.grucis.web.mapper.AdrnViewMapper;
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
  public Response getAdrns() {
    return Response.ok(adrnViewMapper.map(rawModelService.getAllAdrns())).build();
  }
}
