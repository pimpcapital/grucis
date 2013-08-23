package com.grucis.web.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.grucis.dev.io.ExportManager;
import com.grucis.dev.model.progress.ExportProgress;
import com.grucis.dev.service.DictionaryModelService;
import com.grucis.dev.service.ExportModelService;
import com.grucis.web.mapper.MapViewMapper;
import com.grucis.web.view.BufferedViewCollection;
import com.grucis.web.view.MapView;
import org.jboss.resteasy.annotations.GZIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/map")
@Produces("application/json")
public final class MapGalleryController {

  public static final String MAP_EXPORT_PROGRESS = "MAP_EXPORT_PROGRESS";

  @Autowired
  private DictionaryModelService dictionaryModelService;
  @Autowired
  private ExportModelService exportModelService;
  @Autowired
  private MapViewMapper mapViewMapper;
  @Autowired
  private ExecutorService executorService;
  @Autowired
  private ExportManager exportManager;

  @GET
  @Path("/maps")
  public Response getMaps(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<MapView> views = mapViewMapper.map(dictionaryModelService.getAllMapEntries());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<MapView>(total, views.subList(start, to))).build();
  }

  @GET
  @Path("/map/{id}.json")
  @GZIP
  public Response getMap(@PathParam("id") int id) {
    return Response.ok(exportModelService.getTileMap(id, false)).build();
  }

  @POST
  @Path("/export/start")
  public Response startExport(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    if(session.getAttribute(MAP_EXPORT_PROGRESS) == null) {
      final ExportProgress progress = new ExportProgress();
      session.setAttribute(MAP_EXPORT_PROGRESS, progress);
      executorService.submit(new Runnable() {
        public void run() {
          exportManager.exportMaps(progress);
        }
      });
    }
    return Response.ok().build();
  }

  @GET
  @Path("/export/update")
  public Response updateExportProgress(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    return Response.ok(session.getAttribute(MAP_EXPORT_PROGRESS)).build();
  }

}
