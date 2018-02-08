<?php

// Russian Language Module for eXtplorer (translated by Mikhail M. Pigulsky - mikhail@mikhail.pp.ru)
global $_VERSION;

$GLOBALS["charset"] = "UTF-8";
$GLOBALS["text_dir"] = "ltr"; // ('ltr' for left to right, 'rtl' for right to left)
$GLOBALS["date_fmt"] = "Y/m/d H:i";
$GLOBALS["error_msg"] = array(
      // error
      "error"                  => "ОШИБКА(И)",
      "back"                  => "Вернуться",
      
      // root
      "home"                  => "Домашняя директория не существует! Проверьте настройки.",
      "abovehome"            => "Текущая директория не может находится выше домашнего каталога.",
      "targetabovehome"      => "Запрошенная директория не может находится выше домашнего каталога.",

      // exist
      "direxist"            => "Директория не существует",
      //"filedoesexist"      => "Такой файл уже существует",
      "fileexist"            => "Такого файла не существует",
      "itemdoesexist"            => "Такой объект уже существует",
      "itemexist"            => "Такого объекта существует",
      "targetexist"            => "Назначенной директории не существует",
      "targetdoesexist"      => "Назначенного объекта не существует",
      
      // open
      "opendir"            => "Невозможно открыть директорию",
      "readdir"            => "Невозможно прочитать директорию",

      // access
      "accessdir"            => "Вам запрещено заходить в данную директорию",
      "accessfile"            => "Вам запрещено использовать данный файл",
      "accessitem"            => "Вам запрещено использовать данный объект",
      "accessfunc"            => "Вам запрещено использовать данную функцию",
      "accesstarget"            => "Вам запрещено входить в заданную директорию",

      // actions
      "permread"            => "Ошибка в получении прав доступа",
      "permchange"            => "Ошибка в смене прав доступа",
      "openfile"            => "Провал в открытии файла",
      "savefile"            => "Провал в сохранении файла",
      "createfile"            => "Провал в создании файла",
      "createdir"            => "Провал в создании директории",
      "uploadfile"            => "Провал в загрузке файла",
      "copyitem"            => "Провал в копировании",
      "moveitem"            => "Провал в переименовании",
      "delitem"            => "Провал в удалении",
      "chpass"            => "Провал в смене пароля",
      "deluser"            => "Провал в удалении пользователя",
      "adduser"            => "Провал в удалении пользователя",
      "saveuser"            => "Провал в сохранении пользователя",
      "searchnothing"            => "Строка поиска не должна быть пустой",
      
      // misc
      "miscnofunc"            => "Функция недоступна",
      "miscfilesize"            => "Файл превышает максимальный размер",
      "miscfilepart"            => "Файл был загружен частично",
      "miscnoname"            => "Вы должны дать задать имя",
      "miscselitems"            => "Вы не выбрали объект(ы)",
      "miscdelitems"            => "Вы уверены, что хотите удалить {0} объект(а/ов)?",
      "miscdeluser"            => "Вы уверены, что хотите удалить пользователя '{0}'?",
      "miscnopassdiff"      => "Новый пароль не отличается от текущего",
      "miscnopassmatch"      => "Пароли не совпадают",
      "miscfieldmissed"      => "Вы пропустили важное поле",
      "miscnouserpass"      => "Имя пользователя или пароль не правильны",
      "miscselfremove"      => "Вы не можете удалить самого себя",
      "miscuserexist"            => "Такой пользователь уже существует",
      "miscnofinduser"      => "Невозможно найти пользователя",
	"extract_noarchive" => "Файл не является архивом.",
	"extract_unknowntype" => "Не известный тип архива",
	
	'chmod_none_not_allowed' => 'Изменение прав доступа для <none> не допускается',
	'archive_dir_notexists' => 'Указанная вами директория для сохранения не существует.',
	'archive_dir_unwritable' => 'Для сохранения архива, укажите директорию с возможностью записи в нее.',
	'archive_creation_failed' => 'Не удалось сохранить архив.'
);
$GLOBALS["messages"] = array(
      // links
      "permlink"            => "Поменять права доступа",
      "editlink"            => "Редактировать",
      "downlink"            => "Скачать файл",
      "uplink"            => "Наверх",
      "homelink"            => "Домой",
      "reloadlink"            => "Обновить",
      "copylink"            => "Копировать",
      "movelink"            => "Переместить",
      "dellink"            => "Удалить",
      "comprlink"            => "Архивировать",
      "adminlink"            => "Администрирование",
      "logoutlink"            => "Выйти",
      "uploadlink"            => "Загрузить на сервер",
      "searchlink"            => "Поиск",
	"extractlink"	=> "Извлечь архив",
	'chmodlink'		=> 'Права доступа (chmod)', // new mic
	'mossysinfolink'	=> 'eXtplorer информация о системе (eXtplorer, Server, PHP, mySQL)', // new mic
	'logolink'		=> 'Перейти к вебсайту eXtplorer (откроется в новом окне)', // new mic
      
      // list
      "nameheader"            => "Файл",
      "sizeheader"            => "Размер",
      "typeheader"            => "Тип",
      "modifheader"            => "Изменен",
      "permheader"            => "Права",
      "actionheader"            => "Действия",
      "pathheader"            => "Путь",
      
      // buttons
      "btncancel"            => "Отмена",
      "btnsave"            => "Сохранить",
      "btnchange"            => "Изменить",
      "btnreset"            => "Очистить",
      "btnclose"            => "Закрыть",
      "btncreate"            => "Создать",
      "btnsearch"            => "Поиск",
      "btnupload"            => "Загрузить на сервер",
      "btncopy"            => "Копировать",
      "btnmove"            => "Переместить",
      "btnlogin"            => "Войти",
      "btnlogout"            => "Выйти",
      "btnadd"            => "Добавить",
      "btnedit"            => "Редактировать",
      "btnremove"            => "Удалить",
	
	// user messages, new in eXtplorer 1.3.0
	'renamelink'	=> 'Переименовать',
	'confirm_delete_file' => 'Вы уверены, что хотите удалить этот файл? \\n%s',
	'success_delete_file' => 'Элемент(ы) успешно удален.',
	'success_rename_file' => 'Директория/файл %s усперно переименован в %s.',
	
      
      // actions
      "actdir"            => "Папка",
      "actperms"            => "Поменять права",
      "actedit"            => "Правит файл",
      "actsearchresults"      => "Результаты поиска",
      "actcopyitems"            => "Копировать объект(ы)",
      "actcopyfrom"            => "Копировать из /%s в /%s ",
      "actmoveitems"            => "Переместить объект(ы)",
      "actmovefrom"            => "Переместить из /%s в /%s ",
      "actlogin"            => "Войти",
      "actloginheader"      => "Войти, чтобы начать использовать QuiXplorer",
      "actadmin"            => "Администрирование",
      "actchpwd"            => "Сменить пароль",
      "actusers"            => "Пользователи",
      "actarchive"            => "Заархивировать объект(ы)",
      "actupload"            => "Загрузить файл(ы) на сервер",
      
      // misc
      "miscitems"            => "Объект(а/ов)",
      "miscfree"            => "Свободно",
      "miscusername"            => "Пользователь",
      "miscpassword"            => "Пароль",
      "miscoldpass"            => "Старый пароль",
      "miscnewpass"            => "Новый пароль",
      "miscconfpass"            => "Подтвердите пароль",
      "miscconfnewpass"      => "Подтвердите новый пароль",
      "miscchpass"            => "Поменять пароль",
      "mischomedir"            => "Домашняя директория",
      "mischomeurl"            => "Домашний URL",
      "miscshowhidden"      => "Показывать спрятанные объекты",
      "mischidepattern"      => "Прятать файлы",
      "miscperms"            => "Права",
      "miscuseritems"            => "(имя, домашняя директория, показывать спрятанные объекты, права досутпа, активен)",
      "miscadduser"            => "добавить пользователя",
      "miscedituser"            => "редактировать пользователя '%s'",
      "miscactive"            => "Активен",
      "misclang"            => "Язык",
      "miscnoresult"            => "Нет результатов",
      "miscsubdirs"            => "Искать в поддиректориях",
      "miscpermnames"            => array("Только просмотр","Редактирование","Сменя пароля","Правка и смена пароля",
                              "Администратор"),
      "miscyesno"            => array("Да","Нет","Д","Н"),
      "miscchmod"            => array("Владелец", "Группа", "Интернет"),
	// from here all new by mic
	'miscowner'			=> 'Владелец',
	'miscownerdesc'		=> '<strong>Описание:</strong><br />User (UID) /<br />Group (GID)<br />Current rights:<br /><strong> %s ( %s ) </strong>/<br /><strong> %s ( %s )</strong>',

	// sysinfo (new by mic)
	'simamsysinfo'		=> 'eXtplorer информаци о системе',
	'sisysteminfo'		=> 'Информаци о системе',
	'sibuilton'			=> 'Операционная система',
	'sidbversion'		=> 'Версия баз данных (MySQL)',
	'siphpversion'		=> 'PHP версия',
	'siphpupdate'		=> 'Информация: <span style="color: red;">Версия PHP которую вы используйте <strong>не</strong> актуальна!</span><br />Для использования всех функций и возможностей eXtplorer и аддонов,<br />вы должны использовать <strong>PHP. версии 4.3</strong>!',
	'siwebserver'		=> 'Веб сервер',
	'siwebsphpif'		=> 'Веб сервер - PHP интерфейс',
	'simamboversion'	=> 'eXtplorer версия',
	'siuseragent'		=> 'Браузер версия',
	'sirelevantsettings' => 'Важные настройки PHP',
	'sisafemode'		=> 'Safe Mode',
	'sibasedir'			=> 'Open basedir',
	'sidisplayerrors'	=> 'PHP Errors',
	'sishortopentags'	=> 'Short Open Tags',
	'sifileuploads'		=> 'Datei Uploads',
	'simagicquotes'		=> 'Magic Quotes',
	'siregglobals'		=> 'Register Globals',
	'sioutputbuf'		=> 'Output Buffer',
	'sisesssavepath'	=> 'Session Savepath',
	'sisessautostart'	=> 'Session auto start',
	'sixmlenabled'		=> 'XML enabled',
	'sizlibenabled'		=> 'ZLIB enabled',
	'sidisabledfuncs'	=> 'Не включенные функции',
	'sieditor'			=> 'WYSIWYG редактор',
	'siconfigfile'		=> 'Файл настроек',
	'siphpinfo'			=> 'PHP Info',
	'siphpinformation'	=> 'PHP информация',
	'sipermissions'		=> 'Права доступа',
	'sidirperms'		=> 'Права доступа директории',
	'sidirpermsmess'	=> ' Для корректной работы всех функций и возможностей eXtplorer, следующие директории должны иметь разрешение на запись [chmod 0777]',
	'sionoff'			=> array( 'Включено', 'Выключено' ),
	
	'extract_warning' => "Вы действительно хотите извлечь этот файл? Здесь?\\nЭто перезапишет существующие файлы!",
	'extract_success' => "Извлечение успешно завершено!",
	'extract_failure' => "Ошибка при извлечении!",
	
	'overwrite_files' => 'Перезаписать существующий файл(ы)?',
	"viewlink"		=> "Просмотр",
	"actview"		=> "Отображается файл",
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_chmod.php file
	'recurse_subdirs'	=> 'Рекурсивно с подкаталогами?',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to footer.php file
	'check_version'	=> 'Проверить обновления',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_rename.php file
	'rename_file'	=>	'Переименовать директорию или файл...',
	'newname'		=>	'Новое имя',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_edit.php file
	'returndir'	=>	'Вернуться к директории после сохранения?',
	'line'		=> 	'Строка',
	'column'	=>	'Столбец',
	'wordwrap'	=>	'Перенос слов: (только в IE)',
	'copyfile'	=>	'Копировать файл с этим именем',
	
	// Bookmarks
	'quick_jump' => 'Быстрый переход',
	'already_bookmarked' => 'Эта директория уже в закладках',
	'bookmark_was_added' => 'Директория добавлена в список закладок.',
	'not_a_bookmark' => 'Эта директория не в закладках.',
	'bookmark_was_removed' => 'Эта директория удалена из закладок.',
	'bookmarkfile_not_writable' => "Ошибка %s закладки.\n Файл закладки '%s' \nне доступен для записи.",
	
	'lbl_add_bookmark' => 'Добавить эту директорию как закладку.',
	'lbl_remove_bookmark' => 'Удалить эту директорию из закладок',
	
	'enter_alias_name' => 'Пожалуйста введите имя для этой закладки',
	
	'normal_compression' => 'нормальное сжатие',
	'good_compression' => 'хорошее сжатие',
	'best_compression' => 'лучшее сжатие',
	'no_compression' => 'без сжатия',
	
	'creating_archive' => 'Создание архива...',
	'processed_x_files' => 'Обработано %s из %s файлов',
	
	'ftp_header' => 'Локальная FTP аутентификация',
	'ftp_login_lbl' => 'Пожалуйста, введите данные учетной записи, для FTP-сервера',
	'ftp_login_name' => 'FTP имя пользователя',
	'ftp_login_pass' => 'FTP пароль',
	'ftp_hostname_port' => 'FTP сервер Хост и Порт <br />(Порт опционально)',
	'ftp_login_check' => 'Проверка FTP соединения...',
	'ftp_connection_failed' => "Не удалось соеденить с FTP сервером. \nУбедитесь что FTP сервер запущен на сервере.",
	'ftp_login_failed' => "Ошибка входа на FTP сервер. Пожалуйста, проверьте имя пользователя и пароль.",
		
	'switch_file_mode' => 'Текущий режим: <strong>%s</strong>. Вы можете переключиться в режим %s.',
	'symlink_target' => 'Назначение символьной ссылки',
	
	"permchange"		=> "CHMOD успешно:",
	"savefile"		=> "Файл сохранен.",
	"moveitem"		=> "перемещение успешно.",
	"copyitem"		=> "Копирование успешно.",
	'archive_name' 	=> 'Имя архива',
	'archive_saveToDir' 	=> 'Сохранить архив в эту директорию',
	
	'editor_simple'	=> 'Простой режим редактирования',
	'editor_syntaxhighlight'	=> 'Режим подсветки синтаксиса',

	'newlink'	=> 'Новый файл/директория',
	'show_directories' => 'Отображать директории',
	'actlogin_success' => 'Успешный вход!',
	'actlogin_failure' => 'Ошибка входа, попробуйте снова.',
	'directory_tree' => 'Дерево каталогов',
	'browsing_directory' => 'Просмотр каталогов',
	'filter_grid' => 'Фильтр',
	'paging_page' => 'Страница',
	'paging_of_X' => 'из {0}',
	'paging_firstpage' => 'Первая страница',
	'paging_lastpage' => 'Последняя страница',
	'paging_nextpage' => 'Следующая страница',
	'paging_prevpage' => 'Предыдущая страница',
	
	'paging_info' => 'Показано элементов {0} - {1} из {2}',
	'paging_noitems' => 'Нет элементов эля отображения',
	'aboutlink' => 'О eXtplorer...',
	'password_warning_title' => 'Важно - Измените ваш пароль!',
	'password_warning_text' => 'Ученая запись под которой вы зашли (admin с паролем admin) соответствует учетной записи по умолчанию. Вы должны немедленно сменить пароль, для устранения угрозы вторжения третьих лиц!',
	'change_password_success' => 'Ваш пароль был успешно изменен!',
	'success' => 'Успешно',
	'failure' => 'Сбой',
	'dialog_title' => 'Окно диалога',
	'upload_processing' => 'Загрузка, пожалуйста подождите...',
	'upload_completed' => 'Загрузка успешно завершена!',
	'acttransfer' => 'передача с другого сервера',
	'transfer_processing' => 'Передача от сервера к серверу, пожалуйста подождите...',
	'transfer_completed' => 'Передача завершена!',
	'max_file_size' => 'Максимальный размер файла',
	'max_post_size' => 'Максимальный лимит закгрузки',
	'done' => 'Завершено.',
	'permissions_processing' => 'Применяю права доступа, пожалуйста подождите...',
	'archive_created' => 'Архив создан!',
	'save_processing' => 'Сохранение файла...',
	'current_user' => 'Этот скрипт в настоящее время работает с правами следующего пользователя:',
	'your_version' => 'Ваша версия',
	'search_processing' => 'Поиск, пожалуйста подождите...',
	'url_to_file' => 'URL файла',
	'file' => 'Файл'
);
?>
