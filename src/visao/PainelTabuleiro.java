package visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {

	public PainelTabuleiro (Tabuleiro tabuleiro) {
		 		
		setLayout( new GridLayout(
				tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		tabuleiro.ParaCadaCampo(c -> add(new BotaoCampo(c)));
		tabuleiro.registraObservado(e -> {
			
			SwingUtilities.invokeLater(() -> {
				
				if (e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "GANHOU :)");
				}else {
					JOptionPane.showMessageDialog(this, "PERDEU :( ");
				}
				
				tabuleiro.reiniciar();
			});
		});
	}
}
