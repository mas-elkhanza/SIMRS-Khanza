program Alarm;

uses
  Forms,
  UUtama in 'UUtama.pas' {frmUtama};

{$R *.res}

begin
  Application.Initialize;
  Application.Title := '::[ Bel Otomatisnya Khanza ]::';
  Application.CreateForm(TfrmUtama, frmUtama);
  Application.Run;
end.
