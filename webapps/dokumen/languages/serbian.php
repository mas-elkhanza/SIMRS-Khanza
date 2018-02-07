<?php
// $Id: srpski.php 149 2011-05-30 09:36:27Z sloarch $
// Serbian (Cyrillic) Language Module for v2.3 (translated by Bojan Saric)
// Additional translation by:

global $_VERSION;

$GLOBALS["charset"] = "UTF-8";
$GLOBALS["text_dir"] = "ltr"; // ('ltr' for left to right, 'rtl' for right to left)
$GLOBALS["date_fmt"] = "Y/m/d H:i";
$GLOBALS["error_msg"] = array(
	// error
	"error"				=> "Грешка(е)",
	"message"			=> "Порука(е)",
	"back"				=> "Иди назад",

	// root
	"home"				=> "Почетни директориј не постоји. Провјерите подешавања.",
	"abovehome"			=> "Тренутни директориј можда није изнад почетног директорија.",
	"targetabovehome"	=> "Одредишни директориј можда није изнад почетног директорија.",

	// exist
	"direxist"			=> "Овај директориј не постоји.",
	//"filedoesexist"	=> "Ова датотека већ постоји.",
	"fileexist"			=> "Ова датотека не постоји.",
	"itemdoesexist"		=> "Ова ставка већ постоји.",
	"itemexist"			=> "Ова ставка не постоји.",
	"targetexist"		=> "Одредишни директориј не постоји.",
	"targetdoesexist"	=> "Одредишна ставка већ постоји.",

	// open
	"opendir"			=> "Није могуће отворити директориј.",
	"readdir"			=> "Није могуће прочитати директориј.",

	// access
	"accessdir"			=> "Није Вам дозвољен приступ овом директорију.",
	"accessfile"		=> "Није Вам дозвољен приступ овој датотеци.",
	"accessitem"		=> "Није вам дозвољен приступ овој стацви.",
	"accessfunc"		=> "Није Вам дозвољена уоптреба ове функције.",
	"accesstarget"		=> "Није Вам дозвољен приступ одредишном директорију.",

	// actions
	"permread"			=> "Грешка приликом добијања дозвола.",
	"permchange"		=> "CHMOD Failure (This is usually because of a file ownership problem - e.g. if the HTTP user ('wwwrun' or 'nobody') and the FTP user are not the same)",
	"openfile"			=> "Грешка приликом отварања датотеке.",
	"savefile"			=> "Грешка приликом снимања датотеке.",
	"createfile"		=> "Грешка приликом креирања датотеке.",
	"createdir"			=> "Грешка приликом креирања директорија.",
	"uploadfile"		=> "Грешка приликом постављања датотеке.",
	"copyitem"			=> "Грешка приликом копирања.",
	"moveitem"			=> "Грешка приликом премјештања.",
	"delitem"			=> "Грешка приликом брисања.",
	"chpass"			=> "Грешка приликом промјене приступне лозинке.",
	"deluser"			=> "Грешка приликом уклањања корисника.",
	"adduser"			=> "Грешка приликом додавања корисника.",
	"saveuser"			=> "Грешка приликом снимања корисника.",
	"searchnothing"		=> "Морате упусати термин за претрагу.",

	// misc
	"miscnofunc"		=> "Функција није доступна.",
	"miscfilesize"		=> "Величина датотеке је већа од дозвољене.",
	"miscfilepart"		=> "Датотека је само дјеломично додана.",
	"miscnoname"		=> "Морате уписати назив.",
	"miscselitems"		=> "Морате одабрати бар једну ставку.",
	"miscdelitems"		=> "Јесте лис исгурни да желите обрисати ову/ових {0} ставку/ставки?",
	"miscdeluser"		=> "Јесте ли сигурни да желите обрисати корисника '{0}'?",
	"miscnopassdiff"	=> "Нова приступна лозинка се не разликује од тренутне.",
	"miscnopassmatch"	=> "Приступне лозинке се не подударају.",
	"miscfieldmissed"	=> "Нисете унијели податке у важно поље.",
	"miscnouserpass"	=> "Нетачно корисничко име или приступна лозинка.",
	"miscselfremove"	=> "Не можете самостално уклонити.",
	"miscuserexist"		=> "Корисник већ постоји.",
	"miscnofinduser"	=> "Не могу пронаћи корисника.",
	"extract_noarchive"		=> "Датотека није архивског типа",
	"extract_unknowntype"	=> "Непознат тип архиве.",
	
	'chmod_none_not_allowed'	=> 'Changing Permissions to <none> is not allowed',
	'archive_dir_notexists'		=> 'The Save-To Directory you have specified does not exist.',
	'archive_dir_unwritable'	=> 'Please specify a writable directory to save the archive to.',
	'archive_creation_failed'	=> 'Failed saving the Archive File'

);
$GLOBALS["messages"] = array(
	// links
	"permlink"			=> "Промјена дозвола",
	"editlink"			=> "Уреди",
	"downlink"			=> "Преузми",
	"uplink"			=> "Горе",
	"homelink"			=> "Почетак",
	"reloadlink"		=> "Поново учитај",
	"copylink"			=> "Копирај",
	"movelink"			=> "Премјести",
	"dellink"			=> "Обриши",
	"comprlink"			=> "Архивирај",
	"adminlink"			=> "Админ",
	"logoutlink"		=> "Одјава",
	"uploadlink"		=> "Додавање",
	"searchlink"		=> "Претрага",
	'difflink'     		=> 'Дифф',
	"extractlink"		=> "Распакуј архиву",
	'chmodlink'			=> 'Промјени права (chmod) директорија/датотека', // new mic
	'mossysinfolink'	=> 'Систем инфо', // new mic
	'logolink'			=> '_', // new mic

	// list
	"nameheader"		=> "Назив",
	"sizeheader"		=> "Величина",
	"typeheader"		=> "Тип",
	"modifheader"		=> "Вријеме промјене",
	"permheader"		=> "Дозволе",
	"actionheader"		=> "Акције",
	"pathheader"		=> "Путања",

	// buttons
	"btncancel"			=> "Поништи",
	"btnsave"			=> "Сними",
	"btnchange"			=> "Промјени",
	"btnreset"			=> "Поново учитај",
	"btnclose"			=> "Затвори",
	"btnreopen"			=> "Поново отвори",
	"btncreate"			=> "Креирај",
	"btnsearch"			=> "Претрага",
	"btnupload"			=> "Додај на сервер",
	"btncopy"			=> "Копирај",
	"btnmove"			=> "Премјести",
	"btnlogin"			=> "Пријава",
	"btnlogout"			=> "Одјава",
	"btnadd"			=> "Додај",
	"btnedit"			=> "Уреди",
	"btnremove"			=> "Уклони",
	"btndiff"			=> "Дифф",
	
	// user messages, new in eXtplorer 1.3.0
	'renamelink'		=> 'Преименуј',
	'confirm_delete_file' => 'Јесте ли сигурни да желите да обришете ову датотеку? <br />%s',
	'success_delete_file' => 'Ставка(е) успјешно обрисана(е).',
	'success_rename_file' => 'Директориј/датотека %s је успјешно преименована у %s.',
	
	// actions
	"actdir"			=> "Директориј",
	"actperms"			=> "Промјени дозволе",
	"actedit"			=> "Уреди датотеку",
	"actsearchresults"	=> "Резултат претраге",
	"actcopyitems"		=> "Копирај ставку(е)",
	"actcopyfrom"		=> "Копирај од /%s до /%s ",
	"actmoveitems"		=> "Премјести ставку(е)",
	"actmovefrom"		=> "Move from /%s to /%s ",
	"actlogin"			=> "Пријава",
	"actloginheader"	=> "Пријава у систем",
	"actadmin"			=> "Администрација",
	"actchpwd"			=> "Промјена приступне лозинке",
	"actusers"			=> "Корисници",
	"actarchive"		=> "Архивирај ставку(е)",
	"actupload"			=> "Додај датотеку(е)",

	// misc
	"miscitems"			=> "Ставка(е)",
	"miscfree"			=> "Слободно",
	"miscusername"		=> "Корисничко име",
	"miscpassword"		=> "Приступна лозинка",
	"miscoldpass"		=> "Стара приступна лозинка",
	"miscnewpass"		=> "Нова приступна лозинка",
	"miscconfpass"		=> "Потврда приступне лозинке",
	"miscconfnewpass"	=> "Потврда нове приступне лозинке",
	"miscchpass"		=> "Промјена приступне лозинке",
	"mischomedir"		=> "Почетни директориј",
	"mischomeurl"		=> "Интернет адреса",
	"miscshowhidden"	=> "Прикажи скривене ставке",
	"mischidepattern"	=> "Сакриј шаблон",
	"miscperms"			=> "Дозволе",
	"miscuseritems"		=> "(назив, почетни директориј, прикажи скривене ставке, дозволе, активан)",
	"miscadduser"		=> "додај кориниска",
	"miscedituser"		=> "уреди корисника '%s'",
	"miscactive"		=> "Активан",
	"misclang"			=> "Језик",
	"miscnoresult"		=> "Резултати нису доступни.",
	"miscsubdirs"		=> "Претражи и под-директорије",
	"miscpermnames"		=> array("Само преглед","Преглед и уређивање","Промјена лозинке","Преглед, уређивање и промјена лозинке","Администрација"),
	"miscyesno"			=> array("Да","Не","Д","Н"),
	"miscchmod"			=> array("Власник", "Група", "Јавно"),
	'misccontent'		=> "Садржај датотеке",

	// from here all new by mic
	'miscowner'			=> 'Власник',
	'miscownerdesc'		=> '<strong>Опис:</strong><br />Корисник (UID) /<br />Група (GID)<br />Тренутна права:<br /><strong> %s ( %s ) </strong>/<br /><strong> %s ( %s )</strong>',

	// sysinfo (new by mic)
	'simamsysinfo'		=> "Систем инфо",
	'sisysteminfo'		=> 'Инфо',
	'sibuilton'			=> 'Оперативни систем',
	'sidbversion'		=> 'Верзија базе података (MySQL)',
	'siphpversion'		=> 'PHP Верзија',
	'siphpupdate'		=> 'Информација: <span style="color: red;">The PHP version you use is <strong>not</strong> actual!</span><br />To guarantee all functions and features of Mambo and addons,<br />you should use as minimum <strong>PHP.Version 4.3</strong>!',
	'siwebserver'		=> 'Webserver',
	'siwebsphpif'		=> 'WebServer - PHP Interface',
	'simamboversion'	=> 'eXtplorer Version',
	'siuseragent'		=> 'Browser Version',
	'sirelevantsettings'	=> 'Important PHP Settings',
	'sisafemode'		=> 'Safe Mode',
	'sibasedir'			=> 'Open basedir',
	'sidisplayerrors'	=> 'PHP Errors',
	'sishortopentags'	=> 'Short Open Tags',
	'sifileuploads'		=> 'File Uploads',
	'simagicquotes'		=> 'Magic Quotes',
	'siregglobals'		=> 'Register Globals',
	'sioutputbuf'		=> 'Output Buffer',
	'sisesssavepath'	=> 'Session Savepath',
	'sisessautostart'	=> 'Session auto start',
	'sixmlenabled'		=> 'XML enabled',
	'sizlibenabled'		=> 'ZLIB enabled',
	'sidisabledfuncs'	=> 'Disabled functions',
	'sieditor'			=> 'WYSIWYG Editor',
	'siconfigfile'		=> 'Config file',
	'siphpinfo'			=> 'PHP Info',
	'siphpinformation'	=> 'PHP Information',
	'sipermissions'		=> 'Permissions',
	'sidirperms'		=> 'Directory permissions',
	'sidirpermsmess'	=> 'To be shure that all functions and features of eXtplorer are working correct, following folders should have permission to write [chmod 0777]',
	'sionoff'			=> array( 'On', 'Off' ),
	
	'extract_warning'	=> "Да ли заиста желите распаковати ову датотеку? Овдје?<br />Ово ће преписати постојеће датотеке уколико се не користи пажљиво!",
	'extract_success'	=> "Распакивање је успјело",
	'extract_failure'	=> "Дошло је до грешке приликом распакивања.",
	
	'overwrite_files'	=> 'Препиши постојеће датотеке?',
	"viewlink"			=> "Преглед",
	"actview"			=> "Прикажи извор датотеке",
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_chmod.php file
	'recurse_subdirs'	=> 'Препиши и на под-директорије?',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to footer.php file
	'check_version'		=> 'Провјера најновије верзије',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_rename.php file
	'rename_file'		=>	'Преименуј директориј или датотеку...',
	'newname'			=>	'Нови назив',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_edit.php file
	'returndir'			=>	'Return to directory after saving?',
	'line'				=> 	'Line',
	'column'			=>	'Column',
	'wordwrap'			=>	'Wordwrap: (IE only)',
	'copyfile'			=>	'Copy file into this filename',
	
	// Bookmarks
	'quick_jump' 		=> 'Тренутни директориј',
	'already_bookmarked' => 'This directory is already bookmarked',
	'bookmark_was_added' => 'This directory was added to the bookmark list.',
	'not_a_bookmark'	=> 'This directory is not a bookmark.',
	'bookmark_was_removed' => 'This directory was removed from the bookmark list.',
	'bookmarkfile_not_writable' => "Failed to %s the bookmark.\n The Bookmark File '%s' \nis not writable.",
	
	'lbl_add_bookmark'	=> 'Add this Directory as Bookmark',
	'lbl_remove_bookmark' => 'Remove this Directory from the Bookmark List',
	
	'enter_alias_name'	=> 'Please enter the alias name for this bookmark',
	
	'normal_compression' => 'normal compression',
	'good_compression'	=> 'good compression',
	'best_compression'	=> 'best compression',
	'no_compression'	=> 'no compression',
	
	'creating_archive'	=> 'Creating Archive File...',
	'processed_x_files'	=> 'Processed %s of %s Files',
	
	'ftp_header'		=> 'Local FTP Authentication',
	'ftp_login_lbl'		=> 'Please enter the login credentials for the FTP server',
	'ftp_login_name'	=> 'FTP User Name',
	'ftp_login_pass'	=> 'FTP Password',
	'ftp_hostname_port'	=> 'FTP Server Hostname and Port <br />(Port is optional)',
	'ftp_login_check'	=> 'Checking FTP connection...',
	'ftp_connection_failed' => "The FTP server could not be contacted. \nPlease check that the FTP server is running on your server.",
	'ftp_login_failed'	=> "The FTP login failed. Please check the username and password and try again.",
		
	'switch_file_mode'	=> 'Тренутни модалитет: <strong>%s</strong>. Могуће је промијенити у %s модалитет.',
	'symlink_target'	=> 'Target of the Symbolic Link',
	
	"permchange"		=> "CHMOD Success:",
	"savefile"			=> "The File was saved.",
	"moveitem"			=> "Moving succeeded.",
	"copyitem"			=> "Copying succeeded.",
	'archive_name'	 	=> 'Name of the Archive File',
	'archive_saveToDir'	=> 'Save the Archive in this directory',
	
	'editor_simple'		=> 'Simple Editor Mode',
	'editor_syntaxhighlight'	=> 'Syntax-Highlighted Mode',

	'newlink'			=> 'Нова датотека/директориј',
	'show_directories'	=> 'Прикажи директорије',
	'actlogin_success'	=> 'Пријава успјешна!',
	'actlogin_failure'	=> 'Пријава није успјела, покушајте поново.',
	'directory_tree'	=> 'Стабло директорија',
	'browsing_directory'	=> 'Тренутни директориј',
	'filter_grid'		=> 'Филтер',
	'paging_page'		=> 'Страница',
	'paging_of_X'		=> 'од {0}',
	'paging_firstpage'	=> 'Прва страница',
	'paging_lastpage'	=> 'Посљедња страница',
	'paging_nextpage'	=> 'Сљедећа страница',
	'paging_prevpage'	=> 'Предходна страница',
	
	'paging_info'		=> 'Приказујем ставке {0} - {1} од {2}',
	'paging_noitems'	=> 'Нема ставки',
	'aboutlink'			=> 'О...',
	'password_warning_title'	=> 'Important - Change your Password!',
	'password_warning_text'		=> 'The user account you are logged in with (admin with password admin) corresponds to the default eXtplorer priviliged account. Your eXtplorer installation is open to intrusion and you should immediately fix this security hole!',
	'change_password_success'	=> 'Your Password has been changed!',
	'success'			=> 'Успјех',
	'failure'			=> 'Грешка',
	'dialog_title'		=> 'Прозор са подацима',
	'upload_processing'	=> 'Processing Upload, please wait...',
	'upload_completed'	=> 'Upload successful!',
	'acttransfer'		=> 'Transfer from another Server',
	'transfer_processing'	=> 'Processing Server-to-Server Transfer, please wait...',
	'transfer_completed'	=> 'Transfer completed!',
	'max_file_size'		=> 'Maximum File Size',
	'max_post_size'		=> 'Maximum Upload Limit',
	'done'				=> 'Учитавање окончано.',
	'permissions_processing' => 'Applying Permissions, please wait...',
	'archive_created'	=> 'The Archive File has been created!',
	'save_processing'	=> 'Saving File...',
	'current_user'		=> 'Тренутно су на снази овлаштења корисника:',
	'your_version'		=> 'Ваша верзија',
	'search_processing'	=> 'Претрага у току, молим сачекајте...',
	'url_to_file'		=> 'Интернет адреса датотеке',
	'file'				=> 'Датотека'
	
);
?>
