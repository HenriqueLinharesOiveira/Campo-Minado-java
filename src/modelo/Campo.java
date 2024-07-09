package modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false; 
	private boolean minado = false;
	private boolean marcado = false;
	
	private List <Campo> vizinhos = new ArrayList<Campo>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	
	public Campo(int linha,int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registraObservador(CampoObservador observador) {
		observadores.add(observador); 
	}
	
	private void notificarObservadores(CampoEvennto evento) {
		observadores.stream()
		.forEach(o -> o.eventoOcorreu(this, evento));
	}
	 
	public boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
	}
	
	public void alternaMarcacao() {
		if (!aberto) {
			marcado = !marcado;

			if (marcado) {
				notificarObservadores(CampoEvennto.MARCAR);
			}else {
				notificarObservadores(CampoEvennto.DESMARCAR);
			}
			
		}
	}
	
	public boolean abrir() {
		
		if (!aberto && !marcado) {
			aberto = true;
			
			
			if (minado){
				notificarObservadores(CampoEvennto.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
		return false;
		}
	}
	
	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public void minar() {
		minado = true;
		}
	public boolean isMinado() {
		 return minado;
	}
	
	public boolean isMarcado(){
		return marcado;
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObservadores(CampoEvennto.ABRIR);
		}
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minaNaVizinhanca() {
		return (int)vizinhos.stream().filter(v -> v.minado ).count();
	}
	 void reiniciar () {
		 aberto = false; 
		 minado = false;
		 marcado = false;
		 notificarObservadores(CampoEvennto.REINICIAR);
	 }
	 
	 
	 }
