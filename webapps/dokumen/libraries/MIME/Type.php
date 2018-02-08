<?php
/* vim: set expandtab tabstop=4 shiftwidth=4: */
/**
 * Part of MIME_Type
 *
 * PHP version 4 and 5
 *
 * @category File
 * @package  MIME_Type
 * @author   Ian Eure <ieure@php.net>
 * @license  http://www.gnu.org/copyleft/lesser.html LGPL
 * @link     http://pear.php.net/package/MIME_Type
 */

require_once 'PEAR.php';

$_fileCmd = &PEAR::getStaticProperty('MIME_Type', 'fileCmd');
$_fileCmd = 'file';

/**
 * Class for working with MIME types
 *
 * @category MIME
 * @package  MIME_Type
 * @author   Ian Eure <ieure@php.net>
 * @license  http://www.gnu.org/copyleft/lesser.html LGPL
 * @version  Release: @version@
 * @link     http://pear.php.net/package/MIME_Type
 *
 * @method string autoDetect($file, $params = false) Autodetect a file's MIME-type (can be called statically and on an MIME_Type object)
 */
class MIME_Type
{
    /**
     * The MIME media type
     *
     * @var string
     */
    public $media = '';

    /**
     * The MIME media sub-type
     *
     * @var string
     */
    public $subType = '';

    /**
     * Optional MIME parameters
     *
     * @var array
     */
    public $parameters = array();

    /**
     * List of valid media types.
     * A media type is the string in front of the slash.
     * The media type of "text/xml" would be "text".
     *
     * @var array
     */
    public $validMediaTypes = array(
        'text',
        'image',
        'audio',
        'video',
        'application',
        'multipart',
        'message'
    );

    /**
     * If the finfo functions shall be used when they are available
     *
     * @var boolean
     */
    public $useFinfo = true;

    /**
     * If mime_content_type shall be used when available
     *
     * @var boolean
     */
    public $useMimeContentType = true;

    /**
     * If the file command shall be used when available
     *
     * @var boolean
     */
    public $useFileCmd = true;

    /**
     * If the in-built file extension detection shall be used
     *
     * @var boolean
     */
    public $useExtension = true;

    /**
     * Path to the "magic" file database.
     * If NULL, the default one is used
     *
     * @var string
     */
    public $magicFile = null;


    /**
     * Constructor.
     *
     * If $type is set, if will be parsed and the appropriate class vars set.
     * If not, you get an empty class.
     * This is useful, but not quite as useful as parsing a type.
     *
     * @param string $type MIME type
     */
    public function __construct($type = false)
    {
        if ($type) {
            $this->parse($type);
        }
    }

    /**
     * Make "autoDetect" method available.
     *
     * @param string $method    Method to call. Only "autoDetect" supported.
     * @param array  $arguments Function parameters.
     *
     * @return string MIME type
     */
    public function __call($method, $arguments)
    {
        if ($method !== 'autoDetect') {
            return trigger_error(
                'Call to undefined method MIME_TYPE::' . $method . '()',
                E_USER_ERROR
            );
        }
        $type = call_user_func_array(array($this, '_autoDetect'), $arguments);
        $this->parse($type);
        return $type;
    }

    /**
     * Make "autoDetect" method available statically.
     *
     * @param string $method    Method to call. Only "autoDetect" supported.
     * @param array  $arguments Function parameters.
     *
     * @return string MIME type
     */
    public static function __callStatic($method, $arguments)
    {
        if ($method !== 'autoDetect') {
            return trigger_error(
                'Call to undefined method MIME_TYPE::' . $method . '()',
                E_USER_ERROR
            );
        }

        $mt = new MIME_Type();
        return call_user_func_array(array($mt, '_autoDetect'), $arguments);
    }

    /**
     * Parse a mime-type and set the class variables.
     *
     * @param string $type MIME type to parse
     *
     * @return boolean True if the type has been parsed, false if not
     */
    public function parse($type)
    {
        if ($type instanceof PEAR_Error) {
            return false;
        }

        $this->media      = $this->getMedia($type);
        $this->subType    = $this->getSubType($type);
        $this->parameters = array();
        if (MIME_Type::hasParameters($type)) {
            include_once 'MIME/Type/Parameter.php';
            foreach (MIME_Type::getParameters($type) as $param) {
                $param = new MIME_Type_Parameter($param);
                $this->parameters[$param->name] = $param;
            }
        }

        return true;
    }


    /**
     * Does this type have any parameters?
     *
     * @param string $type MIME type to check
     *
     * @return boolean true if $type has parameters, false otherwise
     */
    public static function hasParameters($type)
    {
        if (strstr($type, ';')) {
            return true;
        }
        return false;
    }


    /**
     * Get a MIME type's parameters
     *
     * @param string $type MIME type to get parameters of
     *
     * @return array $type's parameters
     */
    public static function getParameters($type)
    {
        $params = array();
        $tmp    = explode(';', $type);
        for ($i = 1; $i < count($tmp); $i++) {
            $params[] = trim($tmp[$i]);
        }
        return $params;
    }


    /**
     * Strip parameters from a MIME type string.
     *
     * @param string $type MIME type string
     *
     * @return string MIME type with parameters removed
     */
    public static function stripParameters($type)
    {
        if (strstr($type, ';')) {
            return substr($type, 0, strpos($type, ';'));
        }
        return $type;
    }


    /**
     * Removes comments from a media type, subtype or parameter.
     *
     * @param string $string  String to strip comments from
     * @param string $comment Comment is stored in there.
     *                        Do not set it to NULL if you want the comment.
     *
     * @return string String without comments
     */
    public static function stripComments($string, &$comment)
    {
        if (strpos($string, '(') === false) {
            return $string;
        }

        $inquote   = false;
        $escaped   = false;
        $incomment = 0;
        $newstring = '';

        for ($n = 0; $n < strlen($string); $n++) {
            if ($escaped) {
                if ($incomment == 0) {
                    $newstring .= $string[$n];
                } else if ($comment !== null) {
                    $comment .= $string[$n];
                }
                $escaped = false;
            } else if ($string[$n] == '\\') {
                $escaped = true;
            } else if (!$inquote && $incomment > 0 && $string[$n] == ')') {
                $incomment--;
                if ($incomment == 0 && $comment !== null) {
                    $comment .= ' ';
                }
            } else if (!$inquote && $string[$n] == '(') {
                $incomment++;
            } else if ($string[$n] == '"') {
                if ($inquote) {
                    $inquote = false;
                } else {
                    $inquote = true;
                }
            } else if ($incomment == 0) {
                $newstring .= $string[$n];
            } else if ($comment !== null) {
                $comment .= $string[$n];
            }
        }

        if ($comment !== null) {
            $comment = trim($comment);
        }

        return $newstring;
    }


    /**
     * Get a MIME type's media
     * Note: 'media' refers to the portion before the first slash
     *
     * @param string $type MIME type to get media of
     *
     * @return string $type's media
     */
    public static function getMedia($type)
    {
        $tmp = explode('/', $type);
        return strtolower(trim(MIME_Type::stripComments($tmp[0], $null)));
    }


    /**
     * Get a MIME type's subtype
     *
     * @param string $type MIME type to get subtype of
     *
     * @return string $type's subtype, null if invalid mime type
     */
    public static function getSubType($type)
    {
        $tmp = explode('/', $type);
        if (!isset($tmp[1])) {
            return null;
        }
        $tmp = explode(';', $tmp[1]);
        return strtolower(trim(MIME_Type::stripComments($tmp[0], $null)));
    }


    /**
     * Create a textual MIME type from object values
     *
     * This function performs the opposite function of parse().
     *
     * @return string MIME type string
     */
    public function get()
    {
        $type = strtolower($this->media . '/' . $this->subType);
        if (count($this->parameters)) {
            foreach ($this->parameters as $key => $null) {
                $type .= '; ' . $this->parameters[$key]->get();
            }
        }
        return $type;
    }


    /**
     * Is this type experimental?
     *
     * Note: Experimental types are denoted by a leading 'x-' in the media or
     *       subtype, e.g. text/x-vcard or x-world/x-vrml.
     *
     * @param string $type MIME type to check
     *
     * @return boolean true if $type is experimental, false otherwise
     */
    public static function isExperimental($type)
    {
        if (substr(MIME_Type::getMedia($type), 0, 2) == 'x-'
            || substr(MIME_Type::getSubType($type), 0, 2) == 'x-'
        ) {
            return true;
        }
        return false;
    }


    /**
     * Is this a vendor MIME type?
     *
     * Note: Vendor types are denoted with a leading 'vnd. in the subtype.
     *
     * @param string $type MIME type to check
     *
     * @return boolean true if $type is a vendor type, false otherwise
     */
    public static function isVendor($type)
    {
        if (substr(MIME_Type::getSubType($type), 0, 4) == 'vnd.') {
            return true;
        }
        return false;
    }


    /**
     * Is this a wildcard type?
     *
     * @param string $type MIME type to check
     *
     * @return boolean true if $type is a wildcard, false otherwise
     */
    public static function isWildcard($type)
    {
        if ($type == '*/*' || MIME_Type::getSubtype($type) == '*') {
            return true;
        }
        return false;
    }


    /**
     * Perform a wildcard match on a MIME type
     *
     * Example:
     * MIME_Type::wildcardMatch('image/*', 'image/png')
     *
     * @param string $card Wildcard to check against
     * @param string $type MIME type to check
     *
     * @return boolean true if there was a match, false otherwise
     */
    public static function wildcardMatch($card, $type)
    {
        if (!MIME_Type::isWildcard($card)) {
            return false;
        }

        if ($card == '*/*') {
            return true;
        }

        if (MIME_Type::getMedia($card) == MIME_Type::getMedia($type)) {
            return true;
        }

        return false;
    }


    /**
     * Add a parameter to this type
     *
     * @param string $name    Attribute name
     * @param string $value   Attribute value
     * @param string $comment Comment for this parameter
     *
     * @return void
     */
    public function addParameter($name, $value, $comment = false)
    {
        $tmp = new MIME_Type_Parameter();

        $tmp->name               = $name;
        $tmp->value              = $value;
        $tmp->comment            = $comment;
        $this->parameters[$name] = $tmp;
    }


    /**
     * Remove a parameter from this type
     *
     * @param string $name Parameter name
     *
     * @return void
     */
    public function removeParameter($name)
    {
        unset($this->parameters[$name]);
    }

    /**
     * Autodetect a file's MIME-type
     *
     * @param string $file   Path to the file to get the type of
     * @param bool   $params Append MIME parameters if true
     *
     * @return string $file's MIME-type on success, PEAR_Error otherwise
     *
     * @since 1.3.0
     *
     * @internal Tries to use fileinfo extension at first. If that
     *  does not work, mime_magic is used. If this is also not available
     *  or does not succeed, "file" command is tried to be executed with
     *  System_Command. When that fails, too, then we use our in-built
     *  extension-to-mimetype-mapping list.
     */
    protected function _autoDetect($file, $params = false)
    {
        // Sanity checks
        if (!file_exists($file)) {
            return PEAR::raiseError("File \"$file\" doesn't exist");
        }

        if (!is_readable($file)) {
            return PEAR::raiseError("File \"$file\" is not readable");
        }

        if ($this->useFinfo && function_exists('finfo_file')) {
            $finfo = finfo_open(FILEINFO_MIME, $this->magicFile);
            if ($finfo) {
                $type  = finfo_file($finfo, $file);
                finfo_close($finfo);
                if ($type !== false && $type !== '') {
                    return MIME_Type::_handleDetection($type, $params);
                }
            }
        }

        if ($this->useMimeContentType && function_exists('mime_content_type')) {
            $type = mime_content_type($file);
            if ($type !== false && $type !== '') {
                return MIME_Type::_handleDetection($type, $params);
            }
        }

        if ($this->useFileCmd) {
            @include_once 'System/Command.php';
            if (class_exists('System_Command')) {
                $type = $this->_fileAutoDetect($file);
                if ($type !== false && $type !== '') {
                    return MIME_Type::_handleDetection($type, $params);
                }
            }
        }

        if ($this->useExtension) {
            include_once 'MIME/Type/Extension.php';
            $mte = new MIME_Type_Extension();
            return $mte->getMIMEType($file);
        }

        return PEAR::raiseError("Sorry, couldn't determine file type.");
    }


    /**
     * Handles a detected MIME type and modifies it if necessary.
     *
     * @param string $type   MIME Type of a file
     * @param bool   $params Append MIME parameters if true
     *
     * @return string $file's MIME-type on success, PEAR_Error otherwise
     */
    protected static function _handleDetection($type, $params)
    {
        // _fileAutoDetect() may have returned an error.
        if (PEAR::isError($type)) {
            return $type;
        }

        // Don't return an empty string
        if (!$type || !strlen($type)) {
            return PEAR::raiseError("Sorry, couldn't determine file type.");
        }

        // Strip parameters if present & requested
        if (MIME_Type::hasParameters($type) && !$params) {
            $type = MIME_Type::stripParameters($type);
        }

        return $type;
    }


    /**
     * Autodetect a file's MIME-type with 'file' and System_Command
     *
     * @param string $file Path to the file to get the type of
     *
     * @return string $file's MIME-type
     *
     * @since 1.0.0beta1
     */
    protected function _fileAutoDetect($file)
    {
        $cmd   = new System_Command();
        $magic = '';

        // Make sure we have the 'file' command.
        $fileCmd = PEAR::getStaticProperty('MIME_Type', 'fileCmd');
        if (!$cmd->which($fileCmd)) {
            unset($cmd);
            return PEAR::raiseError("Can't find file command \"{$fileCmd}\"");
        }
        if (strlen($this->magicFile)) {
            $magic = '--magic-file ' . escapeshellarg($this->magicFile);
        }

        $cmd->pushCommand($fileCmd, $magic, "-bi " . escapeshellarg($file));
        $res = $cmd->execute();
        unset($cmd);

        return $res;
    }
}
?>
