<?php

defined('BASEPATH') OR exit('No direct script access allowed');
use \Firebase\JWT\JWT;

class Auth extends MY_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        // Configure limits on our controller methods
        // Ensure you have created the 'limits' table and enabled 'limits' within application/config/rest.php
        $this->methods['users_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['users_post']['limit'] = 100; // 100 requests per hour per user/key
        $this->methods['users_delete']['limit'] = 50; // 50 requests per hour per user/key
        $this->load->model('Admin_model');
    }

    public function test_post(){

        $user = $this->post('usere');
        $u = sha1($user);
        $q = array('usere'=>$u);
        $val = $this->Admin_model->getData($q)->row();

        // print_r($val);
        // exit(); 
   
    }

    public function login_post()
    {
        $user = $this->post('usere');
        $pass = $this->post('passworde'); //Username Posted
         //Pasword Posted
        $u = sha1($user);
        $p = sha1($pass);
        $q = array('usere'=>$u);
        $gabung = $u."-".$p;
; //For where query condition
        $kunci = $this->config->item('thekey');
        $invalidLogin = ['status' => 'Cilukkkk Baaaaaa !!!!']; //Respon if login invalid
        $val = $this->Admin_model->getData($q)->row(); //Model to get single data row from database base on username
        $usere = $val->usere;
        $pasworde = $val->passworde;
        $pri = $usere."-".$pasworde;

        if($this->Admin_model->getData($q)->num_rows() == 0){
            $this->response($invalidLogin, REST_Controller::HTTP_NOT_FOUND);
        }
        if($gabung == $pri){  //Condition if password matched
        	// $token['id'] = $val->id;  //From here
            $token['username'] = $u;
            $date = new DateTime();
            $token['iat'] = $date->getTimestamp();
            $token['exp'] = $date->getTimestamp() + 60*60*5; //To here is to generate token
            $output = JWT::encode($token,$kunci ); //This is the output token

            $hasil = array(
                'token' => [$output],
                'metadata' => [
                    'message' => 'OK',
                    'code' => 200
                ]
            );
            $this->set_response($hasil, REST_Controller::HTTP_OK); //This is the respon if success
        }
        else {
            $this->set_response($invalidLogin, REST_Controller::HTTP_NOT_FOUND); //This is the respon if failed
        }
    }

}
