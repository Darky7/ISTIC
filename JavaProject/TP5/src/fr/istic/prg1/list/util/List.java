package fr.istic.prg1.list.util;

import fr.istic.prg1.list_util.SuperT;

import fr.istic.prg1.list_util.Iterator;

public class List<T extends SuperT> {
	// liste en double chainage par references

	private class Element {
		// element de List<Item> : (Item, Element, Element)
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}
	} // class Element

	public class ListIterator implements Iterator<T> {
		private Element current;

		private ListIterator() { 
			this.current = List.this.flag.right;
		}

		@Override
		public void goForward() { 
			this.current = this.current.right;
		}

		@Override
		public void goBackward() { 
			this.current = this.current.left;
		}

		@Override
		public void restart() { 
			this.current = List.this.flag.right;
		}

		@Override
	        public boolean isOnFlag() { 
			return (this.current.value == null);
		}

		@Override
		public void remove() {
			try {
				assert current != flag : "\n\n\nimpossible de retirer le drapeau\n\n\n";
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override		 
		public T getValue() {
			return this.current.value;
		}

		@Override
	    public T nextValue() {
			this.current = this.current.right;
			return this.current.value;
		}

		@Override
		public void addLeft(T v) {
			Element newElement = new Element();
			newElement.value = v;
			newElement.right = this.current;
			newElement.left = this.current.left;

			this.current.left.right = newElement;
			this.current.left = newElement;
			this.current = newElement;
		}

		@Override
		public void addRight(T v) {
			Element newElement = new Element();
			newElement.value = v;
			newElement.right = this.current.right;
			newElement.left = this.current;

			this.current.right.left = newElement;
			this.current.right = newElement;
			this.current = newElement;
		}

		@Override
		public void setValue(T v) {
			this.current.value = v;
		}

		@Override
		public String toString() {
			return "parcours de liste : pas d'affichage possible \n";
		}

	} // class IterateurListe

	private Element flag;

	public List() { 
		// we create a flag and link it to itself
		this.flag = new Element();
		this.flag.right = this.flag;
		this.flag.left = this.flag;
	}

	public ListIterator iterator() { return new ListIterator(); }

	public boolean isEmpty() { 
		ListIterator it = this.iterator();
		return (it.isOnFlag()); 
	}

	public void clear() {
		ListIterator it = this.iterator();
		while (!it.isOnFlag()) {
			it.remove();
			it.goForward();
		}
	}

	public void setFlag(T v) { 
		Element newFlag = new Element();
		newFlag.value = v;
		newFlag.right = this.flag.right;
		newFlag.left = this.flag.left;
		this.flag = newFlag;
	}

	public void addHead(T v) { 
		ListIterator it = this.iterator();
		it.addLeft(v);
	}

	public void addTail(T v) { 
		ListIterator it = this.iterator();
		while (!it.isOnFlag()) {
			it.goForward();
		}
		it.addLeft(v);
	}

	@SuppressWarnings("unchecked")
	public List<T> clone() {
		List<T> nouvListe = new List<T>();
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().clone());
			p.goForward();
		}
		return nouvListe;
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}
