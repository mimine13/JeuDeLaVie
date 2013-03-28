import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Univers implements Serializable {
	/**
	 * 
	 */
	public ArrayList<Cellule> lemonde;
	public ArrayList<Cellule> celmortes;
    private int maxligne ;
    private int maxcolonne;
    private int minligne;
    private int mincolonne;
	public Univers() {
		lemonde = new ArrayList<Cellule>();
		celmortes = new ArrayList<Cellule>();
	}

	public Univers(String s) {

		File f = new File(s);
		Scanner sc = null;
		int deb = 0;
		int finc = 0;
		int maxligne=0;
		int maxcolonne=0;

		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		lemonde = new ArrayList<Cellule>();
		celmortes = new ArrayList<Cellule>();

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
		c1 = st.next();
        int nbligne;
        int nbcolonne;
		if (c1.equals("#P")) {
			deb = Integer.parseInt(st.next());
			this.minligne = deb;
			finc = Integer.parseInt(st.next());
             this.mincolonne = finc;
		}
nbligne=0;
		while ((sc.hasNextLine() && (ligne = sc.nextLine()) != null)) {
			
			 nbcolonne=0;
			int fin = finc;
			for (int i = 0; i <= ligne.length() - 1; i++) {

				if (ligne.charAt(i) == '*') {
					Cellule a = new Cellule(deb, fin, "vivante");
					lemonde.add(a);
				}
				fin++;
				nbcolonne++;
				if (nbcolonne>maxcolonne){
					maxcolonne=nbcolonne;
				}
				
				
			}
			deb++;
			nbligne++;
			if (nbligne>maxligne){
				maxligne=nbligne;
			}
		}
		this.maxligne=maxligne;
		this.maxcolonne=maxcolonne;
		
		
		
	}

	public void Ajoute(Cellule a) {
		if (!lemonde.contains(a) && a.getEtat().equals("vivante"))
			lemonde.add(a);
	}

	public void Supprimer(Cellule b) {
		if (lemonde.contains(b))
			lemonde.remove(b);
	}

	public Cellule contient(coordonnees s) {
		/*
		 * Permet de savoir si la cellule à coordonnée s est une cellule vivante
		 * morte. Si elle n'existe pas, on la crée
		 */
		if (s.valide) {
			Iterator<Cellule> it = lemonde.iterator();
			while (it.hasNext()) {
				Cellule a = (Cellule) it.next();
				if (a.getX() == s.i && a.getY() == s.j
						&& a.getEtat().equals("vivante"))
					return a;
			}
			Iterator<Cellule> it2 = celmortes.iterator();
			while (it2.hasNext()) {
				Cellule a2 = (Cellule) it2.next();
				if (a2.getX() == s.i && a2.getY() == s.j)
					return a2;
			}
		}
		return new Cellule(s.i, s.j, "morte");
	}

	public void AjoutCelMortes() {
		int i, j, dx, dy;
		int nb = 0;
		Iterator<Cellule> it = lemonde.iterator();
		Cellule[] tab = new Cellule[8];
		while (it.hasNext()) {
			Cellule a = it.next();
			if (a.getEtat().equals("vivante")) {
				nb = 0;
				int f = 0;
				i = a.getX() - 1;
				j = a.getY() - 1;
				for (int e = 0; e < 9; e++) {
					dx = i + (e % 3);
					dy = j + (e / 3);

					if (dx != i + 1 || dy != j + 1) {
						tab[f] = contient(new coordonnees(dx, dy));
						f++;
					}
				}

				for (int p = 0; p <= tab.length - 1; p++) {
					if (tab[p].getEtat().equals("morte")
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
	}

	public void TesteListeCelMortes() {
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
					tab[f] = contient(new coordonnees(dx, dy));
					f++;
				}
			}
			for (int p = 0; p <= tab.length - 1; p++) {
				if (tab[p] != null && tab[p].getEtat().equals("vivante"))
					cp++;
			}
			if (cp == 3)
				a.setEtat_suivant("vivante");
			else
				a.setEtat_suivant(a.getEtat());
		}
	}

	public void changerEtat() {
		Iterator<Cellule> it = lemonde.iterator();
		while (it.hasNext()) {
			Cellule a = it.next();
			a.setEtat(a.getEtat_suivant());
			a.setEtat_suivant(" ");

		}
		Iterator<Cellule> it3 = lemonde.iterator();
		while (it3.hasNext()) {
			Cellule a = (Cellule) it3.next();
			if (a.getEtat().equals("morte")) {
				it3.remove();
			}
		}
		Iterator<Cellule> it2 = celmortes.iterator();
		while (it2.hasNext()) {
			Cellule a = (Cellule) it2.next();
			a.setEtat(a.getEtat_suivant());
			a.setEtat_suivant(" ");
			if (a.getEtat().equals("vivante")) {
				lemonde.add(a);
				it2.remove();
			}
		}
		Collections.sort(lemonde);
	}

	public void affichage() {
		Iterator<Cellule> it = lemonde.iterator();
		while (it.hasNext())
			System.out.println(it.next());
	}

	public String toString() {
		StringBuffer res = new StringBuffer();
		Iterator<Cellule> it = lemonde.iterator();
		while (it.hasNext())
			res.append(it.next());
		return res + "";
	}

	public void affiche() {
		for (int i = minligne; i <= (minligne+maxligne)-1; i++) {
			for (int j =mincolonne ; j <= (mincolonne+maxcolonne)-1; j++) {
				if (appartient(i, j)) {
					System.out.print("| * |");
				} else {
					System.out.print("|   |");
				}
			}
			System.out.println();
		}
	}

	public int minimumligne() {
		if (lemonde.isEmpty())
			return 0;
		else {
			Cellule a = (Cellule) lemonde.get(0);
			return a.getX();
		}
	}

	public int minimumcolonne() {
		int min = 0;
		Iterator<Cellule> it = lemonde.iterator();
		if (!it.hasNext())
			return 0;
		Cellule a = (Cellule) it.next();
		min = a.getY();
		while (it.hasNext()) {
			Cellule b = it.next();
			if (b.getY() < min) {
				min = b.getY();
			}
		}
		return min;
	}

	public int maximumligne() {
		if (lemonde.isEmpty())
			return 0;
		Cellule a = (Cellule) lemonde.get(lemonde.size() - 1);
		return a.getX();

	}
// effacer les méthodes : maxcolonne mincolonne ...
	public int maximumcolonne() {
		int max = 0;
		Iterator<Cellule> it = lemonde.iterator();
		if (!it.hasNext())
			return 0;
		Cellule a = (Cellule) it.next();
		max = a.getY();
		while (it.hasNext()) {
			Cellule b = it.next();
			if (b.getY() > max) {
				max = b.getY();
			}
		}
		return max;
	}
// ajouter une condition pour que la boucle s'arrete 
	public boolean appartient(int i, int j) {
		Iterator<Cellule> it = lemonde.iterator();
		while (it.hasNext()) {
			Cellule a = it.next();
			if (a.getX() == i && a.getY() == j) {
				return true;
			}
		}
		return false;
	}

	public boolean jeuMort() {
		if (lemonde.isEmpty())
			return true;
		else
			return false;
	}
// a faire demain
	public boolean jeuStable() {
		ArrayList<Cellule> v, s;
		v = new ArrayList<Cellule>();
		v.addAll(lemonde);
		this.nouvelleGeneration();
		return v.containsAll(lemonde) && lemonde.containsAll(v);

	}

	public void nouvelleGeneration() {
		AjoutCelMortes();
		TesteListeCelMortes();
		changerEtat();
	}
	public int getMinligne() {
		return minligne;
	}

	public void setMinligne(int minligne) {
		this.minligne = minligne;
	}

	public int getMincolonne() {
		return mincolonne;
	}

	public void setMincolonne(int mincolonne) {
		this.mincolonne = mincolonne;
	}

	public int getMaxcolonne() {
		return maxcolonne;
	}

	public void setMaxcolonne(int maxcolonne) {
		this.maxcolonne = maxcolonne;
	}

	public int getMaxligne() {
		return maxligne;
	}

	public void setMaxligne(int maxligne) {
		this.maxligne = maxligne;
	}
}