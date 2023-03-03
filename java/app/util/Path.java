package app.util;

public class Path {
	public static class Web {
		public static final String INDEX = "/";
		public static final String PRIJAVA = "/prijava";
		public static final String KORISNICI = "/korisnici";
		public static final String NOVI_UPITNICI = "/novi_upitnici";
		public static final String ZADNJI_POTRESI_ADMIN = "/zadnji_potresi_admin";
		public static final String ZADNJI_POTRESI = "/zadnji_potresi";
		public static final String STARIJI_POTRESI_ADMIN = "/stariji_potresi_admin";
		public static final String STARIJI_POTRESI = "/stariji_potresi";
		public static final String ODJAVA ="/odjava";
		public static final String REGISTRACIJA = "/registracija";
		public static final String SVI_POTRESI = "/svi_potresi";
		public static final String IZBRISI_KORISNIKA = "/korisnici/izbrisi/:username";
		public static final String IZBRISI_STARI_POTRES = "/stariji_potresi_admin/izbrisi/:idPotres";
		public static final String IZBRISI_ZADNJI_POTRES = "/zadnji_potresi_admin/izbrisi/:idPotres";
		public static final String POMAKNI_POTRES = "/zadnji_potresi_admin/pomakni/:idPotres";
		public static final String NOVI_POTRES = "/novi_potres";
		public static final String DODIJELI_UPITNIK = "/dodijeli_upitnik/:idUpitnik";
		public static final String DODIJELI_UPITNIK_DRUGOM = "/dodijeli_upitnik_drugom/:idPotres/:idUpitnik";
		public static final String IZBRISI_UPITNIK = "/izbrisi_upitnik/:idUpitnik";
		public static final String IZBRISI_UPITNIK_POTRES = "/izbrisi_upitnik_potres/:idPotres/:idUpitnik";
		public static final String ISPUNJENI_UPITNIK = "/ispunjeni_upitnik/:idUpitnik";
		public static final String ISPUNI_UPITNIK_ZA_POTRES = "/ispuni_upitnik/:idPotres";
		public static final String ISPUNI_UPITNIK = "/ispuni_upitnik";
		public static final String KARTA = "/karta/:idPotres";
		public static final String POTRES = "/potres/:idPotres";
		public static final String POTRES_ADMIN = "/potres_admin/:idPotres";
		public static final String UPDATE_LOZINKU = "/update_lozinku";
		public static final String DOWNLOAD_UPITNICI = "/potres/download_upitnici/:idPotres";
		public static final String DOWNLOAD_EARTHQUAKE_INFO = "/download_potres_info/:idPotres";
		public static final String BEZ_PROMJENE_LOZINKE = "/bez_promjene_lozinke";
		public static final String OBAVIJEST = "/obavijest";
	}

	public static class Templates {
		public static  final String INDEX_PAGE = "/templates/index.ftl";
		public static final String ALL_EARTHQUAKES = "/templates/svi.ftl";
		public static final String ALL_EARTHQUAKES_ADMIN = "/templates/sviAdmin.ftl";
		public static final String LOGIN = "/templates/prijava.ftl";
		public static final String REGISTER = "/templates/registracija.ftl";
		public static final String USERS = "/templates/korisnici.ftl";
		public static final String NEW_EARTHQUAKE = "/templates/noviPotres.ftl";
		public static final String NEW_QUESTIONNAIRES = "/templates/noviUpitnici.ftl";
		public static final String EARTHQUAKE = "/templates/potres.ftl";
		public static final String OLD_EARTHQUAKES = "/templates/stariji.ftl";
		public static final String OLD_EARTHQUAKES_ADMIN = "/templates/starijiAdmin.ftl";
		public static final String RECENT_EARTHQUAKES = "/templates/zadnji.ftl";
		public static final String RECENT_EARTHQUAKES_ADMIN = "/templates/zadnjiAdmin.ftl";
		public static final String FILLED_QUESTIONNAIRE = "/templates/ispunjeni_upitnik.ftl";
		public static final String UPDATE_PASSWORD = "/templates/updateLozinke.ftl";
		public static final String MAP = "/templates/karta.ftl";
		public static final String FILL_QUESTIONNAIRE = "/templates/upitnik.ftl";
		public static final String EARTHQUAKE_ADMIN = "/templates/potresAdmin.ftl";
		public static final String NOTIFICATION = "/templates/obavijest.ftl";
	}

}
