program presensi;

uses
  Forms,
  UUtama in 'UUtama.pas' {frmUtama};

{$R *.res}

begin
  Application.Initialize;
  Application.Title := 'Registrasi & Resume Pasien';
  Application.CreateForm(TfrmUtama, frmUtama);
  Application.Run;
end.
