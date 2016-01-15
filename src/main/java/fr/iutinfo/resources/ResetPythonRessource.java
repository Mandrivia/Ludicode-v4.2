package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.Level;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.SaveLevelDao;
import fr.iutinfo.utils.Session;

@Path("/resetPython")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ResetPythonRessource {
	private static SaveLevelDao dao = App.dbi.open(SaveLevelDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idLevel}/{cookie}/{idList}")
	public String getSavedLevel(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel, @PathParam("idList") int idList){
		int idUser = Session.getUser(cookie).getId();
		//System.out.println(dao.selectExistingSave(idUser, idLevel, idList));
		dao.delete(idUser, idLevel, idList);
		return levelDao.getLevelOnList(idList, idLevel).getContent();
	}
	
}
