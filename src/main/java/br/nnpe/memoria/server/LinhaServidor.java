package br.nnpe.memoria.server;

import br.nnpe.memoria.Carta;
import br.nnpe.memoria.client.LinhaJogador;

/*
 * LinhaServidor.java
 *
 * Created on 9 de Outubro de 2003, 20:39
 */

/**
 * 
 * @author Paulo Sobreira
 */
public class LinhaServidor extends Thread {

	private javax.swing.JTextArea saidaTxt;
	private java.net.ServerSocket srv;
	public static volatile java.util.Hashtable listaCarta;
	private static java.util.Hashtable listaJog;
	private boolean jogoIniciado;
	private boolean fimdoJogo;
	public static volatile int vez = 0;
	private boolean ativo;

	/** Creates a new instance of LinhaServidor */
	public LinhaServidor(int porta, javax.swing.JTextArea ta) {
		saidaTxt = ta;
		ativo = true;
		listaJog = new java.util.Hashtable();
		try {
			srv = new java.net.ServerSocket(porta);
			saidaTxt.append("Ok eu sou " + srv.getInetAddress().getLocalHost()
					+ " ouvindo na porta " + srv.getLocalPort() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		gerarTabulero();
	}

	public void run() {
		try {
			sleep(20);
			while (ativo && !jogoIniciado && !interrupted()) {
				LinhaJogadorSrv jogador = new LinhaJogadorSrv(srv.accept());
				jogador.setMeuId(String.valueOf(listaJog.size()));
				listaJog.put(jogador.getMeuId(), jogador);
				jogador.start();
				// System.out.println(listaJog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Getter for property ativo.
	 * 
	 * @return Value of property ativo.
	 * 
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * Setter for property ativo.
	 * 
	 * @param ativo
	 *            New value of property ativo.
	 * 
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Getter for property fimdoJogo.
	 * 
	 * @return Value of property fimdoJogo.
	 * 
	 */
	public boolean isFimdoJogo() {
		return fimdoJogo;
	}

	/**
	 * Setter for property fimdoJogo.
	 * 
	 * @param fimdoJogo
	 *            New value of property fimdoJogo.
	 * 
	 */
	public void setFimdoJogo(boolean fimdoJogo) {
		this.fimdoJogo = fimdoJogo;
	}

	/**
	 * Getter for property jogoIniciado.
	 * 
	 * @return Value of property jogoIniciado.
	 * 
	 */
	public boolean isJogoIniciado() {
		return jogoIniciado;
	}

	/**
	 * Setter for property jogoIniciado.
	 * 
	 * @param jogoIniciado
	 *            New value of property jogoIniciado.
	 * 
	 */
	public void setJogoIniciado(boolean jogoIniciado) {
		try {
			if (jogoIniciado == true) {
				java.util.Enumeration e = listaJog.elements();
				String nome1st = "";
				while (e.hasMoreElements()) {
					LinhaJogadorSrv jog = (LinhaJogadorSrv) e.nextElement();
					if (jog.getPontos() > 0)
						jog.setPontos(0);
					nome1st = jog.getNome();
					jog.getOut().writeUTF(LinhaJogador.INICIAR_TABULERO);
				}
				vez = 0;
				enviaMsgGeral("Vez de " + nome1st + " jogar");
			}
			java.util.Enumeration e = listaCarta.elements();
			while (e.hasMoreElements()) {
				Carta c = (Carta) e.nextElement();
				if (c.isPar())
					c.setPar(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.jogoIniciado = jogoIniciado;
	}

	public static void mostraPtsGeral() {
		synchronized (listaJog) {
			java.util.Enumeration e = listaJog.elements();
			while (e.hasMoreElements()) {
				LinhaJogadorSrv jog = (LinhaJogadorSrv) e.nextElement();
				enviaMsgGeral(jog.getNome() + " " + jog.getPontos() + "pts");
			}
		}
	}

	public static void enviaMsgGeral(java.lang.String msg) {
		synchronized (listaJog) {
			java.util.Enumeration e = listaJog.elements();
			while (e.hasMoreElements()) {
				LinhaJogadorSrv jog = (LinhaJogadorSrv) e.nextElement();
				jog.enviaMsg(msg);
			}
		}
	}

	public static void enviaCmdGeral(java.lang.String msg) {
		synchronized (listaJog) {
			java.util.Enumeration e = listaJog.elements();
			try {
				while (e.hasMoreElements()) {
					LinhaJogadorSrv jog = (LinhaJogadorSrv) e.nextElement();
					jog.getOut().writeUTF(msg);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void gerarTabulero() {
		listaCarta = new java.util.Hashtable();
		java.util.ArrayList listemp = new java.util.ArrayList();
		for (int c = 0; c < 10; c++) {
			Carta par1 = new Carta(c);
			Carta par2 = new Carta(c);
			listemp.add(par1);
			listemp.add(par2);
		}
		java.util.Collections.shuffle(listemp);
		java.util.Iterator it = listemp.iterator();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				final Carta c = (Carta) it.next();
				c.setLinha(i);
				c.setColuna(j);
				listaCarta.put(String.valueOf(i) + String.valueOf(j), c);
			}
		}
	}

	public static void vaiProximo() {
		synchronized (listaJog) {
			if (vez < (listaJog.size() - 1))
				vez++;
			else
				vez = 0;
			java.util.Enumeration e = listaJog.elements();
			while (e.hasMoreElements()) {
				LinhaJogadorSrv jog = (LinhaJogadorSrv) e.nextElement();
				if (Integer.parseInt(jog.getMeuId()) == vez)
					enviaMsgGeral("Vez de " + jog.getNome() + " jogar");
				try {
					jog.getOut().writeUTF(LinhaJogador.BEEP);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * Getter for property srv.
	 * 
	 * @return Value of property srv.
	 * 
	 */
	public java.net.ServerSocket getSrv() {
		return srv;
	}

	/**
	 * Setter for property srv.
	 * 
	 * @param srv
	 *            New value of property srv.
	 * 
	 */
	public void setSrv(java.net.ServerSocket srv) {
		this.srv = srv;
	}

}
