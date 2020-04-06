package fr.istic.prg1.tree;

import java.util.Stack;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 4.0
 * @since 2015-06-15
 * @param <T>
 *            type formel d'objet pour la classe
 *
 *            Les arbres binaires sont construits par chaînage par références
 *            pour les fils et une pile de pères.
 */
public class BinaryTree<T> {

	/**
	 * Type représentant les noeuds.
	 */
	private class Element {
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public boolean isEmpty() {
			return left == null && right == null;
		}
	}

	private Element root;

	public BinaryTree() {
		root = new Element();
		root.left = new Element();
		root.right = new Element();
	}

	/**
	 * @return Un nouvel iterateur sur l'arbre this. Le noeud courant de
	 *         litérateur est positionné sur la racine de larbre.
	 */
	public TreeIterator iterator() {
	    return new TreeIterator();
	}

	/**
	 * @return true si l'arbre this est vide, false sinon
	 */
	public boolean isEmpty() {
	    return this.iterator().isEmpty();
	}

	/**
	 * Classe représentant les itérateurs sur les arbres binaires.
	 */
	public class TreeIterator implements Iterator<T> {
		private Element currentNode;
		private Stack<Element> stack;

		private TreeIterator() {
			stack = new Stack<Element>();
			currentNode = root;
		}

		/**
		 * L'itérateur se positionnne sur le fils gauche du noeud courant.
		 * 
		 * @pre Le noeud courant nest pas un butoir.
		 */
		@Override
		public void goLeft() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
				assert currentNode.left != null: "Le fils gauche n'existe pas";
				stack.push(currentNode);
				currentNode = currentNode.left;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionnne sur le fils droit du noeud courant.
		 * 
		 * @pre Le noeud courant nest pas un butoir.
		 */
		@Override
		public void goRight() {
			try {
				assert !this.isEmpty() : "le butoir n'a pas de fils";
				assert currentNode.right != null: "Le fils droit n'existe pas";
				stack.push(currentNode);
				currentNode = currentNode.right;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionnne sur le père du noeud courant.
		 * 
		 * @pre Le noeud courant nest pas la racine.
		 */
		@Override
		public void goUp() {
			try {
				assert !stack.empty() : " la racine n'a pas de pere";
				if(!stack.empty()) {
					currentNode = stack.pop();
				}
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * L'itérateur se positionne sur la racine de l'arbre.
		 */
		@Override
		public void goRoot() {
			Element temp = null;
			while(!stack.empty()) {
				temp = stack.pop();
			}
			if(temp != null) {
				currentNode = temp;
			}
			
		}

		/**
		 * @return true si l'iterateur est sur un sous-arbre vide, false sinon
		 */
		@Override
		public boolean isEmpty() {
		    return currentNode.isEmpty();
		}

		/**
		 * @return Le genre du noeud courant.
		 */
		@Override
		public NodeType nodeType() {
			NodeType temp;
			if(currentNode.isEmpty()) {
				temp = NodeType.SENTINEL;
			}
			else if((currentNode.left != null) && (currentNode.right !=null)) {
				stack.push(currentNode);
				currentNode = currentNode.left;
				
				if(nodeType() == NodeType.SENTINEL) {
					temp = NodeType.LEAF;
				}
				else {
					temp = NodeType.SIMPLE_RIGHT;
				}
			}else {
				currentNode = stack.peek();
				currentNode = currentNode.right;
				
				if(nodeType()==NodeType.SENTINEL) {
					temp = NodeType.SIMPLE_LEFT;
				}else {
					temp = NodeType.DOUBLE;
				}
			}
			currentNode = stack.pop();
		    return temp;
		}

		/**
		 * Supprimer le noeud courant de l'arbre.
		 * 
		 * @pre Le noeud courant n'est pas un noeud double.
		 */
		@Override
		public void remove() {
			try {
				assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
				Element temp = new Element();
				switch (this.nodeType()) {
				case SIMPLE_LEFT:
					temp = currentNode.left;
					break;
				case SIMPLE_RIGHT:
					temp = currentNode.right;
					break;
				default:
					break;
				}
				currentNode.value = temp.value;
				currentNode.left = temp.left;
				currentNode.right = temp.right;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Vider le sousarbre référencé par le noeud courant, qui devient
		 * butoir.
		 */
		@Override
		public void clear() {
			if(!isEmpty()) {
				clearTree(this);
			}
		}
		private void clearTree(TreeIterator it) {
			if(!it.isEmpty()) {
				it.goLeft();
				clearTree(it);
				it.goUp();
				it.goRight();
				it.clearTree(it);
				it.goUp();
				it.remove();
			}
		}
		/**
		 * @return La valeur du noeud courant.
		 */
		@Override
		public T getValue() {
		    return currentNode.value;
		}

		/**
		 * Créer un nouveau noeud de valeur v à cet endroit.
		 * 
		 * @pre Le noeud courant est un butoir.
		 * 
		 * @param v
		 *            Valeur à ajouter.
		 */

		@Override
		public void addValue(T v) {
			try {
				//uniquement si on est sur une sentinelle
				assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
				currentNode.left = new Element();
				currentNode.right = new Element();
				currentNode.value = v;						
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		/**
		 * Affecter la valeur v au noeud courant.
		 * 
		 * @param v
		 *            La nouvelle valeur du noeud courant.
		 */
		@Override
		public void setValue(T v) {
			currentNode.value = v;
		}

		private void ancestor(int i, int j) {
			try {
				assert !stack.empty() : "switchValue : argument trop grand";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			Element x = stack.pop();
			if (j < i) {
				ancestor(i, j + 1);
			} else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}
			stack.push(x);
		}

		/**
		 * Échanger les valeurs associées au noeud courant et à son père dordre
		 * i (le noeud courant reste inchangé).
		 * 
		 * @pre i>= 0 et racine est père du noeud courant dordre >= i.
		 * 
		 * @param i
		 *            ordre du père
		 */
		@Override
		public void switchValue(int i) {
			try {
				assert i >= 0 : "switchValue : argument negatif";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
			if (i > 0) {
				ancestor(i, 1);
			}
		}
	}
}