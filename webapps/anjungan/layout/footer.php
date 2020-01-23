
    <!-- Jquery Core Js -->
    <script src="<?php echo URL; ?>/plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="<?php echo URL; ?>/plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Select Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- Slimscroll Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/node-waves/waves.js"></script>

    <!-- Jquery CountTo Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-countto/jquery.countTo.js"></script>

    <!-- Jquery DataTable Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/responsive/js/dataTables.responsive.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
    <script src="<?php echo URL; ?>/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

    <!-- Chart Plugins Js -->
    <script src="<?php echo URL; ?>/plugins/chartjs/Chart.bundle.js"></script>

    <!-- Sparkline Chart Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-sparkline/jquery.sparkline.js"></script>

    <!-- Jquery CountTo Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-countto/jquery.countTo.js"></script>

    <!-- Autosize Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/autosize/autosize.js"></script>

    <!-- Moment Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/momentjs/moment.js"></script>

    <!-- Bootstrap Material Datetime Picker Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

    <!-- Jquery Validation Plugin Css -->
    <script src="<?php echo URL; ?>/plugins/jquery-validation/jquery.validate.js"></script>

    <!-- JQuery Steps Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/jquery-steps/jquery.steps.js"></script>

    <!-- Sweet Alert Plugin Js -->
    <script src="<?php echo URL; ?>/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Jquery-UI Js -->
    <script src="<?php echo URL; ?>/js/jquery-ui.min.js"></script>

    <!-- Select2 Js -->
    <script src="<?php echo URL; ?>/js/select2.min.js"></script>

    <!-- Custom Js -->
    <script src="<?php echo URL; ?>/js/admin.js"></script>
	<script language="javascript" type="text/javascript">
							function PrintMeSubmitMe() {
								window.print();
								SubmitMe();
							}

							function SubmitMe() {
								document.MyForm.submit();
							}
	</script>

    <script type="text/javascript">
function admSelectCheck(nameSelect)
{
    if(nameSelect){
        admOptionValue = document.getElementById("BPJS").value;
        if(admOptionValue == nameSelect.value){
            document.getElementById("admDivCheck").style.display = "block";
        }
        else{
            document.getElementById("admDivCheck").style.display = "none";
        }
    }
    else{
        document.getElementById("admDivCheck").style.display = "none";
    }
}

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#image_upload_preview').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#inputFile").change(function () {
        readURL(this);
    });

    function upload_rujukan(){
    document.getElementById("inputFile").click();
    }

    </script>
 	  <script>
        $(document).ready(function() {
            $('.datepicker').bootstrapMaterialDatePicker({
                format: 'YYYY-MM-DD',
                clearButton: true,
                weekStart: 1,
                minDate : new Date(),
                time: false
            });
        } );

	$(function () {

    //Advanced form with validation
    var form = $('#wizard_with_validation').show();
    form.steps({
        headerTag: 'h3',
        bodyTag: 'fieldset',
        transitionEffect: 'slideLeft',
        onInit: function (event, currentIndex) {
            $.AdminBSB.input.activate();

            //Set tab width
            var $tab = $(event.currentTarget).find('ul[role="tablist"] li');
            var tabCount = $tab.length;
            $tab.css('width', (100 / tabCount) + '%');

            //set button waves effect
            setButtonWavesEffect(event);
        },
        onStepChanging: function (event, currentIndex, newIndex) {
            if (currentIndex > newIndex) { return true; }

            if (currentIndex < newIndex) {
                form.find('.body:eq(' + newIndex + ') label.error').remove();
                form.find('.body:eq(' + newIndex + ') .error').removeClass('error');
            }

            form.validate().settings.ignore = ':disabled,:hidden';
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex) {
            setButtonWavesEffect(event);
        },
        onFinishing: function (event, currentIndex) {
            form.validate().settings.ignore = ':disabled';
            return form.valid();
        },
        onFinished: function (event, currentIndex) {
            //swal("Good job!", "Submitted!", "success");
          	//swal({ 
  			//	title: "Selesai",
   			//	text: "Pendaftaran pasien sukses. Silakan cetak antrian anda!!",
    		//	type: "success" 
  			//},
  			//function(){
    		//	//window.location.href = 'pengaduan.php';
			//});
          	if (currentIndex === 2) { //if last step
   				//remove default #finish button
		    	$('#wizard_with_validation').find('a[href="#finish"]').remove(); 
		    	$('#wizard_with_validation').find('a[href="#previous"]').remove(); 
   		    	//append a submit type button
   		    	$('#wizard_with_validation .actions li:last-child').append('<button type="submit" id="submit" class="btn-large">SELESAI</button>');
            }
        }
    });

    form.validate({
        highlight: function (input) {
            $(input).parents('.form-line').addClass('error');
        },
        unhighlight: function (input) {
            $(input).parents('.form-line').removeClass('error');
        },
        errorPlacement: function (error, element) {
            $(element).parents('.form-group').append(error);
        }
    });
	});

	function setButtonWavesEffect(event) {
    	$(event.currentTarget).find('[role="menu"] li a').removeClass('waves-effect');
    	$(event.currentTarget).find('[role="menu"] li:not(.disabled) a').addClass('waves-effect');
	}
        
	  </script>



</body>

</html>

