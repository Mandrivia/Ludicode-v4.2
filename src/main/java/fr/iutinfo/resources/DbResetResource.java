package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.dao.FriendsRelationsDao;
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.dao.LevelProgressDao;
import fr.iutinfo.dao.SaveLevelDao;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Utils;

@Path("/resetDb")
@Produces(MediaType.TEXT_HTML)
public class DbResetResource {

	private static FriendsRelationsDao friendDao = App.dbi.open(FriendsRelationsDao.class);
	private static UserDao userDao = App.dbi.open(UserDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	private static InstructionsDao instructionsDao = App.dbi.open(InstructionsDao.class);
	private static LevelListDao levelListDao = App.dbi.open(LevelListDao.class);
	private static LevelProgressDao levelProgressDAO = App.dbi.open(LevelProgressDao.class);
	private static SaveLevelDao saveLevelDAO = App.dbi.open(SaveLevelDao.class);

	@GET
	public String whichDatabase() {
		return "<ul>" + "<li><a href='resetDb/users'>Reset users table</a></li>"
				+ "<li><a href='resetDb/relations'>Reset relations table</a></li>"
				+ "<li><a href='resetDb/levels'>Reset levels table</a></li>"
				+ "<li><a href='resetDb/instructions'>Reset instructions table</a></li>"
				+ "<li><a href='resetDb/levelList'>Reset levelList</a></li>"
				+ "<li><a href='resetDb/levelProgress'>Reset levelProgress</a></li>"
				+ "<li><a href='resetDb/all'>Reset ALL tables</a></li>" + "</ul>";
	}

	@GET
	@Path("all")
	public String resetDatabase() {

		resetDbUsers();
		resetDbInstructions();
		resetDbLevels();
		resetDbFriendsRelations();
		resetDbLevelList();
		resetDbUsers();
		resetDbLevelProgress();
		resetDbSaveLevel();

		return "All Tables Reset";
	}

	@GET
	@Path("users")
	public String resetDbUsers() {
		userDao.dropUserTable();

		userDao.createUserTable();

		userDao.insert("admin", Utils.hashMD5("admin"), "benjamin.danglot@etudiant.univ-lille1.fr");

		return "Table user Reset";
	}

	@GET
	@Path("levelProgress")
	public String resetDbLevelProgress() {
		levelProgressDAO.dropLevelProgessTable();
		levelProgressDAO.createLevelProgressTable();

		levelProgressDAO.insert(1, 1);
		levelProgressDAO.insert(1, 2);
		levelProgressDAO.insert(1, 3);

		levelProgressDAO.insert(2, 1);
		levelProgressDAO.insert(2, 4);

		return "Table levelProgress Reset";
	}

	@GET
	@Path("relations")
	public String resetDbFriendsRelations() {
		friendDao.dropFriendsRelationsTable();

		friendDao.createFriendsRelationsTable();

		friendDao.createRelation(2, 1);
		friendDao.createRelation(1, 2);
		return "Table friendsRelations Reset";
	}

	@GET
	@Path("levels")
	public String resetDbLevels() {
		levelDao.dropLevelsTable();

		levelDao.createLevelsTable();

		levelDao.insert("Niveau 1", // name
				"1 2 1," + //
						"1 0 1," + // Level content
						"1 3 1", //
				"1", // instructions id list
				2, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 2", // name
				"1 1 1," + //
						"2 0 3," + // Level content
						"1 1 1", //
				"1,3", // instructions id list
				3, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 3", // name
				"1 2 1," + //
						"1 0 1," + // Level content
						"1 0 1," + "1 3 1", //
				"1,10", // instructions id list
				2, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 4", // name
				"2 1 3," + //
						"0 1 0," + // Level content
						"0 0 0", //
				"1,3,10", // instructions id list
				4, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 5", // name
				"2 1 1 1," + //
						"0 0 1 1," + // Level content
						"1 0 0 1," + "1 1 0 3", //
				"1,3,4,10", // instructions id list
				5, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 1", // name
				"2 1 1 1," + //
						"0 1 1 1," + // Level content
						"0 1 1 1," + "0 0 0 3", //
				"1,2,4,5", // instructions id list
				5, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 2", // name
				"3 0 0," + //
						"1 1 0," + // Level content
						"0 0 0," + "2 1 1", //
				"1,2,3,5", // instructions id list
				5, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 3", // name
				"2 1 1 1," + //
						"0 1 1 1," + // Level content
						"0 1 1 1," + "0 0 0 3", //
				"1,3,7,10", // instructions id list
				4, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 4", // name
				"2 1 1 1," + //
						"0 0 0 1," + // Level content
						"1 1 0 1," + "1 1 0 1," + "0 0 0 1," + "3 1 1 1", //
				"1,3,4,7,8,10", // instructions id list
				6, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 5", // name
				"1 1 1 1 2 1," + //
						"0 0 3 1 0 1," + // Level content
						"0 1 1 1 0 0," + "0 1 1 1 0 1," + "0 0 0 0 0 0," + "1 1 1 0 1 1", //
				"1,3,4,7,8,10", // instructions id list
				4, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 1", // name
				"1 1 1 1 1 1," + //
						"0 0 0 0 1 1," + // Level content
						"0 1 1 0 1 1," + "2 1 0 0 0 3," + "0 1 1 1 1 1," + "0 1 1 1 1 1", //
				"1,4,6,11,10", // instructions id list
				5, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 2", // name
				"1 2 1 1 0 3," + //
						"1 0 1 1 0 1," + // Level content
						"1 0 0 1 0 1," + "0 0 1 1 0 1," + "1 0 1 1 0 1," + "0 0 0 0 0 1", //
				"1,3,4,7,8,10,11", // instructions id list
				7, // max number of instructions
				1); // author id

		levelDao.insert("Niveau 3", // name
				"0 0 0 0 0 2 1," + //
						"1 0 1 1 1 1 1," + // Level content
						"1 0 0 0 0 0 1," + "1 0 1 1 1 0 1," + "1 1 0 0 0 0 1," + "1 3 1 0 1 1 1," + "1 0 0 0 1 1 1", //
				"1,10,15,3,4,7", // instructions id list
				6, // max number of instructions
				1); // author id

		levelDao.insert("Premiers pas", // name
				"import turtle\n\n" +

		"t = turtle.Turtle()\n\n",
				"<img src=\"images/pointille.png\" style=\"float:right\"/>Voici votre tortue (la petite flèche noire dans l'encadre ci-dessous). Cette tortue est capable de faire certaines choses,"
						+ " qui vous seront révéler plus tard. Le seul soucis, c’est qu’elle ne parle que le Python!"
						+ " Un langage dans lequel vous avez déjà fait vos premiers pas. Une fois que vous avez entrer le code python,"
						+ " il suffit de clicker sur le bouton run pour que la tortue l’exécute!<br/><br/>"
						+ "<ul><li>I : On va commencer en douceur, voici deux premières instructions que vous pouvez donner à la torture : <b>t.forward(distance)</b> "
						+ "qui va faire avancer la tortue de “distance” dans la direction dans laquelle elle se trouve."
						+ " Dans l’autre sens, on peut la faire reculer avec <b>t.backward(distance)</b>.Faites bouger la tortue!</li><li>II :"
						+ " Vous avez pu remarquer, que la tortue trace son parcours. Pour gérer le stylo de la tortue, il suffit de lui demander"
						+ " : <b>t.penup()</b> pour “remonter” le stylo de la feuille, et <b>t.pendown()</b> pour le baisser et écrire.Maintenant que vous connaissez"
						+ " ces premières instructions, tracer une ligne en pointillés.</li></ul> On également changer la taille du pinceau avec <b>t.pensize(valeur)</b> A droite de l'écran,"
						+ " vous pourrez voir une image du résultat attendu pour les différents exercices.", // instructions
																												// id
																												// list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Carré", // name
				"import turtle\n\n" +

		"t = turtle.Turtle()\n\n",
				"<img src=\"images/carre.png\" style=\"float:right\"/>Bon, c’est pas très drôle, mais c’est un début! <br/>"
						+ "III :  On peut aussi demander à la tortue de changer de direction : <b>t.left(angle)</b> ou <b>t.right(angle)</b> "
						+ "pour tourner de angle à gauche ou à droite respectivement. <br/> <br/>"
						+ "Dessiner un carré grâce à la tortue. (Utiliser une boucle for? :D)", // instructions
																								// id
																								// list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Triangle", // name
				"import turtle\n\n" +

		"t = turtle.Turtle()\n\n",
				"<img src=\"images/triangle.png\" style=\"float:right\">Dessiner un triangle grâce à la torture, faites un simple triangle équilatéral (chaque angle = 60°, donc on tourne de 120°).", // instructions
																																																		// id
																																																		// list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Cercle", // name
				"import turtle\n\n" +

		"t = turtle.Turtle()\n\n",
				"<img src=\"images/cercle.png\" style=\"float:right\">On peut dire qu’un cercle est une multitude de petit trait. "
						+ "On peut donc le réaliser avec des traits de longueur 1 et changer d’angle de 1° entre chaque trait. Dessiner un cercle.", // instructions
																																						// id
																																						// list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Forme plus complexe", // name
				"import turtle\n\n" +

		"t = turtle.Turtle()\n\n",
				"<img src=\"images/jo.png\" style=\"float:right\"/>En fait, il existe déjà une technique pour crée des cercles avec la tortue : "
						+ "<b>t.circle(rayon)</b>.<br/> On peut également changer la couleur du pinceau avec <b>t.color(‘couleur’)</b>. "
						+ "Dessiner le logo des Jeux Olympiques.", // instructions
																	// id list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Le flocon 1", // name
				"import turtle\n\ndef clone(t):\n\tnt = turtle.Turtle()\n\tnt.up()\n\tnt.speed(0)\n\tnt.setpos(t.pos())"
						+ "\n\tnt.setheading(t.heading())\n\tnt.down()\n\t" + "return nt\n\n"
						+ "def recurse(depth, parent_turtle):\n\tif(depth >= 0):\n\t\tchild_turtle = clone(parent_turtle)\n\t\t"
						+ "child_turtle.fd(50)\n\t\trecurse(depth-1,child_turtle)\n\n"
						+ "t = turtle.Turtle()\nrecurse(4,t)",
				"<img src=\"images/part_flocon.png\" style=\"float:right\"/>On se propose de crée une figure complexe. Pour cela nous allons utiliser plusieurs tortue. "
						+ "Afin de vous aidez, nous fournissons la méthode <b>clone(t)</b>, qui clone la tortue passer en paremètre, "
						+ "et le retourne.<br/>"
						+ "On fournit également la base d'une fonction recursive, qui servira tout au long de la suite d'exercices.<br/>"
						+ "L'objectif de cet exercice, et que chaque tortue parent crée deux enfants, qui iront dans des directions différentes. <br/>"
						+ "Le but est de dessiner quelque chose de similaire a l'image.", // instructions
																							// id
																							// list
				0, // max number of instructions
				1); // author id

		levelDao.insert("Le flocon 2", // name
				"import turtle\n\ndef clone(t):\n\tnt = turtle.Turtle()\n\tnt.up()\n\tnt.speed(0)\n\tnt.setpos(t.pos())"
						+ "\n\tnt.setheading(t.heading())\n\tnt.down()\n\t" + "return nt\n\n"
						+ "def recurse(depth, parent_turtle):\n\tif(depth >= 0):\n\t\tchild_turtle = clone(parent_turtle)\n\t\t"
						+ "child_turtle.fd(50)\n\t\trecurse(depth-1,child_turtle)\n\n"
						+ "t = turtle.Turtle()\nrecurse(4,t)",
				"<img src=\"images/flocon.png\" style=\"float:right\"/> Maintenant que vous avez fait une branche du flocon, tenter "
						+ "de reproduire le flocon comme l'image a gauche. Pour améliorer l'aspect finale, vous pouvez cacher une tortue avec la "
						+ "méthode <b>t.hideturtle()</b>", // instructions id
															// list
				0, // max number of instructions
				1);

		/*
		 * levelDao.insert("Niveau 2", // name "2 1 0 0 0 0," + //
		 * "0 1 0 1 0 0," + // Level content "0 0 0 0 1 1," + "0 1 0 1 0 0," +
		 * "1 1 0 0 0 1," + "0 0 0 1 0 0", // "1,3,4,7,8,10,11", // instructions
		 * id list 7, // max number of instructions 1); // author id
		 */

		return "Table levels Reset";
	}

	@GET
	@Path("instructions")
	public String resetDbInstructions() {
		instructionsDao.dropInstructionsTable();

		instructionsDao.createInstructionsTable();

		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png",
				"images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0); // ID
																									// 1
		instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0, "images/doc/reculer.png",
				"images/doc/avancer.gif", "Le personnage se déplace d'une case vers l'arrière.", 0); // ID
																										// 2
		instructionsDao.insert("Pivoter à gauche", "player.turnLeft();", 65, 0, "images/doc/pivoter_gauche.png",
				"images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la gauche.", 0); // ID
																											// 3
		instructionsDao.insert("Pivoter à droite", "player.turnRight();", 65, 0, "images/doc/pivoter_droite.png",
				"images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la droite.", 0); // ID
																											// 4
		instructionsDao.insert("Répeter 3 fois", "for (var i%line% = 0; i%line% < 3; ++i%line%)", 100, 1,
				"images/doc/répéter_n.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront executées 3 fois.", 1); // ID
																				// 5
		instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 1, "images/doc/si_devant.png",
				"images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il y a un chemin devant le personnage.", 2); // ID
																												// 6
		instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 1, "images/doc/si_gauche.png",
				"images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il y a un chemin à gauche du personnage.", 2); // ID
																													// 7
		instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 1, "images/doc/si_droite.png",
				"images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il y a un chemin à droite du personnage.", 2); // ID
																													// 8
		instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 1,
				"images/doc/si_derrière.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il y a un chemin dérrière le personnage.", 2); // ID
																													// 9
		instructionsDao.insert("Répeter jusqu'a l'arrivée", "while (!player.hasArrived())", 100, 1,
				"images/doc/répéter_arrivée.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront executées jusqu'à ce que le personnage arrive à la fin.", 1); // ID
																													// 10
		instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 1,
				"images/doc/si_non_devant.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin devant le personnage.", 2); // ID
																														// 11
		instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 1,
				"images/doc/si_non_gauche.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à gauche du personnage.", 2); // ID
																														// 12
		instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 1,
				"images/doc/si_non_droite.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à droite du personnage.", 2); // ID
																														// 13
		instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 1,
				"images/doc/si_non_derrière.png", "images/doc/avancer.gif",
				"Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin dérrière le personnage.", 2); // ID
																														// 14
		instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 2,
				"images/doc/si_devant_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 15
		instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 2,
				"images/doc/si_gauche_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 16
		instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 2,
				"images/doc/si_droite_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 17
		instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 2,
				"images/doc/si_derrière_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 18
		instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 2,
				"images/doc/si_non_devant_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 19
		instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 2,
				"images/doc/si_non_gauche_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 20
		instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 2,
				"images/doc/si_non_droite_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 21
		instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 2,
				"images/doc/si_non_derrière_sinon.png", "images/doc/avancer.gif",
				"Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.",
				2); // ID 22
		return "Table instructions Reset";
	}

	@GET
	@Path("levelList")
	public String resetDbLevelList() {
		levelListDao.dropLevelListAssociationsTable();
		levelListDao.dropLevelListsTable();

		levelListDao.createLevelListAssociationsTable();
		levelListDao.createLevelListsTable();

		levelListDao.createList("Tutoriel", 1, 0);
		levelListDao.createList("Intermédiaire", 1, 0);
		levelListDao.createList("Expert", 1, 0);
		levelListDao.createList("Python", 1, 1);
		levelListDao.createList("Python avancé", 1, 1);

		levelListDao.insertAssociation(1, 1, 0);
		levelListDao.insertAssociation(1, 2, 1);
		levelListDao.insertAssociation(1, 3, 2);
		levelListDao.insertAssociation(1, 4, 3);
		levelListDao.insertAssociation(1, 5, 4);
		levelListDao.insertAssociation(2, 6, 0);
		levelListDao.insertAssociation(2, 7, 1);
		levelListDao.insertAssociation(2, 8, 2);
		levelListDao.insertAssociation(2, 9, 3);
		levelListDao.insertAssociation(2, 10, 4);
		levelListDao.insertAssociation(3, 11, 0);
		levelListDao.insertAssociation(3, 12, 1);
		levelListDao.insertAssociation(3, 13, 2);

		levelListDao.insertAssociation(4, 14, 0);
		levelListDao.insertAssociation(4, 15, 1);
		levelListDao.insertAssociation(4, 16, 2);
		levelListDao.insertAssociation(4, 17, 3);
		levelListDao.insertAssociation(4, 18, 4);

		levelListDao.insertAssociation(5, 19, 0);
		levelListDao.insertAssociation(5, 20, 1);

		// levelListDao.insertAssociation(3, 14, 3);
		// levelListDao.insertAssociation(3, 15, 4);

		return "Table instructions Reset";
	}

	@GET
	@Path("saveLevel")
	public String resetDbSaveLevel() {
		saveLevelDAO.dropSaveLevelTable();
		saveLevelDAO.createSaveLevelTable();
		return "Table save level Reset";
	}

}
