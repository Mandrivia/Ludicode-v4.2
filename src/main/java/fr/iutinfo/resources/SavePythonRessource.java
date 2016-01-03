package fr.iutinfo.resources;

import java.io.BufferedReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.beans.LevelProgress;
import fr.iutinfo.dao.LevelProgressDao;
import fr.iutinfo.dao.SaveLevelDao;
import fr.iutinfo.utils.Session;

@Path("/savePython")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SavePythonRessource {
	private static SaveLevelDao dao = App.dbi.open(SaveLevelDao.class);
	
	@POST
	@Path("{cookie}/{idLevel}/{code}")
	public Feedback putProgress(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel, @PathParam("code") String code) {
		
		if (!Session.isLogged(cookie))
			return new Feedback(false, "Vous devez être connecte !");
		
		int idUser = Session.getUser(cookie).getId();
		/*LevelProgress tmp = dao.getLevel(idUser, idLevel);
		if (tmp != null)
			return new Feedback(false, "Niveau deja validé !");*/
		dao.update(idUser, idLevel, code);
		
		System.out.println(dao.exist(idUser, idLevel));
		
		/*if(dao.exist(idUser, idLevel) > 0)
			dao.update(idUser, idLevel, request.getParameter("code"));
		else
			dao.insert(idUser, idLevel, request.getParameter("code"));
		*/
		return new Feedback(true, "OK");
	}

}
