package br.com.hrq.cm;

import modelo.Tabuleiro;
import visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(7, 7, 5);
		new TabuleiroConsole(tabuleiro);
	}
}
