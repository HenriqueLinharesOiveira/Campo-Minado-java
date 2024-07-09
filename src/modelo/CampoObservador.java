package modelo;

import java.awt.event.MouseEvent;

public interface CampoObservador {

	public void eventoOcorreu(Campo campo, CampoEvennto evento);

	void mouseDragged(MouseEvent e);

	
}
