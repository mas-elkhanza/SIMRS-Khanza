<?php
// Catalan Language Module for eXtplorer (translated by Claudi Martinez)
global $_VERSION;

$GLOBALS["charset"] = "utf-8";
$GLOBALS["text_dir"] = "ltr"; // ('ltr' for left to right, 'rtl' for right to left)
$GLOBALS["date_fmt"] = "Y/m/d H:i";
$GLOBALS["error_msg"] = array(
	// error
	"error"			=> "ERROR(S)",
	"back"			=> "V&eacute;s Enrere",
	
	// root
	"home"			=> "El directori d'Inici no existeix, reviseu la vostra su configuraci&oacute;.",
	"abovehome"		=> "El directori actual no pot estar per sobre del directori d'Inici.",
	"targetabovehome"	=> "El directori destí no pot estar per sobre del directori d'Inici.",
	
	// exist
	"direxist"		=> "Aquest directori no existeix.",
	//"filedoesexist"	=>  "Aquest fitxer ja existeix.",
	"fileexist"		=> "Aquest fitxer no existeix.",
	"itemdoesexist"		=> "Aquest &iacute;tem ja existeix.",
	"itemexist"		=> "Aquest &iacute;tem no existeix.",
	"targetexist"		=> "El directori dest&iacute; no existeix.",
	"targetdoesexist"	=> "L'&iacute;tem dest&iacute; ja existeix.",
	
	// open
	"opendir"		=> "No s'ha pogut obrir el directori.",
	"readdir"		=> "No s'ha pogut llegir el directori.",
	
	// access
	"accessdir"		=> "No teniu perm&iacute;s d'acc&eacute;s a aquest directori.",
	"accessfile"		=> "No teniu perm&iacute;s d'acc&eacute;s a aquest fitxer.",
	"accessitem"		=> "No teniu perm&iacute;s d'acc&eacute;s a a aquest &iacute;tem.",
	"accessfunc"		=> "No teniu perm&iacute;s per utilitzar aquesta funci&oacute;.",
	"accesstarget"		=> "No teniu perm&iacute;s d'acc&eacute;s al dest&iacute;.",
	
	// actions
	"permread"		=> "No s'han pogut llegir els permisos.",
	"permchange"		=> "No s'han pogut canviar els permisos.",
	"openfile"		=> "No s'ha pogut obrir el fitxer.",
	"savefile"		=> "No s'ha pogut desar el fitxer.",
	"createfile"		=> "No s'ha pogut crear el fitxer.",
	"createdir"		=> "No s'ha pogut crear el directori.",
	"uploadfile"		=> "No s'ha pogut pujar el fitxer.",
	"copyitem"		=> "No s'ha pogut copiar.",
	"moveitem"		=> "No s'ha pogut moure.",
	"delitem"		=> "No s'ha pogut esborrar.",
	"chpass"		=> "No s'ha pogut canviar la contrasenya.",
	"deluser"		=> "No s'ha pogut esborrar l'usuari.",
	"adduser"		=> "No s'ha pogut afegir l'usuari.",
	"saveuser"		=> "No s'ha pogut desar l'usuari.",
	"searchnothing"		=> "Heu d'indicar un terme de cerca.",
	
	// misc
	"miscnofunc"		=> "Funci&oacute; no disponible.",
	"miscfilesize"		=> "El fitxer excedeix la mida m&agrave;xima.",
	"miscfilepart"		=> "El fitxer s'ha pujat parcialment.",
	"miscnoname"		=> "Heu d'indicar un nom.",
	"miscselitems"		=> "No heu seleccionat cap &iacute;tem.",
	"miscdelitems"		=> "Segur que voleu esborrar aquest(s) {0} &iacute;tem(s)?",
	"miscdeluser"		=> "Seguro de voleu esborrar l'usuari '{0}'?",
	"miscnopassdiff"	=> "La nova contrasenya &eacute;s la mateixa que l'antiga.",
	"miscnopassmatch"	=> "Les contrasenyes no coincideixen.",
	"miscfieldmissed"	=> "Hi ha algun camp mal configurat.",
	"miscnouserpass"	=> "Usuari o contrasenya incorrectes.",
	"miscselfremove"	=> "No podeu esborrar el vostre propi usuari.",
	"miscuserexist"		=> "L'usuari ja existeix.",
	"miscnofinduser"	=> "No s'ha trobat l'usuari.",
	"extract_noarchive" => "El fitxer no és un arxiu extra&iuml;ble",
	"extract_unknowntype" => "Fitxer comprimit desconegut.",
	
	'chmod_none_not_allowed' => 'No es permet el canvi de permisos a <cap>.',
	'archive_dir_notexists' => 'El directori escollit per desar no existeix.',
	'archive_dir_unwritable' => 'Si us plau, escolliu un directori amb permisos d\'escriptura per desar-hi el fitxer comprimit.',
	'archive_creation_failed' => 'No s\'ha pogut desar el fitxer comprimit.'
);
$GLOBALS["messages"] = array(
	// links
	"permlink"		=> "Canvia Permisos",
	"editlink"		=> "Edita",
	"downlink"		=> "Descarrega",
	"uplink"		=> "Amunt",
	"homelink"		=> "Inici",
	"reloadlink"		=> "Recarrega",
	"copylink"		=> "Copia",
	"movelink"		=> "Mou",
	"dellink"		=> "Esborra",
	"comprlink"		=> "Comprimeix",
	"adminlink"		=> "Administra",
	"logoutlink"		=> "Surt",
	"uploadlink"		=> "Puja",
	"searchlink"		=> "Cerca",
    'difflink'     		=> 'Compara (Diff)',
	"extractlink"	=> "Extreu Fitxer Comprimit",
	'chmodlink'		=> 'Canvia (chmod) permisos (Directori/Fitxer(s)))', // new mic
	'mossysinfolink'	=> 'Informaci&oacute; de Sistema', // new mic
	'logolink'		=> 'V&eacute;s a la p&agrave;gina d\'eXtplorer', // new mic
	
	// list
	"nameheader"		=> "Nom",
	"sizeheader"		=> "Mida",
	"typeheader"		=> "Tipus",
	"modifheader"		=> "Modificat",
	"permheader"		=> "Permisos",
	"actionheader"		=> "Accions",
	"pathheader"		=> "Cam&iacute;",
	
	// buttons
	"btncancel"		=> "Cancel&middot;la",
	"btnsave"		=> "Desa",
	"btnchange"		=> "Canvia",
	"btnreset"		=> "Restableix",
	"btnclose"		=> "Tanca",
    "btnreopen"		=> "Reopen",
	"btncreate"		=> "Crea",
	"btnsearch"		=> "Cerca",
	"btnupload"		=> "Puja",
	"btncopy"		=> "Copia",
	"btnmove"		=> "Mou",
	"btnlogin"		=> "Entra",
	"btnlogout"		=> "Surt",
	"btnadd"		=> "Afegeix",
	"btnedit"		=> "Edita",
	"btnremove"		=> "Esborra",
    "btndiff"		=> "Compara",
	
	// user messages, new in eXtplorer 1.3.0
	'renamelink'	=> 'Reanomena',
	'confirm_delete_file' => 'Segur que voleu esborrar el fitxer? <br/>%s',
	'success_delete_file' => 'Fitxers esborrats correctament.',
	'success_rename_file' => 'El fitxer/directori %s ara s\'anomena %s.',
	
	
	// actions
	"actdir"		=> "Directori",
	"actperms"		=> "Canvia permisos",
	"actedit"		=> "Edita fitxer",
	"actsearchresults"	=> "Resultat de la cerca.",
	"actcopyitems"		=> "Copia &iacute;tem(s)",
	"actcopyfrom"		=> "Copia de /%s a /%s ",
	"actmoveitems"		=> "Mou &iacute;tem(s)",
	"actmovefrom"		=> "Mou de /%s a /%s ",
	"actlogin"		=> "Login",
	"actloginheader"	=> "Login per utilitzar QuiXplorer",
	"actadmin"		=> "Administraci&oacute;",
	"actchpwd"		=> "Canvia contrasenya",
	"actusers"		=> "Usuaris",
	"actarchive"		=> "Arxiva &iacute;tem(s)",
	"actupload"		=> "Puja Fitxer(s)",
	
	// misc
	"miscitems"		=> "&Iacute;tem(s)",
	"miscfree"		=> "Lliure",
	"miscusername"		=> "Nom d'usuari",
	"miscpassword"		=> "Contrasenya",
	"miscoldpass"		=> "Contrasenya Antiga",
	"miscnewpass"		=> "Contrasenya Nova",
	"miscconfpass"		=> "Confirma la contrasenya",
	"miscconfnewpass"	=> "Confirma nova contrasenya",
	"miscchpass"		=> "Canvia contrasenya",
	"mischomedir"		=> "Directori Inici",
	"mischomeurl"		=> "URL d'inici",
	"miscshowhidden"	=> "Mostra &iacute;tems ocults",
	"mischidepattern"	=> "Oculta patr&oacute;",
	"miscperms"		=> "Permisos",
	"miscuseritems"		=> "(nom, directori Inici, mostra &iacute;tems ocults, permisos, actiu)",
	"miscadduser"		=> "afegeix usuari",
	"miscedituser"		=> "edita usari '%s'",
	"miscactive"		=> "Activa",
	"misclang"		=> "Idioma",
	"miscnoresult"		=> "Resultat(s) no disponible(s).",
	"miscsubdirs"		=> "Cerca de subdirectoris",
	"miscpermnames"		=> array("Nom&eacute;s lectura","Modificar","Canviar contrasenya","Modificar i Canviar contrasenya", "Administrador"),
	"miscyesno"		=> array("Si","No","S","N"),
	"miscchmod"		=> array("Propietari", "Grup", "P&uacute;blic"),
	// from here all new by mic
	'miscowner'			=> 'Propietari',
	'miscownerdesc'		=> '<strong>Descripci&oacute;:</strong><br />Usuari (UID) /<br />Grup (GID)<br />Permisos Actuals:<br /><strong> %s ( %s ) </strong>/<br /><strong> %s ( %s )</strong>',

	// sysinfo (new by mic)
	'simamsysinfo'		=> 'Informaci&oacute; de sistema: eXplorer',
	'sisysteminfo'		=> 'Informaci&oacute; de Sistema',
	'sibuilton'			=> 'Sistema Operatiu',
	'sidbversion'		=> 'Versi&oacute; de la Base de Dades (MySQL)',
	'siphpversion'		=> 'Versi&oacute; del PHP',
	'siphpupdate'		=> 'INFORMACI&Oacute;: <span style="color: red;">La versi&oacute; de PHP que utilitzeu <strong>no &eacute;s</strong> moderna!</span><br />Per garantir tota la funcionalitat d\'eXtplorer,<br />heu d\'utilitzar almenys <strong>la Versi&oacute; de PHP 4.3/strong>!',
	'siwebserver'		=> 'Servidor Web',
	'siwebsphpif'		=> 'Servidor Web - Interf&iacute;cie de PHP',
	'simamboversion'	=> 'Versi&oacute; d\'eXtplorer',
	'siuseragent'		=> 'Versi&oacute; del navegador',
	'sirelevantsettings' => 'Opcions importants de PHP',
	'sisafemode'		=> 'Mode Segur',
	'sibasedir'			=> 'Open basedir',
	'sidisplayerrors'	=> 'Errors de PHP',
	'sishortopentags'	=> 'Short Open Tags',
	'sifileuploads'		=> 'Datei Uploads',
	'simagicquotes'		=> 'Magic Quotes',
	'siregglobals'		=> 'Register Globals',
	'sioutputbuf'		=> 'Buffer de Sortida',
	'sisesssavepath'	=> 'Session Savepath',
	'sisessautostart'	=> 'Session auto start',
	'sixmlenabled'		=> 'XML enabled',
	'sizlibenabled'		=> 'ZLIB enabled',
	'sidisabledfuncs'	=> 'funcions No Actives',
	'sieditor'			=> ' Editor WYSIWYG',
	'siconfigfile'		=> 'Fitxer de Configuraci&oacute;',
	'siphpinfo'			=> 'Informaci&oacute; de PHP',
	'siphpinformation'	=> 'Informaci&oacute; de PHP',
	'sipermissions'		=> 'Permisos',
	'sidirperms'		=> 'Permisos de directoris',
	'sidirpermsmess'	=> 'Per assegurar el correcte funcionament d\'eXtplorer, els seg&uuml;ents directoris han de tenir permisos d\'escriptura [chmod 0777]',
	'sionoff'			=> array( 'Activat', 'Desactivat' ),
	
	'extract_warning' => "Segur que voleu descomprimir aquest fitxer aqu&iacute;?<br/>Aix&ograve; sobreescriur&agrave; fitxers si no teniu cura!",
	'extract_success' => "La descompressi&oacute; s'ha fet correctament.",
	'extract_failure' => "La descompressi&oacute; ha fallat",
	
	'overwrite_files' => 'Sobreescriure el(s) seg&uuml;ent(s) fitxer(s)?',
	"viewlink"		=> "Visualitza",
	"actview"		=> "Es mostra el contingut del fitxer",
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_chmod.php file
	'recurse_subdirs'	=> 'Entrar a subdirectoris?',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to footer.php file
	'check_version'	=> 'Comprova la darrera versi&oacute;',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_rename.php file
	'rename_file'	=>	'Reanomena un directori o un fitxer...',
	'newname'		=>	'Nou Nom',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_edit.php file
	'returndir'	=>	'Voleu tornar al directori despr&eacute; de desar?',
	'line'		=> 	'L&iacute;nia',
	'column'	=>	'Columna',
	'wordwrap'	=>	': (nom&eacute;s IE)',
	'copyfile'	=>	'Voleu copiar el fitxer sobre aquest nom de fitxer?',
	
	// Bookmarks
	'quick_jump' => 'V&eacute;s directament a',
	'already_bookmarked' => 'Aquest directori ja s\'ha afegit als preferits.',
	'bookmark_was_added' => 'Aquest directori s\'ha afegit als preferits.',
	'not_a_bookmark' => 'Aquest directori no &eacute;s un preferit.',
	'bookmark_was_removed' => 'S\'ha esborrat aquest directori de la llista de preferits.',
	'bookmarkfile_not_writable' => "Error al %s el preferit.\n El fitxer de preferits '%s' \nno es pot escriure.",
	
	'lbl_add_bookmark' => 'Afegeix aquest directori com a preferit',
	'lbl_remove_bookmark' => 'Esborra aquest directori de la llista de preferits',
	
	'enter_alias_name' => 'Si us plau indiqueu un &agrave;lies per aquest preferit',
	
	'normal_compression' => 'compressi&oacute; normal',
	'good_compression' => 'compressi&oacute; bona',
	'best_compression' => 'la millor compressi&oacute;',
	'no_compression' => 'sense compressi&oacute;',
	
	'creating_archive' => 'Creant l\'arxiu comprimit...',
	'processed_x_files' => 'Processats %s de %s fitxers',
	
	'ftp_header' => 'Autenticaci&oacute; de FTP local',
	'ftp_login_lbl' => 'Si us plau, indiqueu les credencials d\'acc&eacute;s al FTP',
	'ftp_login_name' => 'Nom d\'usuari del FTP',
	'ftp_login_pass' => 'Contrasenya del FTP',
	'ftp_hostname_port' => 'Servidor i port del FTP <br />(El port &eacute;s opcional)',
	'ftp_login_check' => 'Comprovant la connexi&oacute; FTP...',
	'ftp_connection_failed' => "No s'ha pogut connectar al servidor FTP. \nSi us plau, comproveu el bon funcionament del servidor FTP.",
	'ftp_login_failed' => "Error d'acc&eacute;s al FTP. Si us plau, comproveu l'usuari i la contrasenya i proveu-ho de nou.",
		
	'switch_file_mode' => 'Mode Actual: <strong>%s</strong>. Podria canviar al mode %s',
	'symlink_target' => 'Destí del Link Simb&ograve;lic',
	
	"permchange"		=> "CHMOD correcte:",
	"savefile"		=> "Fitxer desat.",
	"moveitem"		=> "Fitxer mogut.",
	"copyitem"		=> "Fitxer copiat.",
	'archive_name' 	=> 'Nom del fitxer comprimit',
	'archive_saveToDir' 	=> 'Desa el comprimit a aquest directori',
	
	'editor_simple'	=> 'Mode d\'edici&oacute; simple',
	'editor_syntaxhighlight'	=> 'Mode de sintaxi remarcada',

	'newlink'	=> 'Nou Fitxer o Directori',
	'show_directories' => 'Mostra Directoris',
	'actlogin_success' => 'Acc&eacute;s perm&egrave;s!',
	'actlogin_failure' => 'Acc&eacute;s denegat, intenteu-ho de nou.',
	'directory_tree' => 'Arbre de Directoris',
	'browsing_directory' => 'Directori Actual',
	'filter_grid' => 'Filtre',
	'paging_page' => 'P&agrave;gina',
	'paging_of_X' => 'de {0}',
	'paging_firstpage' => 'Primera P&agrave;gina',
	'paging_lastpage' => 'Darrera P&agrave;gina',
	'paging_nextpage' => 'P&agrave;gina Seg&uuml;ent',
	'paging_prevpage' => 'P&agrave;gina Anterior',
	
	'paging_info' => 'Mostrant Elements {0} - {1} of {2}',
	'paging_noitems' => 'No hi ha res a mostrar',
	'aboutlink' => 'Quant a...',
	'password_warning_title' => 'Important - Canvieu la vostra contrasenya!',
	'password_warning_text' => 'L\'usuari amb el que heu entrat (admin amb contrasenya admin) correspon a les credencials per defecte d\'eXtplorer. Aix&ograve; comporta un risc elevat de seguretat i ho heu d\'arreglar immediatament!',
	'change_password_success' => 'S\'ha canviat la contrasenya',
	'success' => 'Correcte',
	'failure' => 'Error',
	'dialog_title' => 'Missatge',
	'upload_processing' => 'Processant la pujada del fitxer. Espereu, si us plau...',
	'upload_completed' => 'Fitxer pujat correctament',
	'acttransfer' => 'Transfereix des d\'un altre servidor',
	'transfer_processing' => 'Processant la transf&egrave;rencia entre servidors. Espereu, si us plau...',
	'transfer_completed' => 'Tranfer&egrave;ncia finalitzada!',
	'max_file_size' => 'Mida M&agrave;xima del Fitxer',
	'max_post_size' => 'L&iacute;mit de pujada',
	'done' => 'Fet',
	'permissions_processing' => 'Aplicant els permisos. Espereu, si us plau...',
	'archive_created' => 'S\'ha creat el fitxer comprimit!',
	'save_processing' => 'Desant el fitxer...',
	'current_user' => 'Aquest Script funciona amb els permisos de l\'usuari:',
	'your_version' => 'La vostra versi&oacute;',
	'search_processing' => 'Cercant. Espereu, si us plau...',
	'url_to_file' => 'URL del fitxer',
	'file' => 'Fitxer'
);
?>
