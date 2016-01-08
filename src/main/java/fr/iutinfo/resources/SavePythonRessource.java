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

@Path("/savePython")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SavePythonRessource {
	private static SaveLevelDao dao = App.dbi.open(SaveLevelDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	
	@POST
	@Path("{cookie}/{idLevel}/{idList}/{code}")
	public Feedback putProgress(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel, @PathParam("code") String code, @PathParam("idList") int idList) {
		
		if (!Session.isLogged(cookie))
			return new Feedback(false, "Vous devez être connecte !");
		
		int idUser = Session.getUser(cookie).getId();
		/*LevelProgress tmp = dao.getLevel(idUser, idLevel);
		if (tmp != null)
			return new Feedback(false, "Niveau deja validé !");*/
		//dao.update(idUser, idLevel, code);
		//dao.insert(idUser, idLevel, code);

		System.out.println(dao.exist(idUser, idLevel, idList));
		
		if(dao.exist(idUser, idLevel, idList) > 0)
			dao.update(idUser, idLevel, code, idList);
		else
			dao.insert(idUser, idLevel, code, idList);
		
		return new Feedback(true, "OK");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idLevel}/{cookie}/{idList}")
	public String getSavedLevel(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel, @PathParam("idList") int idList){
		int idUser = Session.getUser(cookie).getId();
		System.out.println(dao.selectExistingSave(idUser, idLevel, idList));
		List<String> res = dao.selectExistingSave(idUser, idLevel, idList);
		if(res.isEmpty())
			return levelDao.getLevelOnList(idList, idLevel).getContent();
		return res.get(0);
		
	}
	
	
	
}
