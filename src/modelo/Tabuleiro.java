package modelo;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.management.remote.NotificationResult;

public class Tabuleiro implements CampoObservador {
	
	private final int linhas ;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<Consumer<ResultadoEvento>>();
			

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}
	
	public void ParaCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void registraObservado(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		observadores.stream() .forEach( o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
		
	}
	
	
	public void alteraMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternaMarcacao());
	}

	
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registraObservador(this);
				campos.add(campo);
			}
		}
	}
	 
	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	private void sortearMinas() {
		long minasArmadas = 0 ;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio =(int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar () {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvennto evento) {
		if(evento == CampoEvennto.EXPLODIR) {
			mostraminas();
			 notificarObservadores(false);
		}else if (objetivoAlcancado()) {
			 notificarObservadores(true);
		}
	}
	
	private void mostraminas() {
		campos
		.stream()
		.filter(c -> c.isMinado())
		.filter(c -> !c.isMarcado())
		.forEach(c -> c.setAberto(true));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
