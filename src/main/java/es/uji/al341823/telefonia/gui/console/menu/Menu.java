package es.uji.al341823.telefonia.gui.console.menu;

import java.util.Scanner;

/**
 * Created by Juanjo on 15/03/2017.
 */
public abstract class Menu {

	public static final Scanner scanner = new Scanner(System.in);

	private final Menu padre;

	protected Menu(Menu padre) {
		super();
		this.padre = padre;
	}

	public Menu getPadre() {
		return this.padre;
	}

	public abstract void mostrar();

	public abstract String getTitulo();

	public abstract Menu[] getSubmenus();
}
