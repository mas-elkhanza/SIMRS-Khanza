<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ad_post_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Post_model'));
		$this->site->is_logged_in();
		$host = base_url();
	}

	public function index()
	{
		$data = array(
			'heading' => 'Akses salah...',
			'message' => 'Maaf kami tidak bisa memunculkan halaman yang anda cari..'
		);
		$this->site->view_error('error_404',$data);
	}

	public function action($action='')
	{
		if($action == 'view')
		{
			global $SConfig;
			$f 		= ($this->uri->segment(4))	? $this->uri->segment(4) : 'f';
			$l 		= ($this->uri->segment(5))	? $this->uri->segment(5) : 10;
			$p 		= ($this->uri->segment(6))	? $this->uri->segment(6) - 1 : 0;

			$pq 	= $p * $l;
			$no 	= ($p * $l) + 1;

			$where 	= '';
			if($f!='f')
			{
				$where = 'judul like \'%'.$f.'%\' OR id_kat_post like \'%'.$f.'%\' OR tag like \'%'.$f.'%\'';
			}

			$jum 	= $this->Post_model->count($where);
			$r 		= $this->Post_model->getRecordList('post','*',$where,'','id ASC',$l,$pq);

			// Kategori Post
			//$whkr	= array('parent_id !=' => 0); 
			$kr 	= $this->Post_model->getRecordList('kategori','id,parent_id,nm_kategori','','','id ASC');
			foreach ($kr as $key => $value) {
				$lskr[$value->parent_id][] = $value;
			}

			// User untuk pemilihan penulis
			if($this->session->userdata('level') == 1)
			{
				$whrp = '';
			}
			else
			{
				$whrp = array('username' => $this->session->userdata('username'));
			}

			$rp 	= $this->Post_model->getRecordList('user','id,nm_lengkap',$whrp,'','id ASC');
			$lsrp	= ConDataToArray($rp,'id','nm_lengkap');

			// Tag
			$rt 	= $this->Post_model->getRecordList('tag','tag_seo,nm_tag','','','id ASC');
			$lsrt	= ConDataToArray($rt,'tag_seo','nm_tag');

			$data = array(
			'title'		=> 'Artikel Post',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'status'	=> $SConfig->_st_post,
			'kat_post'	=> kategori_post(),
			'id_kat_post'=> get_parent($lskr,'',''),
			'penulis'	=> $lsrp,
			'tag'		=> $lsrt,
			'v'			=> 'ad_post'
			);

			//print_r($r);
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$p = $this->input->post();

			if (isset($_FILES['gambar']['tmp_name']) && $_FILES['gambar']['name']) {
            $foto = 'uploads/post/' . date('YmdHis') . '/';
	            if (!is_dir($foto)) {
	                mkdir($foto, 0777, true);
	            }
	            $foto .= $_FILES['gambar']['name'];
	        } else {
	            $foto = '';
	        }

			foreach ($p['id_kat_post'] as $key => $value) {
				foreach ($value as $k => $v) {
					$t[$key] = $k;
				}
			}
			$e = implode(',', $t);

			$post_atribut = array(
				'meta_title' => $p['meta_title'],
				'meta_keyword' => $p['meta_keyword'],
				'meta_description' => $p['meta_description']
			);

			$d['konten']		= isset($p['konten']) ? $p['konten'] : '';
			$d['judul']			= isset($p['judul']) ? $p['judul'] : '';
			$d['judul_seo']		= isset($p['judul']) ? seo_title($p['judul']) : '';
			$d['headline'] 		= isset($p['H']) ? 'Y' : 'N';	
			$d['slideshow'] 	= isset($p['S']) ? 'Y' : 'N';	
			$d['utama'] 		= isset($p['U']) ? 'Y' : 'N';	
			$d['id_kat_post']	= $e;
			$d['masuk_tgl']		= isset($p['masuk_tgl']) ? date('Y-m-d H:i:s',strtotime($p['masuk_tgl'])) : '';
			$d['masuk_oleh']	= isset($p['masuk_oleh']) ? $p['masuk_oleh'] : '';
			$d['status']		= isset($p['status']) ? $p['status'] : '';
			$d['tag']			= isset($p['tag']) ? $p['tag'] : '';
			$d['file_vidio']	= isset($p['file_vidio']) ? $p['file_vidio'] : '';
			$d['post_atribut']	= json_encode($post_atribut);
			
			/*print_r($d);
				exit();*/
			$rules = $this->Post_model->rules;
			$this->form_validation->set_rules($rules);

			if($p['id'])
			{
				if ($foto) {
		            $d['gambar'] = base_url() . $foto;
		            $d['ket_gambar'] = $p['judul'];
	        	}
	        	else 
	        	{
	        		$d['gambar'] = isset($p['url_gambar']) ? $p['url_gambar'] : '';
	        	}

	        	//echo $this->form_validation->run();

				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Post_model->createHistory('Gagal update post dengan nama : '.$p['judul'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Post_model->createHistory('Update post dengan nama : '.$p['judul'].'',$this->session->userdata('ID'));
					$this->Post_model->update($d,array('id'=>$p['id']));

					if ($foto) {
		                move_uploaded_file($_FILES['gambar']['tmp_name'], $foto);
		            }

					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if ($foto) {
	            	$d['gambar'] = base_url() . $foto;
	            	$d['ket_gambar'] = $p['judul'];
        		}

				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Post_model->createHistory('Gagal tambah post dengan nama : '.$p['judul'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					if ($foto) {
		                move_uploaded_file($_FILES['gambar']['tmp_name'], $foto);
		            }

					$this->Post_model->createHistory('Tambah post dengan nama : '.$p['judul'].'',$this->session->userdata('ID'));
					$this->Post_model->insert($d,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
		}

		else if($action == 'delete')
		{
			$uri = $this->uri->segment(4);
			$idl = explode('k', $uri);
			/*print_r($idl);
			exit();*/

			$_SESSION['error'] = '';
        	$_SESSION['msg']   = 'Data has been succesfully deleted';

			foreach ($idl as $idv)
			{
				$det = $this->Post_model->getRecordList('post','judul','id=\''.$idv.'\'');
				if(!$this->Post_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Post_model->createHistory('Delete judul dengan nama : '.$det[0]->nm_kategori.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}

		else if($action == 'edit')
		{
			global $SConfig;

			$id = $this->input->post('id');
			$r =  $this->Post_model->getRecordList('post','*',array('id'=>$id));


			if($this->session->userdata('level') == 1)
			{
				$whrpe = '';
			}
			else
			{
				$whrpe = array('username' => $this->session->userdata('username'));
			}
			// Penulis
			$rpe 	= $this->Post_model->getRecordList('user','id,nm_lengkap',$whrpe,'','id ASC');
			$lsrpe	= ConDataToArray($rpe,'id','nm_lengkap');

			// Status
			$st_post = $SConfig->_st_post;

			// Tag
			$rte 	= $this->Post_model->getRecordList('tag','tag_seo,nm_tag','','','id ASC');
			$tage	= ConDataToArray($rte,'tag_seo','nm_tag');

			// Kategori Post
			//$whkr	= array('parent_id !=' => 0); 
			$kr 	= $this->Post_model->getRecordList('kategori','id,parent_id,nm_kategori','','','id ASC');
			foreach ($kr as $key => $value) {
				$lskr[$value->parent_id][] = $value;
			}

			$id_kat_post = get_parent($lskr,'',$r[0]->id_kat_post);
			
			/*print_r($r);
			exit();*/
			echo '<form action="'.base_url('ad_post_view/action/save').'" method="post" id="modFormEdit" class="form-horizontal" enctype="multipart/form-data">';
				echo '<div class="col-md-12 col-xs-12">';
					echo '<div class="row">';

						echo '<div class="col-md-12 col-xs-12">';
							echo '<div class="form-group">';
								echo '<label for="tun" class="col-sm-12 control-label">Judul</label>';
								echo '<div class="col-md-12 col-xs-12">';
									echo '<input type="text" class="form-control input-sm" name="judul" placeholder="judul" required="true" value="'.$r[0]->judul.'">';
									echo '<input type="hidden" name="id" value='.$r[0]->id.'>';
								echo '</div>';	
							echo '</div>';
						echo '</div>';

						echo '<div class="col-md-12 col-xs-12">';
							echo '<div class="row">';
								echo '<div class="col-md-12 col-xs-12">';
									echo '<div class="form-group">';
										echo '<div class="col-md-12 col-xs-12">';
											echo '<div class="b-b b-primary nav-active-primary col-xs-12">';

												echo '<ul class="nav nav-tabs">';
													echo '<li class="nav-item"><a class="nav-link active" href data-toggle="tab" data-target="#tab1"><i class="fa fa-th"></i> Posisi</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab2"><i class="fa fa-check"></i> Kategori Post</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab3"><i class="fa fa-clock-o"></i> Waktu</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab4"><i class="fa fa-user"></i> Penulis</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab5"><i class="fa fa-search"></i> Seo</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab6"><i class="fa fa-file-image-o"></i> Upload</a></li>';

													echo '<li class="nav-item"><a class="nav-link" href data-toggle="tab" data-target="#tab7"><i class="fa  fa-power-off"></i> Status</a></li>';
												echo '</ul>';

											echo '</div>';

											echo '<div class="tab-content p-a m-b-md" style="border-bottom: 1px solid #0cc2aa; border-left: 1px solid #0cc2aa;border-right: 1px solid #0cc2aa;">';
												
												// Tab 1
												echo '<div class="tab-pane animated fadeIn text-muted active" id="tab1">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<div class="col-md-12 col-xs-12">';
																
																echo '<label class="md-check">';
						                                        echo '<input type="checkbox" name="H"  data-val="Y" value="Y"';
						                                        if($r[0]->headline == 'Y')
						                                        {
						                                        	echo ' checked';
						                                        }
						                                        else
						                                        {
						                                        	echo ' ';
						                                        }
						                                        echo '> <i class="indigo"></i> Headline';
						                                        echo '</label><br>';

						                                        echo '<label class="md-check">';
						                                        echo '<input type="checkbox" name="S"  data-val="Y" value="Y"';
						                                        if($r[0]->slideshow == 'Y')
						                                        {
						                                        	echo ' checked';
						                                        }
						                                        else
						                                        {
						                                        	echo ' ';
						                                        }
						                                        echo '> <i class="indigo"></i> Slideshow';
						                                        echo '</label><br>';

						                                        echo '<label class="md-check">';
						                                        echo '<input type="checkbox" name="U"  data-val="Y" value="Y"';
						                                        if($r[0]->utama == 'Y')
						                                        {
						                                        	echo ' checked';
						                                        }
						                                        else
						                                        {
						                                        	echo ' ';
						                                        }
						                                        echo '> <i class="indigo"></i> Utama';
						                                        echo '</label>';
																
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

												// Tab 2
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab2">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<div class="col-md-12 col-xs-12">';
																echo $id_kat_post;														
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

												// Tab 3
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab3">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<input type="datetime-local" class="form-control input-sm" name="masuk_tgl" value="'.date('Y-m-d',strtotime($r[0]->masuk_tgl)).'T'.date('H:i:s',strtotime($r[0]->masuk_tgl)).'">';
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

												// Tab 4
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab4">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<div class="col-md-12 col-xs-12">';
																echo htmlSelectFromArray($lsrpe, 'name="masuk_oleh" class="form-control input-sm select2" style="width:100%;"', true);
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

												$ft = json_decode($r[0]->post_atribut);
												//print_r($ft);
												// Tab 5
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab5">';

													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 control-label"><b>Judul</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<input type="text" name="meta_title" class="form-control" value="'.$ft->meta_title.'">';
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	

													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Meta Kata Kunci</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<textarea name="meta_keyword" class="form-control">'.$ft->meta_keyword.'</textarea>';
															echo '</div>';	
														echo '</div>';	
													echo '</div>';

													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Meta Kata Kunci</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<textarea name="meta_keyword" class="form-control">'.$ft->meta_keyword.'</textarea>';
															echo '</div>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<p class="table-bordered">Keyword (kata kunci) apa yang akan Anda bidik, agar website Anda muncul di search engine setiap kali orang mencari kata <br>tersebut di Search Engine</p>';
															echo '</div>';	
														echo '</div>';	
													echo '</div>';

													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Meta Deskripsi</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<textarea name="meta_description" class="form-control">'.$ft->meta_description.'</textarea>';
															echo '</div>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<p class="table-bordered">Silahkan isi, Meta Description agar artikel ini memiliki SEO Score yang bagus dalam Search Engine. Dan website Anda memiliki peringkat yang bagus dalam search Engine, seperti Google maupun Bing.</p>';
															echo '</div>';	
														echo '</div>';	
													echo '</div>';

												echo '</div>';

												// Tab 6
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab6">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Upload gambar</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<input type="file" name="gambar" id="gambar"/>';
															echo '</div>';	
															echo '<br>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<div class="row">';
																	echo '<div class="col-xs-6 col-sm-4 col-md-3">';
																		echo '<div class="box p-a-xs">';
																			echo '<img src="'.$r[0]->gambar.'" alt="" class="img-responsive">';
																			echo '<input type="hidden" name="url_gambar" value="'.$r[0]->gambar.'">';
																		echo '</div>';
																	echo '</div>';
																echo '</div>';	
															echo '</div>';	
														echo '</div>';	
													echo '</div>';

													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Link Url Vidieo</b></label>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<input type="text" name="file_vidio" class="form-control" value="'.@$r[0]->file_vidio.'">';
															echo '</div>';
															echo '<br>';
															echo '<div class="col-md-12 col-xs-12">';
																echo '<video controls class="col-xs-12 col-md-12">';
 	 															echo '<source src="'.base_url() .'/'. $r[0]->file_vidio.'" type="video/mp4">';

  																echo 'Your browser does not support the video tag.';
																echo '</video>';
															echo '</div>';
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

												// Tab 7
												echo '<div class="tab-pane animated fadeIn text-muted" id="tab7">';
													echo '<div class="col-md-12 col-xs-12">';
														echo '<div class="form-group">';
															echo '<div class="col-md-12 col-xs-12">';
																echo htmlSelectFromArray($st_post, 'name="status" class="form-control input-sm select2" style="width:100%;"', true);
															echo '</div>';	
														echo '</div>';	
													echo '</div>';	
												echo '</div>';

											echo '</div>';	

										echo '</div>';	
									echo '</div>';	
								echo '</div>';	
							echo '</div>';
						echo '</div>';

						echo '<div class="col-md-12 col-xs-12">';
							echo '<div class="form-group">';
								echo '<div class="col-md-12 col-xs-12">';
									echo '<textarea class="form-control" rows="5" cols="9" id="editor2" name="konten">'.$r[0]->konten.'</textarea>';
								echo '</div>';	
							echo '</div>';
						echo '</div>';

						echo '<div class="col-md-12 col-xs-12">';
							echo '<div class="form-group">';
								echo '<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Tag</b></label>';
								echo '<div class="col-md-12 col-xs-12">';
									echo htmlSelectFromArray($tage, 'name="tag" class="form-control input-sm select2" style="width:100%;"', true,$r[0]->tag);
								echo '</div>';	
							echo '</div>';
						echo '</div>';

					echo '</div>';
				echo '</div>';
			echo '</form>';
		}
	}
}