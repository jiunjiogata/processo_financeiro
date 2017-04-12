package br.com.processofinanceiro.domain.negocio.utils;

public class Formatacao {

	public static String removerEspacosQualquer(String qualquerString) {
		String stringSemEspaco = null;
		if (qualquerString != null) {
			stringSemEspaco = qualquerString.replaceAll(" ", "");
		}

		return stringSemEspaco;

	}

	public static boolean verificarIntervalo(String str, int intervaloInicial, int intervaloFinal) {
		return menorque(str, intervaloInicial) || maiorque(str, intervaloFinal);
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || ("").equals(str);
	}

	private static boolean menorque(String str, int tamanho) {
		return str.length() < tamanho;
	}

	private static boolean maiorque(String str, int tamanho) {
		return str.length() > tamanho;
	}

}
