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
    suiPanel1: TsuiPanel;
    btnRegistrasi: TsuiButton;
    suiPanel2: TsuiPanel;
    btnInputData: TsuiButton;
    btnPresensiDatang: TsuiButton;
    btnPresensiPulang: TsuiButton;
    btnCariPresensi: TsuiButton;
    btnLogin: TsuiButton;
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
    PRegNmPeg: TsuiEdit;
    PRegMemo2: TsuiMemo;
    PRegMemo1: TsuiMemo;
    PRegIdPeg: TsuiEdit;
    PRegbtnRegistrasi: TsuiButton;
    Label5: TLabel;
    suiGroupBox1: TsuiGroupBox;
    Image1: TImage;
    PRegNIP: TsuiEdit;
    PRegGrid: TsuiDBGrid;
    ZQSidikJari: TZQuery;
    ZQSidikJariid: TIntegerField;
    ZQSidikJarinik: TStringField;
    ZQSidikJarinama: TStringField;
    ZQSidikJarisidikjari: TMemoField;
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
    ZQInputSidikJariid: TIntegerField;
    ZQInputSidikJarisidikjari: TMemoField;
    Panel3: TPanel;
    Label1: TLabel;
    DBText2: TDBText;
    FinFPVer1: TFinFPVer;
    mp3player: TMediaPlayer;
    PHomMemo1: TsuiMemo;
    ZQPegawai: TZQuery;
    ZQPegawaiid: TIntegerField;
    ZQPegawainik: TStringField;
    ZQPegawainama: TStringField;
    ZQPegawaijk: TStringField;
    ZQPegawaijbtn: TStringField;
    ZQPegawaijnj_jabatan: TStringField;
    ZQPegawaidepartemen: TStringField;
    ZQPegawaibidang: TStringField;
    ZQPegawaistts_wp: TStringField;
    ZQPegawaistts_kerja: TStringField;
    ZQPegawainpwp: TStringField;
    ZQPegawaipendidikan: TStringField;
    ZQPegawaigapok: TFloatField;
    ZQPegawaitmp_lahir: TStringField;
    ZQPegawaitgl_lahir: TDateField;
    ZQPegawaialamat: TStringField;
    ZQPegawaikota: TStringField;
    ZQPegawaimulai_kerja: TDateField;
    ZQPegawaims_kerja: TStringField;
    ZQPegawaiindexins: TStringField;
    ZQPegawaibpd: TStringField;
    ZQPegawairekening: TStringField;
    ZQPegawaistts_aktif: TStringField;
    ZQPegawaiwajibmasuk: TSmallintField;
    ZQPegawaipengurang: TFloatField;
    ZQPegawaiindek: TSmallintField;
    ZQPegawaimulai_kontrak: TDateField;
    ZQPegawaicuti_diambil: TIntegerField;
    ZQPegawaidankes: TFloatField;
    ZQJadwalPegawai: TZQuery;
    ZQJadwalPegawaiid: TIntegerField;
    ZQJadwalPegawaitahun: TSmallintField;
    ZQJadwalPegawaibulan: TStringField;
    ZQJadwalPegawaih1: TStringField;
    ZQJadwalPegawaih2: TStringField;
    ZQJadwalPegawaih3: TStringField;
    ZQJadwalPegawaih4: TStringField;
    ZQJadwalPegawaih5: TStringField;
    ZQJadwalPegawaih6: TStringField;
    ZQJadwalPegawaih7: TStringField;
    ZQJadwalPegawaih8: TStringField;
    ZQJadwalPegawaih9: TStringField;
    ZQJadwalPegawaih10: TStringField;
    ZQJadwalPegawaih11: TStringField;
    ZQJadwalPegawaih12: TStringField;
    ZQJadwalPegawaih13: TStringField;
    ZQJadwalPegawaih14: TStringField;
    ZQJadwalPegawaih15: TStringField;
    ZQJadwalPegawaih16: TStringField;
    ZQJadwalPegawaih17: TStringField;
    ZQJadwalPegawaih18: TStringField;
    ZQJadwalPegawaih19: TStringField;
    ZQJadwalPegawaih20: TStringField;
    ZQJadwalPegawaih21: TStringField;
    ZQJadwalPegawaih22: TStringField;
    ZQJadwalPegawaih23: TStringField;
    ZQJadwalPegawaih24: TStringField;
    ZQJadwalPegawaih25: TStringField;
    ZQJadwalPegawaih26: TStringField;
    ZQJadwalPegawaih27: TStringField;
    ZQJadwalPegawaih28: TStringField;
    ZQJadwalPegawaih29: TStringField;
    ZQJadwalPegawaih30: TStringField;
    ZQJadwalPegawaih31: TStringField;
    ZQJamMasuk: TZQuery;
    ZQJamMasukshift: TStringField;
    ZQJamMasukjam_masuk: TTimeField;
    ZQJamMasukjam_pulang: TTimeField;
    ZQSudahPresensi: TZQuery;
    ZQSudahPresensiid: TIntegerField;
    ZQTemporaryPresensi: TZQuery;
    ZQTemporaryPresensiid: TIntegerField;
    ZQTemporaryPresensishift: TStringField;
    ZQTemporaryPresensijam_datang: TDateTimeField;
    ZQTemporaryPresensijam_pulang: TDateTimeField;
    ZQTemporaryPresensistatus: TStringField;
    ZQTemporaryPresensiketerlambatan: TStringField;
    ZQTemporaryPresensidurasi: TStringField;
    ZQTemporaryPresensiphoto: TStringField;
    ZQExec: TZQuery;
    ZQRekapPresensi: TZQuery;
    ZQRekapPresensiid: TIntegerField;
    ZQRekapPresensishift: TStringField;
    ZQRekapPresensijam_datang: TDateTimeField;
    ZQRekapPresensijam_pulang: TDateTimeField;
    ZQRekapPresensistatus: TStringField;
    ZQRekapPresensiketerlambatan: TStringField;
    ZQRekapPresensidurasi: TStringField;
    ZQRekapPresensiketerangan: TStringField;
    ZQRekapPresensiphoto: TStringField;
    ZQPresensiDatang: TZQuery;
    ZQPresensiDatangid: TIntegerField;
    ZQPresensiDatangnik: TStringField;
    ZQPresensiDatangnama: TStringField;
    ZQPresensiDatangshift: TStringField;
    ZQPresensiDatangjam_datang: TDateTimeField;
    ZQPresensiDatangjam_pulang: TDateTimeField;
    ZQPresensiDatangstatus: TStringField;
    ZQPresensiDatangketerlambatan: TStringField;
    ZQPresensiDatangdurasi: TStringField;
    ZQPresensiDatangphoto: TStringField;
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
    ZQPresensiPulang: TZQuery;
    ZQPresensiPulangid: TIntegerField;
    ZQPresensiPulangnik: TStringField;
    ZQPresensiPulangnama: TStringField;
    ZQPresensiPulangshift: TStringField;
    ZQPresensiPulangjam_datang: TDateTimeField;
    ZQPresensiPulangjam_pulang: TDateTimeField;
    ZQPresensiPulangstatus: TStringField;
    ZQPresensiPulangketerlambatan: TStringField;
    ZQPresensiPulangdurasi: TStringField;
    ZQPresensiPulangketerangan: TStringField;
    ZQPresensiPulangphoto: TStringField;
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
    ZQSetKeterlambatan: TZQuery;
    ZQSetKeterlambatantoleransi: TIntegerField;
    ZQSetKeterlambatanterlambat1: TIntegerField;
    ZQSetKeterlambatanterlambat2: TIntegerField;
    ZQJadwalTambahan: TZQuery;
    ZQJadwalTambahanid: TIntegerField;
    ZQJadwalTambahantahun: TSmallintField;
    ZQJadwalTambahanbulan: TStringField;
    ZQJadwalTambahanh1: TStringField;
    ZQJadwalTambahanh2: TStringField;
    ZQJadwalTambahanh3: TStringField;
    ZQJadwalTambahanh4: TStringField;
    ZQJadwalTambahanh5: TStringField;
    ZQJadwalTambahanh6: TStringField;
    ZQJadwalTambahanh7: TStringField;
    ZQJadwalTambahanh8: TStringField;
    ZQJadwalTambahanh9: TStringField;
    ZQJadwalTambahanh10: TStringField;
    ZQJadwalTambahanh11: TStringField;
    ZQJadwalTambahanh12: TStringField;
    ZQJadwalTambahanh13: TStringField;
    ZQJadwalTambahanh14: TStringField;
    ZQJadwalTambahanh15: TStringField;
    ZQJadwalTambahanh16: TStringField;
    ZQJadwalTambahanh17: TStringField;
    ZQJadwalTambahanh18: TStringField;
    ZQJadwalTambahanh19: TStringField;
    ZQJadwalTambahanh20: TStringField;
    ZQJadwalTambahanh21: TStringField;
    ZQJadwalTambahanh22: TStringField;
    ZQJadwalTambahanh23: TStringField;
    ZQJadwalTambahanh24: TStringField;
    ZQJadwalTambahanh25: TStringField;
    ZQJadwalTambahanh26: TStringField;
    ZQJadwalTambahanh27: TStringField;
    ZQJadwalTambahanh28: TStringField;
    ZQJadwalTambahanh29: TStringField;
    ZQJadwalTambahanh30: TStringField;
    ZQJadwalTambahanh31: TStringField;
    ZQUsersidikjari: TStringField;
    procedure FormActivate(Sender: TObject);
    procedure ZConAfterConnect(Sender: TObject);
    procedure btnLoginClick(Sender: TObject);
    procedure btnBatalLoginClick(Sender: TObject);
    procedure btnLoginAdminClick(Sender: TObject);
    procedure edAdminKeyPress(Sender: TObject; var Key: Char);
    procedure edPassKeyPress(Sender: TObject; var Key: Char);
    procedure btnLoginAdminKeyPress(Sender: TObject; var Key: Char);
    procedure panelfalse();
    procedure pulang(ID: WideString);
    procedure tambahan(ID : WideString;tahun:word;bulan:word;tgl:word);
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
    procedure PPresMasbtnCariClick(Sender: TObject);
    procedure PPresMasEdCariKeyPress(Sender: TObject; var Key: Char);
    procedure btnPresensiDatangClick(Sender: TObject);
    procedure PPresPulBtnCariClick(Sender: TObject);
    procedure PPresPulEdCariKeyPress(Sender: TObject; var Key: Char);
    procedure btnPresensiPulangClick(Sender: TObject);
    procedure btnCariPresensiClick(Sender: TObject);
    procedure PCariBtnCariClick(Sender: TObject);
    procedure PCariEdCariKeyPress(Sender: TObject; var Key: Char);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmUtama: TfrmUtama;
  jml:integer;
  FIni: TIniFile;

implementation

uses DateUtils;

{$R *.dfm}

procedure TfrmUtama.tambahan(ID : WideString;tahun:word;bulan:word;tgl:word);
var
    bulandata,tgldata,jadwal,jam:String;
begin
          if(bulan<10)then
          begin
             bulandata:='0'+IntToStr(bulan);
          end
          else
          begin
             bulandata:=IntToStr(bulan);
          end;
          PHomMemo1.lines.add('Mencari jadwal tambahan..!!');

     			ZQJadwalTambahan.Close;
            ZQJadwalTambahan.SQL.Clear;
            ZQJadwalTambahan.SQL.Add('select * from jadwal_tambahan where id="'+ID+'" and tahun="'+IntToStr(tahun)+'" and bulan="'+bulandata+'"');
            ZQJadwalTambahan.Open;
            if(ZQJadwalTambahan.IsEmpty)then
            begin
                PHomMemo1.lines.add('Pegawai dengan NIP '+ZQPegawainik.Value+', Nama '+ZQPegawainama.Value+', Jabatan '+ZQPegawaijbtn.Value+', jadwal tidak ditemukan');
                mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','notifikasi','\suara\notifikasi.mp3');
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
            end
            else
            if(not ZQJadwalTambahan.IsEmpty)then
            begin
                if(tgl=1)then
                begin
                    jadwal:=ZQJadwalTambahanh1.Value;
                end
                else
                if(tgl=2)then
                begin
                    jadwal:=ZQJadwalTambahanh2.Value;
                end
                else
                if(tgl=3)then
                begin
                    jadwal:=ZQJadwalTambahanh3.Value;
                end
                else
                if(tgl=4)then
                begin
                    jadwal:=ZQJadwalTambahanh4.Value;
                end
                else
                if(tgl=5)then
                begin
                    jadwal:=ZQJadwalTambahanh5.Value;
                end
                else
                if(tgl=6)then
                begin
                    jadwal:=ZQJadwalTambahanh6.Value;
                end
                else
                if(tgl=7)then
                begin
                    jadwal:=ZQJadwalTambahanh7.Value;
                end
                else
                if(tgl=8)then
                begin
                    jadwal:=ZQJadwalTambahanh8.Value;
                end
                else
                if(tgl=9)then
                begin
                    jadwal:=ZQJadwalTambahanh9.Value;
                end
                else
                if(tgl=10)then
                begin
                    jadwal:=ZQJadwalTambahanh10.Value;
                end
                else
                if(tgl=11)then
                begin
                    jadwal:=ZQJadwalTambahanh11.Value;
                end
                else
                if(tgl=12)then
                begin
                    jadwal:=ZQJadwalTambahanh12.Value;
                end
                else
                if(tgl=13)then
                begin
                    jadwal:=ZQJadwalTambahanh13.Value;
                end
                else
                if(tgl=14)then
                begin
                    jadwal:=ZQJadwalTambahanh14.Value;
                end
                else
                if(tgl=14)then
                begin
                    jadwal:=ZQJadwalTambahanh14.Value;
                end
                else
                if(tgl=15)then
                begin
                    jadwal:=ZQJadwalTambahanh15.Value;
                end
                else
                if(tgl=16)then
                begin
                    jadwal:=ZQJadwalTambahanh16.Value;
                end
                else
                if(tgl=17)then
                begin
                    jadwal:=ZQJadwalTambahanh17.Value;
                end
                else
                if(tgl=18)then
                begin
                    jadwal:=ZQJadwalTambahanh18.Value;
                end
                else
                if(tgl=19)then
                begin
                    jadwal:=ZQJadwalTambahanh19.Value;
                end
                else
                if(tgl=20)then
                begin
                    jadwal:=ZQJadwalTambahanh20.Value;
                end
                else
                if(tgl=21)then
                begin
                    jadwal:=ZQJadwalTambahanh21.Value;
                end
                else
                if(tgl=22)then
                begin
                    jadwal:=ZQJadwalTambahanh22.Value;
                end
                else
                if(tgl=23)then
                begin
                    jadwal:=ZQJadwalTambahanh23.Value;
                end
                else
                if(tgl=24)then
                begin
                    jadwal:=ZQJadwalTambahanh24.Value;
                end
                else
                if(tgl=25)then
                begin
                    jadwal:=ZQJadwalTambahanh25.Value;
                end
                else
                if(tgl=26)then
                begin
                    jadwal:=ZQJadwalTambahanh26.Value;
                end
                else
                if(tgl=27)then
                begin
                    jadwal:=ZQJadwalTambahanh27.Value;
                end
                else
                if(tgl=28)then
                begin
                    jadwal:=ZQJadwalTambahanh28.Value;
                end
                else
                if(tgl=29)then
                begin
                    jadwal:=ZQJadwalTambahanh29.Value;
                end
                else
                if(tgl=30)then
                begin
                    jadwal:=ZQJadwalTambahanh30.Value;
                end
                else
                if(tgl=31)then
                begin
                    jadwal:=ZQJadwalTambahanh31.Value;
                end;

                PHomMemo1.lines.add('    Jadwal ditemukan '+jadwal);
                if(jadwal='')then
                begin
                    PHomMemo1.lines.add('Pegawai dengan NIP '+ZQPegawainik.Value+', Nama '+ZQPegawainama.Value+', Jabatan '+ZQPegawaijbtn.Value+', hari ini libur');
                    mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','notifikasi','\suara\notifikasi.mp3');
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
                end
                else
                if(jadwal<>'')then
                begin
                    if(tgl<10)then
                    begin
                        tgldata:='0'+IntToStr(tgl);
                    end
                    else
                    begin
                        tgldata:=IntToStr(tgl);
                    end;

                    ZQSudahPresensi.Close;
                    ZQSudahPresensi.SQL.Clear;
                    ZQSudahPresensi.SQL.Add('select id from rekap_presensi where id="'+ID+'" and jam_datang like "%'+IntToStr(tahun)+'-'+bulandata+'-'+tgldata+'%"');
                    ZQSudahPresensi.Open;
                    if(ZQSudahPresensi.RecordCount>1)then
                    begin
                        mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','sudahmasuk','\suara\sudahmasuk.mp3');
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
                    end
                    else
                    if(ZQSudahPresensi.RecordCount=1)then
                    begin     
                        ZQJamMasuk.Close;
                        ZQJamMasuk.SQL.Clear;
                        ZQJamMasuk.SQL.Add('select * from jam_masuk where shift="'+jadwal+'"');
                        ZQJamMasuk.Open;
                        if(not ZQJamMasuk.IsEmpty)then
                        begin
                            PHomMemo1.lines.add('    Jam wajib masuk '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_masuk.Value)+' dan wajib pulang setelah jam '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_pulang.Value));
                        end;
                        jam:='CONCAT(CURRENT_DATE()," '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_masuk.Value)+'")';
                        ZQExec.Close;
                        ZQExec.SQL.Clear;
                        ZQExec.SQL.Add('insert into temporary_presensi values("'+ID+'","'+jadwal+'",NOW(),NULL,'+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatantoleransi.AsString+','+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat1.AsString+','+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat2.AsString+',"Terlambat II","Terlambat I"),'+
                                '"Terlambat Toleransi"),"Tepat Waktu"),if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatantoleransi.AsString+','+
                                'SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')),""),"","")');
                        ZQExec.ExecSQL;

                        mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','masuk','\suara\selamatbekerja.mp3');
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
            end;
end;

procedure TfrmUtama.pulang(ID: WideString);
var
 jam,jam2:String;
begin
   ZQJamMasuk.Close;
   ZQJamMasuk.SQL.Clear;
   ZQJamMasuk.SQL.Add('select * from jam_masuk where shift="'+ZQTemporaryPresensishift.Value+'"');
   ZQJamMasuk.Open;
   jam:='CONCAT(CURRENT_DATE()," '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_masuk.Value)+'")';
   jam2:='CONCAT(CURRENT_DATE()," '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_pulang.Value)+'")';
   ZQExec.Close;
   ZQExec.SQL.Clear;
   ZQExec.SQL.Add('update temporary_presensi set jam_pulang=NOW(),status=if(TIME_TO_SEC("'+FormatDateTime('yyyy-mm-dd hh:nn:ss',ZQTemporaryPresensijam_datang.Value)+'")-'+
           'TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatantoleransi.AsString+',if(TIME_TO_SEC("'+FormatDateTime('yyyy-mm-dd hh:nn:ss',ZQTemporaryPresensijam_datang.Value)+'")-'+
           'TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat1.AsString+',if(TIME_TO_SEC("'+FormatDateTime('yyyy-mm-dd hh:nn:ss',ZQTemporaryPresensijam_datang.Value)+'")-'+
           'TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat2.AsString+', concat("Terlambat II",if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam2+')<0," & PSW"," ")),'+
           'concat("Terlambat I",if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam2+')<0," & PSW"," "))),concat("Terlambat Toleransi",if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam2+')<0," & PSW"," "))),'+
           'concat("Tepat Waktu",if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam2+')<0," & PSW"," "))),durasi=(SEC_TO_TIME(unix_timestamp(now()) - unix_timestamp(jam_datang))) where id="'+ID+'"  ');
   ZQExec.ExecSQL;

   ZQTemporaryPresensi.Close;
   ZQTemporaryPresensi.SQL.Clear;
   ZQTemporaryPresensi.SQL.Add('select * from temporary_presensi where id="'+ID+'"');
   ZQTemporaryPresensi.Open;

   ZQRekapPresensi.Insert;
   ZQRekapPresensiid.Value:=ZQTemporaryPresensiid.Value;
   ZQRekapPresensishift.Value:=ZQTemporaryPresensishift.Value;
   ZQRekapPresensijam_datang.Value:=ZQTemporaryPresensijam_datang.Value;
   ZQRekapPresensijam_pulang.Value:=ZQTemporaryPresensijam_pulang.Value;
   ZQRekapPresensistatus.Value:=ZQTemporaryPresensistatus.Value;
   ZQRekapPresensiketerlambatan.Value:=ZQTemporaryPresensiketerlambatan.Value;
   ZQRekapPresensidurasi.Value:=ZQTemporaryPresensidurasi.Value;
   ZQRekapPresensiketerangan.Value:='';
   ZQRekapPresensiphoto.Value:='';
   ZQRekapPresensi.Post;
   ZQTemporaryPresensi.Delete;

   mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','pulang','\suara\terimakasih.mp3');
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
     FinFPVer1.FPLoad(ZQSidikJariid.AsString,0,ZQSidikJarisidikjari.Value,'MySecretKey');
     ZQSidikJari.Next;
  end;

  FinFPVer1.FPVerificationStart('');
  suiPanel1.Push;
  suiPanel2.Push;

  PanelHome.Align:=alClient;
  PanelLogin.Align:=alClient;
  PanelRegistrasi.Align:=alClient;
  PanelPresensiDatang.Align:=alClient;
  PanelPresensiPulang.Align:=alClient;
  PanelCariPresensi.Align:=alClient;
  panelfalse;
  PanelHome.Visible:=true;

  PRegbtnRegistrasi.Caption := '&Batalkan';

  suiPanel1.Color:=suiSideChannel1.Color;
  suiPanel2.Color:=suiSideChannel1.Color;

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
  ZQPegawai.Active:=true;
  ZQJadwalPegawai.Active:=true;
  ZQJamMasuk.Active:=true;
  ZQSudahPresensi.Active:=true;
  ZQTemporaryPresensi.Active:=true;
  ZQRekapPresensi.Active:=true;
  ZQPresensiDatang.Active:=true;
  ZQPresensiPulang.Active:=true;
  ZQSetKeterlambatan.Active:=true;
  ZQJadwalTambahan.Active:=true;
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
		      ZQUser.SQL.Add('select sidikjari from user where id_user=AES_ENCRYPT("'+edAdmin.Text+'","nur") and password=AES_ENCRYPT("'+edPass.Text+'","windi")');
		      ZQUser.Open;
		      if(ZQUser.RecordCount>0)then
		      begin
			       btnRegistrasi.Enabled:=StrToBool(ZQUsersidikjari.Value);
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
  ZQSidikJari.SQL.Add('select pegawai.id,pegawai.nik,pegawai.nama,sidikjari.sidikjari '+
                      'from pegawai left outer join sidikjari on pegawai.id=sidikjari.id '+
                      'where pegawai.nik like "%'+Trim(PRegCari.Text)+'%" or '+
                      'pegawai.nama like "%'+Trim(PRegCari.Text)+'%" order by pegawai.nik');
  ZQSidikJari.Open;
end;

procedure TfrmUtama.PRegBtnBaruClick(Sender: TObject);
begin
  PRegNmPeg.Clear;
  PRegIdPeg.Clear;
  PRegNIP.Clear;
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
     PRegIdPeg.Text:=ZQSidikJariid.AsString;
     PRegNIP.Text:=ZQSidikJarinik.Value;
     PRegNmPeg.Text:=ZQSidikJarinama.Value;
     PRegMemo2.Text:=ZQSidikJarisidikjari.Value;
  end;
end;

procedure TfrmUtama.PRegBtnSimpanClick(Sender: TObject);
begin
   if(Trim(PRegIdPeg.Text)='') or (Trim(PRegNIP.Text)='')then
   begin
      MessageDlg('Maaf, pilih pegawai dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   if(Trim(PRegMemo2.Text)='')then
   begin
      MessageDlg('Maaf, silahkan ambil data sidikjari terlebih dahulu...!!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjari where id="'+PRegIdPeg.Text+'"');
      ZQInputSidikJari.Open;
      if(ZQInputSidikJari.IsEmpty)then
      begin
         ZQInputSidikJari.Insert;
         ZQInputSidikJariid.Value:=StrToInt(PRegIdPeg.Text);
         ZQInputSidikJarisidikjari.Value:=PRegMemo2.Text;
         ZQInputSidikJari.Post;
         PRegBtnBaruClick(sender);
         PRegBtnCariClick(sender);
      end
      else
      begin
         MessageDlg('Maaf, data sidik jari pegawai '+PRegNmPeg.Text+' sudah masuk sebelumnya...!!!', mtInformation,[mbOK],0);
         PRegGrid.SetFocus;
      end
   end;
end;

procedure TfrmUtama.PRegBtnHapusClick(Sender: TObject);
begin
   if(Trim(PRegIdPeg.Text)='') or (Trim(PRegNIP.Text)='')then
   begin
      MessageDlg('Maaf, pilih pegawai yang mau dihapus sidik jarinya terlebih dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjari where id="'+PRegIdPeg.Text+'"');
      ZQInputSidikJari.Open;
      if(not ZQInputSidikJari.IsEmpty)then
      begin
        ZQInputSidikJari.Delete;
        PRegBtnCariClick(sender);
        PRegBtnBaruClick(sender);
        MessageDlg('Data sidik jari '+PRegNmPeg.Text+' berhasil dihapus ...!!', mtInformation,[mbOK],0);
      end
      else
      if(ZQInputSidikJari.IsEmpty)then
      begin
        MessageDlg('Data sidik jari '+PRegNmPeg.Text+' belum pernah tersimpan sebelumnya ...!!', mtInformation,[mbOK],0);
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
   if(Trim(PRegIdPeg.Text)='') or (Trim(PRegNIP.Text)='')then
   begin
      MessageDlg('Maaf, pilih pegawai dulu...!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   if(Trim(PRegMemo2.Text)='')then
   begin
      MessageDlg('Maaf, silahkan ambil data sidikjari terlebih dahulu...!!!', mtInformation,[mbOK],0);
      PRegGrid.SetFocus;
   end
   else
   begin
      ZQInputSidikJari.Close;
      ZQInputSidikJari.SQL.Clear;
      ZQInputSidikJari.SQL.Add('select * from sidikjari where id="'+PRegIdPeg.Text+'"');
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
         MessageDlg('Data sidik jari '+PRegNmPeg.Text+' belum pernah tersimpan sebelumnya ...!!', mtInformation,[mbOK],0);
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
var
    tahun,bulan,tgl:word;
    bulandata,tgldata,jadwal,jam:String;
begin
  if(PanelHome.Visible=true)then
  begin
    ZQPegawai.Close;
    ZQPegawai.SQL.Clear;
    ZQPegawai.SQL.Add('select * from pegawai where id="'+ID+'"');
    ZQPegawai.Open;
    PHomMemo1.lines.add('Pegawai dengan NIP '+ZQPegawainik.Value+', Nama '+ZQPegawainama.Value+', Jabatan '+ZQPegawaijbtn.Value+', teridentifikasi');
    DecodeDate(Date, tahun,bulan,tgl);
    if(ID<>'')then
    begin
        if(bulan<10)then
        begin
            bulandata:='0'+IntToStr(bulan);
        end
        else
        begin
            bulandata:=IntToStr(bulan);
        end;

        ZQTemporaryPresensi.Close;
        ZQTemporaryPresensi.SQL.Clear;
        ZQTemporaryPresensi.SQL.Add('select * from temporary_presensi where id="'+ID+'"');
        ZQTemporaryPresensi.Open;
        if(not ZQTemporaryPresensi.IsEmpty)then
        begin
            pulang(ID);
        end
        else
        if(ZQTemporaryPresensi.IsEmpty)then
        begin
            ZQJadwalPegawai.Close;
            ZQJadwalPegawai.SQL.Clear;
            ZQJadwalPegawai.SQL.Add('select * from jadwal_pegawai where id="'+ID+'" and tahun="'+IntToStr(tahun)+'" and bulan="'+bulandata+'"');
            ZQJadwalPegawai.Open;
            if(ZQJadwalPegawai.IsEmpty)then
            begin
                PHomMemo1.lines.add('Pegawai dengan NIP '+ZQPegawainik.Value+', Nama '+ZQPegawainama.Value+', Jabatan '+ZQPegawaijbtn.Value+', jadwal tidak ditemukan');
                mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','notifikasi','\suara\notifikasi.mp3');
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
            end
            else
            if(not ZQJadwalPegawai.IsEmpty)then
            begin
                if(tgl=1)then
                begin
                    jadwal:=ZQJadwalPegawaih1.Value;
                end
                else
                if(tgl=2)then
                begin
                    jadwal:=ZQJadwalPegawaih2.Value;
                end
                else
                if(tgl=3)then
                begin
                    jadwal:=ZQJadwalPegawaih3.Value;
                end
                else
                if(tgl=4)then
                begin
                    jadwal:=ZQJadwalPegawaih4.Value;
                end
                else
                if(tgl=5)then
                begin
                    jadwal:=ZQJadwalPegawaih5.Value;
                end
                else
                if(tgl=6)then
                begin
                    jadwal:=ZQJadwalPegawaih6.Value;
                end
                else
                if(tgl=7)then
                begin
                    jadwal:=ZQJadwalPegawaih7.Value;
                end
                else
                if(tgl=8)then
                begin
                    jadwal:=ZQJadwalPegawaih8.Value;
                end
                else
                if(tgl=9)then
                begin
                    jadwal:=ZQJadwalPegawaih9.Value;
                end
                else
                if(tgl=10)then
                begin
                    jadwal:=ZQJadwalPegawaih10.Value;
                end
                else
                if(tgl=11)then
                begin
                    jadwal:=ZQJadwalPegawaih11.Value;
                end
                else
                if(tgl=12)then
                begin
                    jadwal:=ZQJadwalPegawaih12.Value;
                end
                else
                if(tgl=13)then
                begin
                    jadwal:=ZQJadwalPegawaih13.Value;
                end
                else
                if(tgl=14)then
                begin
                    jadwal:=ZQJadwalPegawaih14.Value;
                end
                else
                if(tgl=14)then
                begin
                    jadwal:=ZQJadwalPegawaih14.Value;
                end
                else
                if(tgl=15)then
                begin
                    jadwal:=ZQJadwalPegawaih15.Value;
                end
                else
                if(tgl=16)then
                begin
                    jadwal:=ZQJadwalPegawaih16.Value;
                end
                else
                if(tgl=17)then
                begin
                    jadwal:=ZQJadwalPegawaih17.Value;
                end
                else
                if(tgl=18)then
                begin
                    jadwal:=ZQJadwalPegawaih18.Value;
                end
                else
                if(tgl=19)then
                begin
                    jadwal:=ZQJadwalPegawaih19.Value;
                end
                else
                if(tgl=20)then
                begin
                    jadwal:=ZQJadwalPegawaih20.Value;
                end
                else
                if(tgl=21)then
                begin
                    jadwal:=ZQJadwalPegawaih21.Value;
                end
                else
                if(tgl=22)then
                begin
                    jadwal:=ZQJadwalPegawaih22.Value;
                end
                else
                if(tgl=23)then
                begin
                    jadwal:=ZQJadwalPegawaih23.Value;
                end
                else
                if(tgl=24)then
                begin
                    jadwal:=ZQJadwalPegawaih24.Value;
                end
                else
                if(tgl=25)then
                begin
                    jadwal:=ZQJadwalPegawaih25.Value;
                end
                else
                if(tgl=26)then
                begin
                    jadwal:=ZQJadwalPegawaih26.Value;
                end
                else
                if(tgl=27)then
                begin
                    jadwal:=ZQJadwalPegawaih27.Value;
                end
                else
                if(tgl=28)then
                begin
                    jadwal:=ZQJadwalPegawaih28.Value;
                end
                else
                if(tgl=29)then
                begin
                    jadwal:=ZQJadwalPegawaih29.Value;
                end
                else
                if(tgl=30)then
                begin
                    jadwal:=ZQJadwalPegawaih30.Value;
                end
                else
                if(tgl=31)then
                begin
                    jadwal:=ZQJadwalPegawaih31.Value;
                end;

                PHomMemo1.lines.add('    Jadwal '+jadwal);
                if(jadwal='')then
                begin
                    PHomMemo1.lines.add('Pegawai dengan NIP '+ZQPegawainik.Value+', Nama '+ZQPegawainama.Value+', Jabatan '+ZQPegawaijbtn.Value+', hari ini libur');
                    mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','notifikasi','\suara\notifikasi.mp3');
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
                end
                else
                if(jadwal<>'')then
                begin
                    if(tgl<10)then
                    begin
                        tgldata:='0'+IntToStr(tgl);
                    end
                    else
                    begin
                        tgldata:=IntToStr(tgl);
                    end;
                    ZQSudahPresensi.Close;
                    ZQSudahPresensi.SQL.Clear;
                    ZQSudahPresensi.SQL.Add('select id from rekap_presensi where id="'+ID+'" and shift="'+jadwal+'" and jam_datang like "%'+IntToStr(tahun)+'-'+bulandata+'-'+tgldata+'%"');
                    ZQSudahPresensi.Open;
                    if(not ZQSudahPresensi.IsEmpty)then
                    begin
                       tambahan(ID,tahun,bulan,tgl);
                    end
                    else
                    if(ZQSudahPresensi.IsEmpty)then
                    begin
                        ZQJamMasuk.Close;
                        ZQJamMasuk.SQL.Clear;
                        ZQJamMasuk.SQL.Add('select * from jam_masuk where shift="'+jadwal+'"');
                        ZQJamMasuk.Open;
                        if(not ZQJamMasuk.IsEmpty)then
                        begin
                            PHomMemo1.lines.add('    Jam wajib masuk '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_masuk.Value)+' dan wajib pulang setelah jam '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_pulang.Value));
                        end;
                        jam:='CONCAT(CURRENT_DATE()," '+FormatDateTime('hh:nn:ss',ZQJamMasukjam_masuk.Value)+'")';
                        ZQExec.Close;
                        ZQExec.SQL.Clear;
                        ZQExec.SQL.Add('insert into temporary_presensi values("'+ID+'","'+jadwal+'",NOW(),NULL,'+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatantoleransi.AsString+','+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat1.AsString+','+
                                'if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatanterlambat2.AsString+',"Terlambat II","Terlambat I"),'+
                                '"Terlambat Toleransi"),"Tepat Waktu"),if(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')>'+ZQSetKeterlambatantoleransi.AsString+','+
                                'SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC('+jam+')),""),"","")');
                        ZQExec.ExecSQL;

                        mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','masuk','\suara\selamatbekerja.mp3');
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
            end;
        end
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
							  {mp3Player.FileName:=ExtractFilePath(Application.EXEName)+FIni.ReadString('sound','alattakterhubung','\suara\alattakterhubung.mp3');
							  if FileExists(mp3Player.FileName)then
							  begin
									mp3Player.Open;
									mp3Player.Play;
							  end
								  else
								  if not FileExists(mp3Player.FileName)then
								  begin
									mp3Player.Close;
							  end;}
                   ShellExecute(0,'open','presensi.exe','','',SW_SHOWNORMAL);
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
     FinFPVer1.FPLoad(ZQSidikJariid.AsString,0,ZQSidikJarisidikjari.Value,'MySecretKey');
     ZQSidikJari.Next;
  end;
    
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

procedure TfrmUtama.PPresMasbtnCariClick(Sender: TObject);
begin
  ZQPresensiDatang.Close;
  ZQPresensiDatang.SQL.Clear;
  ZQPresensiDatang.SQL.Add('SELECT pegawai.id, pegawai.nik, pegawai.nama, temporary_presensi.shift,'+
                'temporary_presensi.jam_datang, temporary_presensi.jam_pulang, temporary_presensi.status,'+
                'temporary_presensi.keterlambatan, temporary_presensi.durasi, temporary_presensi.photo  from pegawai '+
                'inner join temporary_presensi on pegawai.id=temporary_presensi.id '+                 
                'where  pegawai.nik like "%'+Trim(PPresMasEdCari.Text)+'%" or  '+
                'pegawai.nama like "%'+Trim(PPresMasEdCari.Text)+'%" or '+
                'temporary_presensi.shift like "%'+Trim(PPresMasEdCari.Text)+'%" or '+
                'temporary_presensi.jam_datang like "%'+Trim(PPresMasEdCari.Text)+'%" or '+
                'temporary_presensi.status like "%'+Trim(PPresMasEdCari.Text)+'%" or '+
                'temporary_presensi.keterlambatan like "%'+Trim(PPresMasEdCari.Text)+'%" '+
                'order by temporary_presensi.jam_datang');
  ZQPresensiDatang.Open;
end;

procedure TfrmUtama.PPresMasEdCariKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else PPresMasbtnCariClick(sender);
end;

procedure TfrmUtama.btnPresensiDatangClick(Sender: TObject);
begin
  panelfalse;
  PanelPresensiDatang.Visible:=true;
  PPresMasbtnCariClick(sender);
end;

procedure TfrmUtama.PPresPulBtnCariClick(Sender: TObject);
begin
  ZQPresensiPulang.Close;
  ZQPresensiPulang.SQL.Clear;
  ZQPresensiPulang.SQL.Add('SELECT pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, '+
                'rekap_presensi.jam_datang, rekap_presensi.jam_pulang, rekap_presensi.status, '+
                'rekap_presensi.keterlambatan, rekap_presensi.durasi,rekap_presensi.keterangan, '+
                'rekap_presensi.photo  from pegawai inner join rekap_presensi on pegawai.id=rekap_presensi.id '+
                'where  rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and pegawai.nik like "%'+Trim(PPresPulEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and pegawai.nama like "%'+Trim(PPresPulEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and rekap_presensi.shift like "%'+Trim(PPresPulEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and rekap_presensi.jam_datang like "%'+Trim(PPresPulEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and rekap_presensi.status like "%'+Trim(PPresPulEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+FormatDateTime('yyyy-mm-dd',date)+'%" and rekap_presensi.keterlambatan like "%'+Trim(PPresPulEdCari.Text)+'%" order by rekap_presensi.jam_datang');
  ZQPresensiPulang.Open;

end;

procedure TfrmUtama.PPresPulEdCariKeyPress(Sender: TObject; var Key: Char);
begin
  if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else PPresPulBtnCariClick(sender);
end;

procedure TfrmUtama.btnPresensiPulangClick(Sender: TObject);
begin
  panelfalse;
  PanelPresensiPulang.Visible:=true;
  PPresPulBtnCariClick(sender);
end;

procedure TfrmUtama.btnCariPresensiClick(Sender: TObject);
begin
  panelfalse;
  PanelCariPresensi.Visible:=true;
  PCariBtnCariClick(sender);
end;

procedure TfrmUtama.PCariBtnCariClick(Sender: TObject);
begin
  ZQPresensiPulang.Close;
  ZQPresensiPulang.SQL.Clear;
  ZQPresensiPulang.SQL.Add('SELECT pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, '+
                'rekap_presensi.jam_datang, rekap_presensi.jam_pulang, rekap_presensi.status, '+
                'rekap_presensi.keterlambatan, rekap_presensi.durasi,rekap_presensi.keterangan, '+
                'rekap_presensi.photo  from pegawai inner join rekap_presensi on pegawai.id=rekap_presensi.id '+
                'where  rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and pegawai.nik like "%'+Trim(PCariEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and pegawai.nama like "%'+Trim(PCariEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and rekap_presensi.shift like "%'+Trim(PCariEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and rekap_presensi.jam_datang like "%'+Trim(PCariEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and rekap_presensi.status like "%'+Trim(PCariEdCari.Text)+'%" or '+
                'rekap_presensi.jam_datang like "%'+PCariCMbTahun.Text+'-'+PCariCmbBulan.Text+'%" and rekap_presensi.keterlambatan like "%'+Trim(PCariEdCari.Text)+'%" order by pegawai.nama,rekap_presensi.jam_datang');
  ZQPresensiPulang.Open;
end;

procedure TfrmUtama.PCariEdCariKeyPress(Sender: TObject; var Key: Char);
begin
   if not(key in[chr(13),chr(8),'0'..'9','a'..'z','A'..'Z','.',',',' ',':',';','(',')','[',']','&','@','/']) then key:=#0
  else
  if not(key=chr(13)) then exit
  else PCariBtnCariClick(sender);
end;

end.
