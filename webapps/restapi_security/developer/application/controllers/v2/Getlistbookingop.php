<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Getlistbookingop extends MY_Controller {

    function __construct()
    {
        parent::__construct();
        $this->auth();
        $this->load->model(['Getlistbookingop_model']);
    }
	
    public function listbookingop_post(){

        $method = $this->post();


        if(!empty($method['tanggalawal'])){
            $where['(booking_operasi.tanggal) >='] = $method['tanggalawal'];
        }else{
            $where['(booking_operasi.tanggal) >='] = date('2019-01-01');
        }

        if(!empty($method['tanggalakhir'])){
            $where['(booking_operasi.tanggal) <=']  = $method['tanggalakhir'];
        }else{
            $where['(booking_operasi.tanggal) <='] = date('2019-12-30');
        }

        $res = $this->Getlistbookingop_model->getData($where);

        foreach ($res as $key => $v) {
            if (!empty($v->no_peserta)) {
                    $peserta = $v->no_peserta;
                }else{
                    $peserta = '';
                };
            if (!empty($v->status == 'Menunggu')) {
                $terlaksana = 0;
            }else{
                $terlaksana = 1;
            };

             $date = new DateTime();
             
            $data[] = array(
                'kodebooking' => $v->no_rawat,
                'tanggaloperasi' => $v->tanggal,
                'jenistindakan' => $v->nm_perawatan,
                'kodepoli' => $v->kd_poli,
                'namapoli' => $v->nm_perawatan,
                'terlaksana' => $terlaksana,
                'nopeserta' => $peserta,
                'lastupdate' => $date->getTimestamp()
            );
        }

        $hasil = [
                'response'=>[
                     'list' => $data
                     // 'tanggaloperasi' => $res->tanggal,
                     // 'jenistindakan' => $res->nm_perawatan,
                     // 'kodepoli' => $res->kd_poli,
                     // 'namapoli' => $res->nm_poli,
                     // 'terlaksana' => $res->no_peserta
                ],
                 'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
        ];
        

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
