package com.adrielcafe.minhaescola;

import com.google.gson.Gson;

public class Util {
	public static final String PREF_URL = "url";
	public static final String PREF_USERDATA = "userData";
	public static final String ABOUT_MESSAGE = "<b>Horário Escolar</b> é um software livre, seu código fonte e instruções de uso estão disponíveis no <a href='https://github.com/adrielcafe/HorarioEscolar'>GitHub</a>."
											 + "<br>Se sua escola utiliza este aplicativo, solicite sua <i>URL do Aluno</i> para ter sua grade de horários sempre atualizada. Caso ainda não use, apresente este aplicativo."
											 + "<br><br>Desenvolvido por:<br>Adriel Café (ac@adrielcafe.com)";
	
	public static Gson gson = new Gson();
	
}