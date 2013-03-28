import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class UniversFrontiere extends Univers {
	int longueur;
	int largeur;

	public UniversFrontiere() {
		super();
		longueur = 5;
		largeur = 5;
	}

	public UniversFrontiere(int a, int b) {
		longueur = a;
		largeur = b;
	}

	public UniversFrontiere(String file) {
		super();
		File f = new File(file);
		Scanner sc = null;
		int l = 0;
		int c = 0;
		int nbligne = 0;
		int nbcolonne = 0;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String ligne = sc.nextLine();
		boolean inutile = true;
		while (sc.hasNextLine() && inutile) {
			if (ligne.charAt(0) == '#' && ligne.charAt(1) != 'P') {
				ligne = sc.nextLine();
			} else {
				inutile = false;
			}
		}
		Scanner st = new Scanner(ligne);
		String c1;
		String c2;
		c1 = st.next();
		if (c1.equals("#P")) {
			nbligne = l;
			nbcolonne = c;
		}
		while ((sc.hasNextLine() && (ligne = sc.nextLine()) != null)) {

			c = 0;
			for (int i = 0; i <= ligne.length() - 1; i++) {

				if (ligne.charAt(i) == '*') {
					Cellule a = new Cellule(l, c, "vivante");
					lemonde.add(a);
				}
				if (c > nbcolonne) {
					nbcolonne = c;
				}
				c++;
			}
			if (l > nbligne) {
				nbligne = l;
			}
			l++;
		}
		this.longueur = nbcolonne;
		this.largeur = nbligne;
	}

	public void Ajouter(Cellule a) {
		if (!lemonde.contains(a) && a.getX() >= 0 && a.getY() >= 0)
			lemonde.add(a);
	}

	/**
	 * cette méthode permet de tester si on sort du tableau
	 * 
	 * @param l
	 *            : représente la ligne
	 * @param c
	 *            : représente la colonne
	 * @return : la ligne et la colonne sous forme de coordonnée
	 */
	public coordonnees tester(int l, int c) {
		coordonnees s = new coordonnees(l, c);
		if (l < 0 || c < 0 || l > largeur || c > longueur) {
			s.i = l;
			s.j = c;
			s.valide = false;
		}
		return s;
	}

	/**
	 * Permet d'ajouter les cellules voisines dans la liste des cellules mortes
	 * et on compte le nombre de cellules vivantes pour connaitre son état
	 * suivant
	 * 
	 * @author Boufatah-Manivannin
	 */
	public void AjoutCelMortes() {
		/*
		 * Permet d'ajouter les cellules voisines dans la liste des cellules
		 * mortes et on compte le nombre de cellules vivantes pour connaitre son
		 * état suivant
		 */
		int i, j;
		int nb = 0;
		Iterator<Cellule> it = lemonde.iterator();
		Cellule[] tab = new Cellule[8];
		while (it.hasNext()) {
			Cellule a = it.next();
			int dx, dy;
			nb = 0;
			int f = 0;
			i = a.getX() - 1;
			j = a.getY() - 1;

			for (int e = 0; e < 9; e++) {
				dx = i + (e % 3);
				dy = j + (e / 3);
				if (dx != i + 1 || dy != j + 1) {
					coordonnees cor = tester(dx, dy);
					if (cor.valide)
						tab[f] = contient(cor);
					else
						tab[f] = null;
					f++;
				}
			}
			for (int p = 0; p <= tab.length - 1; p++) {
				if (tab[p] != null && tab[p].getEtat().equals("morte")
						&& !(celmortes.contains(tab[p])))
					celmortes.add(tab[p]);
			}
			for (int n = 0; n <= tab.length - 1; n++) {
				if (tab[n] != null && tab[n].getEtat().equals("vivante")) {
					nb++;
				}

			}
			if (nb == 2 || nb == 3) {
				a.setEtat_suivant(a.getEtat());
			} else {
				a.setEtat_suivant("morte");
			}
		}
	}

	public void affiche() {

		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < longueur; j++) {
				if (appartient(i, j)) {
					System.out.print("| # |");
				} else {
					System.out.print("|   |");
				}

			}
			System.out.println();
		}
	}
}
