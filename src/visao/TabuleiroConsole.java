package visao;

import java.security.DrbgParameters.NextBytes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import excecao.SairException;
import excecao.explosaoException;
import modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro  tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
  public TabuleiroConsole(Tabuleiro tabuleiro) {
	  this.tabuleiro = tabuleiro;
	  
	  executarjogo();
  }

  private void executarjogo() {
	try {
		boolean continuar = true;
		
		while (continuar) {
			cicloDoJogo();
			
			System.out.println("Outra partida (S/n) ");
			String resposta = entrada.nextLine();
			
			if ("n".equalsIgnoreCase(resposta )) {
				continuar = false;
			}else {
				tabuleiro.reiniciar();
			}
		}
		
	} catch (SairException e) {
		System.out.println("!!!!!!TCHAU!!!!!!!!");
	}finally {
		entrada.close();
	}
  }
  
  private void cicloDoJogo() {
	try {
		
		while (!tabuleiro.objetivoAlcancado()) {
			System.out.println(tabuleiro);
			
			String digitado = CapturaValorDigitado("digite (x,y):");
			
			
		Iterator<Integer> xy =	Arrays.stream(digitado.split(","))
			.map(e -> Integer.parseInt(e.trim())).iterator();
			
		 digitado = CapturaValorDigitado("1 - abrir ou 2 - (des)Marcar:");
		
		 if ("1".equals(digitado)) {
			 tabuleiro.abrir(xy.next(), xy.next() );
		 }else if("2".equals(digitado)) {
			tabuleiro.abrir(xy.next(), xy.next());
		 }
		}
		
		System.out.println(tabuleiro );
		System.out.println("Você ganhou");
	} catch (explosaoException e) {
		System.out.println(tabuleiro);
		System.out.println("Você perdeu!!");
	}
  }
  
  private String CapturaValorDigitado(String texto) {
	  System.out.println(texto);
	  String digitado = entrada.nextLine();
	  
	  if("sair".equalsIgnoreCase(digitado)) {
		  throw new SairException();
	  }
	  
	  return digitado;
  }
  
}
