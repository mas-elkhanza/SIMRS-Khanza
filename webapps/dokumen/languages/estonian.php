<?php
// $Id: english.php 115 2009-01-10 11:18:58Z soeren $
// English Language Module for v2.3 (translated by the QuiX project)


global $_VERSION;

$GLOBALS["charset"] = "UTF-8";
$GLOBALS["text_dir"] = "ltr"; // ('ltr' for left to right, 'rtl' for right to left)
$GLOBALS["date_fmt"] = "Y/m/d H:i";
$GLOBALS["error_msg"] = array(
	// error
	"error"			=> "Viga",
	"message"			=> "Sõnum(it)",
	"back"			=> "Tagasi",

	// root
	"home"			=> "Kodukaust on puudu, kontrolli seadeid.",
	"abovehome"		=> "Praegune kasut ei saa olla kodukaustast ülevalpool.",
	"targetabovehome"	=> "Sihtkaust ei saa olla kodukaustast ülevalpool.",

	// exist
	"direxist"		=> "Seda kausta pole olemas.",
	//"filedoesexist"	=> "See fail on juba olemas.",
	"fileexist"		=> "Seda faili pole olemas.",
	"itemdoesexist"		=> "See objekt on juba olemas.",
	"itemexist"		=> "Seda objekti pole olemas.",
	"targetexist"		=> "Sihtkausta pole olemas.",
	"targetdoesexist"	=> "Sihtobjekt on juba olemas.",

	// open
	"opendir"		=> "Kausta avamine ebaõnnestus.",
	"readdir"		=> "Kausta lugemine ebaõnnestus.",

	// access
	"accessdir"		=> "Sul pole sellele kaustale ligipääsu.",
	"accessfile"		=> "Sul pole sellele failile ligipääsu.",
	"accessitem"		=> "Sul pole sellele objektile ligipääsu.",
	"accessfunc"		=> "Sul pole luba seda funktsiooni kasutada.",
	"accesstarget"		=> "Sul pole sellele sihtkaustale ligipääsu.",

	// actions
	"permread"		=> "Ligipääsuõiguste lugemine ebaõnnestus.",
	"permchange"		=> "Ligipääsuõiguste muutmine ebaõnnestus. (see juhtub enamasti faili omanduse probleemide pärast - nt. kui HTTP kasutaja ('wwwrun' või 'nobody') ja FTP kasutaja pole samad)",
	"openfile"		=> "Faili avamine ebaõnnestus.",
	"savefile"		=> "Faili salvestamine ebaõnnestus.",
	"createfile"		=> "Faili loomine ebaõnnestus.",
	"createdir"		=> "Kausta loomine ebaõnnestus.",
	"uploadfile"		=> "Faili üles laadimine ebaõnnestus.",
	"copyitem"		=> "Kopeerimine ebaõnnestus.",
	"moveitem"		=> "Liigutamine ebaõnnestus.",
	"delitem"		=> "Kustutamine ebaõnnestus.",
	"chpass"		=> "Parooli muutmine ebaõnnestus.",
	"deluser"		=> "Kasutaja kustutamine ebaõnnestus.",
	"adduser"		=> "Kasutaja lisamine ebaõnnestus.",
	"saveuser"		=> "Kasutaja salvestamine ebaõnnestus.",
	"searchnothing"		=> "Sa pead sisestama ka otsingusõna(d).",

	// misc
	"miscnofunc"		=> "Funktsiooni ei saa kasutada.",
	"miscfilesize"		=> "Fail ületab maksimaalset suurust.",
	"miscfilepart"		=> "Fail laeti üles ainult osaliselt.",
	"miscnoname"		=> "Sa pead määrama ka nime.",
	"miscselitems"		=> "Sa pole valinud ühtegi objekti.",
	"miscdelitems"		=> "Oled sa kindel, et tahad kustutada need {0} item(s)?",
	"miscdeluser"		=> "Oled sa kindel, et tahad kustutada kasutajat '{0}'?",
	"miscnopassdiff"	=> "Uus parool on sama, mis praegune parool.",
	"miscnopassmatch"	=> "Paroolid ei kattu.",
	"miscfieldmissed"	=> "Sul jäi oluline lahter täitmata.",
	"miscnouserpass"	=> "kasutajanimi või parool pole õige.",
	"miscselfremove"	=> "Sa ei saa iseennast kustutada.",
	"miscuserexist"		=> "Selline kasutaja on juba olemas.",
	"miscnofinduser"	=> "Ei leia kasutajat.",
	"extract_noarchive" => "See pole lahtipakitav arhiivifail.",
	"extract_unknowntype" => "Tundmatu arhiivifaili tüüp",

	'chmod_none_not_allowed' => 'Õiguste muutmine <none>-ks pole lubatud',
	'archive_dir_notexists' => 'Määratud salvestamiste sihtkausta ei eksisteeri.',
	'archive_dir_unwritable' => 'Palun määra kirjutatav kaust, kuhu arhiivifail salvestada.',
	'archive_creation_failed' => 'Arhiivifaili salvestamine ebaõnnestus'
	
);
$GLOBALS["messages"] = array(
	// links
	"permlink"		=> "Muuda õigusi",
	"editlink"		=> "Muuda",
	"downlink"		=> "Lae alla",
	"uplink"		=> "Üles",
	"homelink"		=> "Pealeht",
	"reloadlink"		=> "Värskenda",
	"copylink"		=> "Kopeeri",
	"movelink"		=> "Liiguta",
	"dellink"		=> "Kustuta",
	"comprlink"		=> "Arhiveeri",
	"adminlink"		=> "Admin",
	"logoutlink"		=> "Välju",
	"uploadlink"		=> "Lae üles",
	"searchlink"		=> "Otsi",
	'difflink'     		=> 'Erinevus',
	"extractlink"	=> "Ava arhiiv",
	'chmodlink'		=> 'Muuda (chmod) õigusi (Kaust/Fail(id))', // new mic
	'mossysinfolink'	=> 'eXtploreri süsteemiinfo (eXtplorer, Server, PHP, mySQL)', // new mic
	'logolink'		=> 'Mine eXtploreri veebilehele (avaneb uus aken)', // new mic

	// list
	"nameheader"		=> "Nimi",
	"sizeheader"		=> "Suurus",
	"typeheader"		=> "Tüüp",
	"modifheader"		=> "Muudetud",
	"permheader"		=> "Õigused",
	"actionheader"		=> "Tegevused",
	"pathheader"		=> "Asukoht",

	// buttons
	"btncancel"		=> "Loobu",
	"btnsave"		=> "Salvesta",
	"btnchange"		=> "Muuda",
	"btnreset"		=> "Taasta",
	"btnclose"		=> "Sulge",
	"btnreopen"			=> "Ava uuesti",
	"btncreate"		=> "Loo",
	"btnsearch"		=> "Otsi",
	"btnupload"		=> "Lae üles",
	"btncopy"		=> "Kopeeri",
	"btnmove"		=> "Liiguta",
	"btnlogin"		=> "Sisene",
	"btnlogout"		=> "Välju",
	"btnadd"		=> "Lisa",
	"btnedit"		=> "Muuda",
	"btnremove"		=> "Eemalda",
	"btndiff"			=> "Erinevus",
	
	// user messages, new in joomlaXplorer 1.3.0
	'renamelink'	=> 'Nimeta ümber',
	'confirm_delete_file' => 'Oled sa kindel, et soovid seda faili kustutada? <br />%s',
	'success_delete_file' => 'Kirje(d)on kustutatud.',
	'success_rename_file' => 'Kausta/faili %s uus nimi on %s.',
	
	// actions
	"actdir"		=> "Kaust",
	"actperms"		=> "Muuda õiguseid",
	"actedit"		=> "Muuda faili",
	"actsearchresults"	=> "Otsingu tulemused",
	"actcopyitems"		=> "Kopeeri kirje(d)",
	"actcopyfrom"		=> "Kopeeri kaustast /%s kausta /%s ",
	"actmoveitems"		=> "Liiguta kirje(d)",
	"actmovefrom"		=> "Liiguta kaustast /%s kausta /%s ",
	"actlogin"		=> "Sisene",
	"actloginheader"	=> "Sisene eXtplorerisse",
	"actadmin"		=> "Administreerimine",
	"actchpwd"		=> "Muuda parooli",
	"actusers"		=> "Kasutajad",
	"actarchive"		=> "Arhiveeri kirje(id)",
	"actupload"		=> "Lae fail(id) üles",

	// misc
	"miscitems"		=> "Kirje(d)",
	"miscfree"		=> "Vaba",
	"miscusername"		=> "Kasutajanimi",
	"miscpassword"		=> "Parool",
	"miscoldpass"		=> "Vana parool",
	"miscnewpass"		=> "Uus parool",
	"miscconfpass"		=> "Korda parooli",
	"miscconfnewpass"	=> "Korda uut parooli",
	"miscchpass"		=> "Muuda parooli",
	"mischomedir"		=> "Kodukaust",
	"mischomeurl"		=> "Kodukausta URL",
	"miscshowhidden"	=> "Näita peidetud faile",
	"mischidepattern"	=> "Peida laiend",
	"miscperms"		=> "Õigused",
	"miscuseritems"		=> "(nimi, kodukaust, näita peidetud faile, õigused, aktiivne)",
	"miscadduser"		=> "lisa kasutaja",
	"miscedituser"		=> "muuda kasutajat '%s'",
	"miscactive"		=> "Aktiivne",
	"misclang"		=> "Keel",
	"miscnoresult"		=> "Ei leidnud midagi.",
	"miscsubdirs"		=> "Otsi alamkaustadest",
	"miscpermnames"		=> array("Ainult vaatamine","Muutmine","Parooli muutmine","Parooli muutmine ja vahetamine","Administraator"),
	"miscyesno"		=> array("Jah","Ei","J","E"),
	"miscchmod"		=> array("Omanik", "Grupp", "Teised"),
	'misccontent'		=> "Faili sisu",

	// from here all new by mic
	'miscowner'			=> 'Omanik',
	'miscownerdesc'		=> '<strong>Kirjeldus:</strong><br />Kasutaja (UID) /<br />Grupp (GID)<br />Praegused õigused:<br /><strong> %s ( %s ) </strong>/<br /><strong> %s ( %s )</strong>',

	// sysinfo (new by mic)
	'simamsysinfo'		=> "eXtplorer süsteemi info",
	'sisysteminfo'		=> 'Süsteemiinfo',
	'sibuilton'			=> 'Operatsioonisüsteem',
	'sidbversion'		=> 'Andmebaasi versioon (MySQL)',
	'siphpversion'		=> 'PHP versioon',
	'siphpupdate'		=> 'INFORMATSIOON: <span style="color: red;">Sinu poolt kasutatav PHP versioon <strong>pole</strong> ajakohane!</span><br />Kõikide Mambo ja selle lisade funktsioonide toimimseks,<br />peaksid sa kasutama vähemalt <strong>PHP versiooni 4.3</strong>!',
	'siwebserver'		=> 'Veebiserver',
	'siwebsphpif'		=> 'Veebiserver - PHP kasutajaliides',
	'simamboversion'	=> 'eXtplorer versioon',
	'siuseragent'		=> 'Veebilehitseja versioon',
	'sirelevantsettings' => 'Olulised PHP seaded',
	'sisafemode'		=> 'Safe Mode',
	'sibasedir'			=> 'Open basedir',
	'sidisplayerrors'	=> 'PHP vead',
	'sishortopentags'	=> 'Short Open Tags',
	'sifileuploads'		=> 'Failide üleslaadimised',
	'simagicquotes'		=> 'Magic Quotes',
	'siregglobals'		=> 'Register Globals',
	'sioutputbuf'		=> 'Väljundi puhverdamine',
	'sisesssavepath'	=> 'Sessioni salvestamise kaust',
	'sisessautostart'	=> 'Session automaatne alustamine',
	'sixmlenabled'		=> 'XML on sisse lülitatud',
	'sizlibenabled'		=> 'ZLIB on sisse lülitatud',
	'sidisabledfuncs'	=> 'Välja lülitatud funktsioonid',
	'sieditor'		=> 'WYSIWYG redaktor',
	'siconfigfile'		=> 'Seadete fail',
	'siphpinfo'		=> 'PHP info',
	'siphpinformation'	=> 'PHP informatsioon',
	'sipermissions'		=> 'Õigused',
	'sidirperms'		=> 'Kaustade õigused',
	'sidirpermsmess'	=> 'Kõikide funktsioonide ja omaduste korrektseks toimimiseks on vaja anda kirjutusõigused [chmod 0777] järgnevatele kaustadele',
	'sionoff'			=> array( 'Sisse lülitatud', 'Välja lülitatud' ),
	
	'extract_warning' => "Oled sa kindel, et soovid seda faili Siia?\\nSee kirjutab üle olemasolevad failid, kui sa sellega ettevaatliklt ringi ei käi!",
	'extract_success' => "Lahtipakkimine on edukalt lõpetatud",
	'extract_failure' => "lahtipakkimine ebaõnnestus",
	
	'overwrite_files' => 'Kirjutada olemasolev(ad) fail(id) üle?',
	"viewlink"		=> "Vaata",
	"actview"		=> "Faili algkoodi näitamine",
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_chmod.php file
	'recurse_subdirs'	=> 'Rekursiivselt alamkataloogid?',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to footer.php file
	'check_version'	=> 'Kontrolli uuema versiooni olemasolu',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_rename.php file
	'rename_file'	=>	'Nimeta kaust või fail ümber...',
	'newname'		=>	'Uus nimi',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_edit.php file
	'returndir'	=>	'Mine pärast salvestamist tagasi kausta?',
	'line'		=> 	'Ridu',
	'column'	=>	'Veergusid',
	'wordwrap'	=>	'Reamurdmine: (ainult IE)',
	'copyfile'	=>	'Kopeeri fail sellesse failinimesse',
	
	// Bookmarks
	'quick_jump' => 'Mine kiiresti',
	'already_bookmarked' => 'See kaust on juba järjehoidjates',
	'bookmark_was_added' => 'See kaust lisati järjehoidjatesse.',
	'not_a_bookmark' => 'See kaust pole järjehoidjates.',
	'bookmark_was_removed' => 'See kaust eemaldati järjehoidjatest.',
	'bookmarkfile_not_writable' => "Järjehoidja (%s) lisamine ebaõnnestus.\n Järjehoidjate fail '%s' \npole kirjutatav.",
	
	'lbl_add_bookmark' => 'Lisa see kaust järjehoidjatesse',
	'lbl_remove_bookmark' => 'Eemalda see kaust järjehoidjatest',
	
	'enter_alias_name' => 'Palun sisesta sellele järjehoidjale nimi',
	
	'normal_compression' => 'tavaline pakkimine',
	'good_compression' => 'hea pakkimine',
	'best_compression' => 'parim pakkimine',
	'no_compression' => 'ei pakita',
	
	'creating_archive' => 'Arhiivifaili loomine...',
	'processed_x_files' => 'Töödeldud %s faili %s failist',
	
	'ftp_header' => 'Kohalik FTP autentimine',
	'ftp_login_lbl' => 'Palun sisesta siia FTK konto andmed',
	'ftp_login_name' => 'FTP kasutajanimi',
	'ftp_login_pass' => 'FTP parool',
	'ftp_hostname_port' => 'FTP serveri hostinimi ja port <br />(Port on valikuline)',
	'ftp_login_check' => 'FTP ühenduse kontrollimine ...',
	'ftp_connection_failed' => "Ühendumine FTP serveriga ebaõnnestus. \nPalun veendus, et FTP server toimib.",
	'ftp_login_failed' => "FTP sisselogimine ebaõnnestus. Palun kontrolli kasutajanime ja parooli ning proovi uuesti.",
		
	'switch_file_mode' => 'Praegune režiim: <strong>%s</strong>. Sa võid lülituda ümber %s režiimi.',
	'symlink_target' => 'Sümboolse lingi sihtaken',
	
	"permchange"		=> "CHMOD on tehtud:",
	"savefile"		=> "Fail on salvestatud.",
	"moveitem"		=> "Liigutamine on tehtud.",
	"copyitem"		=> "Kopeerimine on tehtud.",
	'archive_name' 	=> 'Arhiivifaili nimi',
	'archive_saveToDir' 	=> 'Salvesta arhiiv sellesse kausta',
	
	'editor_simple'	=> 'Lihtne redaktori režiim',
	'editor_syntaxhighlight'	=> 'Süntaksit esiletõstev režiim',

	'newlink'	=> 'Uus fail/kaust',
	'show_directories' => 'Näita kaustasid',
	'actlogin_success' => 'Sisselogimine oli edukas!',
	'actlogin_failure' => 'Sisselogimine ebaõnnestus, proovi uuesti.',
	'directory_tree' => 'Kaustapuu',
	'browsing_directory' => 'Kausta sirvimine',
	'filter_grid' => 'Filter',
	'paging_page' => 'Lehekülg',
	'paging_of_X' => '/ {0}',
	'paging_firstpage' => 'Esimene lehekülg',
	'paging_lastpage' => 'Viimane lehekülg',
	'paging_nextpage' => 'Järgmine lehekülg',
	'paging_prevpage' => 'Eelmine lehekülg',
	
	'paging_info' => 'Kirjade näitamine {0} - {1} / {2}',
	'paging_noitems' => 'Pole kirjeid, mida näidata',
	'aboutlink' => 'Tiitel...',
	'password_warning_title' => 'Oluline - Muuda oma parooli!',
	'password_warning_text' => 'Kasutajakonto, millega sa oled sisse loginud (admin parooliga admin) on vaikimisi eXtploreri priviligeeritud konto. Sinu eXtplorer on avatud sissetungidele ja sa peaksid selle turvaaugu koheselt sulgema!',
	'change_password_success' => 'Sinu parool on muudetud!',
	'success' => 'Korras',
	'failure' => 'Ebaõnnestus',
	'dialog_title' => 'Veebisaidi dialoogiaken',
	'upload_processing' => 'Üleslaadimine, palun oota ...',
	'upload_completed' => 'Üles laetud!',
	'acttransfer' => 'Ühest serverist teise kopeerimine',
	'transfer_processing' => 'Serverist serverisse kopeerimine, palun oota ...',
	'transfer_completed' => 'Kopeerimine on lõpetatud!',
	'max_file_size' => 'Maksimaalne faili suurus',
	'max_post_size' => 'Maksimaalne üleslaadimise limiit',
	'done' => 'Valmis.',
	'permissions_processing' => 'Õiguste määramine, palun oota...',
	'archive_created' => 'Arhiivifail on loodud!',
	'save_processing' => 'Faili salvestamine...',
	'current_user' => 'See skript töötab praegu selle kasutaja õigustega:',
	'your_version' => 'Sinu versioon',
	'search_processing' => 'Otsimine, palun oota...',
	'url_to_file' => 'Faili aadress (URL)',
	'file' => 'Fail'
  
);
?>
