import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

public class Jeu {

	public boolean nom;
	public boolean option;
	public boolean simulation;
	public boolean html;

	public Jeu(String[] args) throws IOException {
		try {
			Choix(args);// faire des switchs 
			if (nom) {
				ListeNom();
			} else if (option) {
				ListeOption();
			} else if (simulation) {
				SimulationJeu(Integer.parseInt(args[1]), args[2]);

			} else {
				if (html) {
					genereHTML ml = new genereHTML();
					ml.openfile("index.html");
				}
			}
		} catch (AException e) {
			System.out.println(e.getMessage());
		}
		;
	}

	private final void ListeNom() {
		System.out.println("** LISTES DES MEMBRES DU GROUPE **");

		System.out.println("BOUFATAH Amine  ");
		System.out.println("MANIVANNIN Elsa");
		System.out.println("MARTIN Juliette");
	}

	private final void ListeOption() {
		System.out
				.println("** java -jar JeuDeLaVie.jar -name : affiche les noms et prénoms des membres du groupe**");
		System.out.println();
		System.out
				.println("** java -jar JeuDeLaVie.jar -h : rappelle la liste des options du programme  **");
		System.out.println();
		System.out
				.println("** java -jar JeuDeLaVie.jar -s d fichier.lif : éxécute une simulation du jeu d’une durée d affichant les configurations du jeu avec le numéro de génération");
		System.out.println();
		System.out
				.println("** java -jar JeuDeLaVie.jar -c max fichier.lif : calcule le type d’évolution du jeu avec ses caractéristiques (taille de la queue, période et déplacement), max représente la durée maximale de simulation pour déduire les résultats du calcul.");
		System.out.println();
		System.out
				.println("** java -jar JeuDeLaVie.jar -w max dossier : calcule le type d’évolution de tous les jeux contenus dans le dossier passé en paramètre et affiche les résultats sous la forme d’un fichier html");
		System.out.println();
	}

	private final void Choix(String[] args) throws AException {
		if (args.length == 1) {
			if (args[0].equals("-name")) {
				nom = true;
			} else if (args[0].equals("-h")) {
				option = true;
			} else {
				throw new AException("Erreur arguments");
			}
		} else if (args.length == 3) {
			if (args[0].equals("-s") && args[1].matches("^[0-9]+$")) {
				simulation = true;
			} else if (args[0].equals("-c") && args[1].matches("^[0-9]+$")) {

			} else if (args[0].equals("-w") && args[1].matches("^[0-9]+$")) {
				html = true;
			} else {
				throw new AException("Erreur d'arguments");
			}
		} else {
			throw new AException("Erreur arguments");
		}

	}

	public void SimulationJeu(final int g, String file) {
		Cellule c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;
		final Univers essai;

		c1 = new Cellule(1, 1, "vivante");
		c2 = new Cellule(1, 2, "vivante");
		c3 = new Cellule(2, 1, "vivante");
		c4 = new Cellule(2, 2, "vivante");
		c5 = new Cellule(0, 2, "vivante");
		c6 = new Cellule(0, 3, "vivante");
		c7 = new Cellule(4, 0, "vivante");
		c8 = new Cellule(0, 4, "vivante");
		c9 = new Cellule(2, 2, "vivante");
		c10 = new Cellule(4, 2, "vivante");
		//essai = new Univers();
		//essai.Ajoute(c1);
		//essai.Ajoute(c2);
		//essai.Ajoute(c3);
		//essai.Ajoute(c4);
		//essai.Ajoute(c5);
		//essai.Ajoute(c6);
		//essai.Ajoute(c7);
		//essai.Ajoute(c8);
		//essai.Ajoute(c9);
		//essai.Ajoute(c10);
		 essai = new UniversCirculaire(file);
		 Collections.sort(essai.lemonde);
		 final Timer timer = new Timer(350, new ActionListener() {
			int numg = 0;

			public void actionPerformed(ActionEvent e) {

				numg = numg + 1;
				// essai.nouvelleGeneration();
				essai.affiche();

				System.out.println("Numero génération : " + numg);
				if (essai.jeuMort()) {
					System.out.println("Jeu Mort");
					System.exit(0);
				} else {
					essai.nouvelleGeneration();
					//if (essai.jeuStable()) {
						//System.out.println("Jeu Stable");
						//System.exit(0);
					//}
					essai.nouvelleGeneration();
				}

				if (numg == g) {
					System.exit(0);
				}

				System.out.println((char) Event.ESCAPE + "8");

			}
		});
		timer.start();

		try {
			System.in.read();
		} catch (Exception e) {
		}
	}
}
