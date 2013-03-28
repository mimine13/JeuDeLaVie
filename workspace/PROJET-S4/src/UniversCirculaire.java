import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class UniversCirculaire extends Univers {
	int longueur;
	int largeur;

	public UniversCirculaire() {
		super();
		longueur = 4;
		largeur = 4;
	}

	public UniversCirculaire(int a, int b) {
		super();
		longueur = a;
		largeur = b;
	}

	public UniversCirculaire(String file) {
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

	public void Ajoute(Cellule a) {
		if (!lemonde.contains(a) && a.getX() >= 0 && a.getY() >= 0)
			lemonde.add(a);
	}

	public coordonnees tester(int l, int c) {
		coordonnees s = new coordonnees(l, c);
		if (c < 0 && l >= 0 && l <= largeur) {
			s.i = l;
			s.j = longueur;
		}
		if (c > longueur && l >= 0 && l <= largeur) {
			s.i = l;
			s.j = 0;
		}
		if (c < 0 && l < 0) {
			s.i = largeur;
			s.j = longueur;
		}
		if (c > longueur && l > largeur) {
			s.i = l;
			s.j = 0;
		}
		if (l < 0 && c >= 0 && c <= longueur) {
			s.i = largeur;
			s.j = c;
		}
		if (l < 0 && c > longueur) {
			s.i = largeur;
			s.j = 0;
		}
		if (l > largeur && c >= 0 && c <= longueur) {
			s.i = 0;
			s.j = c;
		}
		if (c > longueur && l >= 0 && l <= largeur) {
			s.i = l;
			s.j = 0;
		}

		if (c > longueur && l > largeur) {
			s.i = 0;
			s.j = 0;
		}
		if (c < 0 && l > largeur) {
			s.i = 0;
			s.j = longueur;
		}
		return s;
	}

	public void AjoutCelMortes() {
		/*
		 * Permet d'ajouter les cellules voisines dans la liste des cellules
		 * mortes et on compte le nombre de cellules vivantes pour connaitre son
		 * état suivant
		 */
		int i, j, dx, dy;
		int nb;
		Iterator<Cellule> it = lemonde.iterator();
		Cellule[] tab = new Cellule[8];
		while (it.hasNext()) {
			Cellule a = it.next();
			nb = 0;
			int f = 0;
			i = a.getX() - 1;
			j = a.getY() - 1;
			for (int e = 0; e < 9; e++) {
				dx = i + (e % 3);
				dy = j + (e / 3);
				if (dx != i + 1 || dy != j + 1) {
					tab[f] = contient(tester(dx, dy));
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

	public void TesteListeCelMortes() {
		/*
		 * Permet de connaitre l'état suivant des cellules mortes
		 */
		int i, j, dx, dy;
		int cp = 0;
		Iterator<Cellule> it = celmortes.iterator();
		Cellule[] tab = new Cellule[8];
		while (it.hasNext()) {
			Cellule a = (Cellule) it.next();
			cp = 0;
			int f = 0;
			i = a.getX() - 1;
			j = a.getY() - 1;
			for (int e = 0; e < 9; e++) {
				dx = i + (e % 3);
				dy = j + (e / 3);
				if (dx != i + 1 || dy != j + 1) {
					tab[f] = contient(tester(dx, dy));
					f++;
				}
			}

			for (int p = 0; p <= tab.length - 1; p++) {
				if (tab[p].getEtat().equals("vivante"))
					cp++;
			}
			if (cp == 3)
				a.setEtat_suivant("vivante");
			else
				a.setEtat_suivant(a.getEtat());
		}
	}

	public void affiche() {

		for (int i = 0; i <= largeur; i++) {
			for (int j = 0; j <= longueur; j++) {
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
