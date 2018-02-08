<?php
/* vim: set expandtab tabstop=4 shiftwidth=4 softtabstop=4: */

/**
 * Net_FTP observer.
 *
 * This class implements the Observer part of a Subject-Observer
 * design pattern. It listens to the events sent by a Net_FTP instance.
 * This module had many influences from the Log_observer code.
 *
 * PHP versions 4 and 5
 *
 * @category  Networking
 * @package   FTP
 * @author    Tobias Schlitt <toby@php.net>
 * @author    Laurent Laville <pear@laurent-laville.org>
 * @author    Chuck Hagenbuch <chuck@horde.org>
 * @copyright 1997-2008 The PHP Group
 * @license   BSD http://www.opensource.org/licenses/bsd-license.php
 * @version   CVS: $Id: Observer.php 246 2016-02-10 21:21:12Z soeren $
 * @link      http://pear.php.net/package/Net_FTP
 * @since     File available since Release 0.0.1
 */

/**
 * This class implements the Observer part of a Subject-Observer
 * design pattern. It listens to the events sent by a Net_FTP instance.
 * This module had many influences from the Log_observer code.
 *
 * @category  Networking
 * @package   FTP
 * @author    Laurent Laville <pear@laurent-laville.org>
 * @author    Chuck Hagenbuch <chuck@horde.org>
 * @author    Tobias Schlitt <toby@php.net>
 * @copyright 1997-2008 The PHP Group
 * @license   http://www.php.net/license/3_0.txt  PHP License 3.0
 * @version   Release: 1.4.0
 * @link      http://pear.php.net/package/Net_FTP
 * @since     1.3.0.0
 * @access    public
 *
 * @example    observer_upload.php An example of Net_FTP_Observer implementation.
 */
class Net_FTP_Observer
{
    /**
     * Instance-specific unique identification number.
     *
     * @var integer
     * @since 1.3.0
     * @access private
     */
    var $_id;

    /**
     * Creates a new basic Net_FTP_Observer instance.
     *
     * @since 1.3.0
     * @access public
     */
    function Net_FTP_Observer()
    {
        $this->_id = md5(microtime());
    }

    /**
     * Returns the listener's identifier
     *
     * @return string The listener's identifier
     * @since 1.3.0
     * @access public
     */
    function getId()
    {
        return $this->_id;
    }

    /**
     * This is a stub method to make sure that Net_FTP_Observer classes do
     * something when they are notified of a message.  The default behavior
     * is to just do nothing.
     * You should override this method.
     *
     * @param mixed $event A hash describing the net event.
     *
     * @since 1.3.0
     * @access public
     * @return void
     */
    function notify($event)
    {
        return;
    }
}
?>
