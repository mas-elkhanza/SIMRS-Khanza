<?php
// ensure this file is being included by a parent file
if( !defined( '_JEXEC' ) && !defined( '_VALID_MOS' ) ) die( 'Restricted access' );
/**
 * @version $Id: users.php 226 2012-12-25 06:51:02Z soeren $
 * @package eXtplorer
 * @copyright soeren 2007-2009
 * @author The eXtplorer project (http://extplorer.net)
 * @author The	The QuiX project (http://quixplorer.sourceforge.net)
 * 
 * @license
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU General Public License Version 2 or later (the "GPL"), in
 * which case the provisions of the GPL are applicable instead of
 * those above. If you wish to allow use of your version of this file only
 * under the terms of the GPL and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting  the provisions above and replace  them with the notice and
 * other provisions required by the GPL.  If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the GPL."
 * 
 * Administrative Functions regarding users
 */
function ext_load_users() {
	require _EXT_PATH."/config/.htusers.php";
}
//------------------------------------------------------------------------------
function ext_save_users() {
	$cnt=count($GLOBALS["users"]);
	if($cnt>0) sort($GLOBALS["users"]);

	// Make PHP-File
	$content='<?php 
	// ensure this file is being included by a parent file
	if( !defined( \'_JEXEC\' ) && !defined( \'_VALID_MOS\' ) ) die( \'Restricted access\' );
	$GLOBALS["users"]=array(';
	for($i=0;$i<$cnt;++$i) {
		// if($GLOBALS["users"][6]&4==4) $GLOBALS["users"][6]=7;	// If admin, all permissions
		$content.="\r\n\tarray('".$GLOBALS["users"][$i][0]."','".
			$GLOBALS["users"][$i][1]."','".$GLOBALS["users"][$i][2]."','".$GLOBALS["users"][$i][3]."','".
			$GLOBALS["users"][$i][4]."','".$GLOBALS["users"][$i][5]."','".$GLOBALS["users"][$i][6]."',".
			$GLOBALS["users"][$i][7].'),';
	}
	$content.="\r\n); \r\n?>";

	// Write to File
	if( !is_writable(_EXT_PATH."/config/.htusers.php") && !chmod( _EXT_PATH."/config/.htusers.php", 0644 ) ) {
		return false;
	}
	file_put_contents( _EXT_PATH."/config/.htusers.php", $content);

	return true;
}
//------------------------------------------------------------------------------
function &ext_find_user($user,$pass) {
	$return = null;
	$cnt=count($GLOBALS["users"]);
	for($i=0;$i<$cnt;++$i) {
		if($user==$GLOBALS["users"][$i][0]) {
			if($pass===NULL || ($pass==$GLOBALS["users"][$i][1] && $GLOBALS["users"][$i][7])) {
				return $GLOBALS["users"][$i];
			}
		}
	}

	return $return;
}

//------------------------------------------------------------------------------
function ext_update_user($user,$new_data) {
	$data=&ext_find_user($user,NULL);
	if($data==NULL) return false;

	$data=$new_data;
	return ext_save_users();
}
//------------------------------------------------------------------------------
function ext_add_user($data) {
	if(ext_find_user($data[0],NULL)) return false;

	$GLOBALS["users"][]=$data;
	return ext_save_users();
}
//------------------------------------------------------------------------------
function ext_remove_user($user) {
	$data=&ext_find_user($user,NULL);
	if($data==NULL) return false;

	// Remove
	$data=NULL;

	// Copy Valid Users
	$cnt=count($GLOBALS["users"]);
	for($i=0;$i<$cnt;++$i) {
		if($GLOBALS["users"][$i]!=NULL) $ext_save_users[]=$GLOBALS["users"][$i];
	}
	$GLOBALS["users"]=$ext_save_users;
	return ext_save_users();
}
//------------------------------------------------------------------------------
/*
function num_users($active=true) {
	$cnt=count($GLOBALS["users"]);
	if(!$active) return $cnt;

	for($i=0, $j=0;$i<$cnt;++$i) {
		if($GLOBALS["users"][$i][7]) ++$j;
	}
	return $j;
}
*/
//------------------------------------------------------------------------------

?>
