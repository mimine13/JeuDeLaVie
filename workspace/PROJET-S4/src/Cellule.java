import java.io.Serializable;
import java.util.ArrayList;

public class Cellule implements Comparable, Serializable {
	/**
	 * 
	 */

	/**
	 * 
	 */

	private int x;
	private int y;
	private String état;
	private String état_suivant;

	public Cellule(int a, int b, String s) {
		this.x = a;
		this.y = b;
		this.état = s;
		this.état_suivant = " ";
	}

	public Cellule() {
		x = 0;
		y = 0;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getEtat() {
		return état;
	}

	public String getEtat_suivant() {
		return état_suivant;
	}

	public void setEtat_suivant(String s) {
		état_suivant = s;
	}

	public void setX(int a) {
		x = a;
	}

	public void setY(int b) {
		y = b;
	}

	public void setEtat(String s) {
		état = s;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Cellule) {
			Cellule z = (Cellule) o;
			if (this.getX() == z.getX()) {
				return this.getY() - z.getY();
			} else {
				return this.getX() - z.getX();
			}
		} else
			return Integer.MAX_VALUE;
	}

	public String toString() {
		return "Cellule [x=" + x + ", y=" + y + ", état=" + état
				+ ", état_suivant=" + état_suivant + "]";

	}

}