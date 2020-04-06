package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;

/**
 * @author Mickael Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {
		Iterator<Node> it = this.iterator();
		int tmpX = 256, tmpY = 256;
		while (!it.isEmpty()) {
			//Horizontal
			if (tmpX == tmpY) {
				if (x < (tmpX/2))
					it.goLeft();
				else
					it.goRight();
				tmpX = tmpX/2;
			}
			//Vertical
			else {
				if (y < (tmpY/2))
					it.goLeft();
				else
					it.goRight();
				tmpY = tmpY/2;
			}
		}
		it.goUp();
		return (it.getValue().equals(Node.valueOf(1)));
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {
		Iterator<Node> it = image2.iterator();
		Iterator<Node> itThis = this.iterator();
		itThis.clear();
		if (!it.isEmpty())
			affectTP(it, itThis);
	}
	
	private void affectTP(Iterator<Node> it, Iterator<Node> itThis) {
		if (!itThis.isEmpty()) {
			itThis.addValue(it.getValue().copy());
			it.goLeft();
			itThis.goLeft();
			affectTP(it, itThis);
			it.goUp();
			itThis.goUp();
			it.goRight();
			itThis.goRight();
			affectTP(it, itThis);
			it.goUp();
			itThis.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it = image2.iterator();
		Iterator<Node> itThis = this.iterator();
		itThis.clear();
		if (!it.isEmpty())
			rotate180TP(it, itThis);
	}
	
	private void rotate180TP (Iterator<Node> it, Iterator<Node> itThis) {
		if (!it.isEmpty()) {
			int e = it.getValue().state;
			itThis.addValue(Node.valueOf(e));
			it.goLeft();
			itThis.goRight();
			rotate180TP(it, itThis);
			it.goUp();
			itThis.goUp();
			it.goRight();
			itThis.goLeft();
			rotate180TP(it, itThis);
			it.goUp();
			itThis.goUp();
		}
	}
	
	/**
	 * this devient rotation de image2 à 90 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		//Get an iterator
		Iterator<Node> it = this.iterator();
		//Throw the invert
		videoInverseTP(it);
	}
	private void videoInverseTP(Iterator<Node> it) {
		//If not empty
		if (!it.isEmpty()) {
			Node ns = it.getValue();
			//Process this one
			if (ns.state == 1) {
				it.setValue(Node.valueOf(0));
			}
			else if (ns.state == 0) {
				it.setValue(Node.valueOf(1));
			}
			//Process the sons
			it.goLeft();
			videoInverseTP(it);
			it.goUp();
			it.goRight();
			videoInverseTP(it);
			it.goUp();
		}
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		
	}

	/**
	 * this devient quart superieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Le quart superieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		//Get iterators
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();
		Iterator<Node> it3 = this.iterator();

		//Clear this image
		it3.clear();
		if (!it1.isEmpty() && !it2.isEmpty()){
			intersectionTP(it1, it2, it3);
		}
	}
	
	private void intersectionTP(Iterator<Node> it1, Iterator<Node> it2, Iterator<Node> itThis) {
		if (!it1.isEmpty() && !it2.isEmpty()){
			Node ns1 = it1.getValue();
			Node ns2 = it2.getValue();

			if (ns1.equals(Node.valueOf(0)) || ns2.equals(Node.valueOf(0))) {
				itThis.addValue(Node.valueOf(0));
			}
			else if (ns1.equals(Node.valueOf(1)) && ns2.equals(Node.valueOf(1))) {
				itThis.addValue(Node.valueOf(1));
			}
			else if (ns1.equals(Node.valueOf(2)) && ns2.equals(Node.valueOf(2))) {
				itThis.addValue(Node.valueOf(2));
			}
			else {
				itThis.addValue(Node.valueOf(2));
				if (ns1.equals(Node.valueOf(1))) {
					it1.goLeft();
					it1.addValue(Node.valueOf(1));
					it1.goUp();
					it1.goRight();
					it1.addValue(Node.valueOf(1));
					it1.goUp();
				} else {
					it2.goLeft();
					it2.addValue(Node.valueOf(1));
					it2.goUp();
					it2.goRight();
					it2.addValue(Node.valueOf(1));
					it2.goUp();
				}
			}
			it1.goLeft();
			it2.goLeft();
			itThis.goLeft();
			intersectionTP(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();
			it1.goRight();
			it2.goRight();
			itThis.goRight();
			intersectionTP(it1, it2, itThis);
			it1.goUp();
			it2.goUp();
			itThis.goUp();

		}
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		Iterator<Node> it = this.iterator();
		if (!it.isEmpty()) {
			return testDiagonalTP(it);
		} else {
			System.out.println("Image vide!");
			return false;
		}
	}
	
	private boolean testDiagonalTP(Iterator<Node> it) {
		boolean result = true;
		if (!it.isEmpty()) {
			if (it.getValue().equals(Node.valueOf(0))) {
				result = false;
			}
			else if (it.getValue().equals(Node.valueOf(2))) {
				//Go to Left 1st generation
				it.goLeft();
				if (it.getValue().equals(Node.valueOf(1))) {
					//Go Up 1st generation
					it.goUp();
					//Go to Right 1st generation
					it.goRight();
					if (it.getValue().equals(Node.valueOf(0))) {
						result = false;
					} else if (it.getValue().equals(Node.valueOf(2))) {
						// go to Right 2nd generation
						it.goRight();
						if (!testDiagonalTP(it)) {
							result = false;
						}
						it.goUp();
					}
					it.goUp();
				} else if (it.getValue().equals(Node.valueOf(0))) {
					result = false;
				} else {
					it.goLeft();
					if (!testDiagonalTP(it)) {
						result = false;
					}
					it.goUp();
				}
				it.goUp();
			}
		}
		return result;
	}

	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentes par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {
		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction à écrire");
		System.out.println("-------------------------------------------------");
		System.out.println();
	    return false;
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		Iterator<Node> itThis = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		if (it2.isEmpty()) {
			System.out.println("Image2 vide!");
			return false;
		} else {
			return isIncludedInTP(itThis, it2);
		}
	}
	
	private boolean isIncludedInTP (Iterator<Node> itThis, Iterator<Node> it2) {
		boolean result = true;
		if (!it2.isEmpty()) {
			if (!itThis.isEmpty()) {
				if (it2.getValue().equals(Node.valueOf(1)) || itThis.getValue().equals(Node.valueOf(0))) {
					//On ne fait rien
				} else if (it2.getValue().equals(Node.valueOf(0)) || itThis.getValue().equals(Node.valueOf(1))) {
					result = false;
				} else {
					itThis.goLeft();
					it2.goLeft();
					if (isIncludedInTP(itThis, it2)) {
						itThis.goUp();
						it2.goUp();
						itThis.goRight();
						it2.goRight();
						if (!isIncludedInTP(itThis, it2)) {
							result = false;
						}
					} else {
						result = false;
					}
					itThis.goUp();
					it2.goUp();
				}
			}
		}
		return result;
	}

}
