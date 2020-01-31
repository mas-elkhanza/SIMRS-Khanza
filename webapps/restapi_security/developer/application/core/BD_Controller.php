<?php defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
require_once APPPATH . '/libraries/REST_Controller.php';
require_once APPPATH . '/libraries/JWT.php';
require_once APPPATH . '/libraries/BeforeValidException.php';
require_once APPPATH . '/libraries/ExpiredException.php';
require_once APPPATH . '/libraries/SignatureInvalidException.php';
use \Firebase\JWT\JWT;

class BD_Controller extends REST_Controller
{
	private $user_credential;
    public function auth()
    {
        // Konfigurasi Limit kontroller
        $this->methods['users_get']['limit'] = 500; // 500 requests per jam per user/key
        $this->methods['users_post']['limit'] = 100; // 100 requests per jam per user/key
        $this->methods['users_delete']['limit'] = 50; // 50 requests per jam per user/key
        //JWT Auth middleware
        $headers1 = $this->input->get_request_header('Authorization');
        $headers2 = $this->input->get_request_header('x-token');
        //print_r($headers1);
        $kunci = $this->config->item('thekey'); //secret key for encode and decode
        $token= "token";
       	if (!empty($headers1)) {
        	if (preg_match('/Bearer\s(\S+)/', $headers1 , $matches)) {
            $token = $matches[1];
        	}
    	}else{
            $token = $headers2;
        }
        
        
        try {
           $decoded = JWT::decode($token, $kunci, array('HS256'));
           $this->user_data = $decoded;
        } catch (Exception $e) {
            $invalid = ['status' => $e->getMessage()]; //Respon if credential invalid
            $this->response($invalid, 401);//401
        }
    }
}