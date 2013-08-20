package com.grucis.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.grucis.dev.io.ExportManager;
import com.grucis.dev.model.progress.BitmapExportProgress;
import com.grucis.dev.service.ExportModelService;
import com.grucis.dev.service.RawModelService;
import com.grucis.dev.utils.image.ImageUtils;
import com.grucis.web.mapper.AdrnViewMapper;
import com.grucis.web.mapper.BitmapExportSettingViewMapper;
import com.grucis.web.view.AdrnView;
import com.grucis.web.view.BufferedViewCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Path("api/bitmap")
@Produces("application/json")
public final class BitmapGalleryController {

  public static final String BITMAP_EXPORT_PROGRESS = "BITMAP_EXPORT_PROGRESS";

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private ExportModelService exportModelService;
  @Autowired
  private ExportManager exportManager;
  @Autowired
  private AdrnViewMapper adrnViewMapper;
  @Autowired
  private BitmapExportSettingViewMapper bitmapExportSettingViewMapper;
  @Autowired
  protected ExecutorService executorService;

  @GET
  @Path("/adrns")
  public Response getAdrns(@QueryParam("start") int start, @QueryParam("limit") int limit) {
    List<AdrnView> views = adrnViewMapper.map(rawModelService.getAllAdrns());
    int to = start + limit;
    int total = views.size();
    if(to > total) to = total;
    return Response.ok(new BufferedViewCollection<AdrnView>(total, views.subList(start, to))).build();
  }

  @GET
  @Path("/image/{id}.png")
  @Produces("image/png")
  public Response getImage(@PathParam("id") int id) throws IOException {
    return Response.ok(ImageUtils.toBytes(exportModelService.getBitmapImage(id, false))).build();
  }

  @GET
  @Path("/index/{id}.json")
  public Response getIndex(@PathParam("id") int id) throws IOException {
    return Response.ok(exportModelService.getBitmapIndex(id, false)).build();
  }

  @GET
  @Path("/download/{id}.png")
  @Produces("image/png")
  public Response downloadImage(@PathParam("id") int id) throws IOException {
    return Response.ok(ImageUtils.toBytes(exportModelService.getBitmapImage(id, false)))
             .header("Content-Disposition", "attachment; filename=\"" + id + ".png\"")
             .build();
  }

  @GET
  @Path("/export/setting")
  public Response reviewSetting() {
    return Response.ok(bitmapExportSettingViewMapper.map(exportManager.getBitmapExportSetting())).build();
  }

  @POST
  @Path("/export/start")
  public Response startExport(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    if(session.getAttribute(BITMAP_EXPORT_PROGRESS) == null) {
      final BitmapExportProgress progress = new BitmapExportProgress();
      session.setAttribute(BITMAP_EXPORT_PROGRESS, progress);
      executorService.submit(new Runnable() {
        public void run() {
          exportManager.exportBitmaps(progress);
        }
      });
    }
    return Response.ok().build();
  }

  @GET
  @Path("/export/update")
  public Response updateExportProgress(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    return Response.ok(session.getAttribute(BITMAP_EXPORT_PROGRESS)).build();
  }

}
