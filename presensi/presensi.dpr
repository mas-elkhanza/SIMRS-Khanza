program presensi;

uses
  Forms,
  UUtama in 'UUtama.pas' {frmUtama};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TfrmUtama, frmUtama);
  Application.Run;
end.
