package br.nnpe.memoria.server;

import br.nnpe.memoria.Carta;
import br.nnpe.memoria.client.LinhaJogador;

/*
 * LinhaJogadorSrv.java
 *
 * Created on 9 de Outubro de 2003, 20:36
 */

/**
 * 
 * @author Paulo Sobreira
 */
public class LinhaJogadorSrv extends LinhaJogador {

	private Carta carta1, carta2;

	/** Creates a new instance of LinhaJogadorSrv */
	public LinhaJogadorSrv(java.net.Socket con) {
		super(con);

	}

	public void run() {
		try {
			// sleep(20);
			while (!interrupted() && getCon().isConnected()) {
				// String cmd=getIn().readUTF();
				// if (cmd!=null)
				// precessaCmd(cmd);
				precessaCmd(getIn().readUTF());
			}
			getOut().close();
			getIn().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void precessaCmd(java.lang.String cmd) {
		java.util.StringTokenizer tokens = new java.util.StringTokenizer(cmd,
				SEPARADOR);
		String t1 = tokens.nextToken();
		if (t1 == null)
			return;
		if (NOME.equals(t1)) {
			this.setNome(tokens.nextToken());
			LinhaServidor.enviaMsgGeral(getNome() + " Conectado");
		} else if (MSG.equals(t1)) {
			LinhaServidor.enviaMsgGeral(getNome() + " Diz "
					+ tokens.nextToken());
		} else if (JOGAR.equals(t1)) {
			if (this.getMeuId().equals(String.valueOf(LinhaServidor.vez)))
				synchronized (LinhaServidor.listaCarta) {
					efetuarJogada(tokens.nextToken());
				}
			else
				try {
					getOut().writeUTF(NAO_HE_SUA_VEZ);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

		}
	}

	public void efetuarJogada(String cartakey) {
		if (carta1 == null) {
			carta1 = (Carta) LinhaServidor.listaCarta.get(cartakey);
			if (!carta1.isPar())
				LinhaServidor.enviaCmdGeral(VIRA + SEPARADOR + cartakey
						+ SEPARADOR + carta1.getId());
			else
				carta1 = null;
			return;
		}
		if ((carta1 != null) && (carta2 == null)) {

			carta2 = (Carta) LinhaServidor.listaCarta.get(cartakey);

			if ((!carta1.isPar()) && (!carta2.isPar())) {
				LinhaServidor.enviaCmdGeral(VIRA + SEPARADOR + cartakey
						+ SEPARADOR + carta2.getId());
				synchronized (LinhaServidor.listaCarta) {
					try {
						sleep(3000);
						if ((carta1.getId() == carta2.getId())
								&& (!carta1.isPar()) && (!carta2.isPar())) {

							setPontos(getPontos() + 1);
							carta1.setPar(true);
							carta2.setPar(true);
							carta1 = null;
							carta2 = null;
							LinhaServidor.mostraPtsGeral();
							// LinhaServidor.vaiProximo();
							return;
						}
						if (!carta1.isPar()) {
							LinhaServidor.enviaCmdGeral(DESVIRA + SEPARADOR
									+ carta1.getLinha() + carta1.getColuna());

							carta1 = null;
						}
						if (!carta2.isPar()) {
							LinhaServidor.enviaCmdGeral(DESVIRA + SEPARADOR
									+ carta2.getLinha() + carta2.getColuna());

							carta2 = null;

						}
						LinhaServidor.vaiProximo();

						return;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} else {
				LinhaServidor.enviaCmdGeral(DESVIRA + SEPARADOR
						+ carta1.getLinha() + carta1.getColuna());
				carta1 = null;
			}
		}
	}
}
