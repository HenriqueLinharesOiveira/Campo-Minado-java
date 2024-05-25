package br.com.hrq.cm.modelo;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excecao.explosaoException;
import modelo.Campo;



public class CampoTeste {

	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3) ;
	}
	
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado =	campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistencia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}	
	
	
	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		 assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAlternaMarcacao() {
		campo.alternaMarcacao();
		 assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternaMarcacaoDuasChamadas() {
		campo.alternaMarcacao();
		campo.alternaMarcacao();
		 assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeAbrirNaoMinaNaoMarcado() {
		 assertTrue(campo.abrir());
	}
	
	@Test
	void testeAbrirNaoMinaMarcado() {
		campo.alternaMarcacao();
		 assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinaMarcado() {
		campo.alternaMarcacao();
		campo.minar();
		 assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirMinaNaoMarcado() {
		campo.minar();
		
		assertThrows(explosaoException.class,( )->{
			campo.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinos1() {
		
		Campo campo11 = new Campo(1, 1);
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
	
		campo.adicionarVizinho(campo22);
		campo.abrir();		
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
		
		
			 }
		
	@Test
	void testeAbrirComVizinos2() {
		
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		
		campo.adicionarVizinho(campo22);
		campo.abrir();		
		
		assertTrue(campo22.isAberto() && campo11.isFechado());
		
			 }
	}	
