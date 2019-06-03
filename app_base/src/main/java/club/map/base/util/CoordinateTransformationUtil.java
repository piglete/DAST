package club.map.base.util;

/**
 * Created with IntelliJ IDEA.
 * User: zhaojinxiong
 * Date: 2019-04-08
 * Time: 14:48
 * Description: 城建坐标和84坐标互转
 */
public class CoordinateTransformationUtil {

    //中央经度
    private static final double _l0 = 108;

    // 东纬偏移FE = 500000米
    private static final double _FE = 500000;

    // 长半轴 a（米）
    private static final double _a = 6378245;

    // 扁率α
    private static final double _af = 1 / 298.3;

    /**
     * WGS84转CJ(城建)坐标
     *
     * @param JWD
     */
    public static String TranspantWGS842XACJ(String JWD) {
        double dx = -3778477.897641;
        double dy = -609200.217872;
        double roation = 0.5231426094;//度数 需要转为弧度
        double k = 0.999952152407882;
        double coor_x = 0.0, coor_y = 0.0;
        String[] str = JWD.split(",");
        double jd = Double.parseDouble(str[0]);
        double wd = Double.parseDouble(str[1]);
        String coorStr = CoordinateTransformationUtil.WGS84ToBJ54(jd, wd, coor_x, coor_y);
        double c = k * Math.cos(roation * 3.141592653589793238462 / 180);
        double d = k * Math.sin(roation * 3.141592653589793238462 / 180);
        String[] coor = coorStr.split(",");
        coor_x = Double.valueOf(coor[0]);
        coor_y = Double.valueOf(coor[1]);
        double X = dx + (c * coor_x - d * coor_y);
        double Y = dy + (d * coor_x + c * coor_y);
        String cj = String.valueOf(X) + "," + String.valueOf(Y);
        return cj;
    }

    /**
     * CJ(城建)转WGS84坐标
     *
     * @param XY
     */
    public static String TranspantXACJ2WGS84(String XY) {
        double dx = 3784063.71907706;
        double dy = 574703.179022006;
        double roation = -0.5231426094;//度数 需要转为弧度
        double k = 1.00004784988162;
        double coor_x = 0.0, coor_y = 0.0;
        String[] str = XY.split(",");
        coor_x = Double.parseDouble(str[0]);
        coor_y = Double.parseDouble(str[1]);

        double jd = 0;
        double wd = 0;

        double c = k * Math.cos(roation * 3.141592653589793238462 / 180);
        double d = k * Math.sin(roation * 3.141592653589793238462 / 180);
        double X = dx + (c * coor_x - d * coor_y);
        double Y = dy + (d * coor_x + c * coor_y);

        String coorStr = CoordinateTransformationUtil.BJ54ToWGS84(X, Y, jd, wd);
        return coorStr;
    }


    /**
     * WGS84坐标转为北京54坐标
     *
     * @param dmslon
     * @param dmslat
     * @param coor_x
     * @param coor_y
     * @return
     */
    public static String WGS84ToBJ54(double dmslon, double dmslat, double coor_x, double coor_y) {
        //int h = 0, k = 0;
        double a = 0, Alfa = 0;
        double dmsl0 = 0;
        double a0, a2, a4, a6, a8;
        double radlat, radlon, radl0, l;
        double b, t, sb, cb, ita, e1, ee;
        double X, l0;
        double N, c, v;
        double[] Coeficient_a0 = new double[5];

        a = _a; //长半轴 a（米）
        Alfa = _af;
        dmsl0 = _l0;

        /*将角度转化为弧度*/
        radlat = CoordinateTransformationUtil.DMSTORAD(dmslat);
        radlon = CoordinateTransformationUtil.DMSTORAD(dmslon);
        radl0 = CoordinateTransformationUtil.DMSTORAD(dmsl0);
        l = radlon - radl0;

        /*计算椭球的基本参数和中间变量*/

        b = a * (1 - Alfa);
        sb = Math.sin(radlat);
        cb = Math.cos(radlat);
        t = sb / cb;
        e1 = Math.sqrt((a / b) * (a / b) - 1);
        ee = Math.sqrt(1 - (b / a) * (b / a));
        ita = e1 * cb;

        /*计算a0 a2 a4 a6 a8*/
        CoordinateTransformationUtil.a0a2a4a6a8(a, ee, Coeficient_a0);

        a0 = Coeficient_a0[0];
        a2 = Coeficient_a0[1];
        a4 = Coeficient_a0[2];
        a6 = Coeficient_a0[3];
        a8 = Coeficient_a0[4];

        /*计算X*/
        X = a0 * radlat - sb * cb * ((a2 - a4 + a6) + (2 * a4 - 16 * a6 / 3) * sb * sb + 16 * a6 * sb * sb * sb * sb / 3.0);

        /*计算卯酉圈半径N*/

        c = a * a / b;
        v = Math.sqrt(1 + e1 * e1 * cb * cb);
        N = c / v;

        /*计算未知点的坐标*/
        coor_x = X + N * sb * cb * l * l / 2 + N * sb * cb * cb * cb * (5 - t * t + 9 * ita * ita + 4 * ita * ita * ita * ita) * l * l * l * l / 24 + N * sb * cb * cb * cb * cb * cb * (61 - 58 * t * t + t * t * t * t) * l * l * l * l * l * l / 720;
        coor_y = N * cb * l + N * cb * cb * cb * (1 - t * t + ita * ita) * l * l * l / 6 + N * cb * cb * cb * cb * cb * (5 - 18 * t * t + t * t * t * t + 14 * ita * ita - 58 * ita * ita * t * t) * l * l * l * l * l / 120;

        //东纬偏移FE = 500000米 + 带号 * 1000000；
        coor_y += _FE;
        String str = String.valueOf(coor_x) + "," + String.valueOf(coor_y);
        return str;
    }

    /**
     * 北京54坐标转为WGS84坐标
     *
     * @param coor_x
     * @param coor_y
     * @param dmslon
     * @param dmslat
     * @return
     */
    public static String BJ54ToWGS84(double coor_x, double coor_y, double dmslon, double dmslat) {
        //int h = 0, k = 0;
        double a = 0, Alfa = 0;
        double a0, a2, a4, a6, a8;
        //double radlat, radlon, radl0, l;
        double b, t, sb, cb, ita, e1, ee;
        double X, l0;
        double N, c, v;
        double Bf0, Bf1, dB, FBf, bf;
        double itaf, tf;
        double Nf, Mf;
        double B, L, dietaB, dietal;
        double sinBf, cosBf;
        double[] Coeficient_a0 = new double[5];

        l0 = _l0 * Math.PI / 180.0;
        a = _a; //长半轴 a（米）
        Alfa = _af; //扁率

        //东纬偏移FE = 500000米 + 带号 * 1000000；
        coor_y -= _FE;

        /*计算b,e1,e*/
        b = a * (1 - Alfa);
        e1 = Math.sqrt((a / b) * (a / b) - 1);
        ee = Math.sqrt(1 - (b / a) * (b / a));

        /*计算a0 a2 a4 a6 a8*/
        CoordinateTransformationUtil.a0a2a4a6a8(a, ee, Coeficient_a0);

        a0 = Coeficient_a0[0];
        a2 = Coeficient_a0[1];
        a4 = Coeficient_a0[2];
        a6 = Coeficient_a0[3];
        a8 = Coeficient_a0[4];

        X = coor_x;
        Bf0 = X / a0;

        do {
            sinBf = Math.sin(Bf0);
            cosBf = Math.cos(Bf0);
            FBf = -sinBf * cosBf * ((a2 - a4 + a6) + (2.0 * a4 - 16.0 * a6 / 3.0) * sinBf * sinBf +
                    (16.0 / 3.0) * a6 * (sinBf * sinBf * sinBf * sinBf));
            Bf1 = (X - FBf) / a0;
            dB = Bf1 - Bf0;
            Bf0 = Bf1;
        } while (Math.abs(dB * 180.0 / Math.PI * 3600.0) > 0.00001);

        bf = Bf1;
        /*计算c,v,N,M*/
        c = a * a / b;
        v = Math.sqrt(1 + e1 * e1 * Math.cos(bf) * Math.cos(bf));
        Nf = c / v;
        Mf = c / (v * v * v);
        tf = Math.sin(bf) / Math.cos(bf);
        itaf = e1 * Math.cos(bf);

        /*计算dietaB,dietal*/
        dietaB = tf * coor_y * coor_y / (2 * Mf * Nf) - tf * (5 + 3 * tf * tf + itaf * itaf - 9 * tf * tf * itaf * itaf) * coor_y * coor_y * coor_y * coor_y / (24 * Mf * Nf * Nf * Nf) + (61 + 90 * tf * tf + 45 * tf * tf * tf * tf) * coor_y * coor_y * coor_y * coor_y * coor_y * coor_y / (720 * Mf * Nf * Nf * Nf * Nf * Nf);
        dietal = coor_y / (Nf * Math.cos(bf) + (1 + 2 * tf * tf + itaf * itaf) * Math.cos(bf) * coor_y * coor_y / (6 * Nf)) + (5 + 44 * tf * tf + 32 * tf * tf * tf * tf - 2 * itaf * itaf - 16 * itaf * itaf * tf * tf) / (360 * Nf * Nf * Nf * Mf * Mf * Math.cos(bf));
        B = bf - dietaB;
        L = l0 + dietal;
        dmslat = CoordinateTransformationUtil.RADTODMS(B);
        dmslon = CoordinateTransformationUtil.RADTODMS(L);
        String dms = String.valueOf(dmslon) + "," + String.valueOf(dmslat);
        return dms;
    }


    private static double DMSTORAD(double dmsAngle1) {
        int intSignOfDms = 1;
        if (dmsAngle1 < 0) {
            intSignOfDms = -1;
        }
        dmsAngle1 = Math.abs(dmsAngle1);
        double radAngle = dmsAngle1 * Math.PI / 180.0;
        radAngle = radAngle * intSignOfDms;
        return radAngle;
    }

    private static double RADTODMS(double radAngle) {
        int intSignOfRad = 1;
        if (radAngle < 0) intSignOfRad = -1;
        radAngle = Math.abs(radAngle);

        double dmsAngle = radAngle * 180 / Math.PI;
        dmsAngle = dmsAngle * intSignOfRad;
        return dmsAngle;
    }


    private static void a0a2a4a6a8(double a, double e, double[] Coeficient_a0) {
        double m0, m2, m4, m6, m8;
        m0 = a * (1 - e * e);
        m2 = 3 * e * e * m0 / 2;
        m4 = 5 * e * e * m2 / 4;
        m6 = 7 * e * e * m4 / 6;
        m8 = 9 * e * e * m6 / 8;

        /*计算a0 a2 a4 a6 a8*/

        Coeficient_a0[0] = m0 + m2 / 2 + 3 * m4 / 8 + 5 * m6 / 16 + 35 * m8 / 128;
        Coeficient_a0[1] = m2 / 2 + m4 / 2 + 15 * m6 / 32 + 7 * m8 / 16;
        Coeficient_a0[2] = m4 / 8 + 3 * m6 / 16 + 7 * m8 / 32;
        Coeficient_a0[3] = m6 / 32 + m8 / 16;
        Coeficient_a0[4] = m8 / 128;
    }

}
