<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Getorderformforlis extends MY_Controller {
    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->model([
            'GetOrderFormForLis_model',
            'Pasien_model',
            'Jnsperawatanlab_model',
            'Templatelaboratorium_model'
        ]);
        $this->auth();
    }

    private function _orderlaballralan($where=[],$limit='',$offset='')
    {
        if(is_array($where)){
            $res = $this->GetOrderFormForLis_model->getDataRalan($where,$limit,$offset);
        }else{
            $res = $this->GetOrderFormForLis_model->getDataRalan('',$limit,$offset);
        }  

        return $res;     
    }

    private function _orderlaballranap($where=[],$limit='',$offset='')
    {
        if(is_array($where)){
            $res = $this->GetOrderFormForLis_model->getDataRanap($where,$limit,$offset);
        }else{
            $res = $this->GetOrderFormForLis_model->getDataRanap('',$limit,$offset);;
        }  

        return $res;
    }

    private function _getpasien($where=[]){
        if(is_array($where)){
            $res = ['status'=>'Ok','data'=>$this->Pasien_model->getData($where)];
        }else{
            $res = ['status'=>'Not','data'=>'Mohon maaf nomor rekam medis yang anda kirimkan tidak terdaftar.'];
        }

        return $res;
    }

    private function _getmasterlab($where=[]){
        if(is_array($where)){
            $res = ['status'=>'Ok','data'=>$this->Jnsperawatanlab_model->getData($where)];
        }else{
            $res = ['status'=>'Not','data'=>'Mohon maaf nomor rekam medis yang anda kirimkan tidak terdaftar.'];
        }

        return $res;
    }

    private function _getmastertemplate($where=[]){
        if(is_array($where)){
            $res = ['status'=>'Ok','data'=>$this->Templatelaboratorium_model->getData($where)];
        }else{
            $res = ['status'=>'Not','data'=>'Mohon maaf nomor rekam medis yang anda kirimkan tidak terdaftar.'];
        }

        return $res;
    }

    public function orderlab_get()
    {
        $method = $this->get();
       
        if($method['total']==='all' && $method['status']==='ralan'){
            $limit = !empty($method['limit']) ? $method['limit'] : '';
            $offset = !empty($method['offset']) ? $method['offset'] : '';
            $where = ['reg_periksa.stts'=>'Belum','reg_periksa.status_bayar'=>'Belum Bayar','periksa_lab.status'=>'Ralan'];

            $output = [
                'respone'=> [
                    'data' => $this->_orderlaballralan($where,$limit,$offset)
                ],
                'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ]; //This is the output token

            $this->response($output, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            //echo 'ralan all';
        }else if($method['total']==='single' && $method['status']==='ralan'){
            $limit = !empty($method['limit']) ? $method['limit'] : '';
            $offset = !empty($method['offset']) ? $method['offset'] : '';
            $norawat = !empty($method['norawat']) ? str_replace('-', '/', $method['norawat']) : '';
            $where = ['reg_periksa.stts'=>'Belum','reg_periksa.status_bayar'=>'Belum Bayar','periksa_lab.status'=>'Ralan','periksa_lab.no_rawat'=>$norawat];
            $output = [
                'respone'=> [
                    'data' => $this->_orderlaballralan($where,$limit,$offset)
                ],
                'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ]; //This is the output token
            $this->response($output, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            //echo 'ralan single';
        }else if($method['total']==='all' && $method['status']==='ranap'){
            $limit = !empty($method['limit']) ? $method['limit'] : '';
            $offset = !empty($method['offset']) ? $method['offset'] : '';
            $where = ['reg_periksa.stts'=>'Belum','reg_periksa.status_bayar'=>'Belum Bayar','periksa_lab.status'=>'Ranap'];

            $output = [
                'respone'=> [
                    'data' => $this->_orderlaballranap($where,$limit,$offset)
                ],
                'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ]; //This is the output token
            $this->response($output, REST_Controller::HTTP_OK);

        }else if($method['total']==='single' && $method['status']==='ranap'){
            $limit = !empty($method['limit']) ? $method['limit'] : '';
            $offset = !empty($method['offset']) ? $method['offset'] : '';
            $norawat = !empty($method['norawat']) ? str_replace('-', '/', $method['norawat']) : '';
            $where = ['reg_periksa.stts'=>'Belum','reg_periksa.status_bayar'=>'Belum Bayar','periksa_lab.status'=>'Ranap','periksa_lab.no_rawat'=>$norawat];

            $output = [
                'respone'=> [
                    'data' => $this->_orderlaballranap($where,$limit,$offset)
                ],
                'metadata'=> [
                    'message' => 'Ok',
                    'code' => 200
                ]
            ]; //This is the output token
            $this->response($output, REST_Controller::HTTP_OK);

        }else{
            $output = [
                'respone'=> [
                    'data' => $this->_orderlaballranap($where,$limit,$offset)
                ],
                'metadata'=> [
                    'message' => 'Failed',
                    'code' => ''
                ]
            ]; //This is the output toke
            $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
        }
    }

    public function pasien_get(){
        $method = $this->get();
        if($method['norm']){
            $no_rkm_medis = (int) $method['norm'];
            
            $where = ['no_rkm_medis'=>$no_rkm_medis];
            $res = $this->_getpasien($where);
            if($res['status']==='Ok'){
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Ok',
                        'code' => 200
                    ]
                ]; //This is the output token
                $this->response($output, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            }else{
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Failed',
                        'code' => ''
                    ]
                ]; //This is the output token
                $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
            } 
        }else{
            $output = [
                    'respone'=> [
                        'data' => 'method not be found'
                    ],
                    'metadata'=> [
                        'message' => 'Failed',
                        'code' => ''
                    ]
                ]; //This is the output token
            $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
        }
    }

    public function masterlab_get(){
        $method = $this->get();
        if($method['kode']){
            $kode = $method['kode'];
            
            $where = ['kd_jenis_prw'=>$kode];
            $res = $this->_getmasterlab($where);
            if($res['status']==='Ok'){
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Ok',
                        'code' => 200
                    ]
                ]; //This is the output token
                $this->response($output, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            }else{
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Failed',
                        'code' => ''
                    ]
                ]; //This is the output token
                $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
            } 
        }else{
            $output = [
                    'respone'=> [
                        'data' => 'method not be found'
                    ],
                    'metadata'=> [
                        'message' => 'Failed',
                        'code' => ''
                    ]
                ]; //This is the output token
            $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
        }
    }

    public function mastertemplate_get(){
       $method = $this->get();
        if($method['kode']){
            $kode = $method['kode'];
            
            $where = ['kd_jenis_prw'=>$kode];
            $res = $this->_getmastertemplate($where);
            if($res['status']==='Ok'){
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Ok',
                        'code' => 200
                    ]
                ]; //This is the output token
                $this->response($output, REST_Controller::HTTP_OK); // OK (200) being the HTTP response code
            }else{
                $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'Failed',
                        'code' => ''
                    ]
                ]; //This is the output token
                $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
            } 
        }else{
            $output = [
                    'respone'=> [
                        'data' => $res['data']
                    ],
                    'metadata'=> [
                        'message' => 'method not be found',
                        'code' => ''
                    ]
                ]; //This is the output token
            $this->set_response($output, REST_Controller::HTTP_NOT_FOUND); // NOT_FOUND (404) being the HTTP response code
        } 
    }
}
