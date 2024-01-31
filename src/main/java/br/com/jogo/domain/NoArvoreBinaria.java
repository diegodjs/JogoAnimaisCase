package br.com.jogo.domain;

public class NoArvoreBinaria {

	public int elemento;

	public NoArvoreBinaria noEsquerdo;

	public NoArvoreBinaria noDireito;

	public int parent;

	public String valor;

	public NoArvoreBinaria(int elemento, String valor, int parent) {
		// System.out.println(elemento + " - " + valor);
		this.elemento = elemento;
		this.valor = valor;
		this.parent = parent;
	}

	public NoArvoreBinaria() {
	}
}
