/*
  by Ananda Widitomo,S.Kom.
  IT - SIMRS Hj. Fatimah Sulhan
 */
package bridging;

/**
 * Memilih perender QR yang tersedia. Bila kelas {@code satusehattte.QrRendererZxing} (implementasi
 * berbasis ZXing) ADA di classpath, dipakai; jika tidak, jatuh ke {@link QrRendererFallback}.
 * Dengan begini menambah ZXing cukup: (1) taruh jar zxing core+javase di ~/lib & daftarkan di
 * nbproject/project.properties, (2) tambahkan file QrRendererZxing.java — tanpa mengubah dialog.
 */
public final class QrRendererFactory {

    private QrRendererFactory() { }

    public static QrRenderer buat() {
        try {
            Class<?> c = Class.forName("satusehattte.QrRendererZxing");
            Object o = c.getDeclaredConstructor().newInstance();
            if (o instanceof QrRenderer) {
                return (QrRenderer) o;
            }
        } catch (Throwable t) {
            // ZXing/QrRendererZxing belum ada -> pakai fallback.
        }
        return new QrRendererFallback();
    }
}
