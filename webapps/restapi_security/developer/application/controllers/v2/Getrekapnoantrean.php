<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Getrekapnoantrean extends MY_Controller {

    function __construct()
    {
        parent::__construct();
        $this->auth();
        $this->load->model(['Rekapantreanbpjs_model']);
    }
	
    public function rekap_post(){

        $method = $this->post();


        if(!empty($method['tanggalperiksa']) && $this->validateDate($method['tanggalperiksa'])){
            $where['reg_periksa.tgl_registrasi'] = $method['tanggalperiksa'];
        }else{
            $where['reg_periksa.tgl_registrasi'] = date('Y-m-d');
        }

        if(!empty($method['kodepoli'])){
            $where['reg_periksa.kd_poli'] = $method['kodepoli'];
        }

        $wheres['reg_periksa.stts'] = 'Sudah';

        $whereres2 = $where + $wheres;

        

        $res = $this->Rekapantreanbpjs_model->getData($where);
        $res2 = $this->Rekapantreanbpjs_model->count('',$whereres2);
        $totan = count($res);
        $date = new DateTime();

        if ($res2 != 0) {
            $ly = $res2;
        }else{
            $ly = 0;
        }


        if (isset($method['kodepoli'])) {
            foreach ($res as $key => $v) {

                $data = array(
                   'namapoli' => $v->nm_poli,
                   'totalantrean' => $totan,
                   'jumlahterlayani' => $res2,
                   'lastupdate' => $date->getTimestamp(),
                   'lastupdatetanggal' => $date
               );
            };
        }else{
            foreach ($res as $key => $v) {

                $data[] = array(
                   'namapoli' => $v->nm_poli,
                   'totalantrean' => $totan,
                   'jumlahterlayani' => $ly,
                   'lastupdate' => $date->getTimestamp(),
                   'lastupdatetanggal' => $date
               );
            };
        }

        if (!empty($data)) {
            $hasil = [
                'response'=>[
                     $data
                ],
                 'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ];
        
        }else{
            $hasil = [
                'response'=>[
                     'Maaf Data Pasien Tidak Ditemukan Pada Tanggal Tersebut'
                ],
                 'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ];
        }
        

        $this->response($hasil, REST_Controller::HTTP_OK);
    }

    private function validateDate($date, $format = 'Y-m-d')
    {
        $d = DateTime::createFromFormat($format, $date);
        // The Y ( 4 digits year ) returns TRUE for any integer with any number of digits so changing the comparison from == to === fixes the issue.
        return $d && $d->format($format) === $date;
    }

    public function users_get()
    {
        // Users from a data store e.g. database
        $users = [
            ['id' => 1, 'name' => 'John', 'email' => 'john@example.com', 'fact' => 'Loves coding'],
            ['id' => 2, 'name' => 'Jim', 'email' => 'jim@example.com', 'fact' => 'Developed on CodeIgniter'],
            ['id' => 3, 'name' => 'Jane', 'email' => 'jane@example.com', 'fact' => 'Lives in the USA', ['hobbies' => ['guitar', 'cycling']]],
        ];

        $id = $this->get('id');

        // If the id parameter doesn't exist return all the users

        if ($id === NULL)
        {
            // Check if the users data store contains users (in case the database result returns NULL)
            if ($users)
            {
                // Set the response and exit
                $this->response($users, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            }
            else
            {
                // Set the response and exit
                $this->response([
                    'status' => FALSE,
                    'message' => 'No users were found'
                ], REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
            }
        }

        // Find and return a single record for a particular user.

        $id = (int) $id;

        // Validate the id.
        if ($id <= 0)
        {
            // Invalid id, set the response and exit.
            $this->response(NULL, REST_Controller::HTTP_BAD_REQUEST); // BAD_REQUEST (400) being the HTTP response code
        }

        // Get the user from the array, using the id as key for retrieval.
        // Usually a model is to be used for this.

        $user = NULL;

        if (!empty($users))
        {
            foreach ($users as $key => $value)
            {
                if (isset($value['id']) && $value['id'] === $id)
                {
                    $user = $value;
                }
            }
        }

        if (!empty($user))
        {
            $this->set_response($user, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
        }
        else
        {
            $this->set_response([
                'status' => FALSE,
                'message' => 'User could not be found'
            ], REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
        }
    }

    public function users_delete()
    {
        $id = (int) $this->get('id');

        // Validate the id.
        if ($id <= 0)
        {
            // Set the response and exit
            $this->response(NULL, REST_Controller::HTTP_BAD_REQUEST); // BAD_REQUEST (400) being the HTTP response code
        }

        // $this->some_model->delete_something($id);
        $message = [
            'id' => $id,
            'message' => 'Deleted the resource'
        ];

        $this->set_response($message, REST_Controller::HTTP_NO_CONTENT); // NO_CONTENT (204) being the HTTP response code
    }

}
