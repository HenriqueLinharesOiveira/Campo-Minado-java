package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import modelo.Campo;
import modelo.CampoEvennto;
import modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton 
	implements CampoObservador,MouseListener {

	private final Color BG_PADRAO = new Color(160,160,160);
	private final Color BG_MARCADO = new Color(8,179,247);
	private final Color BG_EXPLOSAO = new Color(189,66,68);
	private final Color TEXTO_VERDE = new Color(0,100,0);
	
	private Campo campo;
	
	public BotaoCampo( Campo campo ) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		campo.registraObservador(this);
		
	}
	
	@Override
	public void eventoOcorreu(Campo campo, CampoEvennto evento) {
		switch (evento) {
		case ABRIR: 	
			aplicarEstisloAbrir();
		break;
		case MARCAR:
			aplicarEstisloMarca();
			break;
		case EXPLODIR:
			aplicarEstisloExplodir();
			break;
		default:
			aplicarEstisloPadrao();
	}
}	
	private void aplicarEstisloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}
	private void aplicarEstisloExplodir() {
		setBackground(BG_EXPLOSAO);
		setForeground(Color.WHITE);
		setText("X");
	}
	private void aplicarEstisloMarca() {
		setBackground(BG_MARCADO);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void aplicarEstisloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if (campo.isMinado()) {
			setBackground(BG_EXPLOSAO);
			return;
		}
		
			setBackground(BG_PADRAO); 
			
			switch (campo.minaNaVizinhanca()) {
			case 1: 
				setForeground(TEXTO_VERDE);
				break;
				
			case 2:
				setForeground(Color.BLUE);
				break;
				
			case 3:
				setForeground(Color.YELLOW);
				break;
				
			case 4:
			case 5:
			case 6:
				setForeground( Color.RED);
				break;
			default:
				setForeground(Color.PINK);
				
			}
			
			String valor = !campo.vizinhancaSegura() ? campo.minaNaVizinhanca() + "" : "";
			setText(valor);
	}

	//interface dos evento do mouse

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		}else {
			campo.alternaMarcacao();
		}
	} 
	
	public void mouseClicked(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e ) {}
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	}
