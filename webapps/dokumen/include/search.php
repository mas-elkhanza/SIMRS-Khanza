<?php
// ensure this file is being included by a parent file
if( !defined( '_JEXEC' ) && !defined( '_VALID_MOS' ) ) die( 'Restricted access' );
/**
 * @version $Id: search.php 237 2014-04-25 11:47:48Z soeren $
 * @package eXtplorer
 * @copyright soeren 2007-2014
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
 * File-Search Functions
 */
function ext_search_items($dir)
{ // search for item
    if (empty($dir) && !empty($GLOBALS['__POST']["item"])) {
        $dir = $GLOBALS['__POST']["item"];
    }
    if (isset($GLOBALS['__POST']["searchitem"])) {

        $searchitem = stripslashes($GLOBALS['__POST']["searchitem"]);
        $subdir = !empty($GLOBALS['__POST']["subdir"]);
        $content = $GLOBALS['__POST']["content"];
        $list = make_list($dir, $searchitem, $subdir, $content);
    } else {
        $searchitem = NULL;
        $subdir = true;
    }

    if (empty($searchitem)) {
        show_searchform($dir);
        return;
    }

    // Results in JSON
    $items['totalCount'] = count($list);
    $result = get_result_array($list);
    $start = (int)$GLOBALS['__POST']["start"];
    $limit = (int)$GLOBALS['__POST']["limit"];

    if ($start < $items['totalCount'] && $limit < $items['totalCount']) {
        $result = array_splice($result, $start, $limit);
    }

    $items['items'] = $result;
    $json = new ext_Json();

    while (@ob_end_clean()) ;

    echo $json->encode($items);

}

//------------------------------------------------------------------------------
function make_list($dir, $item, $subdir, $content)
{ // make list of found items

    // search
    if (ext_isFTPMode()) {
        find_item_ftp($dir, $item, $list, $subdir, $content);
    } else {
        find_item($dir, $item, $list, $subdir, $content);
    }
    if (is_array($list)) sort($list);
    return $list;
}

function find_item_ftp($dir,$item,&$list,$recur, $content) {	// find items
	$homedir = realpath($GLOBALS['home_dir']);
    $opendir = $dir;
	// convert shell-wildcards to PCRE Regex Syntax
	$pat=str_replace("?",".",str_replace("*",".*",str_replace(".","\\.",$item)));
	
    if( !is_dir( $dir )) {
        $opendir = get_abs_dir($dir);
    }
	$handle = @$GLOBALS['ext_File']->opendir( $opendir );

	if($handle===false && $dir=="") {
		$handle = @$GLOBALS['ext_File']->opendir($homedir . $GLOBALS['separator']);
	}

	if($handle===false) {
		ext_Result::sendResult('search', false, $opendir .": ".$GLOBALS["error_msg"]["opendir"]);
	}

	while(($new_item=$GLOBALS['ext_File']->readdir($handle))!==false) {
		if( is_array( $new_item ))	{
			$abs_new_item = $new_item;
		} else {
			$abs_new_item = get_abs_item($dir, $new_item);
		}

		//if(!$GLOBALS['ext_File']->file_exists($abs_new_item)) continue;

		if(!get_show_item($dir, $new_item)) continue;

		$isDir = get_is_dir($abs_new_item);
		// match?
		$include = false;
		if(@preg_match('@'.$pat.'@is',$new_item) > 0 ) {
		    $include = true;
		}
		if (!$isDir && $include && 
			$content && 
			$GLOBALS['ext_File']->filesize($abs_new_item) < 524288) {

		    $data = $GLOBALS['ext_File']->file_get_contents( $abs_new_item );
            $pattern = preg_quote($content, '/');
            // finalise the regular expression, matching the whole line
            $pattern = "/^.*$pattern.*\$/m";
    		if (preg_match('@'. $pattern.'@is', $data) > 0 ) {
	    	    $include = true;
		    }
		    
		}
        if( $include ) {
            $list[]=array($dir,$new_item);
        }
		// search sub-directories
		if($isDir && $recur) {
			find_item($abs_new_item,$pat,$list,$recur, $content);
		}
	}

	$GLOBALS['ext_File']->closedir($handle);
}
function find_item($dir,$pat,&$files,$subdir, $content) {
    if (!is_dir($dir)) {
        $dir = get_abs_dir($dir);
    }
	if( !$subdir ) {
		$files = glob($dir.'/'.$pat);
	} else {
        $files = glob_recursive($dir.'/'.$pat);
	}

	if($files===false) {
		ext_Result::sendResult('search', false, $dir .": ".$GLOBALS["error_msg"]["opendir"]);
	}
	if( $content ) {
		$newList = array();
		foreach( $files as $file ) {
            $contents = file_get_contents($file);
			$pattern = preg_quote($content, '/');
			// finalise the regular expression, matching the whole line
			$pattern = "/^.*$pattern.*\$/m";
			// search, and store all matching occurences in $matches
			if(preg_match_all($pattern, $contents, $matches)){
				$newList[] = $file;
			}
		}
		$files = $newList;
	}
	if( !empty( $_POST['mdate_start'] )) {
        $mdate_start = strtotime($_POST['mdate_start']);
		if( empty($_POST['mdate_end'] )) {
			$mdate_end = time();
		} else {
			$mdate_end = strtotime( $_POST['mdate_end'] );
		}
		if( $mdate_start  && $mdate_end ) {
			$newList = array();
			foreach( $files as $file ) {
				$filemtime = filemtime( $file );
				if( $filemtime > $mdate_start && $filemtime < $mdate_end ) {
					$newList[] = $file;
				}
			}
			$files = $newList;
		}
	}
	if( !empty( $_POST['age_value'] )) {
		$age_value =(int)$_POST['age_value'];
		$age_units = array("minutes", "hours", "days", "weeks", "months", "years");
		if (in_array($_POST['age_unit'], $age_units)) {
			$age_unit = $_POST['age_unit'];
		} else {
			$age_unit = "days";
		}
		$age_time = strtotime("-".$age_value." ".$age_unit);

		if( $age_time ) {
			$newList = array();
			foreach( $files as $file ) {
				$filemtime = filemtime( $file );
				if( $filemtime > $age_time ) {
					$newList[] = $file;
				}
			}
			$files = $newList;
		}
	}
    $newList = array();
    foreach( $files as $file ) {
        $newList[] = array(dirname($file),basename($file));
    }
    $files  = $newList;
}



function get_result_array($list) {			// print table of found items
	if(!is_array($list)) return;

	$cnt = count($list);
	$array = array();
	for($i=0;$i<$cnt;++$i) {
		$dir = $list[$i][0];	$item = $list[$i][1];
		$s_dir=str_replace($GLOBALS['home_dir'], '', $dir );	
		if(strlen($s_dir)>65) $s_dir=substr($s_dir,0,62)."...";
		$s_item=$item;	if(strlen($s_item)>45) $s_item=substr($s_item,0,42)."...";
		$link = "";	$target = "";
		
		
		if(get_is_dir($dir,$item)) {
			$img = "dir.png";
			$link = ext_make_link("list",get_rel_item($dir, $item),NULL);
		} else {
			$img = get_mime_type( $item, "img");
			//if(get_is_editable($dir,$item) || get_is_image($dir,$item)) {
			$link = $GLOBALS["home_url"]."/".get_rel_item($dir, $item);
			$target = "_blank";
			//}
		}
		$array[$i]['last_mtime'] = ext_isFTPMode() ? $GLOBALS['ext_File']->filemtime($GLOBALS['home_dir'].'/'.$dir.'/'.$item) : filemtime($dir.'/'.$item);
		$array[$i]['file_id'] = md5($s_dir.$s_item);
		$array[$i]['dir'] = str_replace($GLOBALS['home_dir'], '', $dir );
		$array[$i]['s_dir'] = empty($s_dir) ? '' : $s_dir;
		$array[$i]['file'] = $s_item;
		$array[$i]['link'] = $link;
		$array[$i]['icon'] = _EXT_URL."/images/$img";
	}
	return $array;
}

/**
 * Recursive implementation of glob
 * @param $pattern
 * @param int $flags
 * @return array
 */
function glob_recursive($pattern, $flags = 0)
{
    $files = glob($pattern, $flags);
    if ($files === false) return array();
    foreach (glob(dirname($pattern) . '/*', GLOB_ONLYDIR | GLOB_NOSORT) as $dir) {
        $files = array_merge($files, glob_recursive($dir . '/' . basename($pattern), $flags));
    }

    return $files;
}
//------------------------------------------------------------------------------

function show_searchform($dir='') {
	?>
{
     "height":400,
     "autoScroll":true,

     items: [
        new Ext.TabPanel({
        activeTab: 0,
        items: [{
            "title":"<?php echo $GLOBALS["messages"]["searchlink"] ?>",
            "height": "370",
            "autoScroll":true,
            "items":
                new Ext.DataView({
                "id": "dataview",
                 tpl: new Ext.XTemplate(
                    '<tpl for=".">',
                    '<div class="search-item">',
                        '<h3>',
                        '<a onclick="selectFile(\'{dir}\', \'{file}\');Ext.getCmp(\'dialog\').destroy();return false;" href="#" target="_blank">{s_dir}/{file}, {lastModified:date("M j, Y")}</a>',
                        '</h3>',
                    '</div></tpl>'
                ),
                 store: new Ext.data.Store({
                        proxy: new Ext.data.HttpProxy({
                            url: "<?php echo $GLOBALS['script_name'] ?>"
                        }),
                        reader: new Ext.data.JsonReader({
                            root: 'items',
                            totalProperty: 'totalCount',
                            id: 'file_id'
                        }, [
                            {name: 'fileId', mapping: 'file_id'},
                            {name: 'file', mapping: 'file'},
                            {name: 'dir', mapping: 'dir'},
                            {name: 's_dir', mapping: 's_dir'},
                            {name: 'lastModified', mapping: 'last_mtime', type: 'date', dateFormat: 'timestamp'}
                        ]),
                        baseParams: {
                            limit:20,
                            option: "com_extplorer",
                            action:"search",
                            dir: "<?php echo $dir ?>",
                            content: '0',
                            subdir: '1'
                        }
                    }),
                 itemSelector: 'div.search-item'
            }),

         tbar: [
             'Search: ', ' ',
             new Ext.app.SearchField({
                 store: Ext.getCmp("dataview").store,
                 width:320,
                value: "*"
             })
         ],

         bbar: new Ext.PagingToolbar({
             store: Ext.getCmp("dataview").store,
             pageSize: 20,
             displayInfo: true,
             displayMsg: 'Results {0} - {1} of {2}',
             emptyMsg: "No files to display"
         })
    },
    {
        title: "Search Options",
        xtype:"form",
        layout: "form",
        "height": "350",
        items: [

            {
                id:'myGroup',
                xtype: 'checkboxgroup',
                fieldLabel: 'Extensive Search',
                itemCls: 'x-check-group-alt',
                // Put all controls in a single column with width 100%
                columns: 1,
                items: [
                {
                    boxLabel: 'Search within Subdirectories?', name: 'subdir',
                    checked: true,
                    tooltip: "<?php echo ext_Lang::msg( 'miscsubdirs', true ) ?>?",
                    "listeners": {
                        "check": {
                            fn: function(box, checked) {
                                Ext.getCmp("dataview").store.baseParams.subdir = (checked ? '1' : '0');
                            }
                        }
                    }
                 }
                ]
            },{
                fieldLabel: "<?php echo ext_Lang::msg('misccontent', true) ?>",
                xtype: "textfield",
                width: 200,
                id: "contentfield",
                name: "content",
                "listeners": {
                    "change": {
                        fn: function(field, newValue) {
                            Ext.getCmp("dataview").store.baseParams.content = newValue;
                        }
                    }
                }

            },
            {
                xtype: "compositefield",
                items: [
                {
                    xtype: "checkbox",
                    fieldLabel: "Modification Date between",
                    "listeners": {
                    "check": {
                        fn: function(box, checked) {
                            if( checked ) {Ext.getCmp( "mdate_start" ).enable(); Ext.getCmp( "mdate_end" ).enable(); }
                            else {
								Ext.getCmp( "mdate_start" ).disable(); Ext.getCmp( "mdate_end" ).disable(); 	
								Ext.getCmp("dataview").store.baseParams.mdate_start = "";
								Ext.getCmp("dataview").store.baseParams.mdate_end = "";							
							}
                        }
                    }
                }
                },
                new Ext.form.DateField({
                    tooltip: 'Start',
                    name: 'mdate_start',
                    id: 'mdate_start',
                    "listeners": {
                        "change": {
                            fn: function(field, newValue) {
                                Ext.getCmp("dataview").store.baseParams.mdate_start = newValue;
                            }
                        }
                    },
                    width:90,
                    disabled: true
                }),
                new Ext.form.DateField({
                    tooltip: 'End',
                    name: 'mdate_end',
                    id: 'mdate_end',
                    disabled: true,
                    "listeners": {
                        "change": {
                            fn: function(field, newValue) {
                                Ext.getCmp("dataview").store.baseParams.mdate_end = newValue;
                            }
                        }
                    },
                    width:90
                })

                ]
            },
            {
                xtype: "compositefield",
                items: [
                {
                    xtype: "checkbox",
                    name: "age_enabled",
                    fieldLabel: "max. File Age",
                    "listeners": {
                        "check": {
                            fn: function(box, checked) {
                                if( checked ) {Ext.getCmp( "age_unit" ).enable(); Ext.getCmp( "age_value" ).enable(); }
								else {
									Ext.getCmp( "age_value" ).disable(); Ext.getCmp( "age_unit" ).disable(); 	
									Ext.getCmp("dataview").store.baseParams.age_value = "";
									Ext.getCmp("dataview").store.baseParams.age_unit = "";							
								}
                            }
                        }
                    }
                },
				{
					xtype: "textfield",
					name: "age_value",
					id: "age_value",
					value: 1,
					width: 40,
                    "listeners": {
                        "change": {
                            fn: function(field, newValue) {
                                Ext.getCmp("dataview").store.baseParams.age_value = newValue;
                            }
                        }
                    },
					disabled: true
				},
					new Ext.form.ComboBox({
						triggerAction: 'all',
						lazyRender:true,
						mode: 'local',
						disabled: true,
						name: "age_unit",
						id: "age_unit",
						value: "days",
                        "listeners": {
                            "change": {
                                fn: function(field, newValue) {
                                    Ext.getCmp("dataview").store.baseParams.age_unit = newValue;
                                }
                            }
                        },
						store: new Ext.data.ArrayStore({
							id: 0,
							fields: [
								'myId',
								'displayText'
							],
							data: [["minute", 'Minute(s)'], ["hours", 'Hour(s)'],
								["days", "Day(s)"], ["weeks", "Week(s)"], ["months", "Month(s)"], ["years", "Year(s)" ]
								]
						}),
						valueField: 'myId',
						displayField: 'displayText'
					})
                ]
            }

    ]
    }]
    })
    ]
}

<?php 
	
}

//------------------------------------------------------------------------------
