<?php defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
use \Firebase\JWT\JWT;
 
class Rest extends REST_Controller {

	private $secretkey = 'rest'; //ubah dengan kode rahasia apapun

	public function __construct()
	{
		parent::__construct();
		$this->load->library(['form_validation']);
		$this->load->model(['Bridingbpjs_model']);
	}

	// method untuk melihat token pada user
    public function generate_post(){
        $date = new DateTime();
        $username = $this->input->post('username',TRUE); //ini adalah kolom username pada database yang saya berinama username.
        $password = $this->input->post('password',TRUE); //ini adalah kolom password pada database yang saya berinama password.
        $res = $this->Bridingbpjs_model->getData(['username'=>$username,'status'=>'Y'],1);
        if ($username) {
            if (sha1($username.'-'.$password) == $res[0]->password) {
                $payload['id'] = 1;
                $payload['username'] = 'rsuddepok';
                $payload['iat'] = $date->getTimestamp(); //waktu di buat
                $payload['exp'] = $date->getTimestamp() + 3600; //satu jam
                $output['token'] = JWT::encode($payload,$this->secretkey);
                return $this->response($output,REST_Controller::HTTP_OK);
            } else {
                $this->viewtokenfail($username);
            }
        } else {
            $this->viewtokenfail($username);
        }
    }

    // method untuk jika generate token diatas salah
    public function viewtokenfail($username){
        $this->response([
          'status'=>FALSE,
          'username'=>$username,
          'message'=>'Invalid!'
          ],REST_Controller::HTTP_BAD_REQUEST);
    }

    // method untuk mengecek token setiap melakukan post, put, etc
    public function cektoken(){
        $jwt = $this->input->get_request_header('Authorization');
        //$decode = JWT::decode($jwt,$this->secretkey,array('HS256'));
        if (preg_match('/Bearer\s(\S+)/', $jwt , $matches)) {
            $token = $matches[1];
        }
        //echo '<pre>';
        print_r($token);
        /*try {
            $decode = JWT::decode($jwt,$this->secretkey,array('HS256'));
            if ($decode) {
                return TRUE;
            }
        } catch (Exception $e) {
            exit('<p style="color:red; font-size:25px;">Wrong token</p>');
        }*/
    }

}