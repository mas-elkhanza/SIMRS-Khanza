<?php
// ensure this file is being included by a parent file
if( ! defined( '_JEXEC' ) && ! defined( '_VALID_MOS' ) )
	die( 'Restricted access' ) ;
/**
 * @version $Id: extract.php 249 2016-12-11 16:11:03Z soeren $
 * @package eXtplorer
 * @copyright soeren 2007-2015
 * @author The eXtplorer project (http://extplorer.net)
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
 * 
 */
/**
 * Allows to extract archives on the server
 *
 */
class ext_Extract extends ext_Action {

	function execAction( $dir, $item ) {

        if(($GLOBALS["permissions"]&01)!=01) {
            ext_Result::sendResult('archive', false, ext_Lang::err('accessfunc'));
        }
		if( ! ext_isArchive( $item ) ) {
			ext_Result::sendResult( 'archive', false, $item.': '.ext_Lang::err( 'extract_noarchive' ) ) ;
		} else {
            // CSRF Security Check
            if( !ext_checkToken($GLOBALS['__POST']["token"]) ) {
                ext_Result::sendResult('tokencheck', false, 'Request failed: Security Token not valid.');
            }
			$archive_name = realpath( get_abs_item( $dir, $item ) ) ;

			if( empty( $dir ) ) {
				$extract_dir = realpath( $GLOBALS['home_dir'] ) ;
			} else {
				$extract_dir = realpath( $GLOBALS['home_dir'] . "/" . $dir ) ;
			}

			require_once( _EXT_PATH.'/libraries/Archive/archive.php');
			$res = extArchive::extract($archive_name,$extract_dir );
			if( PEAR::isError($res ) ) {
				ext_Result::sendResult( 'extract', false, ext_Lang::err( 'extract_failure' ).' - '. $res->getMessage() );
			}
			if(	$res === false ) {			
				ext_Result::sendResult( 'extract', false, ext_Lang::err( 'extract_failure' ));
			} else {
				ext_Result::sendResult( 'extract', true, ext_Lang::msg( 'extract_success' ));
			}
			
			ext_Result::sendResult( 'extract', true, ext_Lang::msg( 'extract_success' ) ) ;

		}
	}
}
?>