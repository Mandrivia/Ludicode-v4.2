package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.beans.LeaderboardRow;
import fr.iutinfo.beans.Level;
import fr.iutinfo.beans.LevelInfo;
import fr.iutinfo.beans.LevelProgress;
import fr.iutinfo.beans.NotifLevelCount;

public interface SaveLevelDao {
	
	@SqlUpdate("CREATE TABLE SaveLevel (idUser INT NOT NULL, idLevel INT NOT NULL, content TEXT, CONSTRAINT pk_LevelProgress PRIMARY KEY (idUser, idLevel), CONSTRAINT fk_user FOREIGN KEY (idUser) REFERENCES Users(id), CONSTRAINT fk_level FOREIGN KEY (idLevel) REFERENCES Levels(id))")
	void createSaveLevelTable();
	
	@SqlUpdate("INSERT INTO SaveLevel(idUser, idLevel, content) VALUES(:idUser, :idLevel, :content)")
	void insert(@Bind("idUser") int idUser, @Bind("idLevel") int idLevel, @Bind("content") String content);

	@SqlUpdate("UPDATE SaveLevel SET content = :content WHERE idLevel = :idLevel AND idUser = :idUser")
	void update(@Bind("idUser") int idUser, @Bind("idLevel") int idLevel, @Bind("content") String content);

	@SqlUpdate("SELECT count(*) FROM SaveLevel WHERE idLevel = :idLevel AND idUser = :idUser")
    @RegisterMapperFactory(BeanMapperFactory.class)
	int exist(@Bind("idUser") int idUser, @Bind("idLevel") int idLevel);

	
	@SqlUpdate("drop table if exists SaveLevel")
	void dropSaveLevelTable();
}
