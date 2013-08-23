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
import com.grucis.dev.service.ExportModelService;
import com.grucis.dev.service.RawModelService;
import com.grucis.dev.utils.image.ImageUtils;
import com.grucis.web.mapper.SprAdrnViewMapper;
import com.grucis.web.view.BufferedViewCollection;
import com.grucis.web.view.SprAdrnView;
import org.jboss.resteasy.annotations.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/animation")
@Produces("application/json")
public final class AnimationGalleryController {
  public static final String ANIMATION_EXPORT_PROGRESS = "ANIMATION_EXPORT_PROGRESS";

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private SprAdrnViewMapper sprAdrnViewMapper;
  @Autowired
  private ExportModelService exportModelService;
  @Autowired
  private ExecutorService executorService;
  @Autowired
  private ExportManager exportManager;

  @GET
  @Path("/spradrn")
  public Response getSprAdrns(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<SprAdrnView> views = sprAdrnViewMapper.map(rawModelService.getAllSprAdrns());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<SprAdrnView>(total, views.subList(start, to))).build();
  }

  @GET
  @Path("/sprite/{id}.png")
  @Produces("image/png")
  @Cache(maxAge = Integer.MAX_VALUE)
  public Response getSpriteImage(@PathParam("id") int id) {
    return Response.ok(ImageUtils.toBytes(exportModelService.getAnimationSpriteImage(id, false))).build();
  }

  @GET
  @Path("/index/{id}.json")
  @Cache(maxAge = Integer.MAX_VALUE)
  public Response getAnimationReference(@PathParam("id") int id) {
    return Response.ok(exportModelService.getAnimationSpriteIndex(id, false)).build();
  }

  @POST
  @Path("/export/start")
  public Response startExport(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    if(session.getAttribute(ANIMATION_EXPORT_PROGRESS) == null) {
      final ExportProgress progress = new ExportProgress();
      session.setAttribute(ANIMATION_EXPORT_PROGRESS, progress);
      executorService.submit(new Runnable() {
        public void run() {
          exportManager.exportAnimations(progress);
        }
      });
    }
    return Response.ok().build();
  }

  @GET
  @Path("/export/update")
  public Response updateExportProgress(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    return Response.ok(session.getAttribute(ANIMATION_EXPORT_PROGRESS)).build();
  }
}
