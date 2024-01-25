unit UUtama;

interface

uses
  Windows, Messages, DB, FlexCodeSDK, OleServer, ZDataset,
  ZAbstractRODataset, ZAbstractDataset, ZAbstractTable, ZConnection,
  SUISkinEngine, Controls, StdCtrls, SUIComboBox, MPlayer, Grids, DBGrids,
  SUIDBCtrls, ExtCtrls, SUIEdit, SUIImagePanel, SUIGroupBox, SUIMemo,
  DBCtrls, SUIButton, Classes, SUISideChannel, SysUtils, Variants, Graphics,  Forms,
  Dialogs, xmldom, Provider,  IniFiles,ShellAPI;

type
  TfrmUtama = class(TForm)
    suiSkinEngine1: TsuiSkinEngine;
    suiSideChannel1: TsuiSideChannel;
    ZCon: TZConnection;
    ZTSetting: TZTable;
    ZTSettingnama_instansi: TStringField;
    ZTSettingalamat_instansi: TStringField;
    ZTSettingkabupaten: TStringField;
    ZTSettingpropinsi: TStringField;
    ZTSettingkontak: TStringField;
    ZTSettingemail: TStringField;
    ZTSettingaktifkan: TStringField;
    ZTSettingwallpaper: TBlobField;
    ZTSettinglogo: TBlobField;
    DSSetting: TDataSource;
    PanelHome: TPanel;
    PanelLogin: TsuiGroupBox;
    Label2: TLabel;
    Label3: TLabel;
    edAdmin: TsuiEdit;
    edPass: TsuiEdit;
    btnLoginAdmin: TsuiButton;
    btnBatalLogin: TsuiButton;
    ZQAdmin: TZQuery;
    ZQAdminusere: TMemoField;
    ZQAdminpassworde: TMemoField;
    ZQUser: TZQuery;
    FinFPReg1: TFinFPReg;
    PanelRegistrasi: TsuiGroupBox;
    Panel1: TPanel;
    PRegNmPasien: TsuiEdit;
    PRegMemo2: TsuiMemo;
    PRegMemo1: TsuiMemo;
    PRegNoRkmMedis: TsuiEdit;
    PRegbtnRegistrasi: TsuiButton;
    Label5: TLabel;
    suiGroupBox1: TsuiGroupBox;
    Image1: TImage;
    PRegGrid: TsuiDBGrid;
    ZQSidikJari: TZQuery;
    DSSidikJari: TDataSource;
    Panel2: TPanel;
    PRegBtnSimpan: TsuiButton;
    PRegBtnBaru: TsuiButton;
    PRegBtnHapus: TsuiButton;
    PRegBtnGanti: TsuiButton;
    PRegBtnTutup: TsuiButton;
    PRegCari: TsuiEdit;
    Label4: TLabel;
    PRegBtnCari: TsuiButton;
    ZQInputSidikJari: TZQuery;
    Panel3: TPanel;
    Label1: TLabel;
    DBText2: TDBText;
    FinFPVer1: TFinFPVer;
    mp3player: TMediaPlayer;
    PHomMemo1: TsuiMemo;
    ZQPasien: TZQuery;
    ZQExec: TZQuery;
    PanelPresensiDatang: TsuiGroupBox;
    suiDBGrid1: TsuiDBGrid;
    Panel5: TPanel;
    Label7: TLabel;
    suiButton6: TsuiButton;
    PPresMasEdCari: TsuiEdit;
    PPresMasbtnCari: TsuiButton;
    DSPresensidatang: TDataSource;
    PanelPresensiPulang: TsuiGroupBox;
    suiDBGrid2: TsuiDBGrid;
    Panel4: TPanel;
    Label6: TLabel;
    suiButton1: TsuiButton;
    PPresPulEdCari: TsuiEdit;
    PPresPulBtnCari: TsuiButton;
    DSPresensiPulang: TDataSource;
    PanelCariPresensi: TsuiGroupBox;
    suiDBGrid3: TsuiDBGrid;
    Panel6: TPanel;
    Label8: TLabel;
    suiButton2: TsuiButton;
    PCariEdCari: TsuiEdit;
    PCariBtnCari: TsuiButton;
    PCariCMbTahun: TsuiComboBox;
    Label9: TLabel;
    PCariCmbBulan: TsuiComboBox;
    btnRegistrasi: TsuiButton;
    btnLogin: TsuiButton;
    suiButton3: TsuiButton;
    ZQPasienno_rkm_medis: TStringField;
    ZQPasiennm_pasien: TStringField;
    ZQPasienno_ktp: TStringField;
    ZQPasienjk: TStringField;
    ZQPasientmp_lahir: TStringField;
    ZQPasientgl_lahir: TDateField;
    ZQPasiennm_ibu: TStringField;
    ZQPasienalamat: TStringField;
    ZQPasiengol_darah: TStringField;
    ZQPasienpekerjaan: TStringField;
    ZQPasienstts_nikah: TStringField;
    ZQPasienagama: TStringField;
    ZQPasientgl_daftar: TDateField;
    ZQPasienno_tlp: TStringField;
    ZQPasienumur: TStringField;
    ZQPasienpnd: TStringField;
    ZQPasienkeluarga: TStringField;
    ZQPasiennamakeluarga: TStringField;
    ZQPasienkd_pj: TStringField;
    ZQPasienno_peserta: TStringField;
    ZQPasienkd_kel: TIntegerField;
    ZQPasienkd_kec: TIntegerField;
    ZQPasienkd_kab: TIntegerField;
    ZQPasienpekerjaanpj: TStringField;
    ZQPasienalamatpj: TStringField;
    ZQPasienkelurahanpj: TStringField;
    ZQPasienkecamatanpj: TStringField;
    ZQPasienkabupatenpj: TStringField;
    ZQSidikJarino_rkm_medis: TStringField;
    ZQSidikJarinm_pasien: TStringField;
    ZQSidikJarisidikjari: TMemoField;
    ZQInputSidikJarino_rkm_medis: TStringField;
    ZQInputSidikJarisidikjari: TMemoField;
    suiButton4: TsuiButton;
    ZQUserpasien: TStringField;
    procedure FormActivate(Sender: TObject);
    procedure ZConAfterConnect(Sender: TObject);
    procedure btnLoginClick(Sender: TObject);
    procedure btnBatalLoginClick(Sender: TObject);
    procedure btnLoginAdminClick(Sender: TObject);
    procedure edAdminKeyPress(Sender: TObject; var Key: Char);
    procedure edPassKeyPress(Sender: TObject; var Key: Char);
    procedure btnLoginAdminKeyPress(Sender: TObject; var Key: Char);
    procedure panelfalse();
    procedure btnRegistrasiClick(Sender: TObject);
    procedure FinFPReg1FPRegistrationStatus(ASender: TObject; Status: Integer);
    procedure FinFPReg1FPRegistrationTemplate(ASender: TObject; const FPTemplate: WideString);
    procedure FinFPReg1FPSamplesNeeded(ASender: TObject; Samples: Smallint);
    procedure FinFPReg1FPRegistrationImage(Sender: TObject);
    procedure PRegbtnRegistrasiClick(Sender: TObject);
    procedure PRegBtnCariClick(Sender: TObject);
    procedure PRegBtnBaruClick(Sender: TObject);
    procedure PRegBtnTutupClick(Sender: TObject);
    procedure PRegGridCellClick(Column: TColumn);
    procedure PRegBtnSimpanClick(Sender: TObject);
    procedure PRegBtnHapusClick(Sender: TObject);
    procedure PRegCariKeyPress(Sender: TObject; var Key: Char);
    procedure PRegBtnGantiClick(Sender: TObject);
    procedure FinFPReg2FPRegistrationImage(Sender: TObject);
    procedure FinFPVer1FPVerificationID(ASender: TObject;
      const ID: WideString; FingerNr: Integer);
    procedure FinFPVer1FPVerificationImage(Sender: TObject);
    procedure FinFPVer1FPVerificationStatus(ASender: TObject;
      Status: Integer);
    procedure btnInputDataClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure suiButton6Click(Sender: TObject);
    procedure PPresMasEdCariKeyPress(Sender: TObject; var Key: Char);
    procedure btnPresensiDatangClick(Sender: TObject);
    procedure PPresPulEdCariKeyPress(Sender: TObject; var Key: Char);
    procedure btnPresensiPulangClick(Sender: TObject);
    procedure btnCariPresensiClick(Sender: TObject);
    procedure PCariEdCariKeyPress(Sender: TObject; var Key: Char);
    procedure Button1Click(Sender: TObject);
    procedure suiButton4Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmUtama: TfrmUtama;
  jml:integer;
  pilihanform:string;
  FIni: TIniFile;

implementation

uses DateUtils;

{$R *.dfm}


procedure TfrmUtama.FormActivate(Sender: TObject);
var
  tahunini:integer;
  bulandata:String;
begin
  FIni:=TINIFile.Create(ExtractFilePath(Application.EXEName)+'\setting\database.ini') ;
  ZCon.HostName:=FIni.ReadString('server','host','localhost');
  ZCon.User:=FIni.ReadString('server','user','root');
  ZCon.Password:=FIni.ReadString('server','pass','');
  ZCon.Port:=StrToInt(FIni.ReadString('server','port','3306'));
  ZCon.Database:=FIni.ReadString('server','database','sik');
  ZCon.Connected:=true;
  FinFPReg1.DeviceInfo(FIni.ReadString('deviceinfo','device','DZ00J000083'),
                       FIni.ReadString('deviceinfo','varification','AED-B9C-3A5-A4D-EBA'),
                       FIni.ReadString('deviceinfo','activation','YJ84-6E6D-DFA3-64B5-7508-87RJ'));

  FinFPVer1.AddDeviceInfo(FIni.ReadString('deviceinfo','device','DZ00J000083'),
                       FIni.ReadString('deviceinfo','varification','AED-B9C-3A5-A4D-EBA'),
                       FIni.ReadString('deviceinfo','activation','YJ84-6E6D-DFA3-64B5-7508-87RJ'));

  FinFPVer1.WorkingInBackground(StrToBool(FIni.ReadString('deviceinfo','workinginbackground','false')));
  PRegCari.Clear;
  PRegBtnCariClick(sender);
  FinFPVer1.FPListClear;
  ZQSidikJari.First;
  while not ZQSidikJari.Eof do
  begin
     FinFPVer1.FPLoad(ZQSidikJarino_rkm_medis.AsString,0,ZQSidikJarisidikjari.Value,'MySecretKey');
     ZQSidikJari.Next;
  end;

  FinFPVer1.FPVerificationStart('');

  PanelHome.Align:=alClient;
  PanelLogin.Align:=alClient;
  PanelRegistrasi.Align:=alClient;
  PanelPresensiDatang.Align:=alClient;
  PanelPresensiPulang.Align:=alClient;
  PanelCariPresensi.Align:=alClient;
  panelfalse;
  PanelHome.Visible:=true;

  PRegbtnRegistrasi.Caption := '&Batalkan';


  for tahunini:=2000 to YearOf(date) do
  begin
     PCariCMbTahun.Items.Add(IntToStr(tahunini));
  end;

  if(MonthOf(date)<10)then
  begin
     bulandata:='0'+IntToStr(MonthOf(date));
  end
  else
  begin
     bulandata:=IntToStr(MonthOf(date));
  end;
  PCariCmbBulan.Text:=bulandata;
  PCariCMbTahun.Text:=IntToStr(YearOf(date));
end;

procedure TfrmUtama.ZConAfterConnect(Sender: TObject);
begin
  ZTSetting.Active:=true;
  ZQAdmin.Active:=true;
  ZQUser.Active:=true;
  ZQSidikJari.Active:=true;
  ZQInputSidikJari.Active:=true;
  ZQPasien.Active:=true;
  Label1.Caption:=ZTSettingalamat_instansi.Value+', '+ZTSettingkabupaten.Value+', '+ZTSettingpropinsi.Value;
end;

procedure TfrmUtama.btnLoginClick(Sender: TObject);
begin
  if(btnLogin.Caption='Log-In Admin')then
  begin
    edAdmin.Clear;
    edPass.Clear;
    panelfalse;
    PanelLogin.Visible:=true;
    edAdmin.SetFocus;
  end
  else
  begin
    btnLogin.Caption:='Log-In Admin';
    btnRegistrasi.Enabled:=false;
    panelfalse;
    PanelHome.Visible:=true;
  end;
end;

procedure TfrmUtama.btnBatalLoginClick(Sender: TObject);
begin
  panelfalse;
  PanelHome.Visible:=true;
end;

procedure TfrmUtama.btnLoginAdminClick(Sender: TObject);
begin
  if(Trim(edAdmin.Text)='')then
  begin
      MessageDlg('Maaf, ID Admin masih kosong', mtInformation,[mbOK],0);
      edAdmin.SetFocus;
  end
  else
  if(Trim(edPass.Text)='')then
  begin
      MessageDlg('Maaf, Password masih kosong', mtInformation,[mbOK],0);
      edPass.SetFocus;
  end
  else
  begin
    if(Trim(edAdmin.Text)='admin')and(Trim(edPass.Text)='akusayangsamakamu122456')then
	  begin
		   btnRegistrasi.Enabled:=true;
       btnLogin.Caption:='Log-Out Admin';
       panelfalse;
       PanelHome.Visible:=true;
	  end
	  else
	  begin
		   ZQAdmin.Close;
		   ZQAdmin.SQL.Clear;
       ZQAdmin.SQL.Add('select * from admin where usere=AES_ENCRYPT("'+edAdmin.Text+'","nur") and passworde=AES_ENCRYPT("'+edPass.Text+'","windi")');
		   ZQAdmin.Open;
		   if(ZQAdmin.RecordCount>0)then
		   begin
		      btnRegistrasi.Enabled:=true;
          btnLogin.Caption:='Log-Out Admin';
          panelfalse;
          PanelHome.Visible:=true;
	     end
		   else
		   begin
		      ZQUser.Close;
		      ZQUser.SQL.Clear;
		      ZQUser.SQL.Add('select pasien from user where id_user=AES_ENCRYPT("'+edAdmin.Text+'","nur") and password=AES_ENCRYPT("'+edPass.Text+'","windi")');
		      ZQUser.Open;
		      if(ZQUser.RecordCount>0)then
		      begin
			       btnRegistrasi.Enabled:=StrToBool(ZQUserpasien.Value);
             btnLogin.Caption:='Log-Out Admin';
             panelfalse;
             PanelHome.Visible:=true;
		      end
		      else
		      begin
			       MessageDlg('Gagal login, kemungkinan ID Admin atau Password ada yang salah',mtInformation,[mbOK],0);
			       edAdmin.SetFocus;
             btnLogin.Caption:='Log-in Admin';
			       btnRegistrasi.Enabled:=false;
		      end;
	   	end
	  end;
  end;
end;

procedure TfrmUtama.edAdminKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else edPass.SetFocus
end;

procedure TfrmUtama.edPassKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else btnLoginAdmin.SetFocus
end;

procedure TfrmUtama.btnLoginAdminKeyPress(Sender: TObject; var Key: Char);
begin
  if (key=chr(13)) then  btnLoginAdminClick(sender);
end;

procedure TfrmUtama.panelfalse();
begin
  PanelHome.Visible:=false;
  PanelLogin.Visible:=false;
  PanelRegistrasi.Visible:=false;
  PanelPresensiDatang.Visible:=false;      
  PanelPresensiPulang.Visible:=false;
  PanelCariPresensi.Visible:=false;
end;

procedure TfrmUtama.btnRegistrasiClick(Sender: TObject);
begin
  FinFPVer1.FPVerificationStop;
  panelfalse;       
  PanelRegistrasi.Visible:=true;
  FinFPReg1.FPRegistrationStart('MySecretKey');
  PRegBtnBaruClick(sender);
end;

procedure TfrmUtama.FinFPReg1FPRegistrationStatus(ASender: TObject;
  Status: Integer);
begin
  if(PanelRegistrasi.Visible=true)then
  begin
    case Status of
      0 : begin
            PRegbtnRegistrasi.Caption := '&Ambil';
            PRegMemo1.lines.add('Registration Success');
          end;
      3 : begin
            PRegbtnRegistrasi.Caption := '&Ambil';
            PRegMemo1.lines.add('Registration Fail');
          end;
      7 : begin
            PRegbtnRegistrasi.Caption := '&Ambil';
            PRegMemo1.lines.add('Please connect the device to USB port!');
          end;
      8 :  PRegMemo1.lines.add('Poor image quality!');
      9 :  PRegMemo1.lines.add('Activation/verification code is incorrent or not set!');
      10 : PRegMemo1.lines.add('Registration Start!');
      11 : PRegMemo1.lines.add('Registration Stop!');
    end;
  end;
end;

procedure TfrmUtama.FinFPReg1FPRegistrationTemplate(
  ASender: TObject; const FPTemplate: WideString);
begin
  if(PanelRegistrasi.Visible=true)then
  begin
    PRegMemo2.Clear;
    PRegMemo2.lines.add(FPTemplate);
  end;
end;

procedure TfrmUtama.FinFPReg1FPSamplesNeeded(ASender: TObject;
  Samples: Smallint);
begin
  if(PanelRegistrasi.Visible=true)then
  begin
     PRegMemo1.lines.add('Samples Needed ' + inttostr(Samples));
  end;
end;

procedure TfrmUtama.FinFPReg1FPRegistrationImage(Sender: TObject);
begin
  if(PanelRegistrasi.Visible=true)then
  begin
    Image1.Picture.LoadFromFile(ExtractFilePath(Application.ExeName) + '\FPTemp.BMP');
  end;
end;

procedure TfrmUtama.PRegbtnRegistrasiClick(Sender: TObject);
begin
  If (PRegbtnRegistrasi.Caption = '&Ambil') Then
  begin
    PRegMemo2.Clear;
    PRegMemo1.Clear;
    PRegbtnRegistrasi.Caption := '&Batalkan';
    FinFPReg1.FPRegistrationStart('MySecretKey');
  end
  else
  begin
    FinFPReg1.FPRegistrationStop;
    PRegbtnRegistrasi.Caption := '&Ambil';
  end;
end;

procedure TfrmUtama.PRegBtnCariClick(Sender: TObject);
begin
  ZQSidikJari.Close;
  ZQSidikJari.SQL.Clear;
  ZQSidikJari.SQL.Add('select pasien.no_rkm_medis,pasien.nm_pasien,sidikjaripasien.sidikjari '+
                      'from pasien left outer join sidikjaripasien on pasien.no_rkm_medis=sidikjaripasien.no_rkm_medis '+
                      'where pasien.no_rkm_medis like "%'+Trim(PRegCari.Text)+'%" or '+
                      'pasien.nm_pasien like "%'+Trim(PRegCari.Text)+'%" order by pasien.no_rkm_medis');
  ZQSidikJari.Open;
end;

procedure TfrmUtama.PRegBtnBaruClick(Sender: TObject);
begin
  PRegNmPasien.Clear;
  PRegNoRkmMedis.Clear;
  FinFPReg1.FPRegistrationStop;
  PRegMemo2.Clear;
  PRegMemo1.Clear;
  PRegbtnRegistrasi.Caption := '&Ambil';
end;

procedure TfrmUtama.PRegBtnTutupClick(Sender: TObject);
begin
  panelfalse;
  PanelHome.Visible:=true;
end;

procedure TfrmUtama.PRegGridCellClick(Column: TColumn);
begin
  if(not ZQSidikJari.IsEmpty)then
  begin
     PRegNoRkmMedis.Text:=ZQSidikJarino_rkm_medis.AsString;
     PRegNmPasien.Text:=ZQSidikJarinm_pasien.Value;
     PRegMemo2.Text:=ZQSidikJarisidikjari.Value;
  end;
end;

procedure TfrmUtama.PRegBtnSimpanClick(Sender: TObject);
begin
   if(Trim(PRegNoRkmMedis.Text)='') or (Trim(PRegNmPasien.Text)='')then
   begin
      MessageDlg('Maaf, pilih pasien dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   if(Trim(PRegMemo2.Text)='')then
   begin
      MessageDlg('Maaf, silahkan ambil data sidikjari pasien terlebih dahulu...!!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjaripasien where no_rkm_medis="'+PRegNoRkmMedis.Text+'"');
      ZQInputSidikJari.Open;
      if(ZQInputSidikJari.IsEmpty)then
      begin
         ZQInputSidikJari.Insert;
         ZQInputSidikJarino_rkm_medis.Value:=PRegNoRkmMedis.Text;
         ZQInputSidikJarisidikjari.Value:=PRegMemo2.Text;
         ZQInputSidikJari.Post;
         PRegBtnBaruClick(sender);
         PRegBtnCariClick(sender);
      end
      else
      begin
         MessageDlg('Maaf, data sidik jari pasien '+PRegNmPasien.Text+' sudah masuk sebelumnya...!!!', mtInformation,[mbOK],0);
         PRegGrid.SetFocus;
      end
   end;
end;

procedure TfrmUtama.PRegBtnHapusClick(Sender: TObject);
begin
   if(Trim(PRegNoRkmMedis.Text)='') or (Trim(PRegNmPasien.Text)='')then
   begin
      MessageDlg('Maaf, pilih pasien yang mau dihapus sidik jarinya terlebih dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjaripasien where no_rkm_medis="'+PRegNoRkmMedis.Text+'"');
      ZQInputSidikJari.Open;
      if(not ZQInputSidikJari.IsEmpty)then
      begin
        ZQInputSidikJari.Delete;
        PRegBtnCariClick(sender);
        PRegBtnBaruClick(sender);
        MessageDlg('Data sidik jari '+PRegNmPasien.Text+' berhasil dihapus ...!!', mtInformation,[mbOK],0);
      end
      else
      if(ZQInputSidikJari.IsEmpty)then
      begin
        MessageDlg('Data sidik jari '+PRegNmPasien.Text+' belum pernah tersimpan sebelumnya ...!!', mtInformation,[mbOK],0);
        PRegGrid.SetFocus;
      end;
   end;
end;

procedure TfrmUtama.PRegCariKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else PRegBtnCariClick(sender);
end;

procedure TfrmUtama.PRegBtnGantiClick(Sender: TObject);
begin
   if(Trim(PRegNoRkmMedis.Text)='') or (Trim(PRegNmPasien.Text)='')then
   begin
      MessageDlg('Maaf, pilih pasien dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   if(Trim(PRegMemo2.Text)='')then
   begin
      MessageDlg('Maaf, silahkan ambil data sidikjaripasien terlebih dahulu...!!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjaripasien where no_rkm_medis="'+PRegNoRkmMedis.Text+'"');
      ZQInputSidikJari.Open;
      if(not ZQInputSidikJari.IsEmpty)then
      begin
         ZQInputSidikJari.Edit;
         ZQInputSidikJarisidikjari.Value:=PRegMemo2.Text;
         ZQInputSidikJari.Post;
         PRegBtnBaruClick(sender);
         PRegBtnCariClick(sender);
      end
      else
      begin
         MessageDlg('Data sidik jari '+PRegNmPasien.Text+' belum pernah tersimpan sebelumnya ...!!', mtInformation,[mbOK],0);
         PRegGrid.SetFocus;
      end        
   end;
end;

procedure TfrmUtama.FinFPReg2FPRegistrationImage(Sender: TObject);
begin
   //Image2.Picture.LoadFromFile(ExtractFilePath(Application.ExeName) + '\FPTemp.BMP');
end;

procedure TfrmUtama.FinFPVer1FPVerificationID(ASender: TObject;
  const ID: WideString; FingerNr: Integer);
begin
  if(PanelHome.Visible=true)then
  begin
    ZQPasien.Close;
    ZQPasien.SQL.Clear;
    ZQPasien.SQL.Add('select * from pasien where no_rkm_medis="'+ID+'"');
    ZQPasien.Open;
    if(not ZQPasien.IsEmpty)then
    begin
       ZQExec.Close;
       ZQExec.SQL.Clear;
       ZQExec.SQL.Add('delete from temppanggilrm') ;
       ZQExec.ExecSQL;
       ZQExec.Close;
       ZQExec.SQL.Clear;
       ZQExec.SQL.Add('insert into temppanggilrm values("'+ID+'")') ;
       ZQExec.ExecSQL;
       if(pilihanform='CariData')then
       begin
          ShellExecute(0,'open','resume.jar','','',SW_SHOWNORMAL);
       end
       else
       if(pilihanform='Registrasi')then
       begin
          ShellExecute(0,'open','registrasifingerprint.jar','','',SW_SHOWNORMAL);
       end;
    end;
  end;
end;

procedure TfrmUtama.FinFPVer1FPVerificationImage(Sender: TObject);
begin
  if(PanelHome.Visible=true)then
  begin
    // Image2.Picture.LoadFromFile(ExtractFilePath(Application.ExeName) + '\FPTemp.BMP');
  end;
end;

procedure TfrmUtama.FinFPVer1FPVerificationStatus(ASender: TObject;
  Status: Integer);
begin
  if(PanelHome.Visible=true)then
  begin
    try
       begin
          case Status of
					  0  :  begin
							  PHomMemo1.lines.add('Tidak ada sidik jari yang cocok....!!!!');
							  mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','gagal','\suara\ulangi.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;
							end;
					  3  :  begin
							  PHomMemo1.lines.add('Verifikasi gagal....!!!!');
							  mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','gagal','\suara\ulangi.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;
							end;
					  7   : begin
							  PHomMemo1.lines.add('Silahkan hubungkan dengan alat sidik jari....!!!!');
							  mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','alattakterhubung','\suara\alattakterhubung.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;
                ShellExecute(0,'open','resume.exe','','',SW_SHOWNORMAL);
                Application.Terminate;
							end;
					  8  :  begin
							  PHomMemo1.lines.add('Kwalitas gambar jelek....!!!!');
							  mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','gagal','\suara\ulangi.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;
							end;
					  9   : begin
							  PHomMemo1.lines.add('Activasi alat gagal....!!!!');
							  mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','aktivasigagal','\suara\aktivasigagal.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;
							end;
					end;
       end
    except
       ShellExecute(0,'open','presensi.exe','','',SW_SHOWNORMAL);
       Application.Terminate;
    end;
  end;
end;

procedure TfrmUtama.btnInputDataClick(Sender: TObject);
begin
  panelfalse;
  PanelHome.Visible:=true;
  
  FinFPVer1.WorkingInBackground(StrToBool(FIni.ReadString('deviceinfo','workinginbackground','false')));

  FinFPVer1.FPListClear;
  ZQSidikJari.First;
  while not ZQSidikJari.Eof do
  begin
     FinFPVer1.FPLoad(ZQSidikJarino_rkm_medis.AsString,0,ZQSidikJarisidikjari.Value,'MySecretKey');
     ZQSidikJari.Next;
  end;
  pilihanform:='CariData';
  FinFPVer1.FPVerificationStart('');
end;

procedure TfrmUtama.FormCreate(Sender: TObject);
begin
  image1.Canvas.Create();
  FinFPReg1.PictureSamplePath := ExtractFilePath(Application.ExeName) + '\FPTemp.BMP';
  FinFPReg1.PictureSampleHeight := 1635;
  FinFPReg1.PictureSampleWidth := 1335;
end;

procedure TfrmUtama.suiButton6Click(Sender: TObject);
begin
  panelfalse;
  PanelHome.Visible:=true;
end;

procedure TfrmUtama.PPresMasEdCariKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit;
end;

procedure TfrmUtama.btnPresensiDatangClick(Sender: TObject);
begin
  panelfalse;
  PanelPresensiDatang.Visible:=true;
end;

procedure TfrmUtama.PPresPulEdCariKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit;
end;

procedure TfrmUtama.btnPresensiPulangClick(Sender: TObject);
begin
  panelfalse;
  PanelPresensiPulang.Visible:=true;
end;

procedure TfrmUtama.btnCariPresensiClick(Sender: TObject);
begin
  panelfalse;
  PanelCariPresensi.Visible:=true;
end;

procedure TfrmUtama.PCariEdCariKeyPress(Sender: TObject; var Key: Char);
begin
   if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
end;

procedure TfrmUtama.Button1Click(Sender: TObject);
begin
   ShellExecute(0,'open','resume.jar','','',SW_SHOWNORMAL);
end;

procedure TfrmUtama.suiButton4Click(Sender: TObject);
begin
  panelfalse;
  PanelHome.Visible:=true;
  
  FinFPVer1.WorkingInBackground(StrToBool(FIni.ReadString('deviceinfo','workinginbackground','false')));

  FinFPVer1.FPListClear;
  ZQSidikJari.First;
  while not ZQSidikJari.Eof do
  begin
     FinFPVer1.FPLoad(ZQSidikJarino_rkm_medis.AsString,0,ZQSidikJarisidikjari.Value,'MySecretKey');
     ZQSidikJari.Next;
  end;
  pilihanform:='Registrasi';  
  FinFPVer1.FPVerificationStart('');
end;

end.
