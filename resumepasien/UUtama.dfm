object frmUtama: TfrmUtama
  Left = 300
  Top = 108
  Align = alClient
  BorderStyle = bsSingle
  Caption = '::[ SIMRS KhanzaHMS Subsistem Resume Pasien Via Finger Print ]::'
  ClientHeight = 525
  ClientWidth = 983
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poScreenCenter
  OnActivate = FormActivate
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object suiSideChannel1: TsuiSideChannel
    Left = 0
    Top = 0
    Width = 156
    Height = 525
    UIStyle = MacOS
    BorderColor = 11193514
    CaptionFontColor = clGreen
    ShowButton = True
    SideBarColor = clMoneyGreen
    Caption = 'Menu Utama'
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    Align = suiLeft
    StayOn = False
    Color = clMoneyGreen
    ParentFont = False
    PopupMode = suiMouseOn
    QuickMove = False
    SideBarWidth = 10
    object btnRegistrasi: TsuiButton
      Left = 12
      Top = 107
      Width = 131
      Height = 32
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'Tahoma'
      Font.Style = []
      Caption = 'Registrasi Sidik Jari'
      AutoSize = False
      ParentFont = False
      UIStyle = MacOS
      Enabled = False
      TabOrder = 0
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = btnRegistrasiClick
      ResHandle = 0
    end
    object btnLogin: TsuiButton
      Left = 12
      Top = 148
      Width = 131
      Height = 32
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'Tahoma'
      Font.Style = []
      Caption = 'Log-In Admin'
      AutoSize = False
      ParentFont = False
      UIStyle = MacOS
      TabOrder = 1
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = btnLoginClick
      ResHandle = 0
    end
    object suiButton3: TsuiButton
      Left = 12
      Top = 27
      Width = 131
      Height = 32
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'Tahoma'
      Font.Style = []
      Caption = 'Pencarian Data'
      AutoSize = False
      ParentFont = False
      UIStyle = MacOS
      TabOrder = 2
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = btnInputDataClick
      ResHandle = 0
    end
    object suiButton4: TsuiButton
      Left = 12
      Top = 67
      Width = 131
      Height = 32
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'Tahoma'
      Font.Style = []
      Caption = 'Registrasi Poli'
      AutoSize = False
      ParentFont = False
      UIStyle = MacOS
      TabOrder = 3
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = suiButton4Click
      ResHandle = 0
    end
  end
  object PanelHome: TPanel
    Left = 176
    Top = 1000
    Width = 609
    Height = 353
    BevelOuter = bvNone
    Color = 16711678
    TabOrder = 1
    object Panel3: TPanel
      Left = 0
      Top = 0
      Width = 609
      Height = 73
      Align = alTop
      BevelOuter = bvNone
      Color = clGray
      TabOrder = 0
      object DBText2: TDBText
        Left = 13
        Top = 5
        Width = 377
        Height = 41
        DataField = 'nama_instansi'
        DataSource = DSSetting
        Font.Charset = ANSI_CHARSET
        Font.Color = clGreen
        Font.Height = -29
        Font.Name = 'Tahoma'
        Font.Style = [fsItalic]
        ParentFont = False
      end
      object Label1: TLabel
        Left = 14
        Top = 42
        Width = 54
        Height = 23
        Caption = 'Label1'
        Font.Charset = ANSI_CHARSET
        Font.Color = clGreen
        Font.Height = -19
        Font.Name = 'Tahoma'
        Font.Style = [fsItalic]
        ParentFont = False
      end
    end
    object PHomMemo1: TsuiMemo
      Left = 0
      Top = 73
      Width = 609
      Height = 280
      UIStyle = DeepBlue
      BorderColor = clMoneyGreen
      Align = alClient
      TabOrder = 1
    end
  end
  object PanelLogin: TsuiGroupBox
    Left = 272
    Top = 10
    Width = 409
    Height = 241
    UIStyle = MacOS
    Caption = ':: Silahkan Anda Login ::'
    Color = 16711679
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
    TabOrder = 2
    BorderColor = clMoneyGreen
    object Label2: TLabel
      Left = 13
      Top = 36
      Width = 73
      Height = 13
      Alignment = taRightJustify
      AutoSize = False
      Caption = 'ID Admin :'
    end
    object Label3: TLabel
      Left = 13
      Top = 68
      Width = 73
      Height = 13
      Alignment = taRightJustify
      AutoSize = False
      Caption = 'Password :'
    end
    object edAdmin: TsuiEdit
      Left = 90
      Top = 34
      Width = 201
      Height = 20
      UIStyle = DeepBlue
      BorderColor = clMoneyGreen
      PasswordChar = '*'
      TabOrder = 0
      OnKeyPress = edAdminKeyPress
    end
    object edPass: TsuiEdit
      Left = 90
      Top = 64
      Width = 201
      Height = 20
      UIStyle = DeepBlue
      BorderColor = clMoneyGreen
      PasswordChar = '*'
      TabOrder = 1
      OnKeyPress = edPassKeyPress
    end
    object btnLoginAdmin: TsuiButton
      Left = 110
      Top = 100
      Width = 80
      Height = 27
      Caption = 'Log In'
      AutoSize = False
      UIStyle = MacOS
      TabOrder = 2
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = btnLoginAdminClick
      OnKeyPress = btnLoginAdminKeyPress
      ResHandle = 0
    end
    object btnBatalLogin: TsuiButton
      Left = 211
      Top = 100
      Width = 80
      Height = 27
      Caption = 'Batal'
      AutoSize = False
      UIStyle = MacOS
      TabOrder = 3
      Transparent = True
      ModalResult = 0
      FocusedRectMargin = 2
      Layout = blGlyphLeft
      Spacing = 4
      MouseContinuouslyDownInterval = 100
      OnClick = btnBatalLoginClick
      ResHandle = 0
    end
  end
  object PanelRegistrasi: TsuiGroupBox
    Left = 184
    Top = 1000
    Width = 800
    Height = 487
    UIStyle = MacOS
    Caption = ':: Registrasi Sidik Jari Pasien ::'
    Color = 16711679
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
    TabOrder = 3
    BorderColor = clMoneyGreen
    object Panel1: TPanel
      Left = 3
      Top = 16
      Width = 794
      Height = 185
      Align = alTop
      BevelOuter = bvNone
      Color = 15728623
      TabOrder = 0
      object Label5: TLabel
        Left = 0
        Top = 10
        Width = 60
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Pasien'
      end
      object suiGroupBox1: TsuiGroupBox
        Left = 63
        Top = 26
        Width = 89
        Height = 119
        UIStyle = DeepBlue
        Color = clWindow
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clBlack
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        TabOrder = 0
        BorderColor = clMoneyGreen
        object Image1: TImage
          Left = 3
          Top = 16
          Width = 83
          Height = 100
          Align = alClient
          Stretch = True
          Transparent = True
        end
      end
      object PRegNmPasien: TsuiEdit
        Left = 154
        Top = 8
        Width = 616
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        ReadOnly = True
        TabOrder = 1
      end
      object PRegMemo2: TsuiMemo
        Left = 292
        Top = 32
        Width = 477
        Height = 143
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 2
      end
      object PRegMemo1: TsuiMemo
        Left = 154
        Top = 32
        Width = 136
        Height = 143
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 3
      end
      object PRegNoRkmMedis: TsuiEdit
        Left = 63
        Top = 8
        Width = 89
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        ReadOnly = True
        TabOrder = 4
      end
      object PRegbtnRegistrasi: TsuiButton
        Left = 63
        Top = 149
        Width = 89
        Height = 27
        Caption = '&Ambil'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 5
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegbtnRegistrasiClick
        ResHandle = 0
      end
    end
    object PRegGrid: TsuiDBGrid
      Left = 3
      Top = 201
      Width = 794
      Height = 235
      Align = alClient
      BorderStyle = bsNone
      Color = clWhite
      Ctl3D = True
      DataSource = DSSidikJari
      FixedColor = clWhite
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      Options = [dgTitles, dgIndicator, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit]
      ParentCtl3D = False
      ParentFont = False
      ReadOnly = True
      TabOrder = 1
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clGreen
      TitleFont.Height = -11
      TitleFont.Name = 'MS Sans Serif'
      TitleFont.Style = []
      OnCellClick = PRegGridCellClick
      UIStyle = MacOS
      BorderColor = clWhite
      FocusedColor = clWhite
      SelectedColor = 15728623
      FontColor = clGreen
      TitleFontColor = clGreen
      FixedBGColor = clWhite
      BGColor = clWhite
      Columns = <
        item
          Expanded = False
          FieldName = 'no_rkm_medis'
          Title.Alignment = taCenter
          Title.Caption = 'Nomor RM'
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'nm_pasien'
          Title.Alignment = taCenter
          Title.Caption = 'Nama Pasien'
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'sidikjari'
          Title.Alignment = taCenter
          Title.Caption = 'Sidik Jari'
          Visible = True
        end>
    end
    object Panel2: TPanel
      Left = 3
      Top = 436
      Width = 794
      Height = 48
      Align = alBottom
      BevelOuter = bvNone
      Color = 15728623
      TabOrder = 2
      object Label4: TLabel
        Left = 373
        Top = 17
        Width = 60
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Key Word :'
      end
      object PRegBtnSimpan: TsuiButton
        Left = 4
        Top = 10
        Width = 89
        Height = 27
        Caption = '&Simpan'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 0
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          DE030000424DDE03000000000000360000002800000011000000120000000100
          180000000000A8030000120B0000120B00000000000000000000B19EA14A1500
          896344926D518E674C8E694B8E684B8E684B8E684B8E684B8E684B8E684B8E68
          4B906B4D936D504E280BAB9E99004F200766350CD2C8C2FBFFFFECF0F3EFF2F5
          EFF2F5EFF2F5EFF2F5EFF2F5EFF2F5EFF2F4EEF1F3F3F8FDF8FDFF7D532F501F
          04006F4420603009BFAB9EFFFFFFF6F6F6F6F6F6F7F7F6F7F6F6F7F6F6F7F6F6
          F7F6F6F7F6F6F5F6F6F9FBFBF7F4F26B3F1B6B3D16006E401F63330DBFAB9CFF
          FFFFE6E5E6D3D1D3D9D8D9D8D7D9D8D7D9D8D7D9D8D7D9D8D7D8D5D2D5F3F4F5
          F6F3F07043206A3B18006F422067360FC0AA9DFFFFFFE7E7E7D6D6D7DBDADCDA
          D9DBDAD9DBDAD9DBDAD9DBDAD9DAD8D6D6F4F5F5F6F4F37446226C3F1B00774A
          286D3A15C3B0A2FFFFFFE1E0E1C9C7CBD0CFD2D0CDD1D0CED1D0CED1D0CED1CE
          CED0CBCBCDEFEFF2FBF9F7794E2A7447240080513274411EBDA495FFFFFFF3F5
          F7E4E5E8E8EAECE7E9ECE7E9ECE7E9ECE7E9ECE8E8ECE5E6EAFEFFFFE8E0DA7C
          4C2A7D4E2D008157347F5131865C3EC0AC9BCEBDB1CEBEB0CEBDB0CEBDB0CEBD
          B1CEBDB0CEBEB0CDBCB1CFBFB3CFBEB39671577D502B81553400845B388A5F3F
          794B2774431E784A257949247748247746227747237746227746237746217847
          227645217748238D603F82573600885E3F977051825633895B388355317B4A24
          79482276431E73421C723E1A703E17713F1972401B7C4C288659379063428258
          3500875B3EA8846990684A74482665391A7A6150918075A6978CB6A69BBFAFA5
          C7B8ACC8BAB0BFAFA28B664A82543193654684583400865A3CA98568926E506B
          432871513B9FA5ABB5BBBED2D6DAEDF2F6F8FBFEE7E2DCE6DFDAFFFFFFCABBAF
          825431986E5086593800875A3BAB8567936C4D6B4428775A43A6ACAFB8BAB9CE
          CFCFEDF2F3DED5D17E4F2C7A4924E8E3E0E2D9D3916644A68165885C3D00875A
          3CAD8868936A4D653D20704E39A2A5A8B7B8B9CFCFCFF0F3F6DCD3CC7B441B75
          3D12E5DFDCE3D9D1936948A98366875C3C00865A3BAE876792694D623A1E6B4C
          35A1A3A6B6B7B9CECECEEFF2F5DFD8D289572C844F24E8E5E2E2D7D0926747A9
          8262865C3B00865939AD84649166485E35176845309B9E9EB2B2B3CBCBCBEEF1
          F4D4C7BB4F0E00470400DDD5CCE0D6D0916643AA8161895E3E008B6543C6A387
          A27C5E603B236C5143B0C1CCD1D7D7E2E4E7F6FAFFFAFDFFD7CFC4D4C9C1FFFF
          FFF1EDEAAC8463C19B7B703D170073472695755D8E674B835F438562458E6F55
          906B4D8C65498963478A64468F6C518E6B4F8965478B674B8F6D506B3B23BFAC
          A400}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnSimpanClick
        ResHandle = 0
      end
      object PRegBtnBaru: TsuiButton
        Left = 94
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Baru'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 1
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          26040000424D2604000000000000360000002800000012000000120000000100
          180000000000F0030000120B0000120B00000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFAFDFBDCF3E4C3E9D1ADE1BFB3E3C5C8ECD4E8F7EEFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000FFFFFFFFFFFFFFFFFFFFFFFFC2E9
          CF4FBE7726AB5712A2470D9C400F9E4216A14930AD5C67C789E2F6E9FFFFFFFF
          FFFFFFFFFFFFFFFF0000FFFFFFFFFFFFFFFFFF7ACF981BAC5511A44C089E4307
          9A3E06983C04973A06963A0B993F179E4821AB51BCE8CBFFFFFFFFFFFFFFFFFF
          0000FFFFFFFFFFFF64C98700A23B06A84E00A24302A043049E41059D41049B3E
          0397380095350094310F9941008D1EB9E7C9FFFFFFFFFFFF0000FFFFFFA5DEB8
          06B05205AF5503AA4F06A94D08A54B009D3B008918008A1A049A3E03973A0397
          3802953705953A1BA74CE2F6EAFFFFFF0000EFF8F128BC6900B55A06B35907B1
          5606AC5200A44336B469ECF5EEC7E7D1009129049A3E04973A03973900953500
          933063C485FFFFFF0000BDE8CD00B45205BB6309B85F07B55B07B25A00A74744
          BC74FFFFFFF5FAF6009026039F44059B3F04983A0496380092321BA348E7F7ED
          00008DD9A700B85408C06908BC6300B75C00AA43009E2E2DB561FFFFFFE5F5EB
          00860B00912600942D059C3F04983B01953700932BC7EBD400005CCB8A00C163
          0AC46E00BE6216C06BF5FBF7F0FAF4F2FAF5FFFFFFFFFFFFE9F6EEFCFDFCA7DC
          BC009024069D4105983B008F26ADE1BE000057CC8900C5660AC67001C16617C1
          6FF4F6F3F0F4F0F2F7F2FFFFFFFFFFFFE9F3EDFCFAFBA7DBBC009328049F4405
          9C3F009128A8DEBC00007AD49C00C36109C97309C67002C26900AD4C00A6402C
          BD6FFFFFFFE5F6EC00982800A33E00A54206A94E04A146039D4100972FC0E9CE
          0000AEE3C200C3640AD1810BD1820BD1830CD28400CA754CDA9CFFFFFFFFFFFF
          00B85306C16A08BD6206B85C06B45800A5450DA446DDF4E50000E2F2E522CB7E
          02D4880DD3880CD3870AD18308CF800FC67732B16D2AB36E03C56D08C16B08BD
          6507BB6205B85C00B35250C37EFBFCFB0000FFFFFF87D6A61ED38C00D1850BD3
          870DD3870AD18408D08102D28002CE7B08C77609C57109C26D04BE6504BD622A
          BB6DC3E8CFFFFFFF0000FFFFFFF5F8F337BF6E2ADDA000D18307D1860BD2860B
          D1840BCE800CCC7D0CCA7A07C87500C36B08C36F14BC6A7ACF96FFFFFFFFFFFF
          0000FFFFFFFFFFFFF1F8F242C27347D99C17D6910AD38906D38705D28505D081
          07CE800CCD7E21CE8849CA8870CC8EFFFFFFFFFFFFFFFFFF0000FFFFFFFFFFFF
          FFFFFFF5F9F486D5A352D1903CD28F32D49231D89731D69332D28D42CE8B5DCD
          8EA4DDB7FFFFFFFFFFFFFFFFFFFFFFFF0000FFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFDDF1E2B9E6C992DBAD77D59E80D7A39DDDB4C3E9D0E9F6EDFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFF0000}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnBaruClick
        ResHandle = 0
      end
      object PRegBtnHapus: TsuiButton
        Left = 184
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Hapus'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 2
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          AA030000424DAA03000000000000360000002800000011000000110000000100
          18000000000074030000120B0000120B00000000000000000000FFFFFFFFFFFF
          DDDDF0F9F9FCFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFF9F9FCDDDDEFFFFFFFFFFFFF00FFFFFFB9B9DD0606856F6FC0FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF6E6EBF060686B9B9DDFFFF
          FF00CECEE800008800009C0000956868BCFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFF6767BD00009700009E00008BCECEE800D8D8ED2B2BA90C0CA704
          04AA00009E7979C5FEFEFBFFFFFFFFFFFFFFFFFFFFFFFA7777C500009F0505AC
          0E0EAA2D2DABD8D8ED00FFFFFFD7D7EC3333AD0E0EB00606B70000AB6D6DBFFF
          FFFFFFFFFFFFFFFF6C6CBF0000AC0707BA1010B33434AED7D7EBFFFFFF00FFFF
          FFFFFFFFE0E0EF4D4DB92222C10D0DC80000B36A6ABEFFFFF96868BF0000B50D
          0DCB2323C24D4DBAE1E1EFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFD3D3E94646
          B72929D11E1EDA0707C54646BB0606C51D1DDB2B2BD44949B9D3D3E9FFFFFFFF
          FFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFE2E2EC3434B81919D81B1BE11313
          E01D1DE11E1EDC3434B7E1E1ECFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFD4D4E22F2FC13737EA3535EA2929EC2626C2D5D5E3FFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7171C3
          4848DA6464F46C6CF56262F44545DA7272C4FFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FF00FFFFFFFFFFFFFFFFFFFAFAF77272C42929D35D5DF76E6EF09F9FDE7171F1
          5F5FFA2B2BD47373C4F9F9F7FFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFB79
          79C72D2DCC5F5FF76868F46565C5A6A6CD6666C56969F56060F82E2ECE7878C7
          FFFFFBFFFFFFFFFFFF00FFFFFFFFFFFA6B6BC32F2FCF5A5AF16565E95858C3D7
          D7EAFFFFFFD7D7E95858C26666E95B5BF33030D06C6CC3FFFFFBFFFFFF00FFFF
          FD6F6FC12C2CC85B5BEE5F5FE96161C4CECEE4FFFFFFFFFFFFFFFFFFCECEE463
          63C46161EA5A5AF02E2ECA6F6FC1FFFFFD008E8ECA2C2CCA5959EB5B5BE55656
          C4D8D8E8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD8D8E85757C45D5DE65959ED2D
          2DCB8E8ECA00D9D9EA4141BF5656E55353C1CFCFE6FFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFCFCFE65555C25757E64141BED9D9EA00FFFFFFDBDBEC
          5555B6D3D3E9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFD3D3E95656B6DBDBECFFFFFF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnHapusClick
        ResHandle = 0
      end
      object PRegBtnGanti: TsuiButton
        Left = 274
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Ganti'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 3
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          DE030000424DDE03000000000000360000002800000011000000120000000100
          180000000000A8030000120B0000120B00000000000000000000E6E6E6C8C8C8
          D1D1D1D0D0D0D0D0D0D0D0D0D0D0D0D1D1D1EDEDEDFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00C6C6C6C6C6C6CACACACACACACACACACACACA
          CDCDCDC5C5C5959595EDEDEDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FF00D4D4D4D4D4D4DDDDDDD9D8D8D5D5D5D6D6D7D6D6D6DFDFDFB5B5B5A8A8A8
          F9F9F9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00D2D2D2E8E8E8D2D1D1F0
          EEEDF3EFECEAE9E8E6E7E8ECECECCACACAB3B3B3CACACAECECECFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFF00CECECEFAF9F97C7B7A848B96DEE6F5FFFEF7FCF9F6FB
          F9F9CDCECECDCDCDF6F6F6D3D3D3F4F4F4FFFFFFFFFFFFFFFFFFFFFFFF00CDCD
          CDF0F0EFF2F4F88DAAD081A6DDA8B9DEE8F0F7FFFFFFD0CFCFA8A8A8E1E1E1DC
          DCDDD6D6D5F2F2F2FFFFFFFFFFFFFFFFFF00CDCDCDEDEDEDFFFFFFCDDAEEBCC5
          E76BA8D92AACD3CEF3F9FFFAF8D0CFCFC4C4C5B3B3B29B9B9DA1A1A1E0E0E0FF
          FFFFFFFFFF00CDCDCDEDEDEDFFFFFFE2EAF289C2E22CC9EC00ACD821AED2D8F5
          FAFFFFFFFEFDFEFBFBFAD2D2D59C9C9E868686F8F8F8FFFFFF00CDCDCDEDEDED
          FFFFFFFFFFFFA5E6F141D6F01FCFEF00ABD71CACD2C9EEF6FFFFFFFFFFFFFFFF
          FFEFEFF09C9C9DE7E7E7FFFFFF00CDCDCDEDEDEDFFFFFFFFFFFFFFFFFE97E1EF
          4BD6EF13CBEE00A8D621AAD0DEF6F9FFFFFFFFFFFFFFFFFFC5C5C6E2E2E2FFFF
          FF00CDCDCDEDEDEDFFFFFFFFFFFFFFFFFFFFFEFEACE7F24ED6EE21D1EF00A4D5
          1DA2CDC9EFF6FFFFFFFFFFFFD9D9D9DEDEDEFFFFFF00CDCDCDEDEDEDFFFFFFFE
          FEFEFEFEFEFFFFFEFFFFFEA0E3F152DAF025DCF300A1D11197C9DAF5FCFFFFFF
          DBDBDBDEDEDEFFFFFF00CDCDCDEDEDEDFDFDFDFAFAFAFBFBFBFBFBFBFFFCFBFC
          FDFD99E4F160DFF422E9FD00A1D580B0C4FDF9F7E1E1E1DEDEDFFFFFFF00CDCD
          CDEDEDEDF3F3F3F2F2F2F2F2F2F2F2F2F1F1F1F7F4F3FFF9F4ABE2EC48D2EB81
          CDDBDCC8C2B6B4B5D9DAD8E5E5E1FFFFFF00CFCFCFE6E6E6ECECECE9E9E9EAEA
          EAEAEAEAEAEAEAE9E9E9EFEAEAF6F0EDBCCBD2E7DEDEE5E2E1AFAEAEBEBEA3FF
          FFFAFFFFFF00D1D1D1DDDDDDDEDEDEDFDFDFDFDFDFDFDFDFDFDFDFDFDFDFDEDF
          DFE0DFDEEBE6E3C7C5C4E2E2E3FFFFE65252BC3333CEF3F3F800C3C3C3D1D1D1
          D9D9D9D7D7D7D7D7D7D7D7D7D7D7D7D7D7D7D7D7D7D6D7D7D8D8D8DDDDDEC9C9
          C18080CD3333E11313DADDDDF300E7E7E7D3D3D3DBDBDBDADADADADADADADADA
          DADADADADADADADADADADADADADADADBDBDBE2E2DAB1B1DB7B7BECDBDBF9FFFF
          FF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnGantiClick
        ResHandle = 0
      end
      object PRegBtnTutup: TsuiButton
        Left = 680
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Tutup'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 4
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          36030000424D3603000000000000360000002800000010000000100000000100
          1800000000000003000000000000000000000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFE3E3F1FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDDDDEEFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7878BF1114957878BFFFFFFFFF
          FFFFFFFFFFFFFFFF7575BE1115967575BEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          7576BF121CA2112CBF121CA27576BFFFFFFFFFFFFF7576BF121DA2112DBF111D
          A47373BEFFFFFFFFFFFFFFFFFF7879C2131EA61432C21432C31432C2121EA575
          76C17576C1121EA51432C21432C31432C3121FA67576C1FFFFFFE0E0F12024A1
          6176D71A39C71837C71837C71837C6131FA71420A91837C61837C71837C71B39
          C86579D91F22A0E2E3F2FFFFFF7879C53C44B4657CDB1E40CB1C3ECB1C3ECB1C
          3DCA1C3DCA1C3ECB1C3ECB1F40CC677CDB3A41B37576C4FFFFFFFFFFFFFFFFFF
          7476C73D45B8667FDD2246CF2045CF2045CF2045CF2045CF2347D06981DD3942
          B67779C8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7477C8313EB9264DD3244DD424
          4DD4244DD4244DD4254DD32E3BB7777AC9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF7477CB182BB72953D82954D92954D92954D92954D92953D81829B67477
          CBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7478CC1A2EBB2D5BDC2D5CDD2D5CDD2F
          5EDD2F5DDD2D5CDD2D5CDD2D5BDC192DB97478CCFFFFFFFFFFFFFFFFFF7378CF
          1A31BF3162E13163E23163E23365E27495EA7193EA3364E23163E23163E23162
          E11A2FBE7378CFFFFFFFDCDEF3212CB96F94EC356AE6356AE6376CE67699ED3B
          4AC54050C87397ED376BE6356AE6356AE67397ED1E29B7E2E3F5FFFFFF7378D2
          4858CE7299F03B71EA799DF03C4BC8767BD37378D24353CB769AF03B71EA779C
          F14353CB7378D2FFFFFFFFFFFFFFFFFF7075D34A5BD18DACF33C4CCA757BD5FF
          FFFFFFFFFF7278D44354CD8DACF34354CD7075D3FFFFFFFFFFFFFFFFFFFFFFFF
          FCFCFE757BD6212EC0757BD6FFFFFFFFFFFFFFFFFFFFFFFF757BD6212EC0757B
          D6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFFFFFFFFFFFF}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnTutupClick
        ResHandle = 0
      end
      object PRegCari: TsuiEdit
        Left = 436
        Top = 14
        Width = 186
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 5
        OnKeyPress = PRegCariKeyPress
      end
      object PRegBtnCari: TsuiButton
        Left = 623
        Top = 12
        Width = 39
        Height = 24
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 6
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          AA030000424DAA03000000000000360000002800000011000000110000000100
          18000000000074030000120B0000120B00000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFB6B7B7454545555555E3E3E300FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9B9A9A2426262727270000008181
          8100FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF9899992A2B2B39393930303027272790909000FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9393931818183432322F2E2E
          7575755F5F5FDCDCDC00FFFFFFFFFFFFFFFFFFFFFFFFD7D7D7B1B1B1B1B1B1C6
          C6C6FCFCFCE2E2E20304041F20202828287A7A7A4D4D4DCDCDCDFFFFFF00FFFF
          FFFFFFFFD1D1D18F8F8FAFAFAFDDDEDEE1E1E3C6C7C7A0A0A0B6B6B659595908
          08086D6D6D3B3B3BE1E1E1FFFFFFFFFFFF00FFFFFFD2D2D2929292F1F1F3D5D6
          D7C0BFBCBDBCBACACAC8E8E8E9C4C3C3B0B0B0606060252525CCCCCCFFFFFFFF
          FFFFFFFFFF00F0F0F0AEAEAEE5E6E6BAB9B6CFCBC2E2DCD3E4E0D7DFDAD0B8B6
          B0D6D6D7BEBEBED7D7D7EFEFEFFFFFFFFFFFFFFFFFFFFFFFFF00D7D7D7CBCBCC
          C1C0C0CEC9C0DFDBD3E0DDD7E5E1DBE9E6DFEAE6DDB1B0ABE3E4E5B6B6B6FFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00CCCCCCBDBEBFD0CDCBD3D1CAD2D0CCDAD7D1
          DEDBD6E3E0DBEDEAE5D4D2CBBCBCBCACADADF3F3F3FFFFFFFFFFFFFFFFFFFFFF
          FF00C4C4C4BDBDBED8D8D3F5F3EFD9D8D5CECCC8D1CFCCD9D7D3DFDDD7DBD8D2
          B7B7B6B3B4B5E4E4E4FFFFFFFFFFFFFFFFFFFFFFFF00D0D0D0C1C1C2CAC9C5F5
          F4F3F7F5F3F1F0EEE8E7E4E3E1DEE9E7E4DCDBD7B9B9BAB7B8B8E8E8E8FFFFFF
          FFFFFFFFFFFFFFFFFF00DCDCDCD2D2D2B4B2B4E6E4E2EDECEBF2F2F0F5F3F1F1
          F0EEEBEAE9C1BFBED1D1D0AEAEAEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00EBEB
          EBCDCDCDD8D7D7B4B4B4DDDDDCE4E3E2E5E4E4E3E1E1CBCCCBAEAEAEE0E0E0C3
          C3C3FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFCDCDCDD8D8D8DDDDDDB7B7
          B7C4C4C4C5C4C4BDBEBEBDBDBEEFEFEFAFAFAFF0F0F0FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFF00FFFFFFFFFFFFCCCCCCD5D5D5E1E1E1D6D6D6D5D5D5DBDBDBDFDF
          DFBEBEBEE9E9E9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFEFEFE
          FFFFFFEAEAEADFDFDFD7D7D7D5D5D5D8D8D8E1E1E1F7F7F7FFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = PRegBtnCariClick
        ResHandle = 0
      end
    end
  end
  object mp3player: TMediaPlayer
    Left = 576
    Top = 1000
    Width = 85
    Height = 22
    VisibleButtons = [btPlay, btPause, btStop]
    TabOrder = 4
  end
  object PanelPresensiDatang: TsuiGroupBox
    Left = 167
    Top = 1000
    Width = 800
    Height = 487
    UIStyle = MacOS
    Caption = ':: Presensi Masuk Pegawai ::'
    Color = 16711679
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
    TabOrder = 5
    BorderColor = clMoneyGreen
    object suiDBGrid1: TsuiDBGrid
      Left = 3
      Top = 16
      Width = 794
      Height = 420
      Align = alClient
      BorderStyle = bsNone
      Color = clWhite
      Ctl3D = True
      DataSource = DSPresensidatang
      FixedColor = clWhite
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      Options = [dgTitles, dgIndicator, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit]
      ParentCtl3D = False
      ParentFont = False
      ReadOnly = True
      TabOrder = 0
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clGreen
      TitleFont.Height = -11
      TitleFont.Name = 'MS Sans Serif'
      TitleFont.Style = []
      OnCellClick = PRegGridCellClick
      UIStyle = MacOS
      BorderColor = clWhite
      FocusedColor = clWhite
      SelectedColor = 15728623
      FontColor = clGreen
      TitleFontColor = clGreen
      FixedBGColor = clWhite
      BGColor = clWhite
      Columns = <
        item
          Expanded = False
          FieldName = 'nik'
          Title.Alignment = taCenter
          Title.Caption = 'NIP'
          Width = 109
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'nama'
          Title.Alignment = taCenter
          Title.Caption = 'Nama Pegawai'
          Width = 222
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'shift'
          Title.Alignment = taCenter
          Title.Caption = 'Shift'
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_datang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Datang'
          Width = 122
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_pulang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Pulang'
          Width = 124
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'status'
          Title.Alignment = taCenter
          Title.Caption = 'Status'
          Width = 94
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'keterlambatan'
          Title.Alignment = taCenter
          Title.Caption = 'Keterlambatan'
          Width = 87
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'durasi'
          Title.Alignment = taCenter
          Title.Caption = 'Durasi'
          Width = 108
          Visible = True
        end>
    end
    object Panel5: TPanel
      Left = 3
      Top = 436
      Width = 794
      Height = 48
      Align = alBottom
      BevelOuter = bvNone
      Color = 15728623
      TabOrder = 1
      object Label7: TLabel
        Left = 13
        Top = 17
        Width = 60
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Key Word :'
      end
      object suiButton6: TsuiButton
        Left = 680
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Tutup'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 0
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          36030000424D3603000000000000360000002800000010000000100000000100
          1800000000000003000000000000000000000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFE3E3F1FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDDDDEEFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7878BF1114957878BFFFFFFFFF
          FFFFFFFFFFFFFFFF7575BE1115967575BEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          7576BF121CA2112CBF121CA27576BFFFFFFFFFFFFF7576BF121DA2112DBF111D
          A47373BEFFFFFFFFFFFFFFFFFF7879C2131EA61432C21432C31432C2121EA575
          76C17576C1121EA51432C21432C31432C3121FA67576C1FFFFFFE0E0F12024A1
          6176D71A39C71837C71837C71837C6131FA71420A91837C61837C71837C71B39
          C86579D91F22A0E2E3F2FFFFFF7879C53C44B4657CDB1E40CB1C3ECB1C3ECB1C
          3DCA1C3DCA1C3ECB1C3ECB1F40CC677CDB3A41B37576C4FFFFFFFFFFFFFFFFFF
          7476C73D45B8667FDD2246CF2045CF2045CF2045CF2045CF2347D06981DD3942
          B67779C8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7477C8313EB9264DD3244DD424
          4DD4244DD4244DD4254DD32E3BB7777AC9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF7477CB182BB72953D82954D92954D92954D92954D92953D81829B67477
          CBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7478CC1A2EBB2D5BDC2D5CDD2D5CDD2F
          5EDD2F5DDD2D5CDD2D5CDD2D5BDC192DB97478CCFFFFFFFFFFFFFFFFFF7378CF
          1A31BF3162E13163E23163E23365E27495EA7193EA3364E23163E23163E23162
          E11A2FBE7378CFFFFFFFDCDEF3212CB96F94EC356AE6356AE6376CE67699ED3B
          4AC54050C87397ED376BE6356AE6356AE67397ED1E29B7E2E3F5FFFFFF7378D2
          4858CE7299F03B71EA799DF03C4BC8767BD37378D24353CB769AF03B71EA779C
          F14353CB7378D2FFFFFFFFFFFFFFFFFF7075D34A5BD18DACF33C4CCA757BD5FF
          FFFFFFFFFF7278D44354CD8DACF34354CD7075D3FFFFFFFFFFFFFFFFFFFFFFFF
          FCFCFE757BD6212EC0757BD6FFFFFFFFFFFFFFFFFFFFFFFF757BD6212EC0757B
          D6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFFFFFFFFFFFF}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = suiButton6Click
        ResHandle = 0
      end
      object PPresMasEdCari: TsuiEdit
        Left = 77
        Top = 14
        Width = 543
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 1
        OnKeyPress = PPresMasEdCariKeyPress
      end
      object PPresMasbtnCari: TsuiButton
        Left = 623
        Top = 12
        Width = 39
        Height = 24
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 2
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          AA030000424DAA03000000000000360000002800000011000000110000000100
          18000000000074030000120B0000120B00000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFB6B7B7454545555555E3E3E300FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9B9A9A2426262727270000008181
          8100FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF9899992A2B2B39393930303027272790909000FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9393931818183432322F2E2E
          7575755F5F5FDCDCDC00FFFFFFFFFFFFFFFFFFFFFFFFD7D7D7B1B1B1B1B1B1C6
          C6C6FCFCFCE2E2E20304041F20202828287A7A7A4D4D4DCDCDCDFFFFFF00FFFF
          FFFFFFFFD1D1D18F8F8FAFAFAFDDDEDEE1E1E3C6C7C7A0A0A0B6B6B659595908
          08086D6D6D3B3B3BE1E1E1FFFFFFFFFFFF00FFFFFFD2D2D2929292F1F1F3D5D6
          D7C0BFBCBDBCBACACAC8E8E8E9C4C3C3B0B0B0606060252525CCCCCCFFFFFFFF
          FFFFFFFFFF00F0F0F0AEAEAEE5E6E6BAB9B6CFCBC2E2DCD3E4E0D7DFDAD0B8B6
          B0D6D6D7BEBEBED7D7D7EFEFEFFFFFFFFFFFFFFFFFFFFFFFFF00D7D7D7CBCBCC
          C1C0C0CEC9C0DFDBD3E0DDD7E5E1DBE9E6DFEAE6DDB1B0ABE3E4E5B6B6B6FFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00CCCCCCBDBEBFD0CDCBD3D1CAD2D0CCDAD7D1
          DEDBD6E3E0DBEDEAE5D4D2CBBCBCBCACADADF3F3F3FFFFFFFFFFFFFFFFFFFFFF
          FF00C4C4C4BDBDBED8D8D3F5F3EFD9D8D5CECCC8D1CFCCD9D7D3DFDDD7DBD8D2
          B7B7B6B3B4B5E4E4E4FFFFFFFFFFFFFFFFFFFFFFFF00D0D0D0C1C1C2CAC9C5F5
          F4F3F7F5F3F1F0EEE8E7E4E3E1DEE9E7E4DCDBD7B9B9BAB7B8B8E8E8E8FFFFFF
          FFFFFFFFFFFFFFFFFF00DCDCDCD2D2D2B4B2B4E6E4E2EDECEBF2F2F0F5F3F1F1
          F0EEEBEAE9C1BFBED1D1D0AEAEAEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00EBEB
          EBCDCDCDD8D7D7B4B4B4DDDDDCE4E3E2E5E4E4E3E1E1CBCCCBAEAEAEE0E0E0C3
          C3C3FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFCDCDCDD8D8D8DDDDDDB7B7
          B7C4C4C4C5C4C4BDBEBEBDBDBEEFEFEFAFAFAFF0F0F0FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFF00FFFFFFFFFFFFCCCCCCD5D5D5E1E1E1D6D6D6D5D5D5DBDBDBDFDF
          DFBEBEBEE9E9E9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFEFEFE
          FFFFFFEAEAEADFDFDFD7D7D7D5D5D5D8D8D8E1E1E1F7F7F7FFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        ResHandle = 0
      end
    end
  end
  object PanelPresensiPulang: TsuiGroupBox
    Left = 183
    Top = 1000
    Width = 800
    Height = 487
    UIStyle = MacOS
    Caption = ':: Presensi Pulang Pegawai ::'
    Color = 16711679
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
    TabOrder = 6
    BorderColor = clMoneyGreen
    object suiDBGrid2: TsuiDBGrid
      Left = 3
      Top = 16
      Width = 794
      Height = 420
      Align = alClient
      BorderStyle = bsNone
      Color = clWhite
      Ctl3D = True
      DataSource = DSPresensiPulang
      FixedColor = clWhite
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      Options = [dgTitles, dgIndicator, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit]
      ParentCtl3D = False
      ParentFont = False
      ReadOnly = True
      TabOrder = 0
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clGreen
      TitleFont.Height = -11
      TitleFont.Name = 'MS Sans Serif'
      TitleFont.Style = []
      OnCellClick = PRegGridCellClick
      UIStyle = MacOS
      BorderColor = clWhite
      FocusedColor = clWhite
      SelectedColor = 15728623
      FontColor = clGreen
      TitleFontColor = clGreen
      FixedBGColor = clWhite
      BGColor = clWhite
      Columns = <
        item
          Expanded = False
          FieldName = 'id'
          Title.Alignment = taCenter
          Title.Caption = 'NIP'
          Width = 105
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'nama'
          Title.Alignment = taCenter
          Title.Caption = 'Nama'
          Width = 221
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'shift'
          Title.Alignment = taCenter
          Title.Caption = 'Shift'
          Width = 80
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_datang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Datang'
          Width = 130
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_pulang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Pulang'
          Width = 133
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'status'
          Title.Alignment = taCenter
          Title.Caption = 'Status'
          Width = 104
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'keterlambatan'
          Title.Alignment = taCenter
          Title.Caption = 'Keterlambatan'
          Width = 107
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'durasi'
          Title.Alignment = taCenter
          Title.Caption = 'Durasi'
          Width = 99
          Visible = True
        end>
    end
    object Panel4: TPanel
      Left = 3
      Top = 436
      Width = 794
      Height = 48
      Align = alBottom
      BevelOuter = bvNone
      Color = 15728623
      TabOrder = 1
      object Label6: TLabel
        Left = 13
        Top = 17
        Width = 60
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Key Word :'
      end
      object suiButton1: TsuiButton
        Left = 680
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Tutup'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 0
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          36030000424D3603000000000000360000002800000010000000100000000100
          1800000000000003000000000000000000000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFE3E3F1FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDDDDEEFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7878BF1114957878BFFFFFFFFF
          FFFFFFFFFFFFFFFF7575BE1115967575BEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          7576BF121CA2112CBF121CA27576BFFFFFFFFFFFFF7576BF121DA2112DBF111D
          A47373BEFFFFFFFFFFFFFFFFFF7879C2131EA61432C21432C31432C2121EA575
          76C17576C1121EA51432C21432C31432C3121FA67576C1FFFFFFE0E0F12024A1
          6176D71A39C71837C71837C71837C6131FA71420A91837C61837C71837C71B39
          C86579D91F22A0E2E3F2FFFFFF7879C53C44B4657CDB1E40CB1C3ECB1C3ECB1C
          3DCA1C3DCA1C3ECB1C3ECB1F40CC677CDB3A41B37576C4FFFFFFFFFFFFFFFFFF
          7476C73D45B8667FDD2246CF2045CF2045CF2045CF2045CF2347D06981DD3942
          B67779C8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7477C8313EB9264DD3244DD424
          4DD4244DD4244DD4254DD32E3BB7777AC9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF7477CB182BB72953D82954D92954D92954D92954D92953D81829B67477
          CBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7478CC1A2EBB2D5BDC2D5CDD2D5CDD2F
          5EDD2F5DDD2D5CDD2D5CDD2D5BDC192DB97478CCFFFFFFFFFFFFFFFFFF7378CF
          1A31BF3162E13163E23163E23365E27495EA7193EA3364E23163E23163E23162
          E11A2FBE7378CFFFFFFFDCDEF3212CB96F94EC356AE6356AE6376CE67699ED3B
          4AC54050C87397ED376BE6356AE6356AE67397ED1E29B7E2E3F5FFFFFF7378D2
          4858CE7299F03B71EA799DF03C4BC8767BD37378D24353CB769AF03B71EA779C
          F14353CB7378D2FFFFFFFFFFFFFFFFFF7075D34A5BD18DACF33C4CCA757BD5FF
          FFFFFFFFFF7278D44354CD8DACF34354CD7075D3FFFFFFFFFFFFFFFFFFFFFFFF
          FCFCFE757BD6212EC0757BD6FFFFFFFFFFFFFFFFFFFFFFFF757BD6212EC0757B
          D6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFFFFFFFFFFFF}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = suiButton6Click
        ResHandle = 0
      end
      object PPresPulEdCari: TsuiEdit
        Left = 77
        Top = 14
        Width = 543
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 1
        OnKeyPress = PPresPulEdCariKeyPress
      end
      object PPresPulBtnCari: TsuiButton
        Left = 623
        Top = 12
        Width = 39
        Height = 24
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 2
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          AA030000424DAA03000000000000360000002800000011000000110000000100
          18000000000074030000120B0000120B00000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFB6B7B7454545555555E3E3E300FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9B9A9A2426262727270000008181
          8100FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF9899992A2B2B39393930303027272790909000FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9393931818183432322F2E2E
          7575755F5F5FDCDCDC00FFFFFFFFFFFFFFFFFFFFFFFFD7D7D7B1B1B1B1B1B1C6
          C6C6FCFCFCE2E2E20304041F20202828287A7A7A4D4D4DCDCDCDFFFFFF00FFFF
          FFFFFFFFD1D1D18F8F8FAFAFAFDDDEDEE1E1E3C6C7C7A0A0A0B6B6B659595908
          08086D6D6D3B3B3BE1E1E1FFFFFFFFFFFF00FFFFFFD2D2D2929292F1F1F3D5D6
          D7C0BFBCBDBCBACACAC8E8E8E9C4C3C3B0B0B0606060252525CCCCCCFFFFFFFF
          FFFFFFFFFF00F0F0F0AEAEAEE5E6E6BAB9B6CFCBC2E2DCD3E4E0D7DFDAD0B8B6
          B0D6D6D7BEBEBED7D7D7EFEFEFFFFFFFFFFFFFFFFFFFFFFFFF00D7D7D7CBCBCC
          C1C0C0CEC9C0DFDBD3E0DDD7E5E1DBE9E6DFEAE6DDB1B0ABE3E4E5B6B6B6FFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00CCCCCCBDBEBFD0CDCBD3D1CAD2D0CCDAD7D1
          DEDBD6E3E0DBEDEAE5D4D2CBBCBCBCACADADF3F3F3FFFFFFFFFFFFFFFFFFFFFF
          FF00C4C4C4BDBDBED8D8D3F5F3EFD9D8D5CECCC8D1CFCCD9D7D3DFDDD7DBD8D2
          B7B7B6B3B4B5E4E4E4FFFFFFFFFFFFFFFFFFFFFFFF00D0D0D0C1C1C2CAC9C5F5
          F4F3F7F5F3F1F0EEE8E7E4E3E1DEE9E7E4DCDBD7B9B9BAB7B8B8E8E8E8FFFFFF
          FFFFFFFFFFFFFFFFFF00DCDCDCD2D2D2B4B2B4E6E4E2EDECEBF2F2F0F5F3F1F1
          F0EEEBEAE9C1BFBED1D1D0AEAEAEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00EBEB
          EBCDCDCDD8D7D7B4B4B4DDDDDCE4E3E2E5E4E4E3E1E1CBCCCBAEAEAEE0E0E0C3
          C3C3FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFCDCDCDD8D8D8DDDDDDB7B7
          B7C4C4C4C5C4C4BDBEBEBDBDBEEFEFEFAFAFAFF0F0F0FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFF00FFFFFFFFFFFFCCCCCCD5D5D5E1E1E1D6D6D6D5D5D5DBDBDBDFDF
          DFBEBEBEE9E9E9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFEFEFE
          FFFFFFEAEAEADFDFDFD7D7D7D5D5D5D8D8D8E1E1E1F7F7F7FFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        ResHandle = 0
      end
    end
  end
  object PanelCariPresensi: TsuiGroupBox
    Left = 151
    Top = 1000
    Width = 800
    Height = 487
    UIStyle = MacOS
    Caption = ':: Pencarian Presensi Pegawai ::'
    Color = 16711679
    Font.Charset = ANSI_CHARSET
    Font.Color = clGreen
    Font.Height = -11
    Font.Name = 'Tahoma'
    Font.Style = []
    ParentFont = False
    TabOrder = 7
    BorderColor = clMoneyGreen
    object suiDBGrid3: TsuiDBGrid
      Left = 3
      Top = 16
      Width = 794
      Height = 420
      Align = alClient
      BorderStyle = bsNone
      Color = clWhite
      Ctl3D = True
      DataSource = DSPresensiPulang
      FixedColor = clWhite
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clGreen
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      Options = [dgTitles, dgIndicator, dgColumnResize, dgColLines, dgRowLines, dgTabs, dgRowSelect, dgConfirmDelete, dgCancelOnExit]
      ParentCtl3D = False
      ParentFont = False
      ReadOnly = True
      TabOrder = 0
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clGreen
      TitleFont.Height = -11
      TitleFont.Name = 'MS Sans Serif'
      TitleFont.Style = []
      OnCellClick = PRegGridCellClick
      UIStyle = MacOS
      BorderColor = clWhite
      FocusedColor = clWhite
      SelectedColor = 15728623
      FontColor = clGreen
      TitleFontColor = clGreen
      FixedBGColor = clWhite
      BGColor = clWhite
      Columns = <
        item
          Expanded = False
          FieldName = 'id'
          Title.Alignment = taCenter
          Title.Caption = 'NIP'
          Width = 105
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'nama'
          Title.Alignment = taCenter
          Title.Caption = 'Nama'
          Width = 221
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'shift'
          Title.Alignment = taCenter
          Title.Caption = 'Shift'
          Width = 80
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_datang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Datang'
          Width = 130
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'jam_pulang'
          Title.Alignment = taCenter
          Title.Caption = 'Jam Pulang'
          Width = 133
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'status'
          Title.Alignment = taCenter
          Title.Caption = 'Status'
          Width = 104
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'keterlambatan'
          Title.Alignment = taCenter
          Title.Caption = 'Keterlambatan'
          Width = 107
          Visible = True
        end
        item
          Expanded = False
          FieldName = 'durasi'
          Title.Alignment = taCenter
          Title.Caption = 'Durasi'
          Width = 99
          Visible = True
        end>
    end
    object Panel6: TPanel
      Left = 3
      Top = 436
      Width = 794
      Height = 48
      Align = alBottom
      BevelOuter = bvNone
      Color = 15728623
      TabOrder = 1
      object Label8: TLabel
        Left = 201
        Top = 17
        Width = 60
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Key Word :'
      end
      object Label9: TLabel
        Left = 5
        Top = 17
        Width = 44
        Height = 13
        Alignment = taRightJustify
        AutoSize = False
        Caption = 'Tahun :'
      end
      object suiButton2: TsuiButton
        Left = 680
        Top = 11
        Width = 89
        Height = 27
        Caption = '&Tutup'
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 0
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          36030000424D3603000000000000360000002800000010000000100000000100
          1800000000000003000000000000000000000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFE3E3F1FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDDDDEEFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7878BF1114957878BFFFFFFFFF
          FFFFFFFFFFFFFFFF7575BE1115967575BEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          7576BF121CA2112CBF121CA27576BFFFFFFFFFFFFF7576BF121DA2112DBF111D
          A47373BEFFFFFFFFFFFFFFFFFF7879C2131EA61432C21432C31432C2121EA575
          76C17576C1121EA51432C21432C31432C3121FA67576C1FFFFFFE0E0F12024A1
          6176D71A39C71837C71837C71837C6131FA71420A91837C61837C71837C71B39
          C86579D91F22A0E2E3F2FFFFFF7879C53C44B4657CDB1E40CB1C3ECB1C3ECB1C
          3DCA1C3DCA1C3ECB1C3ECB1F40CC677CDB3A41B37576C4FFFFFFFFFFFFFFFFFF
          7476C73D45B8667FDD2246CF2045CF2045CF2045CF2045CF2347D06981DD3942
          B67779C8FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7477C8313EB9264DD3244DD424
          4DD4244DD4244DD4254DD32E3BB7777AC9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF7477CB182BB72953D82954D92954D92954D92954D92953D81829B67477
          CBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7478CC1A2EBB2D5BDC2D5CDD2D5CDD2F
          5EDD2F5DDD2D5CDD2D5CDD2D5BDC192DB97478CCFFFFFFFFFFFFFFFFFF7378CF
          1A31BF3162E13163E23163E23365E27495EA7193EA3364E23163E23163E23162
          E11A2FBE7378CFFFFFFFDCDEF3212CB96F94EC356AE6356AE6376CE67699ED3B
          4AC54050C87397ED376BE6356AE6356AE67397ED1E29B7E2E3F5FFFFFF7378D2
          4858CE7299F03B71EA799DF03C4BC8767BD37378D24353CB769AF03B71EA779C
          F14353CB7378D2FFFFFFFFFFFFFFFFFF7075D34A5BD18DACF33C4CCA757BD5FF
          FFFFFFFFFF7278D44354CD8DACF34354CD7075D3FFFFFFFFFFFFFFFFFFFFFFFF
          FCFCFE757BD6212EC0757BD6FFFFFFFFFFFFFFFFFFFFFFFF757BD6212EC0757B
          D6FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFDCDEF5FFFFFFFFFFFFFFFFFFFFFFFF}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        OnClick = suiButton6Click
        ResHandle = 0
      end
      object PCariEdCari: TsuiEdit
        Left = 264
        Top = 14
        Width = 356
        Height = 20
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        TabOrder = 1
        OnKeyPress = PCariEdCariKeyPress
      end
      object PCariBtnCari: TsuiButton
        Left = 623
        Top = 12
        Width = 39
        Height = 24
        AutoSize = False
        UIStyle = MacOS
        TabOrder = 2
        Transparent = True
        ModalResult = 0
        FocusedRectMargin = 2
        Glyph.Data = {
          AA030000424DAA03000000000000360000002800000011000000110000000100
          18000000000074030000120B0000120B00000000000000000000FFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFB6B7B7454545555555E3E3E300FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9B9A9A2426262727270000008181
          8100FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
          FFFFFF9899992A2B2B39393930303027272790909000FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF9393931818183432322F2E2E
          7575755F5F5FDCDCDC00FFFFFFFFFFFFFFFFFFFFFFFFD7D7D7B1B1B1B1B1B1C6
          C6C6FCFCFCE2E2E20304041F20202828287A7A7A4D4D4DCDCDCDFFFFFF00FFFF
          FFFFFFFFD1D1D18F8F8FAFAFAFDDDEDEE1E1E3C6C7C7A0A0A0B6B6B659595908
          08086D6D6D3B3B3BE1E1E1FFFFFFFFFFFF00FFFFFFD2D2D2929292F1F1F3D5D6
          D7C0BFBCBDBCBACACAC8E8E8E9C4C3C3B0B0B0606060252525CCCCCCFFFFFFFF
          FFFFFFFFFF00F0F0F0AEAEAEE5E6E6BAB9B6CFCBC2E2DCD3E4E0D7DFDAD0B8B6
          B0D6D6D7BEBEBED7D7D7EFEFEFFFFFFFFFFFFFFFFFFFFFFFFF00D7D7D7CBCBCC
          C1C0C0CEC9C0DFDBD3E0DDD7E5E1DBE9E6DFEAE6DDB1B0ABE3E4E5B6B6B6FFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00CCCCCCBDBEBFD0CDCBD3D1CAD2D0CCDAD7D1
          DEDBD6E3E0DBEDEAE5D4D2CBBCBCBCACADADF3F3F3FFFFFFFFFFFFFFFFFFFFFF
          FF00C4C4C4BDBDBED8D8D3F5F3EFD9D8D5CECCC8D1CFCCD9D7D3DFDDD7DBD8D2
          B7B7B6B3B4B5E4E4E4FFFFFFFFFFFFFFFFFFFFFFFF00D0D0D0C1C1C2CAC9C5F5
          F4F3F7F5F3F1F0EEE8E7E4E3E1DEE9E7E4DCDBD7B9B9BAB7B8B8E8E8E8FFFFFF
          FFFFFFFFFFFFFFFFFF00DCDCDCD2D2D2B4B2B4E6E4E2EDECEBF2F2F0F5F3F1F1
          F0EEEBEAE9C1BFBED1D1D0AEAEAEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00EBEB
          EBCDCDCDD8D7D7B4B4B4DDDDDCE4E3E2E5E4E4E3E1E1CBCCCBAEAEAEE0E0E0C3
          C3C3FFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFCDCDCDD8D8D8DDDDDDB7B7
          B7C4C4C4C5C4C4BDBEBEBDBDBEEFEFEFAFAFAFF0F0F0FFFFFFFFFFFFFFFFFFFF
          FFFFFFFFFF00FFFFFFFFFFFFCCCCCCD5D5D5E1E1E1D6D6D6D5D5D5DBDBDBDFDF
          DFBEBEBEE9E9E9FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFEFEFE
          FFFFFFEAEAEADFDFDFD7D7D7D5D5D5D8D8D8E1E1E1F7F7F7FFFFFFFFFFFFFFFF
          FFFFFFFFFFFFFFFFFFFFFFFFFF00}
        Layout = blGlyphLeft
        Spacing = 4
        MouseContinuouslyDownInterval = 100
        ResHandle = 0
      end
      object PCariCMbTahun: TsuiComboBox
        Left = 52
        Top = 14
        Width = 77
        Height = 21
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        ArrowColor = clBlack
        ButtonColor = 15728623
        ItemHeight = 13
        TabOrder = 3
      end
      object PCariCmbBulan: TsuiComboBox
        Left = 132
        Top = 14
        Width = 53
        Height = 21
        UIStyle = DeepBlue
        BorderColor = clMoneyGreen
        ArrowColor = clBlack
        ButtonColor = 15728623
        ItemHeight = 13
        TabOrder = 4
        Items.Strings = (
          '01'
          '02'
          '03'
          '04'
          '05'
          '06'
          '07'
          '08'
          '09'
          '10'
          '11'
          '12')
      end
    end
  end
  object suiSkinEngine1: TsuiSkinEngine
    Active = True
    Ready = True
    SkinFile = 'C:\resumepasien\Skins\Wave\WaveColor1.ssk'
    ResSysMenuMin = 'Mi&nimize'
    ResSysMenuMax = 'Ma&ximize/Restore'
    ResSysMenuClose = '&Close'
    DisabledTag = 9999
    DrawScrollBar = True
    Version = '4.91'
    BuiltIn = False
    SkinDialogs = True
    SkinAllForms = True
    AdditionalBuiltInSkins = <>
    NoChangeButtonsFontColor = False
    NoChangeLabelFontColor = False
    Left = 200
  end
  object ZCon: TZConnection
    Protocol = 'mysql'
    Port = 3306
    AfterConnect = ZConAfterConnect
    Left = 264
  end
  object ZTSetting: TZTable
    Connection = ZCon
    TableName = 'setting'
    Left = 296
    object ZTSettingnama_instansi: TStringField
      FieldName = 'nama_instansi'
      Required = True
      Size = 60
    end
    object ZTSettingalamat_instansi: TStringField
      FieldName = 'alamat_instansi'
      Size = 70
    end
    object ZTSettingkabupaten: TStringField
      FieldName = 'kabupaten'
      Size = 30
    end
    object ZTSettingpropinsi: TStringField
      FieldName = 'propinsi'
      Size = 30
    end
    object ZTSettingkontak: TStringField
      FieldName = 'kontak'
      Required = True
      Size = 50
    end
    object ZTSettingemail: TStringField
      FieldName = 'email'
      Required = True
      Size = 50
    end
    object ZTSettingaktifkan: TStringField
      FieldName = 'aktifkan'
      Required = True
      Size = 3
    end
    object ZTSettingwallpaper: TBlobField
      FieldName = 'wallpaper'
    end
    object ZTSettinglogo: TBlobField
      FieldName = 'logo'
      Required = True
    end
  end
  object DSSetting: TDataSource
    DataSet = ZTSetting
    Left = 424
  end
  object ZQAdmin: TZQuery
    Connection = ZCon
    SQL.Strings = (
      'select * from admin')
    Params = <>
    Left = 328
    object ZQAdminusere: TMemoField
      FieldName = 'usere'
      BlobType = ftMemo
    end
    object ZQAdminpassworde: TMemoField
      FieldName = 'passworde'
      BlobType = ftMemo
    end
  end
  object ZQUser: TZQuery
    Connection = ZCon
    SQL.Strings = (
      'select pasien from user')
    Params = <>
    Left = 360
    object ZQUserpasien: TStringField
      FieldName = 'pasien'
      Required = True
      Size = 5
    end
  end
  object FinFPReg1: TFinFPReg
    AutoConnect = False
    ConnectKind = ckNewInstance
    OnFPRegistrationStatus = FinFPReg1FPRegistrationStatus
    OnFPRegistrationTemplate = FinFPReg1FPRegistrationTemplate
    OnFPSamplesNeeded = FinFPReg1FPSamplesNeeded
    OnFPRegistrationImage = FinFPReg1FPRegistrationImage
    Left = 232
  end
  object ZQSidikJari: TZQuery
    Connection = ZCon
    SQL.Strings = (
      
        'select pasien.no_rkm_medis,pasien.nm_pasien,sidikjaripasien.sidi' +
        'kjari from pasien '
      
        ' left outer join sidikjaripasien on pasien.no_rkm_medis=sidikjar' +
        'ipasien.no_rkm_medis order by pasien.no_rkm_medis')
    Params = <>
    Left = 392
    object ZQSidikJarino_rkm_medis: TStringField
      DisplayWidth = 22
      FieldName = 'no_rkm_medis'
      Required = True
      Size = 15
    end
    object ZQSidikJarinm_pasien: TStringField
      DisplayWidth = 83
      FieldName = 'nm_pasien'
      Size = 40
    end
    object ZQSidikJarisidikjari: TMemoField
      DisplayWidth = 47
      FieldName = 'sidikjari'
      Required = True
      BlobType = ftMemo
    end
  end
  object DSSidikJari: TDataSource
    DataSet = ZQSidikJari
    Left = 456
  end
  object ZQInputSidikJari: TZQuery
    Connection = ZCon
    SQL.Strings = (
      'select * from sidikjaripasien')
    Params = <>
    Left = 488
    object ZQInputSidikJarino_rkm_medis: TStringField
      FieldName = 'no_rkm_medis'
      Required = True
      Size = 15
    end
    object ZQInputSidikJarisidikjari: TMemoField
      FieldName = 'sidikjari'
      Required = True
      BlobType = ftMemo
    end
  end
  object FinFPVer1: TFinFPVer
    AutoConnect = False
    ConnectKind = ckRunningOrNew
    OnFPVerificationStatus = FinFPVer1FPVerificationStatus
    OnFPVerificationID = FinFPVer1FPVerificationID
    OnFPVerificationImage = FinFPVer1FPVerificationImage
    Left = 168
  end
  object ZQPasien: TZQuery
    Connection = ZCon
    SQL.Strings = (
      'select * from pasien')
    Params = <>
    Left = 520
    Top = 2
    object ZQPasienno_rkm_medis: TStringField
      FieldName = 'no_rkm_medis'
      Required = True
      Size = 15
    end
    object ZQPasiennm_pasien: TStringField
      FieldName = 'nm_pasien'
      Size = 40
    end
    object ZQPasienno_ktp: TStringField
      FieldName = 'no_ktp'
    end
    object ZQPasienjk: TStringField
      FieldName = 'jk'
      Size = 1
    end
    object ZQPasientmp_lahir: TStringField
      FieldName = 'tmp_lahir'
      Size = 15
    end
    object ZQPasientgl_lahir: TDateField
      FieldName = 'tgl_lahir'
    end
    object ZQPasiennm_ibu: TStringField
      FieldName = 'nm_ibu'
      Required = True
      Size = 40
    end
    object ZQPasienalamat: TStringField
      FieldName = 'alamat'
      Size = 200
    end
    object ZQPasiengol_darah: TStringField
      FieldName = 'gol_darah'
      Size = 2
    end
    object ZQPasienpekerjaan: TStringField
      FieldName = 'pekerjaan'
      Size = 15
    end
    object ZQPasienstts_nikah: TStringField
      FieldName = 'stts_nikah'
      Size = 7
    end
    object ZQPasienagama: TStringField
      FieldName = 'agama'
      Size = 12
    end
    object ZQPasientgl_daftar: TDateField
      FieldName = 'tgl_daftar'
    end
    object ZQPasienno_tlp: TStringField
      FieldName = 'no_tlp'
      Size = 13
    end
    object ZQPasienumur: TStringField
      FieldName = 'umur'
      Required = True
    end
    object ZQPasienpnd: TStringField
      FieldName = 'pnd'
      Required = True
      Size = 3
    end
    object ZQPasienkeluarga: TStringField
      FieldName = 'keluarga'
      Required = True
      Size = 7
    end
    object ZQPasiennamakeluarga: TStringField
      FieldName = 'namakeluarga'
      Required = True
      Size = 50
    end
    object ZQPasienkd_pj: TStringField
      FieldName = 'kd_pj'
      Required = True
      Size = 3
    end
    object ZQPasienno_peserta: TStringField
      FieldName = 'no_peserta'
      Size = 25
    end
    object ZQPasienkd_kel: TIntegerField
      FieldName = 'kd_kel'
      Required = True
    end
    object ZQPasienkd_kec: TIntegerField
      FieldName = 'kd_kec'
      Required = True
    end
    object ZQPasienkd_kab: TIntegerField
      FieldName = 'kd_kab'
      Required = True
    end
    object ZQPasienpekerjaanpj: TStringField
      FieldName = 'pekerjaanpj'
      Required = True
      Size = 15
    end
    object ZQPasienalamatpj: TStringField
      FieldName = 'alamatpj'
      Required = True
      Size = 100
    end
    object ZQPasienkelurahanpj: TStringField
      FieldName = 'kelurahanpj'
      Required = True
      Size = 60
    end
    object ZQPasienkecamatanpj: TStringField
      FieldName = 'kecamatanpj'
      Required = True
      Size = 60
    end
    object ZQPasienkabupatenpj: TStringField
      FieldName = 'kabupatenpj'
      Required = True
      Size = 60
    end
  end
  object ZQExec: TZQuery
    Connection = ZCon
    Params = <>
    Left = 680
    Top = 2
  end
  object DSPresensidatang: TDataSource
    Left = 776
  end
  object DSPresensiPulang: TDataSource
    Left = 840
    Top = 8
  end
end
